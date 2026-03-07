# Playwright-Java-UI

This project is generated from an archetype. It exists out of TestNG, Playwright, Allure and OWNER as the main dependencies.

## 报告生成
先执行测试，生成结果文件
mvn clean test

再生成并打开报告
mvn allure:report
mvn allure:serve  # 这个会在浏览器打开报告


## 解决Jenkins拉取代码问题
```shell
docker exec -it jenkins bash
# 创建 .ssh 目录
mkdir -p /var/jenkins_home/.ssh
# 手动添加 GitHub 主机密钥
ssh-keyscan github.com >> /var/jenkins_home/.ssh/known_hosts
# 赋予权限（Jenkins 运行用户）
chmod 600 /var/jenkins_home/.ssh/known_hosts
chown jenkins:jenkins /var/jenkins_home/.ssh/known_hosts


```

## Docker 容器
docker volume create jenkins-data
docker volume ls
docker volume inspect jenkins-data
docker volume create jenkins-workspace

```shell
docker run -d \
  --name jenkins \
  --restart=always \  # 容器异常退出时自动重启
  -p 8080:8080 \      # 宿主机8080端口映射到容器8080（访问Jenkins）
  -p 50000:50000 \    # 宿主机50000端口映射到容器50000（代理通信）
  -v jenkins-data:/var/jenkins_home \  # 数据卷挂载（配置/插件）
  -v jenkins-workspace:/var/jenkins_workspace \  # 工作空间挂载
  -v /var/run/docker.sock:/var/run/docker.sock \  # 让Jenkins容器操作宿主机Docker（可选，后续构建Docker镜像用）
  -v /usr/bin/docker:/usr/bin/docker \  # 挂载Docker命令（可选）
  -u root \  # 以root用户运行（避免权限不足，生产环境可细化权限）
  jenkins/jenkins:lts-jdk17
  
  
  
docker run -d --name jenkins --restart=always -p 8080:8080  -p 50000:50000 -v jenkins-data:/var/jenkins_home -v jenkins-workspace:/var/jenkins_workspace -v /var/run/docker.sock:/var/run/docker.sock -v /usr/bin/docker:/usr/bin/docker -u root jenkins/jenkins:lts-jdk17  
/var/jenkins_home/secrets/initialAdminPassword

jenkins无法访问的原因
检查腾讯云安全组是否开放 8080 端口（最常见原因）
登录腾讯云控制台 → 进入你的服务器实例详情页
找到「安全组」→ 点击「配置规则」
检查入站规则是否有：
协议：TCP
端口：8080
来源：0.0.0.0/0（或你的本地 IP）
如果没有，点击「添加规则」，按上面配置添加。

```

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


