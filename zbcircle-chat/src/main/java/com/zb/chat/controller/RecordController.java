package com.zb.chat.controller;

import com.zb.auth.common.model.RestResponse;
import com.zb.chat.pojo.Record;
import com.zb.chat.service.RecordService;
import feign.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/records")
    public RestResponse getRecords(@RequestParam String fromUser,@RequestParam String toUser){
        List<Record> records = recordService.getRecords(fromUser,toUser);
        return RestResponse.success(records);
    }

    @PostMapping("/insert")
    public RestResponse insert(@RequestBody Record record){
        Boolean flag = recordService.insert(record);
        return flag ? RestResponse.success("插入成功") : RestResponse.validfail("插入失败");
    }
}
