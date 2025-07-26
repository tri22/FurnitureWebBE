package com.example.myfurniture.service;


import com.example.myfurniture.dto.request.LogRequest;
import com.example.myfurniture.entity.Log;
import com.example.myfurniture.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "error_serializing";
        }
    }

    public void addLog(LogRequest request) {
        Log log = new Log();
        log.setIp(request.getIp());
        log.setUser(request.getUser());
        log.setDate(request.getDate());
        log.setAction(request.getAction());
        log.setDataOut(toJson(request.getDataOut()));
        log.setDataIn(toJson(request.getDataIn()));
        log.setLevel(request.getLevel());
        log.setResource(request.getResource());
        logRepository.save(log);

    }

    @Async
    public CompletableFuture<List<Log>> getAllLogAsync() {
        return CompletableFuture.completedFuture(logRepository.findAll());
    }

}
