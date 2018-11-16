package com.qa.auto;

import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PracticeTest2 {

    WebDriver driver;

    @BeforeClass
    public void initiazlie() throws MalformedURLException {
        URL driverPath = this.getClass().getClassLoader().getResource("drivers\\geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", driverPath.getPath());

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setBrowserName("firefox");
        capabilities.setPlatform(Platform.WINDOWS);

        driver = new RemoteWebDriver(new URL("http://CSLK-AFTDB-82-3:5566/wd/hub"), capabilities);
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("http://newtours.demoaut.com/index.php");
    }

    @Test(priority = 1)
    public void registerNewUser(){
        driver.findElement(By.linkText("REGISTER")).click();
        driver.findElement(By.name("firstName")).sendKeys("Nilanthi");
        driver.findElement((By.name("lastName"))).sendKeys("Eddie");
        Select countryCmb = new Select(driver.findElement(By.name("country")));
        countryCmb.selectByVisibleText("UNITED KINGDOM");

        driver.findElement(By.id("email")).sendKeys("NilaE");
        driver.findElement(By.name("password")).sendKeys("cambio12345");
        driver.findElement(By.name("confirmPassword")).sendKeys("cambio12345");
        driver.findElement(By.xpath("//input[@type='image' and @name='register']")).click();

        driver.findElement(By.xpath("//*[contains(text(),'Dear')]")).isDisplayed();
        driver.findElement(By.xpath("//*[contains(text(), \"Thank you for registering\")]")).isDisplayed();
        driver.findElement(By.xpath("//b[contains(text(), \"Note: Your user name is NilaE.\")]")).isDisplayed();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 2)
    public void findFlights(){
        driver.findElement(By.xpath("//td/img[contains(@src, 'mast_flightfinder.gif')]")).isDisplayed();
        driver.findElement(By.xpath("//input[@value='oneway' and @type='radio']")).click();

//        RETURN flight table
        //*[contains(text(), "RETURN")]//following::font[text()="SELECT"]
    }

    @AfterClass
    public void teardown(){
        driver.quit();
    }
}
