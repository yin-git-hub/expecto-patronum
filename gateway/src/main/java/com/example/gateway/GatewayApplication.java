package com.example.gateway;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
public class GatewayApplication {
    private static final Logger LOG = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) {
        ApplicationContext run = SpringApplication.run(GatewayApplication.class, args);
        Environment env = run.getEnvironment();
        LOG.info("gateway启动成功！！");
        LOG.info("测试地址: \thttp://127.0.0.1:{}{}/test1", env.getProperty("server.port"), env.getProperty("cloud.water.sty"));

    }

}
