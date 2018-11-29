package com.webmagic.crawler.pipeline;

import com.webmagic.crawler.entity.News;
import com.webmagic.crawler.repository.NewsRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 入库模块Pipeline
 *
 * @author wpq
 * @date 2018/11/22 14:30
 */
@Component
public class JianShuPipeline implements Pipeline {

    @Autowired
    private NewsRepostory newsRepostory;

    private static JianShuPipeline jianShuPipeline;

    @PostConstruct
    public void init(){
        jianShuPipeline = this;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getKey().contains("news")) {
                News news = (News) entry.getValue();
                //检查链接是否已存在
                if (null == jianShuPipeline.newsRepostory.findByTitle(news.getTitle())) {
                    jianShuPipeline.newsRepostory.save(news);
                }
            }
        }
    }

}
