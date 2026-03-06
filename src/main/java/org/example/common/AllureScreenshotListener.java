package org.example.common;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import java.io.ByteArrayInputStream;

public class AllureScreenshotListener extends TestListenerAdapter {

    private static PlaywrightManager playwrightManager; // 你的Playwright管理类

    public static void setPlaywrightManager(PlaywrightManager manager) {
        playwrightManager = manager;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        takeScreenshotOnFailure();
        super.onTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        takeScreenshotOnFailure(); // 可选：跳过也截图
        super.onTestSkipped(result);
    }

    private void takeScreenshotOnFailure() {
        try {
            if (playwrightManager != null && playwrightManager.getPage() != null) {
                byte[] screenshot = playwrightManager.getPage().screenshot(
                        new Page.ScreenshotOptions().setFullPage(true)
                );

                Allure.addAttachment(
                        "失败截图 - " + java.time.LocalDateTime.now(),
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        "png"
                );

                System.out.println("✅ 失败截图已添加到Allure报告");
            }
        } catch (Exception e) {
            System.err.println("❌ 截图失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
