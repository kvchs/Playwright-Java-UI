package org.example.api.keyword;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Allure;
import org.example.common.LogUtils;
import org.example.enums.LocatorType;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;


/**
 * Playwright关键字驱动核心类：封装所有UI操作关键字
 */
public class PlayWrightKeyword {

    private static final Logger logger = LogUtils.getLogger(PlayWrightKeyword.class);
    private final Page page;
    private static final int DEFAULT_TIMEOUT = 20000;
    public PlayWrightKeyword(Page page){
        this.page = page;
    }


    /**
     * 通用定位元素方法
     * @param locatorType 定位方式
     * @param value 定位值
     * @return Locator
     */
    private Locator getLocator(LocatorType locatorType, String value){
        LogUtils.debug(logger, String.format("定位元素：类型=%s, 值=%s", locatorType.getType(), value));
        return switch (locatorType){
            case ID -> page.locator("#"+value);
            case XPATH, CSS -> page.locator(value);
            case NAME -> page.locator("[name='" + value + "']");
            case CLASS_NAME -> page.locator("." + value);
            case TEXT -> page.locator(":text('" + value + "')");
            case PLACEHOLDER -> page.locator(("[placeholder='" + value + "']"));
        };
    }

    // ========== 基础关键字 ==========
    /**
     * 打开URL关键字
     * @param url 目标URL
     */
    public void openUrl(String url) {
        try {
            LogUtils.info(logger, String.format("打开URL：%s", url));
            page.navigate(url);
            page.waitForLoadState(LoadState.NETWORKIDLE, new Page.WaitForLoadStateOptions().setTimeout(DEFAULT_TIMEOUT));
        } catch (Exception e) {
            LogUtils.error(logger, "打开URL失败：" + url, e);
            throw new RuntimeException("打开URL失败", e);
        }
    }

    /**
     * 输入关键字
     * @param locatorType 定位方式
     * @param value 定位值
     * @param text 输入内容
     */
    public void input(LocatorType locatorType, String value, String text) {
        try {
            LogUtils.info(logger, String.format("输入内容：定位类型=%s, 定位值=%s, 输入文本=%s", locatorType.getType(), value, text));
            Locator locator = getLocator(locatorType, value);
            // 等待元素可交互
            locator.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT));
            locator.clear(); // 清空输入框
            locator.type(text); // 模拟真实输入
        } catch (TimeoutError e) {
            LogUtils.error(logger, "输入超时：元素定位不到或不可交互", e);
            throw new RuntimeException("输入操作超时", e);
        } catch (Exception e) {
            LogUtils.error(logger, "输入失败", e);
            throw new RuntimeException("输入操作失败", e);
        }
    }

    /**
     * 点击关键字
     * @param locatorType 定位方式
     * @param value 定位值
     */
    public void click(LocatorType locatorType, String value) {
        try {
            LogUtils.info(logger, String.format("点击元素：定位类型=%s, 定位值=%s", locatorType.getType(), value));
            Locator locator = getLocator(locatorType, value);
            locator.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT));
            locator.click();
        } catch (TimeoutError e) {
            LogUtils.error(logger, "点击超时：元素定位不到或不可交互", e);
            throw new RuntimeException("点击操作超时", e);
        } catch (Exception e) {
            LogUtils.error(logger, "点击失败", e);
            throw new RuntimeException("点击操作失败", e);
        }
    }

    /**
     * 等待元素可见关键字
     * @param locatorType 定位方式
     * @param value 定位值
     */
    public void waitForElementVisible(LocatorType locatorType, String value) {
        try {
            LogUtils.debug(logger, String.format("等待元素可见：定位类型=%s, 定位值=%s", locatorType.getType(), value));
            Locator locator = getLocator(locatorType, value);
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(DEFAULT_TIMEOUT));
        } catch (TimeoutError e) {
            LogUtils.error(logger, "元素不可见超时", e);
            throw new RuntimeException("元素不可见超时", e);
        }
    }

    // ========== 扩展关键字（按需添加） ==========
    /**
     * 获取元素文本关键字
     * @param locatorType 定位方式
     * @param value 定位值
     * @return 元素文本
     */
    public String getElementText(LocatorType locatorType, String value) {
        try {
            LogUtils.debug(logger, String.format("获取元素文本：定位类型=%s, 定位值=%s", locatorType.getType(), value));
            Locator locator = getLocator(locatorType, value);
            locator.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT));
            String text = locator.textContent();
            LogUtils.info(logger, String.format("获取元素文本成功：%s", text));
            return text;
        } catch (Exception e) {
            LogUtils.error(logger, "获取元素文本失败", e);
            throw new RuntimeException("获取元素文本失败", e);
        }
    }

    /**
     * 截图关键字（同步到Allure报告）
     * @param screenshotName 截图名称
     */
    public void takeScreenshot(String screenshotName) {
        try {
            LogUtils.info(logger, String.format("截取页面截图：%s", screenshotName));
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                    .setFullPage(true)
                    .setPath(java.nio.file.Paths.get("target/screenshots/" + screenshotName + ".png")));
            // 截图添加到Allure报告
            Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            LogUtils.error(logger, "截图失败", e);
            throw new RuntimeException("截图失败", e);
        }
    }
}
