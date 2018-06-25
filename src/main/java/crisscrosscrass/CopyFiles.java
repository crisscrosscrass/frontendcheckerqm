package crisscrosscrass;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;


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
}
