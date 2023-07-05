package com.zb.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class BlogDoc {

    private Long id;

    private Long userId;
    private String title;

    private String images;
    private String content;

    private Integer liked;

    private Integer comments;


    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<String> suggestion;

    public BlogDoc() {
    }

    public BlogDoc(Blog blog) {
        System.out.println(blog);
        this.id = blog.getId();
        this.userId = blog.getUserId();
        this.title = blog.getTitle();
        this.images = blog.getImages();
        this.content = blog.getContent();
        this.liked = blog.getLiked();
        this.comments = blog.getComments();
        this.createTime = blog.getCreateTime();
        this.updateTime = blog.getUpdateTime();

        this.suggestion = Arrays.asList(this.title);
    }
}
