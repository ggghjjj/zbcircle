package com.zb.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.auth.common.exception.ZbException;
import com.zb.auth.common.model.PageResult;
import com.zb.search.feignclient.BlogServiceClient;
import com.zb.search.pojo.Blog;
import com.zb.search.pojo.BlogDoc;
import com.zb.search.pojo.RequestBlogParams;
import com.zb.search.service.BlogService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private BlogServiceClient blogServiceClient;
    @Override
    public PageResult getList(RequestBlogParams requestParams) {
        return null;
    }

    @Override
    public List<String> getSuggestions(String pre) {
        return null;
    }

    @Override
    public void insertById(Long id) {
        Blog blog = blogServiceClient.getBlogById(id);

        BlogDoc blogDoc = new BlogDoc(blog);


        // 创建Request对象
        IndexRequest request = new IndexRequest("blog").id(blogDoc.getId().toString());
        // 准备json文档
        request.source(JSON.toJSONString(blogDoc), XContentType.JSON);
        // 发送请求
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
          DeleteRequest request = new DeleteRequest("hotel", id.toString());

            client.delete(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ZbException(e);
        }
    }
}
