package com.webmagic.crawler.repository;

import com.webmagic.crawler.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wpq
 * @date 2018/11/22 16:05
 */
@Repository
public interface NewsRepostory extends JpaRepository<News,String> {

    News findByTitle(@Param("title") String title);
}
