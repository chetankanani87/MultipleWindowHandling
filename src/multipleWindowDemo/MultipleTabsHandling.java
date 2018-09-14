package multipleWindowDemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import lib.BrowserDriverUtility;
import lib.EmailWithAttachmentUtility;
import lib.ExtentReportUtility;
import lib.ScreenshotUtility;

public class MultipleTabsHandling {
	WebDriver dr;
	ExtentReports report = ExtentReportUtility.InvokeExtentReport();
	ExtentTest logger = report.createTest("File Upload Test");
	WebElement ele;
	String path1, path2, path3, path4;

	@BeforeTest
	public void InvokeBrowser() {
		try {
			dr = BrowserDriverUtility.InvokeBrowser("webdriver.chrome.driver",
					"C:\\Chetan\\SeleniumSuite\\WebDrivers\\chromedriver.exe", "http://seleniumpractise.blogspot.com/2017/07/");
			path1 = ScreenshotUtility.CaptureScreenshot(dr, "1_MainPage");
			logger.pass("Main Page - Screenshot taken.", MediaEntityBuilder.createScreenCaptureFromPath(path1).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void IFrameHandle() {
		// Handling Tab or Window
		try {
			//String parent = dr.getWindowHandle();

			List<WebElement> list = dr.findElements(By.xpath("//a[@name='link1']"));
			for (WebElement ele : list) {
				ele.click();
			}
			
			Set<String> allWindows = dr.getWindowHandles();
			System.out.println("Total Windows are: " + allWindows.size());
			
			ArrayList<String> tabs = new ArrayList<>(allWindows);
			int i =2;
			for(String str : tabs) {
				dr.switchTo().window(str);
				System.out.println("Title of page is: " + dr.getTitle());
				path1 = ScreenshotUtility.CaptureScreenshot(dr, i+"_TabWindow");
				logger.pass(i+"_Tab Window Page - Screenshot taken.", MediaEntityBuilder.createScreenCaptureFromPath(path1).build());
				i++;
			}
			
			//to search something in google
			dr.switchTo().window(tabs.get(3));
			dr.findElement(By.name("q")).sendKeys("Selenium is awsome... isn't it?");;
			dr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void tearDown() {
		try {
			EmailWithAttachmentUtility.SendEmail(
					"Test Case for Multiple Window Handling is Passed - Screenshot files are uploaded successfully...!!!",
					"Congratulations...Bro!!!", path1, "Screenshot of Main page which is working fine...!!!");
			report.flush();
			Thread.sleep(1000);
			dr.quit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
