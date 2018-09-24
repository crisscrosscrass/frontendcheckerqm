package crisscrosscrass.Tasks;

import crisscrosscrass.Tests.MainMenuOnHomePageTest;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Report {
    final static Logger logger = Logger.getLogger(Report.class);

    public void writeToFile(String ReportTitle, String ReportMessage){
        File reportFile = new File("temp//report.txt");
        if(!reportFile.exists()) {
            logger.info("reportFile not exist");
            createReportFile();
        }
        try {
            BufferedWriter buffW = new BufferedWriter(new FileWriter(reportFile,true));
            buffW.write(ReportTitle+"\n"+ReportMessage+"\r");
            try{
                buffW.close();
            }catch (Exception noBufferedWriter){
                noBufferedWriter.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeToFile(String ReportMessage){
        File reportFile = new File("temp//report.txt");
        if(!reportFile.exists()) {
            logger.info("reportFile not exist");
            createReportFile();
        }
        try {
            BufferedWriter buffW = new BufferedWriter(new FileWriter(reportFile,true));
            buffW.write(ReportMessage+"\r\n");
            try{
                buffW.close();
            }catch (Exception noBufferedWriter){
                noBufferedWriter.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeToNamedFile(String ReportTitle, String ReportMessage, String fileName){
        File reportFile = new File("temp//"+fileName+".txt");
        if(!reportFile.exists()) {
            logger.info("reportFile not exist");
            createNamedReportFile(fileName);
        }
        try {
            BufferedWriter buffW = new BufferedWriter(new FileWriter(reportFile,true));
            buffW.write(ReportTitle+"\n"+ReportMessage+"\r");
            try{
                buffW.close();
            }catch (Exception noBufferedWriter){
                noBufferedWriter.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeToNamedFile(String ReportMessage, String fileName){
        File reportFile = new File("temp//"+fileName+".txt");
        if(!reportFile.exists()) {
            logger.info("reportFile not exist");
            createNamedReportFile(fileName);
        }
        try {
            BufferedWriter buffW = new BufferedWriter(new FileWriter(reportFile,true));
            buffW.write(ReportMessage+"\r\n");
            try{
                buffW.close();
            }catch (Exception noBufferedWriter){
                noBufferedWriter.printStackTrace();
            }
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
    private void createNamedReportFile(String fileName) {
        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";

        File reportFile = new File(location+""+fileName+".txt");
        try {
            reportFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearWrittenReport(){
        File reportFile = new File("temp//report.txt");
        if(!reportFile.exists()) {
            logger.info("reportFile not exist");
            createReportFile();
        }
        try {
            BufferedWriter buffW = new BufferedWriter(new FileWriter(reportFile,false));
            buffW.write("");
            try{
                buffW.close();
            }catch (Exception noBufferedWriter){
                noBufferedWriter.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearWrittenNamendReport(String fileName){
        File reportFile = new File("temp//"+fileName+".txt");
        if(!reportFile.exists()) {
            logger.info("reportFile not exist");
            createReportFile();
        }
        try {
            BufferedWriter buffW = new BufferedWriter(new FileWriter(reportFile,false));
            buffW.write("");
            try{
                buffW.close();
            }catch (Exception noBufferedWriter){
                noBufferedWriter.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
