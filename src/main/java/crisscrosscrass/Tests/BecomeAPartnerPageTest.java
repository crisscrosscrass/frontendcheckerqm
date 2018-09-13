package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.ScreenshotViaWebDriver;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BecomeAPartnerPageTest {
    final static org.apache.log4j.Logger logger = Logger.getLogger(BecomeAPartnerPageTest.class);

    public void checkingRegisterButton(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox RegisterButton, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = "Checking Register Button";
        ChangeCheckBox.adjustStyle(false,"progress",RegisterButton);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.register.button"))));
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.register.button")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.becomePartner.close"))));
                    ChangeCheckBox.adjustStyle(true,"complete",RegisterButton);
                    report.writeToFile(infoMessage, "Functioning Pop Up appears!");
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",RegisterButton);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Register\" Button");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",RegisterButton);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",RegisterButton);
            webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
    public void checkingBecomePartnerButton(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox RegisterButton, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = "Checking Become Partner Button";
        ChangeCheckBox.adjustStyle(false,"progress",RegisterButton);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.register.banner.button"))));
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.register.banner.button")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.becomePartner.close"))));
                    ChangeCheckBox.adjustStyle(true,"complete",RegisterButton);
                    report.writeToFile(infoMessage, "Functioning Pop Up appears!");
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",RegisterButton);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Register\" Button");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",RegisterButton);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",RegisterButton);
            webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
    public void checkingGoToTopButton(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox GoToTopButton, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = "Checking Go to Top Button";
        ChangeCheckBox.adjustStyle(false,"progress",GoToTopButton);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.main.footer.box"))));
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.footer.box"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.totopbutton"))));
                    webDriver.findElementByXPath(Homepage.getProperty("page.main.totopbutton")).click();
                    try {
                        webDriver.findElementByXPath(Homepage.getProperty("partnerpage.info.h3")).click();
                        ChangeCheckBox.adjustStyle(true,"complete",GoToTopButton);
                        report.writeToFile(infoMessage, "Successful, Initial banner (H3) is on user's view");
                    }catch (Exception noH3InViewPort){
                        ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                        report.writeToFile(infoMessage, "Not successful, Initial banner (H3) is NOT on user's view");
                        noH3InViewPort.printStackTrace();
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Register\" Button");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
            webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
    public void checkingCountryFlags(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox CountryFlags, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = "Checking Country Flags";
        ChangeCheckBox.adjustStyle(false,"progress",CountryFlags);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.info.countryFlags"))));
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("partnerpage.info.countryFlags"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    List<WebElement> AllCountryFlags = webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.info.countryFlags"));
                    List<WebElement> AllCountryNames = webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.info.countryNames"));
                    ArrayList allCollectedLinks = new ArrayList();
                    ArrayList allCollectedNames = new ArrayList();
                    for (WebElement CountryFlagURL : AllCountryFlags){
                        allCollectedLinks.add(CountryFlagURL.getAttribute("href").trim().toLowerCase());
                    }
                    for (WebElement CountryNames : AllCountryNames){
                        allCollectedNames.add(CountryNames.getText().trim());
                    }
                    for (int i = 0 ; i < allCollectedLinks.size() ; i++){
                        ((JavascriptExecutor)webDriver).executeScript("window.open()");
                        tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(1)); //switches to new tab
                        webDriver.get(allCollectedLinks.get(i).toString());
                        if (webDriver.getCurrentUrl().trim().toLowerCase().equals(allCollectedLinks.get(i).toString())){
                            report.writeToFile("Link from "+allCollectedNames.get(i).toString()+" is redirected to a functioning page  ",webDriver.getCurrentUrl().toLowerCase().trim());
                        }else {
                            report.writeToFile("Link from "+allCollectedNames.get(i).toString()+" is redirected to a NOT functioning page  ",webDriver.getCurrentUrl().toLowerCase().trim());
                        }

                        webDriver.switchTo().window(tabs.get(1)).close();
                        webDriver.switchTo().window(tabs.get(0));
                    }
                    report.writeToFile("");
                    ChangeCheckBox.adjustStyle(true,"complete",CountryFlags);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Complete");

                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",CountryFlags);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Country Flags\"");
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "CountryFlags.png");
                    if (isSuccessful){
                        report.writeToFile(infoMessage+" Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile(infoMessage+" Screenshot: ", "Screenshot not successful!");
                    }
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",CountryFlags);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",CountryFlags);
            webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
    public void checkingLoginPartnerdashboard(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox LoginPartnerdashboard, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = "Checking Login Partnerdashboard";
        ChangeCheckBox.adjustStyle(false,"progress",LoginPartnerdashboard);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.info.login.button"))));
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("partnerpage.info.login.button"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");

                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.info.login.button")).click();
                    if (webDriver.getCurrentUrl().contains("login")){
                        report.writeToFile(infoMessage, "User is redirected to a Login page ");
                    }else {
                        report.writeToFile(infoMessage, "User is redirected to a functioning page but not with Login ");
                    }

                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.dashboard.forgot.button"))));
                        webDriver.findElementByXPath(Homepage.getProperty("partnerpage.dashboard.forgot.button")).click();
                        if (webDriver.getCurrentUrl().contains("reset-password")){
                            report.writeToFile(infoMessage, "User is redirected to a reset-password page ");
                            ChangeCheckBox.adjustStyle(true,"complete",LoginPartnerdashboard);
                        }else {
                            report.writeToFile(infoMessage, "User is redirected to a functioning page but not with Reset Password ");
                            ChangeCheckBox.adjustStyle(true,"nope",LoginPartnerdashboard);
                        }
                        report.writeToFile(infoMessage, "Complete");
                    }catch (Exception noForgotPassword){
                        ChangeCheckBox.adjustStyle(true,"nope",LoginPartnerdashboard);
                        webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                        report.writeToFile(infoMessage, "Couldn't detect \"Forget Password\" Button");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",LoginPartnerdashboard);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Login Button\"");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",LoginPartnerdashboard);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",LoginPartnerdashboard);
            webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

    public void checkingHelpRegisterTab(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox HelpRegisterTab, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = "Checking Login Partnerdashboard";
        ChangeCheckBox.adjustStyle(false,"progress",HelpRegisterTab);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.tab.help"))));

                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",HelpRegisterTab);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect Tab \"Help\"");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",HelpRegisterTab);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",HelpRegisterTab);
            webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}
