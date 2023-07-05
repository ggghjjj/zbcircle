package com.zb.search;

import com.alibaba.fastjson.JSON;
import com.zb.auth.common.model.RestResponse;
import com.zb.search.feignclient.BlogServiceClient;
import com.zb.search.pojo.Blog;
import com.zb.search.pojo.BlogDoc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ZbcircleSearchApplicationTests {

    @Autowired
    private BlogServiceClient blogServiceClient;
    @Test
    void contextLoads() {
        RestResponse<Blog> blog = blogServiceClient.getBlogById(12307L);
        Blog result = blog.getResult();
        System.out.println(result);
//        Blog blog = (Blog) response.getResult();
//        System.out.println(blog);
    }

    @Test
    void test() {


    }

}
