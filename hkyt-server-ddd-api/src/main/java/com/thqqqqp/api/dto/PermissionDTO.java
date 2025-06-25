package com.thqqqqp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 权限数据传输对象
 */
@Data
@Schema(description = "权限信息")
public class PermissionDTO {

    @Schema(description = "权限ID")
    private Long id;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限编码")
    private String code;

    @Schema(description = "权限类型：menu-菜单，button-按钮")
    private String type;

    @Schema(description = "权限URL")
    private String url;

    @Schema(description = "请求方法")
    private String method;

    @Schema(description = "父级权限ID")
    private Long parentId;

    @Schema(description = "状态：1-启用，0-禁用")
    private Integer status;
}