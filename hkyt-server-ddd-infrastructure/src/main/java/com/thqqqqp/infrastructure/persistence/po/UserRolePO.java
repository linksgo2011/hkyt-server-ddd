package com.thqqqqp.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户-角色关联表实体
 */
@Data
@TableName("sys_user_role")
public class UserRolePO {
    
    @TableId
    private Long id;
    
    private Long userId;
    
    private Long roleId;
    
    private String createdBy;
    
    private LocalDateTime createdTime;
    
    private String updatedBy;
    
    private LocalDateTime updatedTime;
    
    private String remark;
}