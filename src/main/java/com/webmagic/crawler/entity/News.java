package com.webmagic.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author wpq
 * @date 2018/11/22 14:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "news")
@DynamicUpdate
@DynamicInsert
public class News implements Serializable {

    private static final long serialVersionUID = -2109282598575633803L;

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    @Column(name = "id" ,length = 32)
    private String id;

    @Column(name = "title",length = 100)
    private String title;

    @Lob
    @Column(name = "info")
    private String info;

    @Column(name = "link",length = 150)
    private String link;

}
