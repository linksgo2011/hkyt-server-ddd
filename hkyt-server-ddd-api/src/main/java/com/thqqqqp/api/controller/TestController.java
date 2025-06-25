package com.thqqqqp.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "测试接口", description = "用于测试 Swagger 配置的接口")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Operation(summary = "问候接口", description = "根据传入的名字返回问候语")
    @GetMapping("/hello")
    public String hello(@Parameter(description = "用户名") @RequestParam(required = false, defaultValue = "World") String name) {
        return String.format("Hello, %s!", name);
    }
}