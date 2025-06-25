package com.thqqqqp.domain.auth.model.entity;

import com.thqqqqp.domain.auth.model.valobj.UserId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
public class User {
    private UserId id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private String studentNo;
    private String userType;
    private Boolean status;
    private String registerType;
    private String source;
    private String unionid;
    private Set<UserRole> roles;
    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;
    private String remark;

    private User(String username, String password, String userType) {
        this.username = Objects.requireNonNull(username, "用户名不能为空");
        this.password = Objects.requireNonNull(password, "密码不能为空");
        this.userType = Objects.requireNonNull(userType, "用户类型不能为空");
        this.status = true;
        this.registerType = "system";
        this.roles = new HashSet<>();
    }

    public static User create(String username, String password, String userType, String nickname, 
                            String phone, String email, String studentNo, String source) {
        User user = new User(username, password, userType);
        user.nickname = nickname;
        user.phone = phone;
        user.email = email;
        user.studentNo = studentNo;
        user.source = source;
        return user;
    }

    public void addRole(UserRole role) {
        this.roles.add(Objects.requireNonNull(role, "角色不能为空"));
    }

    public void removeRole(UserRole role) {
        this.roles.remove(Objects.requireNonNull(role, "角色不能为空"));
    }

    public boolean hasRole(UserRole role) {
        return this.roles.contains(Objects.requireNonNull(role, "角色不能为空"));
    }

    public void disable() {
        this.status = false;
    }

    public void enable() {
        this.status = true;
    }

    public void changePassword(String newPassword) {
        this.password = Objects.requireNonNull(newPassword, "新密码不能为空");
    }

    public void updateProfile(String nickname, String phone, String email) {
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
    }
}