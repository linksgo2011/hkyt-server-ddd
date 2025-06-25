package com.thqqqqp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "创建用户请求")
public class CreateUserRequest {

    @Schema(description = "用户名", required = true)
    private String username;

    @Schema(description = "邮箱", required = true)
    private String email;

    @Schema(description = "密码", required = true)
    private String password;
}