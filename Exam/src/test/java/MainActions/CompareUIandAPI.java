package MainActions;
import com.microsoft.playwright.*;

import ObjectRepository.Objectrepository;
import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CompareUIandAPI extends BaseTest {
	private static final String API_URL = "https://api-prod.prod.cms.df.services.vodafone.com.au/plan/postpaid-simo?serviceType=New";
	private static final String FALLBACK_WORD = "Add to Cart";
	Objectrepository pageobj = new Objectrepository();
	
	@Test
	public void testButtonLabelsMatchAPI() throws IOException, InterruptedException {
		page.navigate("https://www.vodafone.com.au/plans/sim-only");
        
		//LIST OF BUTTON LABELS
		List<String> buttonlabels = Arrays.asList(pageobj.Addtocart, pageobj.Addtocart2,pageobj.Addtocart3);
		
		
        // SHOWS BUTTON LABEL VALUES AND SELECTOR
		String expectedApiWord = getExpectedCtaLabelFromApi();
		for (int x = 0; x < buttonlabels.size(); x++) {
			String currentlabel = buttonlabels.get(x);
			Locator currentLocator = page.locator(currentlabel);
			String ButtonTextContent = currentLocator.textContent().trim();
			System.out.printf("Add to Cart Button %d: (%s) Text label: '%s'\n", x + 1, currentlabel, ButtonTextContent);
			boolean isMatch = ButtonTextContent.toLowerCase().contains(expectedApiWord.toLowerCase());
			Assert.assertTrue(isMatch, String.format(
					"Validation Failed for Button %d: UI button text '%s' does not contain the expected API word '%s'.",
					x + 1, ButtonTextContent, expectedApiWord));
		}
		System.out.println("Validation Passed: All 3 buttons contain the expected word");
	}

	
	//GET REQUEST FOR API
	private String getExpectedCtaLabelFromApi() throws IOException, InterruptedException {
		 HttpClient client = HttpClient.newHttpClient();
	        ObjectMapper mapper = new ObjectMapper();
	        HttpRequest request = HttpRequest.newBuilder()                
	                .uri(URI.create(API_URL)) 
	                .header("Accept", "application/json")
	                .build();
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() != 200) {
	            System.err.println("API call failed with status code: " + response.statusCode() + ". Using fallback word: " + FALLBACK_WORD);
	            return FALLBACK_WORD;
	        }	        
	        String body = response.body();
	        try {
	            JsonNode rootNode = mapper.readTree(body);
	            JsonNode plansArray = rootNode.path("responsePayload").path("items");
	            if (plansArray.isArray() && plansArray.size() > 0) {
	                JsonNode firstPlan = plansArray.get(0);  
	                String ctaLabel = firstPlan.path("planDetails").path("buyNowCtaLabel").asText(); 	                
	                if (ctaLabel != null && !ctaLabel.isBlank()) {
	                    return ctaLabel.trim();
	                }
	            }
	            System.err.println("Could not find the expected CTA path in API response. Using fallback word: " + FALLBACK_WORD);
	            return FALLBACK_WORD;
	        } catch (Exception e) {
	            System.err.println("Error during JSON parsing: " + e.getMessage() + ". Using fallback word: " + FALLBACK_WORD);
	            return FALLBACK_WORD;
	        }
	    }

}
