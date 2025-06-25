package com.thqqqqp.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户表实体
 */
@Data
@TableName("sys_user")
public class UserPO {
    
    @TableId
    private Long id;
    
    private String username;
    
    private String password;
    
    private String nickname;
    
    private String phone;
    
    private String email;
    
    private String studentNo;
    
    private String userType;
    
    private Integer status;
    
    private String registerType;
    
    private String source;
    
    private String unionid;
    
    private String createdBy;
    
    private LocalDateTime createdTime;
    
    private String updatedBy;
    
    private LocalDateTime updatedTime;
    
    private String remark;
}