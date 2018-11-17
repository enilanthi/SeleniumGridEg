package com.qa.selenium.test.auto;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PracticeTest {

    private static final Logger log = Logger.getLogger(PracticeTest.class);

    WebDriver driver;

    @BeforeClass
    public void initateDriver(){
        URL driverPath = this.getClass().getClassLoader().getResource("drivers\\geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", driverPath.getPath());
        driver = new FirefoxDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.ebay.com/");
    }

    @Test
    public void searchItemEbay(){
        driver.findElement(By.id("gh-ac")).sendKeys("Samsung s9");
        Select cmb_category = new Select(driver.findElement(By.xpath("//div[@id='gh-cat-box']/select")));
        log.info("Debug no of catogries: " + cmb_category.getOptions().size());
        for (WebElement element: cmb_category.getOptions()) {
            log.info(element.getText());
        }
        cmb_category.selectByVisibleText("Cell Phones & Accessories");
//        cmb_category.selectByValue("Cell Phones & Accessories");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        log.info("Verify result page title");
        Assert.assertEquals(driver.getTitle(), "Samsung s9 in Cell Phones and Accessories | eBay",
                "Expected title not found");

        WebElement cmb_bestMatch = driver.findElement(By.linkText("Best Match"));
        Actions actions = new Actions(driver);
        actions.moveToElement(cmb_bestMatch).click().build().perform();

        actions.moveToElement(driver.findElement(By.linkText("Price + Shipping: lowest first"))).
                click().build().perform();

//         TODO: wait until page load

        Assert.assertEquals(
                driver.findElement(By.xpath("//div[@class='sort-menu-container']/a")).getText(),
                "Price + Shipping: lowest first");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
