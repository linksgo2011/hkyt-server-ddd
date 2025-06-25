package com.thqqqqp.domain.auth.model.entity;

import com.thqqqqp.domain.auth.model.valobj.PermissionId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Permission {
    private PermissionId id;
    private String name;
    private String code;
    private String type;
    private String url;
    private String method;
    private Long parentId;
    private Boolean status;
    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;
    private String remark;

    private Permission(String name, String code, String type, String url, String method, Long parentId) {
        this.name = Objects.requireNonNull(name, "权限名称不能为空");
        this.code = Objects.requireNonNull(code, "权限编码不能为空");
        this.type = Objects.requireNonNull(type, "权限类型不能为空");
        this.url = url;
        this.method = method;
        this.parentId = parentId;
        this.status = true;
    }

    public static Permission create(String name, String code, String type, String url, String method, Long parentId, String remark) {
        Permission permission = new Permission(name, code, type, url, method, parentId);
        permission.remark = remark;
        return permission;
    }

    public void disable() {
        this.status = false;
    }

    public void enable() {
        this.status = true;
    }
}