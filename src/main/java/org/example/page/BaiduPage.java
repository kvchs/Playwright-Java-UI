package org.example.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.common.PlaywrightManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaiduPage {

    private static final Logger log = LoggerFactory.getLogger(BaiduPage.class);
    private final Page page;
    private final Locator searchInput;
    private final Locator searchBtn;


    public BaiduPage(){
        this.page = PlaywrightManager.getPage();
        this.searchInput = page.locator("//*[@class='chat-input-textarea chat-input-scroll-style']");
        this.searchBtn = page.locator("#chat-submit-button");
    }

    // 打开百度首页
    public void open(String url) {
        page.navigate(url);
        page.waitForLoadState();
        log.info("打开百度首页：{}", url);
    }

    // 搜索操作
    public void search(String keyword) {
        searchInput.fill(keyword);
        searchBtn.click();
        page.waitForLoadState();
        log.info("搜索关键词：{}", keyword);
    }

    // 获取搜索结果标题
    public String getResultTitle() {
        return page.title();
    }
}
