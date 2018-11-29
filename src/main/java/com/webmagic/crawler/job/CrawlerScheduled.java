package com.webmagic.crawler.job;

import com.webmagic.crawler.processor.JianShuProcessor;
import com.webmagic.crawler.pipeline.JianShuPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**
 * 定时任务
 *
 * @author wpq
 * @date 2018/11/22 15:30
 */
@Component
public class CrawlerScheduled {

    @Autowired
    private JianShuPipeline jianShuPipeline;

    /**
     *  简书 每分钟执行一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void jianShuScheduled(){
        System.out.println("----开始执行简书定时任务");
        Spider spider = Spider.create(new JianShuProcessor());
        spider.addUrl(JianShuProcessor.list);
        spider.addPipeline(jianShuPipeline);
        spider.thread(5);
        spider.setExitWhenComplete(true);
        spider.start();
        spider.stop();
    }
}
