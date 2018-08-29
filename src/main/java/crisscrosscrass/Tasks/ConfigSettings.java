package crisscrosscrass.Tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ConfigSettings {

    public void createConfigSettingstFile(String inputSearch) throws IOException {
        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";

        //File reportFile = new File(location+"report.txt");

        Properties properties = new Properties();
        properties.setProperty("inputSearch",inputSearch);
        FileWriter writer = new FileWriter(location+"ConfigSettings.properties");
        properties.store(writer,"Author: Crisscrosscrass");
        writer.close();
    }
}
