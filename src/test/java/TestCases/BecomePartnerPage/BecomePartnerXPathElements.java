package TestCases.BecomePartnerPage;

import TestCases.BrandPage.BrandXPathElements;
import crisscrosscrass.countries;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BecomePartnerXPathElements {
    final static Logger logger = Logger.getLogger(BecomePartnerXPathElements.class);
    private static ChromeDriver driver;
    private static Properties Homepage;
    private static String countrieSelection = "AT";
    private static String locator;
    WebElement element;

    @BeforeClass
    public static void openBrowser(){
        String resourceName = "configs/page.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Homepage = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            Homepage.load(resourceStream);
        }catch (Exception nope){
            nope.getStackTrace();
        }
        ChromeOptions option = new ChromeOptions();
        //option.addArguments("--disable-infobars");
        //option.addArguments("--disable-notifications");
        //option.addArguments("--start-maximized");
        //option.addArguments("--headless");
        System.setProperty("webdriver.chrome.driver", "temp//chromedriver.exe");
        driver = new ChromeDriver(option);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    @Test
    public void checkBecomePartnerPageTabStartH3(){
        locator = "partnerpage.info.h3";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationBecomePartnerPageURL()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkBecomePartnerPageTabHelpH3(){
        locator = "partnerpage.help.h3";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationBecomePartnerPageURL()));
        //click on Tab Help
        driver.findElementByXPath(Homepage.getProperty("partnerpage.tab.help")).click();
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkBecomePartnerPageTabPartnerH3(){
        locator = "partnerpage.partner.h3";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationBecomePartnerPageURL()));
        //click on Tab Partner
        driver.findElementByXPath(Homepage.getProperty("partnerpage.tab.partner")).click();
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkBecomePartnerPageTabStartBecomePartnerButton(){
        locator = "partnerpage.shops.register.banner.button";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationBecomePartnerPageURL()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkBecomePartnerPageTabHelpBecomePartnerButton(){
        locator = "partnerpage.tab.help.becomePartner.button";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationBecomePartnerPageURL()));
        driver.findElementByXPath(Homepage.getProperty("partnerpage.tab.help")).click();
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkBecomePartnerTabPartnerBecomePartnerButton(){
        locator = "partnerpage.tab.partner.becomePartner.button";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationBecomePartnerPageURL()));
        driver.findElementByXPath(Homepage.getProperty("partnerpage.tab.partner")).click();
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @AfterClass
    public static void closeBrowser(){
        try {
            driver.close();
        }catch (Exception driverClosing){
            driverClosing.printStackTrace();
        }
        try {
            driver.quit();
        }catch (Exception driverQuit){
            driverQuit.printStackTrace();
        }
    }

}
