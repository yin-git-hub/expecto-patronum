package com.example;

 import com.example.controller.ScrollingWebsocketController;
 import com.example.controller.UserWebSocketController;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.context.ApplicationContext;
 import org.springframework.scheduling.annotation.EnableAsync;
 import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 */
@EnableTransactionManagement
@SpringBootApplication
@EnableAsync
public class WaterStyApplication {
    private static final Logger LOG = LoggerFactory.getLogger(WaterStyApplication.class);

    public static void main(String[] args) {
        ApplicationContext run = SpringApplication.run(WaterStyApplication.class, args);
        ScrollingWebsocketController.setApplicationContext(run);
        UserWebSocketController.setApplicationContext(run);
        Environment env = run.getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("测试地址: \thttp://127.0.0.1:{}{}/test1", env.getProperty("server.port"), env.getProperty("server.servlet.context-path"));

    }
}
