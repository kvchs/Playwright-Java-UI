package org.example.testcase;


import org.example.common.LogUtils;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.example.page.NewBaiduPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.slf4j.Logger;

public class NewBaiduTest {
    private static final Logger logger = LogUtils.getLogger(NewBaiduPage.class);
    private Playwright playwright;
    private Page page;
    private NewBaiduPage newBaiduPage;

    @BeforeClass
    public void setUp() {
        LogUtils.info(logger, "初始化Playwright环境");
        // 初始化Playwright（可封装到BaseTest）
        playwright = com.microsoft.playwright.Playwright.create();
        page = playwright.chromium().launch(new com.microsoft.playwright.BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(java.util.Arrays.asList("--no-sandbox")))
                .newPage();
        newBaiduPage = new NewBaiduPage(page);
    }

    @Test
    @Feature("百度搜索功能")
    @Story("搜索Playwright关键字")
    public void testBaiduSearch() {
        try {
            LogUtils.info(logger, "开始执行百度搜索测试用例");
            // 调用页面类方法（底层是关键字驱动）
            newBaiduPage.openBaidu("https://www.baidu.com");
//            newBaiduPage.search("Playwright Java");
            // 断言
//            String title = baiduPage.getSearchResultTitle();
//            Assert.assertTrue(title.contains("Playwright Java"), "搜索结果标题不符合预期");

            LogUtils.info(logger, "百度搜索测试用例执行成功");
        } catch (Exception e) {
            LogUtils.error(logger, "百度搜索测试用例执行失败", e);
            throw e; // 抛出异常，TestNG标记用例失败
        }
    }

    @AfterClass
    public void tearDown() {
        LogUtils.info(logger, "销毁Playwright环境");
        if (playwright != null) {
            playwright.close();
        }
    }
}