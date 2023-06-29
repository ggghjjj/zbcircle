package com.zb.chat.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "record")
@Builder
public class Record {
    private String _id;
    private String time;
    private String content;
    private String fromName;
    private String fromAvatar;
    private String toName;
}
