package com.zb.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestBlogParams {
    private String key;
    private Long college_id;
    private Integer page;
    private Integer size;

}
