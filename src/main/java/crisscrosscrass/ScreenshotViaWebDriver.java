package crisscrosscrass;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotViaWebDriver {

    static boolean answer = false;


    public static boolean printScreen(WebDriver webDriver, String fileName){

            String location = System.getProperty("user.dir");
            location = location.replace("\\", "//");
            location += "//temp//";
            //System.out.println(location);
            File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File(location + fileName));
                answer = true;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Screenshot was not made");
                answer = false;
            }
            return answer;
    }
}
