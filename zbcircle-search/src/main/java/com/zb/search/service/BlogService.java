package com.zb.search.service;

import com.zb.auth.common.model.PageResult;
import com.zb.search.pojo.BlogDoc;
import com.zb.search.pojo.RequestBlogParams;

import java.util.List;

public interface BlogService {

    public PageResult getList(RequestBlogParams requestParams);

    List<String> getSuggestions(String pre);

    void insertById(Long id);

    void deleteById(Long id);
}
