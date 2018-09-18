package crisscrosscrass.Tasks;

import java.io.*;
import java.util.Properties;

public class ConfigSettings {

    public void saveConfigSettings(Properties properties) {
        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";
        FileWriter writer = null;
        try {
            writer = new FileWriter(location+"UserSettings.properties");
            properties.store(writer,System.getProperty("user.name"));
        } catch (IOException noWritter) {
            noWritter.printStackTrace();
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
    public Properties readConfigSettings(){
        Properties properties = new Properties();
        try{
            String location = System.getProperty("user.dir");
            location = location.replace("\\","//");
            location += "//temp//";
            FileReader reader = new FileReader(location+"UserSettings.properties");
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
