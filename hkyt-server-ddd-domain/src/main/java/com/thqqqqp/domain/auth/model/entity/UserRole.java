package com.thqqqqp.domain.auth.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
public class UserRole {
    private Long userId;
    private Long roleId;
    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;
    private String remark;

    private UserRole(Long userId, Long roleId) {
        this.userId = Objects.requireNonNull(userId, "用户ID不能为空");
        this.roleId = Objects.requireNonNull(roleId, "角色ID不能为空");
    }

    public static UserRole create(Long userId, Long roleId) {
        return new UserRole(userId, roleId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(userId, userRole.userId) && Objects.equals(roleId, userRole.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}