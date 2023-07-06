package com.zb.post.service;

import com.zb.auth.common.model.PageParams;
import com.zb.auth.common.model.PageResult;
import com.zb.post.pojo.BlogComments;

public interface BlogCommentsService {
    void save(BlogComments blogComments);

    PageResult<BlogComments> getList(PageParams params);

    void deleteBlog(Long id);

    void like(Long id);

    PageResult<BlogComments> queryBlogComments(PageParams params,Long id);
}
