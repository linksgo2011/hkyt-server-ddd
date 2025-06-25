package com.thqqqqp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新用户请求")
public class UpdateUserRequest {

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "是否启用")
    private Boolean enabled;
}