package com.webmagic.crawler.processor;

import com.webmagic.crawler.entity.News;
import com.webmagic.crawler.pipeline.JianShuPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * 爬取模块Processor
 *
 * @author wpq
 * @date 2018/11/22 14:21
 */
public class JianShuProcessor implements PageProcessor {

    /**
     * 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
     * <p>
     * Site.me().setCharset("utf-8").setSleepTime(1000).setRetryTimes(3)
     */
    private Site site = Site.me()
            .setDomain("jianshu.com")
            .setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");


    public final static String list = "https://www.jianshu.com";

    @Override
    public void process(Page page) {
        if (page.getUrl().regex(list).match()) {
            // 首页抓取
            if (page.getUrl().toString().equals(list)) {
                // 获取所有指定css下的链接
                List<String> all = page.getHtml().css("div#list-container").links().all();
                page.addTargetRequests(all);
            } else {
                List<Selectable> nodes = page.getHtml().xpath("//div[@class='article']").nodes();
                nodes.forEach(s -> {
                    // 获取文本标题
                    String title = s.xpath("//h1[@class='title']/text()").toString();
                    // 获取文本内容
                    String info = s.xpath("//div/div[@class='show-content-free']").toString();
                    News news = new News();
                    news.setTitle(title);
                    news.setInfo(info);
                    news.setLink(page.getUrl().toString());
                    page.putField("news" + title, news);
                });
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new JianShuProcessor());
        spider.addUrl(list);
        spider.addPipeline(new JianShuPipeline());
        spider.thread(5);
        spider.setExitWhenComplete(true);
        spider.start();
    }
}
