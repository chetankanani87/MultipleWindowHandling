package lib;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HighlightUtility {
	public static void highLightElement(WebDriver dr, WebElement ele) {
		
		JavascriptExecutor jse = (JavascriptExecutor) dr;
		jse.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');", ele);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		
		jse.executeScript("arguments[0].setAttribute('style', 'border: 2px solid white')", ele);
	
	}
}
