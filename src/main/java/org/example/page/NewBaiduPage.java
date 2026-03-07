package org.example.page;

import com.microsoft.playwright.Page;
import org.example.api.keyword.PlayWrightKeyword;
import org.example.common.LogUtils;
import org.example.enums.LocatorType;
import org.slf4j.Logger;

public class NewBaiduPage {
    private static final Logger logger = LogUtils.getLogger(NewBaiduPage.class);
    private final PlayWrightKeyword keyword;

    // 元素定位符（统一管理，便于维护）
    private static final LocatorType SEARCH_INPUT_TYPE = LocatorType.XPATH;
    private static final String SEARCH_INPUT_VALUE = "//*[@class='chat-input-textarea chat-input-scroll-style']";
    private static final LocatorType SEARCH_BTN_TYPE = LocatorType.ID;
    private static final String SEARCH_BTN_VALUE = "chat-submit-button";

    public NewBaiduPage(Page page){
        this.keyword = new PlayWrightKeyword(page);
    }


    /**
     * 打开百度首页
     * @param url 百度URL
     */
    public void openBaidu(String url) {
        LogUtils.info(logger, "打开百度首页");
        keyword.openUrl(url);
        // 等待搜索框可见
        keyword.waitForElementVisible(SEARCH_INPUT_TYPE, SEARCH_INPUT_VALUE);
        keyword.takeScreenshot("百度首页:" + url);
    }
}
