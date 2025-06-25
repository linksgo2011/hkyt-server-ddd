package com.thqqqqp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新权限请求")
public class UpdatePermissionRequest {
    @Schema(description = "权限名称")
    private String name;
    
    @Schema(description = "权限类型")
    private String type;
    
    @Schema(description = "权限URL")
    private String url;
    
    @Schema(description = "请求方法")
    private String method;
    
    @Schema(description = "父权限ID")
    private Long parentId;
    
    @Schema(description = "备注")
    private String remark;
}