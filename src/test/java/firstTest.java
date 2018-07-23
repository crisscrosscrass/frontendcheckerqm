import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@Ignore
public class firstTest {


    private WebDriver webDriver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "temp//chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @Test
    public void FirstTestCase(){
        webDriver.manage().window().maximize();
        webDriver.navigate().to("https://www.ladenzeile.de/");
        webDriver.close();
    }

    @Test
    public void SecondTestCase(){
        webDriver.navigate().to("https://www.ladenzeile.de/");
        ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"logo\"]/img").click();
        webDriver.close();
    }

    @Test
    public void ThirdTestCase(){
        webDriver.navigate().to("https://www.ladenzeile.de/");
        Assert.assertTrue( webDriver.getCurrentUrl().contains("https://www.ladenzeile.de/") );
        webDriver.close();

    }

}
