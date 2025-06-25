package com.thqqqqp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "角色信息")
public class RoleDTO {

    @Schema(description = "角色ID")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色描述")
    private String remark;

    @Schema(description = "角色状态")
    private Boolean status;

    @Schema(description = "角色拥有的权限列表")
    private List<PermissionDTO> permissions;
}