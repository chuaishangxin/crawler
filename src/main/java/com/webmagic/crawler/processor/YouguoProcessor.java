package com.webmagic.crawler.processor;

import com.webmagic.crawler.pipeline.YouguoPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * 爬取模块Processor
 *
 * @author wpq
 * @date 2018/11/22 14:21
 */
public class YouguoProcessor implements PageProcessor {

    /**
     * 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
     * <p>
     * Site.me().setCharset("utf-8").setSleepTime(1000).setRetryTimes(3)
     */
    private Site site = Site.me()
            .setDomain("94img.com")
            .setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");

    private final static String disk = "F:\\尤果\\";

    private final static String INDEX_LIST = "http://www.94img.com/albums/UGirls-\\d+\\.html";

    private final static String INDEX = "http://www.94img.com/albums/UGirls.html";

    private final static String DET = "http://www.94img.com/photos/UGirls-\\d+\\.html";

    public final static String title = "尤果";

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        if (page.getUrl().toString().equals(INDEX)) {
            //列表分页
            addPages(page);
            //列表详情
            addLinks(page);
        } else if (page.getUrl().regex(INDEX_LIST).match()) {
            //列表详情
            addLinks(page);
        } else if (page.getUrl().regex(DET).match()) {
            // 详情分页
            addPages(page);
            // 详情保存
            saveImg(page);
        } else {
            // 详情保存
            saveImg(page);
        }
    }

    /**
     * 获取分页链接
     *
     * @param page 页面元素
     */
    private void addPages(Page page) {
        // 总页数
        int count = Integer.valueOf(page.getHtml().xpath("//div[@class='paginator']/span[@class='count']/text()").regex("\\d+").toString());
        List<String> links = new ArrayList<>(count - 1);
        String link = page.getUrl().toString();
        for (int i = 2; i <= count; i++) {
            links.add(link.substring(0, link.lastIndexOf(".")) + "-" + i + ".html");
        }
        page.addTargetRequests(links);
    }

    /**
     * 获取分页链接
     *
     * @param page 页面元素
     */
    private void addLinks(Page page) {
        List<Selectable> nodes = page.getHtml().xpath("//div[@class='pic_box']/table/tbody/tr/td").nodes();
        List<String> uilinks = new ArrayList<>();
        nodes.stream().forEach(s -> uilinks.add(s.xpath("//a/@href").toString()));
        page.addTargetRequests(uilinks);
    }

    /**
     * 保存图片
     *
     * @param page 页面元素
     */
    private void saveImg(Page page) {
        String path = page.getHtml().xpath("//div[@class='inline_full']/text()").toString().replaceAll(" ", "");
        List<Selectable> nodes = page.getHtml().xpath("//div[@class='pic_box']/table/tbody/tr/td").nodes();
        nodes.stream().forEach(s -> {
            String src = s.xpath("//img/@src").toString();
            page.putField(title + src, disk + path + "\\," + src);
        });

    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new YouguoProcessor());
        spider.addUrl(INDEX);
        spider.addPipeline(new YouguoPipeline());
        spider.thread(50);
        spider.setExitWhenComplete(true);
        spider.start();
    }
}
