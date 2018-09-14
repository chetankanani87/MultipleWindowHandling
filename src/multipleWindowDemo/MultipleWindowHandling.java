package multipleWindowDemo;

import java.io.IOException;
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
import lib.EmailWithAttachmentUtility2;
import lib.ExtentReportUtility;
import lib.ScreenshotUtility;

public class MultipleWindowHandling {
	WebDriver dr = BrowserDriverUtility.InvokeBrowser("webdriver.chrome.driver",
			"C:\\Chetan\\SeleniumSuite\\WebDrivers\\chromedriver.exe",
			"http://www.naukri.com");
	ExtentReports report = ExtentReportUtility.InvokeExtentReport();
	ExtentTest logger = report.createTest("File Upload Test");
	WebElement ele;
	String path1, path2, path3, path4;

	@BeforeTest
	public void InvokeBrowser() {
		try {
			path1 = ScreenshotUtility.CaptureScreenshot(dr, "1_MainPage");
			logger.pass("Main Page - Screenshot taken.", MediaEntityBuilder.createScreenCaptureFromPath(path1).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void IFrameHandle() {
		//Handling Tab or Window
		try {
			String parent = dr.getWindowHandle();
			Set<String> allWindows = dr.getWindowHandles();
			System.out.println("Total Windows are: " + allWindows.size());
			
			Thread.sleep(5000);
			
			for(String child : allWindows) {
				if(!parent.equalsIgnoreCase(child)) {
					dr.switchTo().window(child);
					System.out.println("Child Window Title is: " + dr.getTitle());
					Thread.sleep(2000);
					dr.close();
				}
			}
			dr.switchTo().window(parent);
			
			dr.findElement(By.xpath("//input[@value='Register with us']")).click();
			path2 = ScreenshotUtility.CaptureScreenshot(dr, "2_RegisterWithUsPage");
			logger.pass("Register With Us Page - Screenshot taken.", MediaEntityBuilder.createScreenCaptureFromPath(path2).build());


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void tearDown() {
		try {
			EmailWithAttachmentUtility2.SendEmail(
					"Test Case for Multiple Window Handling is Passed - Screenshot files are uploaded successfully...!!!",
					"Congratulations...Bro!!!", path1, "Screenshot of Main page which is working fine...!!!", path2,
					"Screenshot of -Register With Us- page which is working fine...!!!");
			report.flush();
			Thread.sleep(1000);
			dr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
