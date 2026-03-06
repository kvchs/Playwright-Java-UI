package org.example.testcase;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Feature;
import org.example.common.PlaywrightManager;
import org.example.page.BaiduPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BaiduTest {

    private static final Logger log = LoggerFactory.getLogger(BaiduTest.class);
    private BaiduPage baiduPage;

    //用例执行前初始化
    @BeforeClass
    public void setUp(){
        PlaywrightManager.init();
        baiduPage = new BaiduPage();
    }

    @Test
    @Feature("百度搜索功能")
    @Story("搜索关键字返回结果")
    @Severity(SeverityLevel.CRITICAL)
    public void testBaiduSearch(){
        try{
            baiduPage.open("https://www.baidu.com");
            baiduPage.search("Playwright Java");
            // 断言结果标题包含关键字
            String title = baiduPage.getResultTitle();
            Assert.assertTrue(title.contains("Playwright Java"), "搜索标题不符合预期");
            log.info("用例执行成功: {}", title);
        }catch (Exception e){
            PlaywrightManager.takeScreenshot();
            log.error("用例执行失败", e);
            throw e;
        }




    }

    @AfterClass
    public void tearDown(){
        PlaywrightManager.close();
    }
}
