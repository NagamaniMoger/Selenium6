package com.herokuapp.theinternet;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExceptionsTests {

	private WebDriver driver;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setUp(String browser) {

//	     Create Driver
		switch (browser) {
		case "chrome":
			driver = new ChromeDriver();
			System.out.println("chrome browser started");
			break;

		case "firefox":
			driver = new FirefoxDriver();
			System.out.println("firefox browser started");
			break;

		default:
			System.out.println("Do not know how to start" + browser + "starting chrome instead");
			driver = new ChromeDriver();
			break;
		}

		driver.manage().window().maximize();

	}

	@Test
	public void elementNotInteractableExceptionTest() {

		// open webpage
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		System.out.println("page opened");

		// click on Add button
		WebElement addButton = driver.findElement(By.id("add_btn"));
		addButton.click();

		System.out.println("clicked on button");

		// Explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement row2Input = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

		// Verify row2 input field displayed
		Assert.assertTrue(row2Input.isDisplayed(), "row2 is not displayed");
		
		//Type text into the second input field
		row2Input.sendKeys("pasta");
		
		//Push Save button using locator By.name(“Save”)
		WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']/button[@name='Save']"));
		saveButton.click();
		
		//Verify text saved
		WebElement confirmationMessage = driver.findElement(By.id("confirmation"));
		String actualMessage = confirmationMessage.getText();
		Assert.assertEquals(actualMessage, "Row 2 was saved", "Actual message does not contain the expected message");
	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
//      close browser
		driver.quit();
		System.out.println("Test Finished");
	}

}
