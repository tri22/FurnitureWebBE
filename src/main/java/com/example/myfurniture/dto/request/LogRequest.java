package com.example.myfurniture.dto.request;

import com.example.myfurniture.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogRequest {
    private User user;
    private Date date;
    private String ip;
    private String action;
    private String resource;
    private Object dataIn;
    private Object dataOut;
    private String level;

}
