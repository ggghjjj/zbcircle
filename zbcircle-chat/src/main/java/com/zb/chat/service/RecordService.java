package com.zb.chat.service;

import com.zb.chat.pojo.Record;

import java.util.List;

public interface RecordService {

    List<Record> getRecords(String fromUser, String toUser);

    Boolean insert(Record record);
}
