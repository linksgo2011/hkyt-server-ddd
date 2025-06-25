package com.thqqqqp.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色-权限关联表实体
 */
@Data
@TableName("sys_role_permission")
public class RolePermissionPO {
    
    @TableId
    private Long id;
    
    private Long roleId;
    
    private Long permissionId;
    
    private String createdBy;
    
    private LocalDateTime createdTime;
    
    private String updatedBy;
    
    private LocalDateTime updatedTime;
    
    private String remark;
}