package com.webmagic.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 * @author wpq
 * @date 2018/11/22 16:52
 */
@SpringBootApplication
@EnableScheduling
public class CrawlerApplication{

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }
}
