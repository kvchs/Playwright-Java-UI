package org.example.common;

import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class LogUtils {

    // 私有化构造，禁止实例化
    private LogUtils() {};

    /**
     * 获取Logger实例
     * @param clazz 类对象
     * @return Logger
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * 封装INFO级别日志，同步到Allure报告
     * @param logger 日志实例
     * @param message 日志内容
     */
    public static void info(Logger logger, String message){
       logger.info(message);
        Allure.addAttachment("INFO日志", new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 封装DEBUG级别日志
     * @param logger 日志实例
     * @param message 日志内容
     */
    public static void debug(Logger logger, String message) {
        logger.debug(message);
    }

    /**
     * 封装ERROR级别日志，同步到Allure报告
     * @param logger 日志实例
     * @param message 日志内容
     * @param throwable 异常信息
     */
    public static void error(Logger logger, String message, Throwable throwable) {
        logger.error(message, throwable);
        Allure.addAttachment("ERROR日志", new ByteArrayInputStream((message + "\n" + getStackTrace(throwable)).getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 异常堆栈转字符串
     * @param throwable 异常
     * @return 堆栈字符串
     */
    private static String getStackTrace(Throwable throwable){
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element: throwable.getStackTrace()){
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
