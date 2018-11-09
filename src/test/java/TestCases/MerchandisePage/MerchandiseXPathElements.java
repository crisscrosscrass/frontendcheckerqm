package TestCases.MerchandisePage;

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

public class MerchandiseXPathElements {
    final static Logger logger = Logger.getLogger(MerchandiseXPathElements.class);
    private static ChromeDriver driver;
    private static Properties Homepage;
    private static String countrieSelection = "DE";
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
    public void checkMerchandisePageLetterButtons(){
        locator = "merchandisepage.alphabet.buttons";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMerchandiseOverviewPageURL()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

    @Test
    public void checkMerchandisePageLetters(){
        locator = "merchandisepage.alphabet.letters";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMerchandiseOverviewPageURL()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

    @Test
    public void checkMerchandisePageLinks(){
        locator = "merchandisepage.merchandise.links";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMerchandiseOverviewPageURL()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

    @Test
    public void checkMerchandiseSearchBar(){
        locator = "merchandisepage.search.bar";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMerchandiseOverviewPageURL()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

    @Test
    public void checkMerchandiseSearchSuggestions(){
        locator = "merchandisepage.search.suggestions";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMerchandiseOverviewPageURL()));
        WebElement element = driver.findElement(By.xpath(Homepage.getProperty("merchandisepage.search.bar")));
        element.sendKeys("lady gaga".trim());
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

    @Test
    public void checkMerchandiseIfoH3(){
        locator = "merchandisepage.info.h3";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMerchandiseOverviewPageURL()));
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


