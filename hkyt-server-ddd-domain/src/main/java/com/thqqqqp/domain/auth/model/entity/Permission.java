package com.thqqqqp.domain.auth.model.entity;

import com.thqqqqp.domain.auth.model.valobj.PermissionId;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
public class Permission {
    private PermissionId id;
    private String name;
    private String code;
    private String type;     // menu/button/api
    private String url;
    private String method;
    private Long parentId;
    private Boolean status;
    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;
    private String remark;

    /**
     * 私有构造
     */
    private Permission(String name, String code, String type, String url, String method, Long parentId) {
        this.name = Objects.requireNonNull(name, "权限名称不能为空");
        this.code = Objects.requireNonNull(code, "权限编码不能为空");
        this.type = Objects.requireNonNull(type, "权限类型不能为空");
        this.url = url;
        this.method = method;
        this.parentId = parentId;
        this.status = true;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }

    /**
     * 工厂方法：创建新权限
     */
    public static Permission create(String name, String code, String type, String url, String method, Long parentId, String remark) {
        Permission permission = new Permission(name, code, type, url, method, parentId);
        permission.remark = remark;
        return permission;
    }

/*    *//**
     * 全量还原构造
     *//*
    public Permission(Long id, String name, String code, String type, String url,
                      String method, Long parentId, Boolean status,
                      String createdBy, LocalDateTime createdTime,
                      String updatedBy, LocalDateTime updatedTime, String remark) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.type = type;
        this.url = url;
        this.method = method;
        this.parentId = parentId;
        this.status = status;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.updatedBy = updatedBy;
        this.updatedTime = updatedTime;
        this.remark = remark;
    }*/

    /**
     * 禁用权限
     */
    public void disable() {
        this.status = false;
        this.updatedTime = LocalDateTime.now();
    }

    /**
     * 启用权限
     */
    public void enable() {
        this.status = true;
        this.updatedTime = LocalDateTime.now();
    }

    /**
     * 更新权限信息
     */
    public void update(String name, String type, String url, String method, Long parentId, String remark) {
        this.name = Objects.requireNonNull(name, "权限名称不能为空");
        this.type = Objects.requireNonNull(type, "权限类型不能为空");
        this.url = url;
        this.method = method;
        this.parentId = parentId;
        this.remark = remark;
        this.updatedTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
