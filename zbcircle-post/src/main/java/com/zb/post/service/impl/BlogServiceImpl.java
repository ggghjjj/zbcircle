package com.zb.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zb.auth.common.constants.RedisConstants;
import com.zb.auth.common.model.PageParams;
import com.zb.auth.common.model.PageResult;
import com.zb.auth.common.model.RestResponse;
import com.zb.post.constants.MqConstants;
import com.zb.post.dao.BlogMapper;
import com.zb.post.pojo.Blog;
import com.zb.post.pojo.User;
import com.zb.post.service.BlogService;
import com.zb.post.utils.SecurityUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean saveBlog(Blog blog) {
        int insert = blogMapper.insert(blog);
        rabbitTemplate.convertAndSend(MqConstants.BLOG_EXCHANGE,MqConstants.BLOG_INSERT_KEY,blog.getId());
        return insert > 0 ? true : false;
    }


    public void isBlogLiked(Blog blog) {
        User user = SecurityUtil.getUser();
        if(user==null) {
            return ;
        }
        Long userId = user.getId();

        String key = RedisConstants.BLOG_LIKED_KEY + blog.getId();
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        blog.setIsLike(score != null);
    }

    // 点赞和取消点赞
    @Override
    public void likeBlog(Long id) {
        String key = RedisConstants.BLOG_LIKED_KEY + id;
        User user = SecurityUtil.getUser();
        Long userId = user.getId();
        System.out.println(user);
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
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
        User user = SecurityUtil.getUser();
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

    // 查询某人的所有帖子
    @Override
    public PageResult<Blog>  queryBlogByUserId(PageParams params, Long id) {

        User user = SecurityUtil.getUser();
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

    // 查询热门帖子按照点赞排序
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

    @Override
    public void deleteBlog(Long id) {
        rabbitTemplate.convertAndSend(MqConstants.BLOG_EXCHANGE,MqConstants.BLOG_DELETE_KEY,id);
        blogMapper.deleteById(id);

    }


    private void updateBlog(Blog blog) {
        Long userId = blog.getUserId();
        User user = SecurityUtil.getUser();
        blog.setIcon(user.getUserpic());
        blog.setName(user.getNickname());
    }
}
