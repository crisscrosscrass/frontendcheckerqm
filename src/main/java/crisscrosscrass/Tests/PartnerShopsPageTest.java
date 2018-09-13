package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import crisscrosscrass.Controller.MainControllerFrontEndCheck;
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
import java.util.concurrent.ThreadLocalRandom;

public class PartnerShopsPageTest {
    final static Logger logger = Logger.getLogger(PartnerShopsPageTest.class);

    public void checkingGoToTopButton(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox GoToTopButton, Text statusInfo, TextField inputPartnerShopPageURL, Properties Homepage){
        final String infoMessage = "Checking Go to Top Button";
        ChangeCheckBox.adjustStyle(false,"progress",GoToTopButton);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.main.footer.box"))));
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.footer.box"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.totopbutton"))));
                    webDriver.findElementByXPath(Homepage.getProperty("page.main.totopbutton")).click();
                    try {
                        webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.h3")).click();
                        ChangeCheckBox.adjustStyle(true,"complete",GoToTopButton);
                        report.writeToFile(infoMessage, "Successful, Initial banner (H3) is on user's view");
                    }catch (Exception noH3InViewPort){
                        ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                        report.writeToFile(infoMessage, "Not successful, Initial banner (H3) is NOT on user's view");
                        noH3InViewPort.printStackTrace();
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                    webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Go To Top\" Button");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
            webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

    public void checkingBecomePartnerPopUp(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox BecomePartnerPopUp, Text statusInfo, TextField inputPartnerShopPageURL, Properties Homepage){
        final String infoMessage = "Checking Become Partner Button";
        ChangeCheckBox.adjustStyle(false,"progress",BecomePartnerPopUp);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.becomePartner.button"))));
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.becomePartner.button")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.becomePartner.close"))));
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.becomePartner.close")).click();
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.shops.becomePartner.close"))));
                    if (!webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.becomePartner.close")).isDisplayed()){
                        ChangeCheckBox.adjustStyle(true,"complete",BecomePartnerPopUp);
                        webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                        report.writeToFile(infoMessage, "Successful, functioning Pop Up appears!");
                    }else {
                        ChangeCheckBox.adjustStyle(true,"nope",BecomePartnerPopUp);
                        webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                        report.writeToFile(infoMessage, "Successful, functioning Pop Up appears!");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",BecomePartnerPopUp);
                    webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Become Partner\" Button");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",BecomePartnerPopUp);
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",BecomePartnerPopUp);
            webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
    public void checkingSortingReviews(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox SortingReviews, Text statusInfo, TextField inputPartnerShopPageURL, Properties Homepage){
        final String infoMessage = "Checking Sorting Reviews";
        ChangeCheckBox.adjustStyle(false,"progress",SortingReviews);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.shops.sort.button"))));
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("partnerpage.shops.sort.button"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.sort.button"))));
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.sort.button")).click();
                    List<WebElement> SortingOptions = webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.shops.sort.options"));
                    SortingOptions.get(0).click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.shops.firstshop.stars"))));
                    hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("partnerpage.shops.sort.button"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    if(webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.shops.firstshop.stars")).size() == 5){
                        report.writeToFile("Sorting- Best Review", "Successful ! Could detect "+webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.shops.firstshop.stars")).size()+" Stars for first Shop");
                    }else {
                        report.writeToFile("Sorting- Best Review", "Not successful ! Couldn't detect  Stars for first Shop");
                    }
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.shops.sort.button"))));
                    hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("partnerpage.shops.sort.button"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.sort.button"))));
                    webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.sort.button")).click();
                    SortingOptions = webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.shops.sort.options"));
                    SortingOptions.get(1).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.ratingcounts"))));
                    hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("partnerpage.shops.sort.button"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    List<WebElement> ShopRatingCounts = webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.shops.ratingcounts"));
                    final int FirstShopAmountOfReviews = Integer.parseInt(ShopRatingCounts.get(0).getText().replaceAll("([A-Z]|[a-z]).*","").trim());
                    final int LastShopAmountOfReviews = Integer.parseInt(ShopRatingCounts.get(ShopRatingCounts.size()-1).getText().replaceAll("([A-Z]|[a-z]).*","").trim());
                    if (FirstShopAmountOfReviews >= LastShopAmountOfReviews){
                        report.writeToFile("Sorting- Number of Reviews", "Successful ! First shop in the list has more reviews ("+FirstShopAmountOfReviews+") than last shop ("+LastShopAmountOfReviews+")");
                        ChangeCheckBox.adjustStyle(true,"complete",SortingReviews);
                    }else {
                        report.writeToFile("Sorting- Number of Reviews", "Successful ! First shop in the list has more reviews ("+FirstShopAmountOfReviews+") than last shop ("+LastShopAmountOfReviews+")");
                        ChangeCheckBox.adjustStyle(true,"nope",SortingReviews);
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",SortingReviews);
                    webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Sorting\" Button");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",SortingReviews);
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",SortingReviews);
            webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

    public void checkingShopLinkName(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox ShopLinkName, Text statusInfo, TextField inputPartnerShopPageURL, Properties Homepage){
        final String infoMessage = "Checking Shop Link Name";
        ChangeCheckBox.adjustStyle(false,"progress",ShopLinkName);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.shoplinknames"))));
                    List<WebElement> AllShopLinkNames = webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.shops.shoplinknames"));
                    final int randomSelectedNumber = ThreadLocalRandom.current().nextInt(0, AllShopLinkNames.size() );
                    Point hoverItem = AllShopLinkNames.get(randomSelectedNumber).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    String selectedShopName = AllShopLinkNames.get(randomSelectedNumber).getText();
                    AllShopLinkNames.get(randomSelectedNumber).click();
                    if (webDriver.getTitle().toLowerCase().contains(selectedShopName.toLowerCase())){
                        ChangeCheckBox.adjustStyle(true,"complete",ShopLinkName);
                        report.writeToFile(infoMessage, "Successful! User is redirected to page that contains the Shop Name in Page Title");
                    }else {
                        ChangeCheckBox.adjustStyle(true,"nope",ShopLinkName);
                        report.writeToFile(infoMessage, "Not successful! User is redirected to page("+webDriver.getCurrentUrl().toLowerCase().trim()+") that NOT contains the Shop Name("+selectedShopName.toLowerCase()+") in Page Title("+webDriver.getTitle().toLowerCase()+")");
                        boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "checkingShopLinkName.png");
                        if (isSuccessful){
                            report.writeToFile(infoMessage, "Screenshot successful!");
                        }else {
                            report.writeToFile(infoMessage, "Screenshot not successful!");
                        }
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",ShopLinkName);
                    webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Sorting\" Button");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",ShopLinkName);
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",ShopLinkName);
            webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
    public void checkingShopLinkLogo(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox ShopLinkLogo, Text statusInfo, TextField inputPartnerShopPageURL, Properties Homepage){
        final String infoMessage = "Checking Shop Link Logo";
        ChangeCheckBox.adjustStyle(false,"progress",ShopLinkLogo);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.shops.shoplogos.noplaceholder"))));
                    List<WebElement> AllShopLogoLinksWithoutPlaceholder = webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.shops.shoplogos.noplaceholder"));
                    final int randomSelectedNumber = ThreadLocalRandom.current().nextInt(0, AllShopLogoLinksWithoutPlaceholder.size() );
                    Point hoverItem = AllShopLogoLinksWithoutPlaceholder.get(randomSelectedNumber).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    String LogoURLFromSelectedItem = AllShopLogoLinksWithoutPlaceholder.get(randomSelectedNumber).getAttribute("src");
                    AllShopLogoLinksWithoutPlaceholder.get(randomSelectedNumber).click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.shop.image"))));
                    if (webDriver.findElementByXPath(Homepage.getProperty("page.grid.shop.image")).getAttribute("src").equals(LogoURLFromSelectedItem)){
                        ChangeCheckBox.adjustStyle(true,"complete",ShopLinkLogo);
                        report.writeToFile(infoMessage, "Successful! Logo URL in HP and logo URL on upper left side of redirected page are the same");
                    }else {
                        ChangeCheckBox.adjustStyle(true,"nope",ShopLinkLogo);
                        report.writeToFile(infoMessage, "Not successful! Logo URL in HP and logo URL on upper left side of redirected page are NOT the same");
                        boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "checkingShopLinkLogo.png");
                        if (isSuccessful){
                            report.writeToFile(infoMessage, "Screenshot successful!");
                        }else {
                            report.writeToFile(infoMessage, "Screenshot not successful!");
                        }
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",ShopLinkLogo);
                    webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Sorting\" Button");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",ShopLinkLogo);
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",ShopLinkLogo);
            webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingShopReview(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox ShopLinkLogo, Text statusInfo, TextField inputPartnerShopPageURL, Properties Homepage){
        final String infoMessage = "Checking Shop Link Logo";
        ChangeCheckBox.adjustStyle(false,"progress",ShopLinkLogo);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.shopreviews"))));
                    List<WebElement> AllShopReviews = webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.shops.shopreviews"));
                    final int randomSelectedNumber = ThreadLocalRandom.current().nextInt(0, AllShopReviews.size() );
                    Point hoverItem = AllShopReviews.get(randomSelectedNumber).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    AllShopReviews.get(randomSelectedNumber).click();
                    if (webDriver.getCurrentUrl().contains("review")){
                        ChangeCheckBox.adjustStyle(true,"complete",ShopLinkLogo);
                        report.writeToFile(infoMessage, "Successful! User is redirected to a functioning page with the word \"review\" in the URl");
                    }else {
                        ChangeCheckBox.adjustStyle(true,"nope",ShopLinkLogo);
                        report.writeToFile(infoMessage, "Not successful! User is NOT redirected to a functioning page with the word \"review\" in the URl");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",ShopLinkLogo);
                    webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Sorting\" Button");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",ShopLinkLogo);
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",ShopLinkLogo);
            webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingShopSearchBox(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox ShopSearchBox, Text statusInfo, TextField inputPartnerShopPageURL,TextField inputPartnerShopSearch, Properties Homepage){
        final String infoMessage = "Checking Shop Link Logo";
        ChangeCheckBox.adjustStyle(false,"progress",ShopSearchBox);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    String[] ShopSearchAliases = inputPartnerShopSearch.getText().trim().split("\\|");
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.shopreviews"))));
                    for (int i = 0 ; i < ShopSearchAliases.length ; i++){
                        //scroll to searchbar
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.shops.searchbar"))));
                        Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("partnerpage.shops.searchbar"))).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        //enter keyword based on input
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.searchbar"))));
                        WebElement element = webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.searchbar"));
                        element.sendKeys(ShopSearchAliases[i].trim());
                        element.submit();
                        //scroll to searchbar
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("partnerpage.shops.searchbar"))));
                        hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("partnerpage.shops.searchbar"))).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        //check suggestions and put them into the Report List
                        if(webDriver.findElements(By.xpath(Homepage.getProperty("partnerpage.shops.shoplinknames"))).size() > 0){
                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("partnerpage.shops.shoplinknames"))));
                            List<WebElement> SearchResults = webDriver.findElementsByXPath(Homepage.getProperty("partnerpage.shops.shoplinknames"));
                            report.writeToFile("Provided Shop list for Searchkeyword \""+ShopSearchAliases[i].trim()+"\" the following Results :");
                            if (SearchResults.size() != 0){
                                for (WebElement SearchResult : SearchResults){
                                    report.writeToFile(SearchResult.getText());
                                }
                            }else {
                                report.writeToFile("Provided Shop list for Searchkeyword \""+ShopSearchAliases[i].trim()+"\" couldn't loaded completely");
                            }
                        }else {
                            report.writeToFile("Provided Shop list contains no Results for \""+ShopSearchAliases[i].trim()+"\" !");
                        }
                        report.writeToFile("");
                    }
                    ChangeCheckBox.adjustStyle(true,"complete",ShopSearchBox);
                    report.writeToFile(infoMessage, "Complete");
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",ShopSearchBox);
                    webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Search Bar\" or \"Search Results\"");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",ShopSearchBox);
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",ShopSearchBox);
            webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
}
