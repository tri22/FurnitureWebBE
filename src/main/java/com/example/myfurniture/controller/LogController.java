package com.example.myfurniture.controller;

import com.example.myfurniture.dto.response.ApiResponse;
import com.example.myfurniture.entity.Log;
import com.example.myfurniture.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/log")
@Slf4j
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("/all-async")
    public CompletableFuture<ApiResponse<List<Log>>> getAllLogAsync() {
        return logService.getAllLogAsync().thenApply(logs -> {
            ApiResponse<List<Log>> res = new ApiResponse<>();
            res.setResult(logs);
            return res;
        });
    }

}

