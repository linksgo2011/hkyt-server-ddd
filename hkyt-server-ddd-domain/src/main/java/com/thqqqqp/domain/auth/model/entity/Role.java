package com.thqqqqp.domain.auth.model.entity;

import com.thqqqqp.domain.auth.model.valobj.RoleId;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@ToString
public class Role {
    private RoleId id;
    private String name;
    private String code;
    private Boolean status;
    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;
    private String remark;

    // 权限集合
    private Set<Permission> permissions = new HashSet<>();

    /**
     * 私有构造
     */
    private Role(String name, String code) {
        this.name = Objects.requireNonNull(name, "角色名称不能为空");
        this.code = Objects.requireNonNull(code, "角色编码不能为空");
        this.status = true;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }

    /**
     * 工厂方法：创建新角色
     */
    public static Role create(String name, String code, String remark) {
        Role role = new Role(name, code);
        role.remark = remark;
        return role;
    }

/*    *//**
     * 全量还原构造（数据库->领域）
     *//*
    public Role(Long id, String name, String code, Boolean status,
                String createdBy, LocalDateTime createdTime, String updatedBy,
                LocalDateTime updatedTime, String remark) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.status = status;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.updatedBy = updatedBy;
        this.updatedTime = updatedTime;
        this.remark = remark;
    }*/

    /**
     * 添加权限
     */
    public void addPermission(Permission permission) {
        this.permissions.add(Objects.requireNonNull(permission, "权限不能为空"));
    }

    public void addPermissions(Collection<Permission> permissionList) {
        if (permissionList != null) {
            for (Permission permission : permissionList) {
                addPermission(permission);
            }
        }
    }

    /**
     * 移除权限
     */
    public void removePermission(Permission permission) {
        this.permissions.remove(Objects.requireNonNull(permission, "权限不能为空"));
    }

    /**
     * 禁用角色
     */
    public void disable() {
        this.status = false;
        this.updatedTime = LocalDateTime.now();
    }

    /**
     * 启用角色
     */
    public void enable() {
        this.status = true;
        this.updatedTime = LocalDateTime.now();
    }

    /**
     * 更新角色信息
     */
    public void update(String name, String remark) {
        this.name = Objects.requireNonNull(name, "角色名称不能为空");
        this.remark = remark;
        this.updatedTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(code, role.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
