package MainActions;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;

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


public class AssertValues extends BaseTest{
	
	@Test
	public void AssertValues() {
		Objectrepository pageobj = new Objectrepository();
		
		page.navigate("https://www.vodafone.com.au/plans/sim-only");
		if(page.locator(pageobj.AddtoCart).isVisible()) {
			page.locator(pageobj.AddtoCart).scrollIntoViewIfNeeded();
			page.locator(pageobj.AddtoCart).click();
		}
		page.waitForTimeout(5000);
		page.locator("//div//text[contains(@data-testid, 'sticky-cart-cost')]").scrollIntoViewIfNeeded();
		String stickycartcostcompare = page.locator(pageobj.StickyCartprice).textContent();
		System.out.println("StickyCartCost: " + stickycartcostcompare);
		page.waitForTimeout(5000);
		page.locator(pageobj.ContinuetoCart).click();
		String plancostcompare = page.locator(pageobj.PlanPrice).textContent();
		System.out.println("plancost: " + plancostcompare );
		
		Assert.assertEquals(stickycartcostcompare, plancostcompare);
		System.out.print("Costings are similiar from Sticky Cart: ");
	}

}
