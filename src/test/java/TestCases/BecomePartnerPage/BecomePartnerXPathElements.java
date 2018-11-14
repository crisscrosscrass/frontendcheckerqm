package TestCases.BecomePartnerPage;

import crisscrosscrass.countries;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
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
    public void checkBecomePartnerPageRegisterButton(){
        locator = "partnerpage.shops.register.button";
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
    public void checkBecomePartnerPageTabHelp(){
        locator = "partnerpage.tab.help";
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
    public void checkBecomePartnerPageTabPartner(){
        locator = "partnerpage.tab.partner";
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
        public void checkBecomePartnerPageTabHelpContent(){
            locator = "partnerpage.tab.help.content";
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
    public void checkBecomePartnerPageTabPartnerContent(){
        locator = "partnerpage.tab.partner.content";
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

    @Test
    public void checkBecomePartnerPageTabPartnerFeed(){
        locator = "partnerpage.tab.partner.feedproviders";
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
    public void checkBecomePartnerPageTabHelpBecomePartnerClose(){
        locator = "partnerpage.shops.becomePartner.close";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationBecomePartnerPageURL()));
        driver.findElementByXPath(Homepage.getProperty("partnerpage.tab.help")).click();
        driver.findElementByXPath(Homepage.getProperty("partnerpage.tab.help.becomePartner.button")).click();
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

    @Test
    public void checkBecomePartnerPageTabHelpDownloadButton(){
        locator = "partnerpage.help.download.button";
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
    public void checkBecomePartnerPageCountryFlags(){
        locator = "partnerpage.info.countryFlags";
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
    public void checkBecomePartnerPageCountryNames(){
        locator = "partnerpage.info.countryNames";
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
    public void checkBecomePartnerPageInfoLoginButton(){
        locator = "partnerpage.info.login.button";
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
    public void checkBecomePartnerPageDashboardForgot(){
        locator = "partnerpage.dashboard.forgot.button";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationBecomePartnerPageURL()));
        Point hoverItem = driver.findElementByXPath(Homepage.getProperty("partnerpage.info.login.button")).getLocation();
        ((JavascriptExecutor)driver).executeScript("return window.title;");
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
        driver.findElementByXPath(Homepage.getProperty("partnerpage.info.login.button")).click();
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

//for commit
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
