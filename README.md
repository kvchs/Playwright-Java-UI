# Playwright-Java-UI

This project is generated from an archetype. It exists out of TestNG, Playwright, Allure and OWNER as the main dependencies.

## 报告生成
先执行测试，生成结果文件
mvn clean test

再生成并打开报告
mvn allure:report
mvn allure:serve  # 这个会在浏览器打开报告

## Run tests

`mvn clean test`

## Run tests in specific browser

`mvn clean test -Dbrowser=chromium`

`mvn clean test -Dbrowser=chromium_headed`

`mvn clean test -Dbrowser=firefox`

`mvn clean test -Dbrowser=firefox_headed`

`mvn clean test -Dbrowser=webkit`

`mvn clean test -Dbrowser=webkit_headed`

`mvn clean test -Dbrowser=chrome`

`mvn clean test -Dbrowser=chrome_headed`

`mvn clean test -Dbrowser=msedge`

`mvn clean test -Dbrowser=msedge_headed`

## Generate report

`mvn allure:report`

## Serve report

`mvn allure:serve`

## Check for dependency updates

`mvn versions:display-dependency-updates`

## Update to the latest release version

`mvn versions:update-properties`


