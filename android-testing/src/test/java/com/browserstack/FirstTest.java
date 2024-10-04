package com.browserstack;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class FirstTest extends AppiumTest {

    @Test
    public void test() throws Exception {
        handleLocationPermissionDialog();
        handlePhoneStatePermissionDialog();
        clickGetIntelligenceButton();
        testCheckForANRDialog();
    }


    private void handleLocationPermissionDialog() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement allowButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button[contains(@text, 'While using the app')]")));
            allowButton.click();
            System.out.println("Location permission" + " accepted.");
        } catch (Exception e) {
            System.out.println("No " + "Location permission" + " dialog appeared or could not be accepted: " + e.getMessage());
        }
    }

    private void handlePhoneStatePermissionDialog() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement allowButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button[contains(@text, 'Allow')]")));
            allowButton.click();
            System.out.println("Read Phone State permission" + " accepted.");
        } catch (Exception e) {
            System.out.println("No " + "Read Phone State permission" + " dialog appeared or could not be accepted: " + e.getMessage());
        }
    }

    private void clickGetIntelligenceButton() {
        try {
            WebElement getIntelligenceMenuItem = new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                    ExpectedConditions.elementToBeClickable(AppiumBy.id("com.sign3labs.fraudsdk:id/menu_sdk"))); // Replace com.example.app with your actual package name
            getIntelligenceMenuItem.click();
            if (getIntelligenceMenuItem.isDisplayed()){
                System.out.println("Get intelligence button clicked.");
            }else {
                System.out.println("Get intelligence button not clicked.");
            }
        } catch (Exception e) {
            System.out.println("Get intelligence : " + e.getMessage());
        }
    }

    public void testCheckForANRDialog() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean anrDialogHandled = false;

        int maxAttempts = 90;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                WebElement anrDialog = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@text='Wait']"))); // Update the text as needed
                if (anrDialog != null) {
                    WebElement closeAppButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button[contains(@text, 'Close app')]")));
                    closeAppButton.click();
                    if (anrDialog.isDisplayed()){
                        System.out.println("ANR dialog was not closed successfully.");
                    }else {
                        System.out.println("ANR dialog is closed successfully.");
                    }
                    anrDialogHandled = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println("ANR dialog is not present on the screen. Attempt: " + (attempt + 1));
            }
            Thread.sleep(60000);
        }

        if (!anrDialogHandled) {
            System.out.println("No ANR dialog was detected after " + maxAttempts + " attempts.");
        }
    }

}
