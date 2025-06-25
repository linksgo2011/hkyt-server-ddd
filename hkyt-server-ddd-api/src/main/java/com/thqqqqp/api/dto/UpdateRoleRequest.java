package com.thqqqqp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新角色请求")
public class UpdateRoleRequest {

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色描述")
    private String remark;
}