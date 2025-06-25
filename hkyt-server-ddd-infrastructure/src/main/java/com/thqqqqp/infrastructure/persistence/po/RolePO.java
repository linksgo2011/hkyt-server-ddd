package com.thqqqqp.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色表实体
 */
@Data
@TableName("sys_role")
public class RolePO {
    
    @TableId
    private Long id;
    
    private String name;
    
    private String code;
    
    private Integer status;
    
    private String createdBy;
    
    private LocalDateTime createdTime;
    
    private String updatedBy;
    
    private LocalDateTime updatedTime;
    
    private String remark;
}