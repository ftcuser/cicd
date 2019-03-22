package com.kodcu;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;



public class WebIntegrationTest {

	private String Seleniumhub = "http://ec2-34-232-13-73.compute-1.amazonaws.com:4444/wd/hub";
	private String baseUrl =  "http://ec2-34-232-13-73.compute-1.amazonaws.com:8090";
	private static WebDriver driver;
	private WebDriverWait driverWait;
	private String actualTitle;
		
	
@Before
public void openBrowser() {
	System.out.println("* * * Test Started! * * *");

String hub = System.getProperty("Selenium.hub");
if(hub == null) {
hub = Seleniumhub;
}

String base = System.getProperty("app.baseurl");
if(base == null) {
base = baseUrl;
}
System.out.println("Selenium HUB : " + hub);
System.out.println("The app URL : " + base);

URL hubUrl = null;
try{
	
	System.out.println("Hello");
hubUrl = new URL(hub);
}catch(Exception e){

}

Capabilities cap = DesiredCapabilities.firefox();
System.out.println("Hello1");
driver = new RemoteWebDriver(hubUrl, cap);
System.out.println("Hello2");
driverWait = new WebDriverWait(driver, 30);
System.out.println("Hello3");
driver.get(base);
// screenshotHelper = new ScreenshotHelper();
}

@After
public void saveScreenshotAndCloseBrowser() throws IOException {
//screenshotHelper.saveScreenshot("screenshot.png");
driver.quit();
}
	
@Test
public void webIntegrationTest() throws Exception {
String actualTitle = driver.getTitle();
String expectedTitle = "orders Application";
assertEquals(expectedTitle,actualTitle);
System.out.println("ActualTitle is " + actualTitle );

}
}
