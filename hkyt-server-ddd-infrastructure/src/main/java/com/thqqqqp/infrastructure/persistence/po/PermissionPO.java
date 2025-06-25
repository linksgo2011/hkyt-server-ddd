package com.thqqqqp.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 权限表实体
 */
@Data
@TableName("sys_permission")
public class PermissionPO {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private String code;
    
    private String type;
    
    private String url;
    
    private String method;
    
    private Long parentId;
    
    private Integer status;
    
    private String createdBy;
    
    private LocalDateTime createdTime;
    
    private String updatedBy;
    
    private LocalDateTime updatedTime;
    
    private String remark;
}