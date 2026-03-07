// 声明式流水线语法（推荐，易维护）
pipeline {
    // 任意可用节点执行（若需指定节点，改为 agent { label 'your-node-label' }）
    agent any

    // 环境变量配置（统一版本/路径）
    environment {
        // 关联 Jenkins 全局配置的工具（名称需和 Jenkins 中一致）
        JAVA_HOME = tool 'JDK 17'
        M2_HOME = tool 'Maven 3.6.3'
        ALLURE_HOME = tool 'Allure 2.30.0'
        // 拼接环境变量，确保工具可执行
        PATH = "${JAVA_HOME}/bin:${M2_HOME}/bin:${ALLURE_HOME}/bin:${env.PATH}"
        // 项目根目录（Jenkins 工作空间）
        PROJECT_DIR = "${env.WORKSPACE}"
        // Allure 报告结果目录（和 pom.xml 中一致）
        ALLURE_RESULTS_DIR = "${PROJECT_DIR}/target/allure-results"
    }

    // 构建触发器（可选，根据需求配置）
    triggers {
        // 定时执行：每天凌晨 2 点（H 表示随机分钟，避免并发）
        cron('H 2 * * *')
        // 代码提交触发（需配置 Git 钩子）
        // pollSCM('H/15 * * * *')
    }

    // 构建步骤（核心流程）
    stages {
        stage('1. 拉取代码') {
            steps {
                echo "===== 拉取代码到工作空间：${PROJECT_DIR} ====="
                // 拉取 Git 代码（替换为你的仓库地址/分支）
                checkout scmGit(
                    branches: [[name: '*/main']], // 分支名：main/master 按需修改
                    userRemoteConfigs: [[url: 'https://github.com/kvchs/Playwright-Java-UI.git']] // 你的代码仓库地址
                )
            }
        }

        stage('2. 环境检查') {
            steps {
                echo "===== 验证环境配置 ====="
                // 验证 Java/Maven/Allure 版本，确保环境正确
                sh 'java -version'
                sh 'mvn -v'
                sh 'allure --version'
                // 验证项目目录和 pom.xml 存在
                sh "ls -l ${PROJECT_DIR} | grep pom.xml"
            }
        }

        stage('3. 安装依赖 + 下载 Playwright 浏览器') {
            steps {
                echo "===== 安装 Maven 依赖 + 下载 Playwright 浏览器 ====="
                dir(PROJECT_DIR) {
                    // 清理旧依赖/构建文件
                    sh 'mvn clean'
                    // 安装依赖（跳过测试），同时触发 Playwright 浏览器下载
                    sh 'mvn install -DskipTests'
                }
            }
            // 失败时终止流水线（依赖安装失败无需继续）
            post {
                failure {
                    echo "依赖安装失败，终止构建！"
                    error("Stage 3: 依赖安装失败")
                }
            }
        }

        stage('4. 执行自动化测试') {
            steps {
                echo "===== 执行 TestNG 测试用例 ====="
                dir(PROJECT_DIR) {
                    // 执行测试（生成 Allure 原始报告）
                    sh 'mvn test'
                }
            }
            // 无论测试成功/失败，都生成 Allure 报告
            post {
                always {
                    echo "===== 生成 Allure 报告 ====="
                    // 集成 Allure 报告到 Jenkins（关键：和插件联动）
                    allure(
                        includeProperties: false,
                        jdk: 'JDK 17', // 关联 JDK
                        results: [[path: "${ALLURE_RESULTS_DIR}"]] // Allure 原始数据目录
                    )
                }
            }
        }
    }

    // 构建后操作（告警/清理）
    post {
        // 测试成功时
        success {
            echo "===== 构建成功！====="
            // 可选：发送成功邮件（需配置 Jenkins 邮件服务器）
            // emailext(
            //     to: 'your-email@company.com',
            //     subject: "[Jenkins] 自动化测试构建成功 - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            //     body: "测试报告地址：${env.BUILD_URL}allure/"
            // )
        }

        // 测试失败时
        failure {
            echo "===== 构建失败！====="
            // 可选：发送失败邮件（包含失败详情）
            // emailext(
            //     to: 'your-email@company.com',
            //     subject: "[Jenkins] 自动化测试构建失败 - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            //     body: """
            //         构建编号：${env.BUILD_NUMBER}
            //         构建地址：${env.BUILD_URL}
            //         失败原因：测试用例执行失败，详见 Allure 报告：${env.BUILD_URL}allure/
            //     """
            // )
        }

        // 无论成功/失败，清理临时文件（可选）
        always {
            echo "===== 清理临时文件 ====="
            dir(PROJECT_DIR) {
                sh 'mvn clean -q' // -q 静默清理，减少日志
            }
        }
    }
}