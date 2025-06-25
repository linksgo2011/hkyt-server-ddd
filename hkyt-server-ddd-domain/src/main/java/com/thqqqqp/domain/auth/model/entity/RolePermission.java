package com.thqqqqp.domain.auth.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
public class RolePermission {
    private Long roleId;
    private Long permissionId;
    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;
    private String remark;

    private RolePermission(Long roleId, Long permissionId) {
        this.roleId = Objects.requireNonNull(roleId, "角色ID不能为空");
        this.permissionId = Objects.requireNonNull(permissionId, "权限ID不能为空");
    }

    public static RolePermission create(Long roleId, Long permissionId) {
        return new RolePermission(roleId, permissionId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return Objects.equals(roleId, that.roleId) && Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId);
    }
}