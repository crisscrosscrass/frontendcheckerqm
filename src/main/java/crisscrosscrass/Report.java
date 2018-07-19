package crisscrosscrass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Report {

    public void writeToFile(String ReportTitle, String ReportMessage){
        File reportFile = new File("temp//report.txt");
        if(!reportFile.exists()) {
            System.out.println("reportFile not exist");
            createReportFile();
        }
        try {
            BufferedWriter buffW = new BufferedWriter(new FileWriter(reportFile,true));
            buffW.write(ReportTitle+"\n"+ReportMessage+"\r");
            buffW.close();
            //System.out.println("Written into file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createReportFile() {
        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";

        File reportFile = new File(location+"report.txt");
        try {
            reportFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearWrittenReport(){
        File reportFile = new File("temp//report.txt");
        if(!reportFile.exists()) {
            System.out.println("reportFile not exist");
            createReportFile();
        }
        try {
            BufferedWriter buffW = new BufferedWriter(new FileWriter(reportFile,false));
            buffW.write("");
            buffW.close();
            //System.out.println("clear written text file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
