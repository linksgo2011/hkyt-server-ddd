package com.thqqqqp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "创建权限请求")
public class CreatePermissionRequest {
    @Schema(description = "权限名称", required = true)
    private String name;
    
    @Schema(description = "权限编码", required = true)
    private String code;
    
    @Schema(description = "权限类型", required = true)
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