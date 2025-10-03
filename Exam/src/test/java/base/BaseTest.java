package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BaseTest {
	protected Playwright playwright;
	protected Browser browser;
	protected Page page;
	protected Download download;
	
	@BeforeMethod
	public void setUp() {
		playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(
				new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
		BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
         page = context.newPage();
	}
	
	@AfterMethod
    public void tearDown() {
    	if(browser != null) browser.close();
    	if(playwright!= null) playwright.close();
	
    }
}
