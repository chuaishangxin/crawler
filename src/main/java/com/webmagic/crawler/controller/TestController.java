package com.webmagic.crawler.controller;

import com.webmagic.crawler.pipeline.JianShuPipeline;
import com.webmagic.crawler.processor.JianShuProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 * @author wpq
 * @date 2018/11/22 15:37
 */
@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/test")
    public String test(){
        Spider spider = Spider.create(new JianShuProcessor());
        spider.addUrl(JianShuProcessor.list);
        spider.addPipeline(new JianShuPipeline());
        spider.thread(5);
        spider.setExitWhenComplete(true);
        spider.start();
        return "ok";
    }
}
