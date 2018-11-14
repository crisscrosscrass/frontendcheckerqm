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
    Report failedTestCases = new Report();

    public void checkingRegisterButton(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox RegisterButton, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        failedTestCases.writeToNamedFile("CHECKING BECOME A PARTNER PAGE", "FailAndReview");
        final String infoMessage = RegisterButton.getText();
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
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't detect \"Register\" Button on Become a Partner Page", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 43.1","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",RegisterButton);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't navigate to Register button on Become a Partner Page", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 43.1","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",RegisterButton);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Register button on Become a Partner Page: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 43.1","FailAndReview");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
    public void checkingBecomePartnerButton(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox RegisterButton, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = RegisterButton.getText();
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
                    //scroll to button
                    Point hoverItem = webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.register.banner.button")).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    //click on button
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.register.banner.button")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.becomePartner.close"))));
                    ChangeCheckBox.adjustStyle(true,"complete",RegisterButton);
                    report.writeToFile(infoMessage, "Functioning Pop Up appears!");
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",RegisterButton);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Become a Partner\" Button");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Could not detect \"Become a Partner\" button on Become a Partner Page", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 43.2","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",RegisterButton);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Could not navigate to \"Become a Partner\" button on Become a Partner Page", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 43.2","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",RegisterButton);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Become a Partner Page: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 43.2","FailAndReview");
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
                    for (int i = 0; i < 1; i++) {
                        Thread.sleep(300);
                        js.executeScript("window.scrollBy(0,100)");
                    }
                    for (int i = 0; i < 1; i++) {
                        Thread.sleep(300);
                        js.executeScript("window.scrollBy(0,-100)");
                    }
                    try {
                        WebElement h3Element = webDriver.findElementByXPath(Homepage.getProperty("partnerpage.info.h3"));
                        h3Element.click();
                        boolean isInViewPort = (boolean)((JavascriptExecutor)webDriver).executeScript(
                                "var elem = arguments[0],                 " +
                                        "  box = elem.getBoundingClientRect(),    " +
                                        "  cx = box.left + box.width / 2,         " +
                                        "  cy = box.top + box.height / 2,         " +
                                        "  e = document.elementFromPoint(cx, cy); " +
                                        "for (; e; e = e.parentElement) {         " +
                                        "  if (e === elem)                        " +
                                        "    return true;                         " +
                                        "}                                        " +
                                        "return false;                            "
                                , h3Element);
                        if (isInViewPort){
                            ChangeCheckBox.adjustStyle(true,"complete",GoToTopButton);
                            report.writeToFile("Go to Top- Help: ", "Initial banner (H3) is on user's view");
                        }else {
                            ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                            report.writeToFile("Go to Top- Help: ", "Initial banner (H3) is NOT on user's view");
                            failedTestCases.writeToNamedFile("Please check: Go to Top button seems not to work on Become a Partner Page", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 43.3","FailAndReview");
                        }
                        webDriver.findElementByXPath(Homepage.getProperty("partnerpage.info.h3")).click();
                        ChangeCheckBox.adjustStyle(true,"complete",GoToTopButton);
                        report.writeToFile(infoMessage, "Successful, Initial banner (H3) is on user's view");
                    }catch (Exception noH3InViewPort){
                        ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                        report.writeToFile(infoMessage, "Not successful, Initial banner (H3) is NOT on user's view");
                        failedTestCases.writeToNamedFile("Please check: Go to Top button seems not to work on Become a Partner Page", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 43.3","FailAndReview");
                        noH3InViewPort.printStackTrace();
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Register\" Button");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Could not detect Go to Top Button on Become a Partner Page", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 43.3","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Could not navigate to Go to Top button on Become a Partner Page", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 43.3","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Go to top button on Become a Partner: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 43.3","FailAndReview");
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
                            report.writeToFile("Country Flag Link from "+allCollectedNames.get(i).toString()+" is redirected to a functioning page  ",webDriver.getCurrentUrl().toLowerCase().trim());
                        }else {
                            report.writeToFile("Country Flag Link from "+allCollectedNames.get(i).toString()+" is redirected to a NOT functioning page  ",webDriver.getCurrentUrl().toLowerCase().trim());
                           failedTestCases.writeToNamedFile(" Please check: Country Flag Link from "+allCollectedNames.get(i).toString()+" is redirected to a NOT functioning page  ",webDriver.getCurrentUrl().toLowerCase().trim(), "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 44","FailAndReview");
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
                    failedTestCases.writeToNamedFile(" Please check: Could not detect Country Flag Link." , "FailAndReview");
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "CountryFlags.png");
                    if (isSuccessful){
                        report.writeToFile(infoMessage+" Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("For more information about the Country flags error, see CountryFlags", "Screenshot Successful!", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 44","FailAndReview");
                    }else {
                        report.writeToFile(infoMessage+" Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("For more information about the Country flags error, see CountryFlags", "Screenshot not Successful!", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 44","FailAndReview");
                    }
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",CountryFlags);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't navigate to country flag link on Become a Partner page", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 44","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",CountryFlags);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check country flags: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 44","FailAndReview");
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
                        failedTestCases.writeToNamedFile(infoMessage, "Please check Partner Dashboard login Page: login seems to not be working");
                        failedTestCases.writeToNamedFile("=================================TC 45","FailAndReview");
                    }

                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.dashboard.forgot.button"))));
                        webDriver.findElementByXPath(Homepage.getProperty("partnerpage.dashboard.forgot.button")).click();
                        if (webDriver.getCurrentUrl().contains("reset-password")){
                            report.writeToFile(infoMessage, "User is redirected to a reset-password page ");
                            ChangeCheckBox.adjustStyle(true,"complete",LoginPartnerdashboard);
                        }else {
                            report.writeToFile(infoMessage, "User is redirected to a functioning page but not with Reset Password ");
                            failedTestCases.writeToNamedFile(infoMessage, "Please check: when re-setting password on Partner Dashboard log in, user is redirected to page which is not working", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 45","FailAndReview");
                            ChangeCheckBox.adjustStyle(true,"nope",LoginPartnerdashboard);
                        }
                        report.writeToFile(infoMessage, "Complete");
                    }catch (Exception noForgotPassword){
                        ChangeCheckBox.adjustStyle(true,"nope",LoginPartnerdashboard);
                        webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                        report.writeToFile(infoMessage, "Couldn't detect \"Forget Password\" Button");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: couldn't detect \"Forget Password\" button on Partner Dashboard", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 45","FailAndReview");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",LoginPartnerdashboard);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Login Button\"");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: could not detect \"Login\" button on Partner Dashboard", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 45","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",LoginPartnerdashboard);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check Partner Dashboard: could not navigate to Login", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 45","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",LoginPartnerdashboard);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Partner Dashboard login: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 45","FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }

    public void checkingTabHelpSection(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox HelpRegisterTab, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = "Checking Tab Help Section";
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
                    //click on Help Tab
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.tab.help"))));
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.tab.help")).click();
                    //check if correct page
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.tab.help.content"))));
                    if (webDriver.findElementByXPath(Homepage.getProperty("partnerpage.tab.help.content")).isDisplayed()){
                        report.writeToFile("Help Tab: ", "Help Introduction is displayed");
                    }else{
                        report.writeToFile("Help Tab: ", "Help Introduction is NOT displayed");
                        failedTestCases.writeToNamedFile("Partner Page. Help tab", "Please check: Help Introduction is not displayed", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 46.1","FailAndReview");
                    }
                    try {
                        //check if register button
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.register.button"))));
                        //click on register button
                        webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.register.button")).click();
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.becomePartner.close"))));
                        if (webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.becomePartner.close")).isDisplayed()){
                            webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.becomePartner.close")).click();
                            report.writeToFile("Register- Help: ", "Functioning Pop Up appears");
                        }else{
                            report.writeToFile("Register- Help: ", "Functioning Pop Up does NOT appear !");
                            failedTestCases.writeToNamedFile("Register button- Help tab on Become Partner page", "Please check: register pop up seems to not be working", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 46.2","FailAndReview");
                        }
                        try {
                            //check if become Partner button
                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.tab.help.becomePartner.button"))));
                            //scroll to button
                            Point hoverItem = webDriver.findElementByXPath(Homepage.getProperty("partnerpage.tab.help.becomePartner.button")).getLocation();
                            ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                            ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                            //click on become partner button
                            webDriver.findElementByXPath(Homepage.getProperty("partnerpage.tab.help.becomePartner.button")).click();
                            if (webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.becomePartner.close")).isDisplayed()){
                                webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.becomePartner.close")).click();
                                report.writeToFile("Become Partner Button- Help: ", "Functioning Pop Up appears");
                            }else{
                                report.writeToFile("Become Partner Button- Help: ", "Functioning Pop Up does NOT appear !");
                                failedTestCases.writeToNamedFile("Become a Partner button- Help tab on Become Partner page", "Please check: Become a Partner Button seems to not be working", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 46.3","FailAndReview");
                            }
                            try {
                                //scroll down to bottom
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.main.footer.box"))));
                                Point hoverItemButton = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.footer.box"))).getLocation();
                                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItemButton.getY())+");");
                                //click on GoToTop
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.totopbutton"))));
                                webDriver.findElementByXPath(Homepage.getProperty("page.main.totopbutton")).click();
                                //check if H3 is in viewport
                                WebElement h3Element = webDriver.findElementByXPath(Homepage.getProperty("partnerpage.help.h3"));
                                //create a wait event for 1 Second
                                for (int i = 0; i < 1; i++) {
                                    Thread.sleep(1000);
                                    js.executeScript("window.scrollBy(0,-1)");
                                }
                                //check viewport via js
                                boolean isInViewPort = (boolean)((JavascriptExecutor)webDriver).executeScript(
                                        "var elem = arguments[0],                 " +
                                                "  box = elem.getBoundingClientRect(),    " +
                                                "  cx = box.left + box.width / 2,         " +
                                                "  cy = box.top + box.height / 2,         " +
                                                "  e = document.elementFromPoint(cx, cy); " +
                                                "for (; e; e = e.parentElement) {         " +
                                                "  if (e === elem)                        " +
                                                "    return true;                         " +
                                                "}                                        " +
                                                "return false;                            "
                                        , h3Element);
                                if (isInViewPort){
                                    ChangeCheckBox.adjustStyle(true,"complete",HelpRegisterTab);
                                    report.writeToFile("Go to Top- Help: ", "Initial banner (H3) is on user's view");
                                }else {
                                    ChangeCheckBox.adjustStyle(true,"nope",HelpRegisterTab);
                                    report.writeToFile("Go to Top- Help: ", "Initial banner (H3) is NOT on user's view");
                                    failedTestCases.writeToNamedFile("Go to Top- Help tab: ", "Please check: Go to Top button seems not to work on Become a Partner Page- Help tab", "FailAndReview");
                                    failedTestCases.writeToNamedFile("=================================TC 46.4","FailAndReview");
                                }
                                report.writeToFile("");
                                report.writeToFile(infoMessage, "Complete!");
                            }catch (Exception noGoToTopButton){
                                ChangeCheckBox.adjustStyle(true,"nope",HelpRegisterTab);
                                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                                report.writeToFile("Go to Top- Help", "Couldn't detect \"Go To Top\" Button");
                                failedTestCases.writeToNamedFile("Go to Top- Help tab: ", "Please check: Couldn't detect \"Go To Top\" Button", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 46.4","FailAndReview");
                                noGoToTopButton.printStackTrace();
                            }
                        }catch (Exception noBecomePartnerButton){
                            ChangeCheckBox.adjustStyle(true,"nope",HelpRegisterTab);
                            webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                            report.writeToFile("Become Partner Button- Help", "Couldn't detect \"Become Partner\" Button");
                            failedTestCases.writeToNamedFile("Become Partner Button- Help tab ", "Please check: Couldn't detect \"Become Partner\" Button", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 46.3","FailAndReview");
                            noBecomePartnerButton.printStackTrace();
                        }
                    }catch (Exception noRegisterButton){
                        ChangeCheckBox.adjustStyle(true,"nope",HelpRegisterTab);
                        webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                        report.writeToFile("Register- Help", "Couldn't detect \"Register\" Button");
                        failedTestCases.writeToNamedFile("Go to Top- Help tab on Become a Partner: ", "Please check: Couldn't detect \"Register\" Button", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 46.1","FailAndReview");
                        noRegisterButton.printStackTrace();
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",HelpRegisterTab);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile("Help Tab", "Couldn't detect Tab \"Help\"");
                    failedTestCases.writeToNamedFile("Go to Top- Help tab on Become a Partner: ", "Please check: Couldn't detect \"Help\" tab", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 46.2","FailAndReview");

                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",HelpRegisterTab);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check Become a Partner Help tab: couldn't navigate to the requested site", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 46","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",HelpRegisterTab);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Become a Partner Help tab: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 46","FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }

    public void checkingDownloadOnHelp(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox DownloadPDFHelp, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = "Checking Tab Partner Section";
        ChangeCheckBox.adjustStyle(false,"progress",DownloadPDFHelp);
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
                    //click on Help Tab
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.tab.help"))));
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.tab.help")).click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.tab.help.content"))));
                    //scroll down to Download
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.help.download.button"))));
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("partnerpage.help.download.button"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    //click download
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.help.download.button")).click();
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(100);
                        js.executeScript("window.scrollBy(0,100)");
                    }
                    tabs = new ArrayList<>(webDriver.getWindowHandles());
                    webDriver.switchTo().window(tabs.get(1));
                    if(webDriver.getCurrentUrl().contains("pdf")){
                        report.writeToFile(infoMessage, "Functioning PDF appears");
                        ChangeCheckBox.adjustStyle(true,"complete",DownloadPDFHelp);
                    }else {
                        report.writeToFile(infoMessage, "No functioning PDF appears");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check Partner tab on Become Partner page: no functioning PDF appears", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 47","FailAndReview");
                        ChangeCheckBox.adjustStyle(true,"nope",DownloadPDFHelp);
                    }
                    webDriver.switchTo().window(tabs.get(1)).close();
                    webDriver.switchTo().window(tabs.get(0));
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",DownloadPDFHelp);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Download Help\" Button");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check Partner tab on Become Partner page: Couldn't detect \"Download Help\" Button", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 47","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",DownloadPDFHelp);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check Download button on Partner tab on Become Partner page: Couldn't navigate to a requested Site!", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 47","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",DownloadPDFHelp);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Download button on Partner tab on Become Partner page: browser not responding!", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 47","FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingTabPartnerSection(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox PartnerTabSection, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = "Checking Tab Partner Section";
        ChangeCheckBox.adjustStyle(false,"progress",PartnerTabSection);
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
                    //click on Partner Tab
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.tab.partner"))));
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.tab.partner")).click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.tab.partner.content"))));
                    if (webDriver.findElementByXPath(Homepage.getProperty("partnerpage.tab.partner.content")).isDisplayed()){
                        report.writeToFile("Partner Tab: ", "Partner Introduction is displayed");
                    }else{
                        report.writeToFile("Partner Tab: ", "Partner Introduction is NOT displayed");
                        failedTestCases.writeToNamedFile("Partner Tab on Become Partner Page: ", "Please check: Partner Introduction is NOT displayed", "FailAndReview");
                        failedTestCases.writeToNamedFile("================================= TC 48.1","FailAndReview");
                    }
                    try {
                        //check if register button
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.register.button"))));
                        //click on register button
                        webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.register.button")).click();
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.becomePartner.close"))));
                        if (webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.becomePartner.close")).isDisplayed()){
                            webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.becomePartner.close")).click();
                            report.writeToFile("Register- Help: ", "Functioning Pop Up appears");
                        }else{
                            report.writeToFile("Register- Help: ", "Functioning Pop Up does NOT appear !");
                            failedTestCases.writeToNamedFile("Register- Partner tab on Become a Partner: ", "Please check: Functioning Pop Up does NOT appear when clicking on Register!", "FailAndReview");
                            failedTestCases.writeToNamedFile("================================= TC 48.2","FailAndReview");
                        }
                        try {
                            //check if become Partner button
                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.tab.partner.becomePartner.button"))));
                            //click on become partner button
                            webDriver.findElementByXPath(Homepage.getProperty("partnerpage.tab.partner.becomePartner.button")).click();
                            if (webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.becomePartner.close")).isDisplayed()){
                                webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.becomePartner.close")).click();
                                report.writeToFile("Become Partner Button- Help: ", "Functioning Pop Up appears");
                            }else{
                                report.writeToFile("Become Partner Button- Help: ", "Functioning Pop Up does NOT appear !");
                                failedTestCases.writeToNamedFile("Become Partner Button- Partner tab on Become a Partner: ", "Please check: Functioning Pop Up does NOT appear when clicking on Become Partner!", "FailAndReview");
                                failedTestCases.writeToNamedFile("================================= TC 48.3","FailAndReview");
                            }
                            try {
                                //scroll down to bottom
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.main.footer.box"))));
                                Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.footer.box"))).getLocation();
                                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                                //click on GoToTop
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.totopbutton"))));
                                webDriver.findElementByXPath(Homepage.getProperty("page.main.totopbutton")).click();
                                //check if H3 is in viewport
                                WebElement h3Element = webDriver.findElementByXPath(Homepage.getProperty("partnerpage.partner.h3"));
                                for (int i = 0; i < 5; i++) {
                                    Thread.sleep(100);
                                    js.executeScript("window.scrollBy(0,100)");
                                }
                                for (int i = 0; i < 5; i++) {
                                    Thread.sleep(100);
                                    js.executeScript("window.scrollBy(0,-100)");
                                }
                                boolean isInViewPort = (boolean)((JavascriptExecutor)webDriver).executeScript(
                                        "var elem = arguments[0],                 " +
                                                "  box = elem.getBoundingClientRect(),    " +
                                                "  cx = box.left + box.width / 2,         " +
                                                "  cy = box.top + box.height / 2,         " +
                                                "  e = document.elementFromPoint(cx, cy); " +
                                                "for (; e; e = e.parentElement) {         " +
                                                "  if (e === elem)                        " +
                                                "    return true;                         " +
                                                "}                                        " +
                                                "return false;                            "
                                        , h3Element);
                                if (isInViewPort){
                                    ChangeCheckBox.adjustStyle(true,"complete",PartnerTabSection);
                                    report.writeToFile("Go to Top- Help: ", "Initial banner (H3) is on user's view");
                                }else {
                                    ChangeCheckBox.adjustStyle(true,"nope",PartnerTabSection);
                                    report.writeToFile("Go to Top- Help: ", "Initial banner (H3) is NOT on user's view");
                                    failedTestCases.writeToNamedFile("Go to Top- Partner tab on Become a Partner: ", "Please check: Go to Top button seems not to work on Become a Partner Page- Partner tab", "FailAndReview");
                                    failedTestCases.writeToNamedFile("================================= TC 48.4","FailAndReview");
                                }
                                report.writeToFile("");
                                report.writeToFile(infoMessage, "Complete!");
                            }catch (Exception noGoToTopButton){
                                ChangeCheckBox.adjustStyle(true,"nope",PartnerTabSection);
                                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                                report.writeToFile("Go to Top- Help", "Couldn't detect \"Go To Top\" Button");
                                failedTestCases.writeToNamedFile("Go to Top- Partner tab on Become a Partner: ", "Please check: Couldn't detect \"Go To Top\" Button on Become a Partner Page- Partner tab", "FailAndReview");
                                failedTestCases.writeToNamedFile("================================= TC 48.4","FailAndReview");
                                noGoToTopButton.printStackTrace();
                            }
                        }catch (Exception noBecomePartnerButton){
                            ChangeCheckBox.adjustStyle(true,"nope",PartnerTabSection);
                            webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                            report.writeToFile("Become Partner Button- Help", "Couldn't detect \"Become Partner\" Button");
                            failedTestCases.writeToNamedFile("Become Partner Button- Partner tab on Become a Partner: ", "Please check: Couldn't detect \"Become Partner\" Button on Become a Partner Page- Partner tab", "FailAndReview");
                            failedTestCases.writeToNamedFile("================================= TC 48.3","FailAndReview");
                            noBecomePartnerButton.printStackTrace();
                        }
                    }catch (Exception noRegisterButton){
                        ChangeCheckBox.adjustStyle(true,"nope",PartnerTabSection);
                        webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                        report.writeToFile("Register- Help", "Couldn't detect \"Register\" Button");
                        failedTestCases.writeToNamedFile("Register Button- Partner tab on Become a Partner: ", "Please check: Couldn't detect \"Register\" Button on Become a Partner Page- Partner tab", "FailAndReview");
                        failedTestCases.writeToNamedFile("================================= TC 48.2","FailAndReview");
                        noRegisterButton.printStackTrace();
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",PartnerTabSection);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect Tab \"Partner\"");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't detect Tab \"Partner\" on Partner tab", "FailAndReview");
                    failedTestCases.writeToNamedFile("================================= TC 48.1","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",PartnerTabSection);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't navigate to Partner tab", "FailAndReview");
                failedTestCases.writeToNamedFile("================================= TC 48","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",PartnerTabSection);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Partner tab: Browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("================================= TC 48","FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingFeedProviders(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox FeedProviders, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = "Checking Feed Providers";
        ChangeCheckBox.adjustStyle(false,"progress",FeedProviders);
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
                    //click on Partner Tab
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.tab.partner"))));
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.tab.partner")).click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.tab.partner.content"))));

                    //get all Links for Feed Provider
                    List<WebElement> AllFeedProviderElements = webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.tab.partner.feedproviders"));
                    ArrayList allCollectedProviderURLs = new ArrayList();
                    for (WebElement FeedProvider : AllFeedProviderElements){
                        allCollectedProviderURLs.add(FeedProvider.getAttribute("href").toLowerCase().trim());
                    }
                    report.writeToFile("Feed Provider Links:");
                    failedTestCases.writeToNamedFile("Feed provider Links", "FailAndReview");
                    for (int i = 0 ; i < allCollectedProviderURLs.size() ; i++){
                        ((JavascriptExecutor)webDriver).executeScript("window.open()");
                        tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(1)); //switches to new tab
                        webDriver.get(allCollectedProviderURLs.get(i).toString());
                        if (webDriver.getCurrentUrl().toLowerCase().trim().equals(allCollectedProviderURLs.get(i).toString())){
                            report.writeToFile("Link for "+allCollectedProviderURLs.get(i).toString()+" is redirected to a correct page  ",webDriver.getCurrentUrl().toLowerCase().trim());
                        }else {
                            report.writeToFile("Link for "+allCollectedProviderURLs.get(i).toString()+" is redirected to a different page  ",webDriver.getCurrentUrl().toLowerCase().trim());
                            failedTestCases.writeToNamedFile("Please check: Link for "+allCollectedProviderURLs.get(i).toString()+" is redirected to a different page  ",webDriver.getCurrentUrl().toLowerCase().trim(), "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 49","FailAndReview");
                        }
                        webDriver.switchTo().window(tabs.get(1)).close();
                        webDriver.switchTo().window(tabs.get(0));
                    }
                    report.writeToFile("");
                    report.writeToFile(infoMessage, "Complete");
                    ChangeCheckBox.adjustStyle(true,"complete",FeedProviders);
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",FeedProviders);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Feed Providers\"");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't detect \"Feed Providers\"", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 49","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",FeedProviders);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't navigate to feed providers", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 49","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",FeedProviders);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check feed providers: Browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 49","FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
}
