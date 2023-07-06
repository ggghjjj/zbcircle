package com.zb.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zb.auth.common.constants.RedisConstants;
import com.zb.auth.common.model.PageParams;
import com.zb.auth.common.model.PageResult;
import com.zb.post.dao.BlogCommentsMapper;
import com.zb.post.pojo.Blog;
import com.zb.post.pojo.BlogComments;
import com.zb.post.pojo.User;
import com.zb.post.service.BlogCommentsService;
import com.zb.post.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogCommentsServiceImpl implements BlogCommentsService {

    @Autowired
    private BlogCommentsMapper blogCommentsMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void save(BlogComments blogComments) {
        blogCommentsMapper.insert(blogComments);
    }

    @Override
    public PageResult<BlogComments> getList(PageParams params) {
        QueryWrapper<BlogComments> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("liked").orderByDesc("create_time");
        Page<BlogComments> page = new Page<>(params.getPageNo(), params.getPageSize());
        Page<BlogComments> BlogCommentsPage = blogCommentsMapper.selectPage(page, queryWrapper);
        List<BlogComments> records = BlogCommentsPage.getRecords();
        records.forEach(blogComments->{
            this.updateBlog(blogComments);
            this.isBlogLiked(blogComments);
        });
        long total = BlogCommentsPage.getTotal();
        PageResult<BlogComments> result = new PageResult<>(records, total, params.getPageNo(), params.getPageSize());
        return result;
    }

    @Override
    public void deleteBlog(Long id) {
        blogCommentsMapper.deleteById(id);
    }

    @Override
    public void like(Long id) {
        String key = RedisConstants.BLOG_COMMENTS_LIKED_KEY + id;
        User user = SecurityUtil.getUser();
        Long userId = user.getId();
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        BlogComments blogComments = blogCommentsMapper.selectById(id);
        if(score == null) {
            blogComments.setLiked(blogComments.getLiked() + 1);
            stringRedisTemplate.opsForZSet().add(key, userId.toString(),System.currentTimeMillis());
        }else {
            blogComments.setLiked(blogComments.getLiked() -  1);
            stringRedisTemplate.opsForZSet().remove(key, userId.toString());
        }
        blogCommentsMapper.updateById(blogComments);
    }

    @Override
    public PageResult<BlogComments> queryBlogComments(PageParams params,Long id) {
        QueryWrapper<BlogComments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        Page<BlogComments> page = new Page<>(params.getPageNo(), params.getPageSize());
        Page<BlogComments> BlogCommentsPage = blogCommentsMapper.selectPage(page, queryWrapper);
        List<BlogComments> records = BlogCommentsPage.getRecords();
        records.forEach(blogComments->{
            this.updateBlog(blogComments);
            this.isBlogLiked(blogComments);
        });
        long total = BlogCommentsPage.getTotal();
        PageResult<BlogComments> result = new PageResult<>(records, total, params.getPageNo(), params.getPageSize());
        return result;
    }

    private void updateBlog(BlogComments blogComments) {
        User user = SecurityUtil.getUser();
        blogComments.setIcon(user.getUserpic());
        blogComments.setName(user.getNickname());
    }

    public void isBlogLiked(BlogComments blogComments) {
        User user = SecurityUtil.getUser();
        if(user==null) {
            log.info("用户不存在");
            return ;
        }
        Long userId = user.getId();

        String key = RedisConstants.BLOG_COMMENTS_LIKED_KEY + blogComments.getId();
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        blogComments.setIsLike(score != null);
    }
}
