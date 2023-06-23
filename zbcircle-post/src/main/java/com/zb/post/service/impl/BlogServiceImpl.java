package com.zb.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zb.auth.common.constants.RedisConstants;
import com.zb.auth.common.model.PageParams;
import com.zb.auth.common.model.PageResult;
import com.zb.auth.common.model.RestResponse;
import com.zb.post.dao.BlogMapper;
import com.zb.post.pojo.Blog;
import com.zb.post.service.BlogService;
import com.zb.post.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.Action;
import java.security.Security;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean saveBlog(Blog blog) {
        int insert = blogMapper.insert(blog);
        return insert > 0 ? true : false;
    }

    public void isBlogLiked(Blog blog) {
        SecurityUtil.User user = SecurityUtil.getUser();
        if(user==null) {
            return ;
        }
        Long userId = user.getId();

        String key = RedisConstants.BLOG_LIKED_KEY + blog.getId();
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        blog.setIsLike(score != null);
    }
    @Override
    public void likeBlog(Long id) {
        String key = RedisConstants.BLOG_LIKED_KEY + id;
        SecurityUtil.User user = SecurityUtil.getUser();
        Long userId = user.getId();

        Double score = stringRedisTemplate.opsForZSet().score(key, userId);
        Blog blog = blogMapper.selectById(id);
        if(score == null) {
            blog.setLiked(blog.getLiked() + 1);
            stringRedisTemplate.opsForZSet().add(key, userId.toString(),System.currentTimeMillis());
        }else {
            blog.setLiked(blog.getLiked() -  1);
            stringRedisTemplate.opsForZSet().remove(key, userId.toString());
        }
        blogMapper.updateById(blog);
    }

    @Override
    public PageResult<Blog> queryMyBlog(PageParams params) {
        SecurityUtil.User user = SecurityUtil.getUser();
        Long userId = user.getId();
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        Page<Blog> page = new Page<>(params.getPageNo(), params.getPageSize());
        // 查询数据内容获得结果
        Page<Blog> pageResult = blogMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<Blog> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        PageResult<Blog> result = new PageResult<>(list, total, params.getPageNo(), params.getPageSize());
        return result;
    }

    @Override
    public PageResult<Blog>  queryBlogByUserId(PageParams params, Long id) {

        SecurityUtil.User user = SecurityUtil.getUser();
        Long userId = user.getId();
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        Page<Blog> page = new Page<>(params.getPageNo(), params.getPageSize());
        // 查询数据内容获得结果
        Page<Blog> pageResult = blogMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<Blog> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        PageResult<Blog> result = new PageResult<>(list, total, params.getPageNo(), params.getPageSize());
        return result;
    }

    @Override
    public Blog queryBlog(Long id) {
        return blogMapper.selectById(id);
    }

    @Override
    public PageResult<Blog> queryHotBlog(PageParams params) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("liked");
        Page<Blog> page = new Page<>(params.getPageNo(), params.getPageSize());

        Page<Blog> pageResult = blogMapper.selectPage(page, queryWrapper);
        List<Blog> records = pageResult.getRecords();
        records.forEach(blog->{
            this.updateBlog(blog);
            this.isBlogLiked(blog);
        });
        long total = pageResult.getTotal();
        PageResult<Blog> result = new PageResult<>(records, total, params.getPageNo(), params.getPageSize());

        return result;
    }

    private void updateBlog(Blog blog) {
        Long userId = blog.getUserId();
        SecurityUtil.User user = SecurityUtil.getUser();
        blog.setIcon(user.getUserpic());
        blog.setName(user.getNickname());
    }
}
