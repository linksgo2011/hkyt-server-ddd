package com.thqqqqp.domain.auth.model.entity;

import com.thqqqqp.domain.auth.model.valobj.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
public class User {

    // 领域属性
    private UserId id;
    private String username;
    private String nickname;
    private String password;
    private PhoneNumber phone;
    private Email email;
    private StudentNo studentNo;
    private UserType userType;
    private Boolean status;
    private RegisterType registerType;
    private SourceType source;
    private String unionId;
    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;
    private String remark;

    // 用户-角色关系（领域内强关系，支持一人多角色/切换）
    private Set<Role> roles = new HashSet<>();

    /**
     * 私有构造，确保外部不能随意 new
     */
    private User(String username, String password) {
        this.username = Objects.requireNonNull(username, "用户名不能为空");
        this.password = Objects.requireNonNull(password, "密码不能为空");
        this.status = true;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }

/*    *//**
     * 全量还原构造（用于数据库->领域还原）
     *//*
    public User(Long id, String username, String nickname, String password,
                PhoneNumber phone, Email email, StudentNo studentNo, UserType userType, Boolean status,
                RegisterType registerType, SourceType source, String unionId,
                String createdBy, LocalDateTime createdTime, String updatedBy, LocalDateTime updatedTime, String remark) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.studentNo = studentNo;
        this.userType = userType;
        this.status = status;
        this.registerType = registerType;
        this.source = source;
        this.unionId = unionId;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.updatedBy = updatedBy;
        this.updatedTime = updatedTime;
        this.remark = remark;
    }*/

    /**
     * 工厂方法：创建新用户
     */
    public static User create(String username, String password, String nickname,
                              String phone, String email, String studentNo,
                              String userType, String registerType, String source,
                              String unionId, String remark) {
        User user = new User(username, password);
        user.nickname = nickname;
        user.phone = new PhoneNumber(phone);
        user.email = new Email(email);
        user.studentNo = new StudentNo(studentNo);
        user.userType = new UserType(userType);
        user.registerType = new RegisterType(registerType);
        user.source = new SourceType(source);
        user.unionId = unionId;
        user.remark = remark;
        return user;
    }

    /**
     * 更新基本信息
     */
    public void updateProfile(String nickname, String phone, String email, String studentNo) {
        this.nickname = nickname;
        this.phone = new PhoneNumber(phone);
        this.email = new Email(email);
        this.studentNo = new StudentNo(studentNo);
        this.updatedTime = LocalDateTime.now();
    }

    /**
     * 修改密码
     */
    public void changePassword(String newPassword) {
        this.password = Objects.requireNonNull(newPassword, "新密码不能为空");
        this.updatedTime = LocalDateTime.now();
    }

    /**
     * 启用账户
     */
    public void enable() {
        this.status = true;
        this.updatedTime = LocalDateTime.now();
    }

    /**
     * 禁用账户
     */
    public void disable() {
        this.status = false;
        this.updatedTime = LocalDateTime.now();
    }

    /**
     * 绑定/变更 unionId
     */
    public void bindUnionId(String unionId) {
        this.unionId = unionId;
        this.updatedTime = LocalDateTime.now();
    }

    /**
     * 分配角色
     */
    public void addRole(Role role) {
        this.roles.add(Objects.requireNonNull(role, "角色不能为空"));
    }

    public void addRoles(Collection<Role> roleList) {
        if (roleList != null) {
            for (Role role : roleList) {
                addRole(role);
            }
        }
    }

    /**
     * 移除角色
     */
    public void removeRole(Role role) {
        this.roles.remove(Objects.requireNonNull(role, "角色不能为空"));
    }

    /**
     * 判断是否有某个角色
     */
    public boolean hasRole(Role role) {
        return this.roles.contains(role);
    }

    /**
     * 判断是否管理员
     */
    public boolean isAdmin() {
        return this.userType != null && UserType.Type.ADMIN.equals(this.userType.getValue());
    }

}
