package com.zb.chat.service.impl;

import com.zb.chat.feignclient.UserServiceClient;
import com.zb.chat.pojo.Record;
import com.zb.chat.pojo.User;
import com.zb.chat.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserServiceClient userServiceClient;
    @Override
    public List<Record> getRecords(String fromUser, String toUser) {

        if(toUser.equals("all")) {
            Criteria one = Criteria.where("toName").is(toUser);
            Query query = new Query(one);
            List<Record> records = mongoTemplate.find(query, Record.class, "record");
            return records;
        }

        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("fromName").is(fromUser).and("toName").is(toUser),
                Criteria.where("fromName").is(toUser).and("toName").is(fromUser)
        );
        Query query = new Query(criteria);
        List<Record> records = mongoTemplate.find(query, Record.class, "record");

        return records;
    }

    @Override
    public Boolean insert(Record record) {
        Record re = mongoTemplate.insert(record, "record");
        if(re==null) return false;
        return true;
    }


}
