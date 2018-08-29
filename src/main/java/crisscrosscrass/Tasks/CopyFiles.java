package crisscrosscrass.Tasks;

import crisscrosscrass.Main;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.Properties;


public class CopyFiles {
    public void copyFileThere(){
        /*
        InputStream in = getClass().getResourceAsStream("chromedriver.exe");
        OutputStream out = new FileOutputStream("temp/chromedriver.exe");
        IOUtils.copy(in, out);


        File file = new File(".");
        for(String fileNames : file.list()) System.out.println(fileNames);



        File f1 = new File("chromedriver.exe");
        File f2 = new File("temp//chromedriver.exe");
        try {
            FileUtils.copyFile(f1,f2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        URL mydriver = Main.class.getClassLoader().getResource("chromedriver.exe");
        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";
        //System.out.println(location);

        File newWebDriver = new File(location+"chromedriver.exe");
        try {
            FileUtils.copyURLToFile(mydriver, newWebDriver);
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

       // System.out.println("Copied !");

    }

    public void CopyUserSettingsThere() {
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
