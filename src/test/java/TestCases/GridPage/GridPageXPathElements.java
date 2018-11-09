package TestCases.GridPage;

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

public class GridPageXPathElements {
    final static Logger logger = Logger.getLogger(GridPageXPathElements.class);
    private static ChromeDriver driver;
    private static Properties Homepage;
    private static String countrieSelection = "DE";
    private static String locator;
    private static String gridUrl = "https://www.shopalike.cz/obleceni/sleva/obchod-boardstar/";
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
    public void checkGridWindows(){
        locator = "page.grid.windows";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        driver.findElementByXPath(Homepage.getProperty("page.main.links")).click();
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

    @Test
    public void checkGridWindowsContinue(){
        locator = "page.grid.windows.continue";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        driver.findElementByXPath(Homepage.getProperty("page.main.links")).click();
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

    @Test
    public void checkGridLoader(){
        locator = "page.grid.loader";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        driver.findElementByXPath(Homepage.getProperty("page.main.links")).click();
        driver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

    @Test
    public void checkGridSortDropdown(){
        locator = "page.grid.sort.dropdown.button";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        driver.findElementByXPath(Homepage.getProperty("page.main.links")).click();
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

    @Test
    public void checkGridSortDropdownOption(){
        locator = "page.grid.sort.dropdown.options";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        driver.findElementByXPath(Homepage.getProperty("page.main.links")).click();
        driver.findElementByXPath(Homepage.getProperty("page.grid.loader "));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

    @Test
    public void checkGridSize(){
        locator = "page.grid.size.button";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.navigate().to(gridUrl);
        //driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try{
            element = driver.findElement (By.xpath(Homepage.getProperty(locator)));
        }catch (Exception xpathNotFound){
            logger.error("Couldn't find "+locator+" \n" +
                    Homepage.get(locator)+" | might be outdated");
        }
        Assert.assertNotNull(element);
    }

  @Test
    public void checkGridItemsNumber(){
        locator = "page.grid.items.number";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
      driver.navigate().to(gridUrl);
      //driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
            try {
                element = driver.findElement(By.xpath(Homepage.getProperty(locator)));
            } catch (Exception xpathNotFound) {
                logger.error("Couldn't find " + locator + " \n" +
                        Homepage.get(locator) + " | might be outdated");
            }

        Assert.assertNotNull(element);
    }

    @Test
    public void checkGridFirstTag(){
        locator = "page.grid.first.tag";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.navigate().to(gridUrl);
        //driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try {
            element = driver.findElement(By.xpath(Homepage.getProperty(locator)));
        } catch (Exception xpathNotFound) {
            logger.error("Couldn't find " + locator + " \n" +
                    Homepage.get(locator) + " | might be outdated");
        }

        Assert.assertNotNull(element);
    }


    @Test
    public void checkPageNumbers(){
        locator = "page.pageNumbers";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.navigate().to(gridUrl);
        //driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try {
            element = driver.findElement(By.xpath(Homepage.getProperty(locator)));
        } catch (Exception xpathNotFound) {
            logger.error("Couldn't find " + locator + " \n" +
                    Homepage.get(locator) + " | might be outdated");
        }

        Assert.assertNotNull(element);
    }

   /** @Test
    public void checkPreviousPageButton(){
        locator = "page.previousPage.button";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.navigate().to(gridUrl);
        //driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
            driver.findElementByXPath(Homepage.getProperty("page.pageNumbers")).click();
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.previousPage.button"))));

        try {
            element = driver.findElement(By.xpath(Homepage.getProperty(locator)));
        } catch (Exception xpathNotFound) {
            logger.error("Couldn't find " + locator + " \n" +
                    Homepage.get(locator) + " | might be outdated");
        }

        Assert.assertNotNull(element);
    }     */

    @Test
    public void checkItemsLinks(){
        locator = "page.items.links";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.navigate().to(gridUrl);
        //driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try {
            element = driver.findElement(By.xpath(Homepage.getProperty(locator)));
        } catch (Exception xpathNotFound) {
            logger.error("Couldn't find " + locator + " \n" +
                    Homepage.get(locator) + " | might be outdated");
        }

        Assert.assertNotNull(element);
    }

    @Test
    public void checkItemsPrice(){
        locator = "page.items.price";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.navigate().to(gridUrl);
        //driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try {
            element = driver.findElement(By.xpath(Homepage.getProperty(locator)));
        } catch (Exception xpathNotFound) {
            logger.error("Couldn't find " + locator + " \n" +
                    Homepage.get(locator) + " | might be outdated");
        }

        Assert.assertNotNull(element);
    }

    @Test
    public void checkItemsInfoIcon(){
        locator = "page.items.info.icon";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.navigate().to(gridUrl);
        //driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try {
            element = driver.findElement(By.xpath(Homepage.getProperty(locator)));
        } catch (Exception xpathNotFound) {
            logger.error("Couldn't find " + locator + " \n" +
                    Homepage.get(locator) + " | might be outdated");
        }

        Assert.assertNotNull(element);
    }

    @Test
    public void checkItemsShopnames(){
        locator = "page.items.shopnames";
        logger.info("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
        driver.navigate().to(gridUrl);
        //driver.get(String.valueOf(countries.valueOf(countrieSelection).getLocationMainPage()));
        try {
            element = driver.findElement(By.xpath(Homepage.getProperty(locator)));
        } catch (Exception xpathNotFound) {
            logger.error("Couldn't find " + locator + " \n" +
                    Homepage.get(locator) + " | might be outdated");
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
