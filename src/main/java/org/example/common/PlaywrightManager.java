package org.example.common;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.impl.driver.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.microsoft.playwright.Playwright;

public class PlaywrightManager {

    private static final Logger log = LoggerFactory.getLogger(PlaywrightManager.class);
    private static Playwright playWright;
    private static Browser browser;
    private static Page page;

    // 初始化Playwright和浏览器
    public static void init(){
        playWright = Playwright.create();
        //无头模式（Jenkins运行时使用）， 本地调试可以改为headless(false)
        browser = playWright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        log.info("Playwright浏览器初始化完成");
    }

//    static {
//        Driver.ensureDriverInstalled( ,true);
//    }

    // 获取page对象
    public static Page getPage(){
        return page;
    }

    // 关闭资源
    public static void close(){
        if (browser != null){
            browser.close();
        }
        if (playWright != null){
            playWright.close();
        }
        log.info("Playwright浏览器已关闭");
    }

    // Allure截图附件
    public static byte[] takeScreenshot(){
        return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }
}
