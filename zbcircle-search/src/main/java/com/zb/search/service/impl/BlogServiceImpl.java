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
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ghj
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private BlogServiceClient blogServiceClient;


    @Override
    public PageResult getList(RequestBlogParams params) {

        SearchRequest request = new SearchRequest("blog");
        buildBasicQuery(params,request);
        Integer page = params.getPage();
        Integer size = params.getSize();

        request.source().from((page-1)*size).size(size);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return handleResponse(response,page,size);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析结果
     * @param response
     * @param page
     * @param size
     * @return
     */
    private PageResult handleResponse(SearchResponse response,Integer page,Integer size) {
        List<BlogDoc> list = new ArrayList<>();
        SearchHits searchHits = response.getHits();
        Long total = searchHits.getTotalHits().value;
        SearchHit[] hits = searchHits.getHits();
        Arrays.stream(hits).forEach(hit->{
            String json = hit.getSourceAsString();

            BlogDoc hotelDoc = JSON.parseObject(json, BlogDoc.class);
            Object[] sortValues = hit.getSortValues();
            list.add(hotelDoc);
        });
        return new PageResult(list,total,page,size);
    }

    /**
     * 构建查询条件
     * @param params
     * @param request
     */
    public void buildBasicQuery(RequestBlogParams params, SearchRequest request) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        String key = params.getKey();

        if(key ==null || "".equals(key)) {
            boolQuery.must(QueryBuilders.matchAllQuery());
        }else {
            boolQuery.must(QueryBuilders.matchQuery("all", key));
        }

        if(params.getCollege_id() != null && !params.getCollege_id().equals("")){
            boolQuery.filter(QueryBuilders.termQuery("collegeId", params.getCollege_id()));
        }

        request.source().query(boolQuery);
    }


    @Override
    public List<String> getSuggestions(String pre) {
        try {
            // 1.准备Request
            SearchRequest request = new SearchRequest("blog");
            // 2.准备DSL
            request.source().suggest(new SuggestBuilder().addSuggestion(    //new SuggestBuilder()不是SuggestBuilders
                    "suggestions",    //自定义补全的名字，后面根据它解析response
                    SuggestBuilders.completionSuggestion("suggestion")    //字段名
                            .prefix(pre)    //需要补全的文本
                            .skipDuplicates(true)
                            .size(10)
            ));
            // 3.发起请求
            SearchResponse response = null;

            response = client.search(request, RequestOptions.DEFAULT);

            // 4.解析结果
            Suggest suggest = response.getSuggest();
            // 4.1.根据补全查询名称，获取补全结果，注意返回值和ctrl+alt+v生成的内容不一样
            CompletionSuggestion suggestions = suggest.getSuggestion("suggestions");
            // 4.2.获取options
            List<CompletionSuggestion.Entry.Option> options = suggestions.getOptions();
            // 4.3.遍历
            List<String> list = new ArrayList<>(options.size());
            for (CompletionSuggestion.Entry.Option option : options) {
                String text = option.getText().toString();
                list.add(text);
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            throw new ZbException("添加失败");
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
          DeleteRequest request = new DeleteRequest("hotel", id.toString());

            client.delete(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ZbException("删除失败");
        }
    }
}
