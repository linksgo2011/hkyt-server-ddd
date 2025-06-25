package com.thqqqqp.domain.auth.model.entity;

import com.thqqqqp.domain.auth.model.valobj.RoleId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
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
    private Set<RolePermission> permissions;

    private Role(String name, String code) {
        this.name = Objects.requireNonNull(name, "角色名称不能为空");
        this.code = Objects.requireNonNull(code, "角色编码不能为空");
        this.status = true;
        this.permissions = new HashSet<>();
    }

    public static Role create(String name, String code, String remark) {
        Role role = new Role(name, code);
        role.remark = remark;
        return role;
    }

    public void addPermission(RolePermission permission) {
        this.permissions.add(Objects.requireNonNull(permission, "权限不能为空"));
    }

    public void addPermissions(Collection<RolePermission> permissionList) {
        if (permissionList != null) {
            for (RolePermission permission : permissionList) {
                addPermission(permission);
            }
        }
    }

    public void removePermission(RolePermission permission) {
        this.permissions.remove(Objects.requireNonNull(permission, "权限不能为空"));
    }

    public boolean hasPermission(RolePermission permission) {
        return this.permissions.contains(Objects.requireNonNull(permission, "权限不能为空"));
    }

    public void disable() {
        this.status = false;
    }

    public void enable() {
        this.status = true;
    }
}