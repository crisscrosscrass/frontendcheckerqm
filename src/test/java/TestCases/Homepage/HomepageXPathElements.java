package TestCases.Homepage;

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

public class HomepageXPathElements {
    final static Logger logger = Logger.getLogger(HomepageXPathElements.class);
    private static ChromeDriver driver;
    private static Properties Homepage;
    private static String countrieSelection = "FR";
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
    public void checkMainLogo(){
        locator = "page.main.logo";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

    @Test
    public void checkMainCategorLinksLeft(){
        locator = "page.main.category.links.left";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkShopPromoLink(){
        locator = "page.main.shop.promo.link";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkShopPromoImage(){
        locator = "page.main.shop.promo.image";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkShopPromoCategory(){
        locator = "page.main.shop.promo.category";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkNewsletterInput(){
        locator = "page.main.newsletter.input";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkNewsletterClose(){
        locator = "page.main.newsletter.close";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        //scoll down d
        Point hoverItem = driver.findElementByXPath(Homepage.getProperty("page.main.newsletter.icon")).getLocation();
        ((JavascriptExecutor)driver).executeScript("return window.title;");
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
        driver.findElementByXPath(Homepage.getProperty("page.main.newsletter.icon")).click();
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkNewsletterIcon(){
        locator = "page.main.newsletter.icon";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkFooterLinks(){
        locator = "page.main.footer.links";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkSearchBar(){
        locator = "page.search.bar";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElementById(Homepage.getProperty(locator));
            element.sendKeys("body");
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkFeedbackIcon(){
        locator = "page.main.feedback.icon";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkPrivacyPopup(){
        locator = "page.main.privacy.popup";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkImprintCostumerButton(){
        locator = "imprintpage.costumer.button";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getlocationImprintPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }
    @Test
    public void checkPrivacypageLink(){
        locator = "privacypage.link";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf("DE").getPrivacyPage()));
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
