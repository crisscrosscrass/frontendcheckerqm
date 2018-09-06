package crisscrosscrass.Tasks;

import crisscrosscrass.Main;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.Properties;


public class CopyFiles {
    public void copyChromeDriverFile(){
        URL mydriver = Main.class.getClassLoader().getResource("chromedriver.exe");
        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";

        File newWebDriver = new File(location+"chromedriver.exe");
        try {
            FileUtils.copyURLToFile(mydriver, newWebDriver);
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    public void copyUserSettings() {
        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";
        File dir = new File(location);
        dir.mkdir();
        Properties properties = new Properties();
        properties.setProperty("inputSearch","https://www.ladenzeile.de/");
        properties.setProperty("inputEmailAdress","tester@testermail.com");
        properties.setProperty("inputTextSearchAndSuggestions","pumps | sneakers blue");
        properties.setProperty("inputGridPageURL","https://www.ladenzeile.de/mode/");
        properties.setProperty("inputGridPageKeyword","nike");
        properties.setProperty("inputGridPageURLWithWindows","https://www.ladenzeile.de/mode/damen/");
        properties.setProperty("inputGridPageURLWithFillIns","https://taschen.ladenzeile.de/kulturbeutel-kammtasche/");
        properties.setProperty("inputBrandPageOverview","https://www.ladenzeile.de/marken.html");
        properties.setProperty("inputLucenePage","emaroon");
        properties.setProperty("inputAccountEmail","tester@visual-meta.com");
        properties.setProperty("inputAccountPassword","testpassword");
        properties.setProperty("inputPartnerShopPageURL","https://www.ladenzeile.de/partner/shops");
        FileWriter writer = null;
        try {
            writer = new FileWriter(location+"UserSettings.properties");
            properties.store(writer,System.getProperty("user.name"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null){
                try{
                    writer.close();
                }catch (Exception notClosed){
                    notClosed.printStackTrace();
                }
            }
        }
    }
}
