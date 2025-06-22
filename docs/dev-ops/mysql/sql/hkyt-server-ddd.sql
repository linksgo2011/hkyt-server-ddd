/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50639
 Source Host           : localhost:3306
 Source Schema         : road-map

 Target Server Type    : MySQL
 Target Server Version : 50639
 File Encoding         : 65001

 Date: 15/07/2023 09:26:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE database if NOT EXISTS `hkyt-server-ddd` default character set utf8mb4 collate utf8mb4_0900_ai_ci;
use `hkyt-server-ddd`;

-- 用户主表（领域核心实体/聚合根）
CREATE TABLE sys_user (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                          username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名（支持手机号/邮箱/自定义账号等）',
                          nickname VARCHAR(255) DEFAULT NULL COMMENT '昵称（可选）',
                          password VARCHAR(100) DEFAULT NULL COMMENT '密码（加密存储）',
                          phone VARCHAR(20) UNIQUE DEFAULT NULL COMMENT '手机号',
                          email VARCHAR(100) UNIQUE DEFAULT NULL COMMENT '邮箱',
                          student_no VARCHAR(20) UNIQUE DEFAULT NULL COMMENT '学号',
                          user_type VARCHAR(20) DEFAULT 'student' COMMENT '用户类型（student/teacher/admin/parent/volunteer等）',
                          status TINYINT DEFAULT 1 COMMENT '账号状态（1正常，0禁用）',

    -- 新增字段
                          register_type VARCHAR(20) DEFAULT 'system' COMMENT '注册方式（system/phone/email/wechat_mp/qq等）',
                          source VARCHAR(50) DEFAULT NULL COMMENT '用户来源（如小程序/公众号/活动/批量导入等）',
                          unionid VARCHAR(64) DEFAULT NULL COMMENT '微信/第三方unionid（跨平台唯一标识）',

                          created_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
                          created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          updated_by VARCHAR(50) DEFAULT NULL COMMENT '修改人',
                          updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                          remark VARCHAR(255) DEFAULT NULL COMMENT '备注'
) COMMENT='系统用户表';

-- 第三方授权表（支持一用户多渠道绑定，联合唯一约束）
CREATE TABLE sys_user_oauth (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                                user_id BIGINT NOT NULL COMMENT '用户ID',
                                oauth_type VARCHAR(32) NOT NULL COMMENT '授权类型（wechat_mp/qq/feishu/钉钉等）',
                                openid VARCHAR(64) NOT NULL COMMENT '第三方openid',
                                unionid VARCHAR(64) DEFAULT NULL COMMENT '第三方平台unionid（与主表可对齐）',
                                extra JSON DEFAULT NULL COMMENT '额外信息（如昵称、头像等）',

                                created_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
                                created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                updated_by VARCHAR(50) DEFAULT NULL COMMENT '修改人',
                                updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                remark VARCHAR(255) DEFAULT NULL COMMENT '备注',

                                UNIQUE KEY uk_user_oauth (oauth_type, openid)
) COMMENT='用户第三方授权绑定表';

-- 系统角色表（面向领域角色/支持角色切换）
CREATE TABLE sys_role (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                          name VARCHAR(50) NOT NULL COMMENT '角色名称',
                          code VARCHAR(50) UNIQUE NOT NULL COMMENT '角色唯一标识',
                          status TINYINT DEFAULT 1 COMMENT '角色状态（1正常，0禁用）',

                          created_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
                          created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          updated_by VARCHAR(50) DEFAULT NULL COMMENT '修改人',
                          updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                          remark VARCHAR(255) DEFAULT NULL COMMENT '备注'
) COMMENT='系统角色表';

-- 权限资源表（支持菜单/按钮/API权限，前后端可共用）
CREATE TABLE sys_permission (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                                name VARCHAR(100) NOT NULL COMMENT '权限名称',
                                code VARCHAR(100) UNIQUE NOT NULL COMMENT '权限标识（唯一编码）',
                                type VARCHAR(20) DEFAULT 'api' COMMENT '权限类型（menu/button/api）',
                                url VARCHAR(200) DEFAULT NULL COMMENT '资源路径（API或菜单路由）',
                                method VARCHAR(10) DEFAULT NULL COMMENT 'HTTP方法（GET/POST等，API类权限用）',
                                parent_id BIGINT DEFAULT NULL COMMENT '父权限ID（层级菜单）',
                                status TINYINT DEFAULT 1 COMMENT '权限状态（1正常，0禁用）',

                                created_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
                                created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                updated_by VARCHAR(50) DEFAULT NULL COMMENT '修改人',
                                updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                remark VARCHAR(255) DEFAULT NULL COMMENT '备注'
) COMMENT='系统权限资源表';

-- 用户角色关联表（联合主键，不要自增ID，直接支持一人多角色/角色切换）
CREATE TABLE sys_user_role (
                               user_id BIGINT NOT NULL COMMENT '用户ID',
                               role_id BIGINT NOT NULL COMMENT '角色ID',

                               created_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
                               created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               updated_by VARCHAR(50) DEFAULT NULL COMMENT '修改人',
                               updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                               remark VARCHAR(255) DEFAULT NULL COMMENT '备注',

                               PRIMARY KEY (user_id, role_id)
) COMMENT='用户与角色关联表';

-- 角色权限关联表（联合主键，不要自增ID）
CREATE TABLE sys_role_permission (
                                     role_id BIGINT NOT NULL COMMENT '角色ID',
                                     permission_id BIGINT NOT NULL COMMENT '权限ID',

                                     created_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
                                     created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     updated_by VARCHAR(50) DEFAULT NULL COMMENT '修改人',
                                     updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                     remark VARCHAR(255) DEFAULT NULL COMMENT '备注',

                                     PRIMARY KEY (role_id, permission_id)
) COMMENT='角色与权限关联表';

-- 学生信息扩展表（后续可增family_info等其他扩展表，领域建模灵活挂载）
CREATE TABLE student_info (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                              user_id BIGINT NOT NULL COMMENT '关联用户ID',
                              college_id BIGINT DEFAULT NULL COMMENT '学院ID',
                              class_id BIGINT DEFAULT NULL COMMENT '班级ID',
                              real_name VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
                              gender TINYINT DEFAULT NULL COMMENT '性别（1男，2女，0未知）',
                              birth_date DATE DEFAULT NULL COMMENT '出生日期',
                              avatar VARCHAR(255) DEFAULT NULL COMMENT '头像地址',

                              created_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
                              created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              updated_by VARCHAR(50) DEFAULT NULL COMMENT '修改人',
                              updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                              remark VARCHAR(255) DEFAULT NULL COMMENT '备注',

                              UNIQUE KEY uk_user_id (user_id)
) COMMENT='学生信息扩展表';

-- 家庭信息扩展表（示例，灵活扩展领域对象，不影响主表）
CREATE TABLE family_info (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                             user_id BIGINT NOT NULL COMMENT '关联用户ID',
                             contact_name VARCHAR(50) DEFAULT NULL COMMENT '联系人姓名',
                             relation VARCHAR(20) DEFAULT NULL COMMENT '与学生关系',
                             phone VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
                             address VARCHAR(255) DEFAULT NULL COMMENT '家庭住址',

                             created_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
                             created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             updated_by VARCHAR(50) DEFAULT NULL COMMENT '修改人',
                             updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                             remark VARCHAR(255) DEFAULT NULL COMMENT '备注',

                             KEY idx_user_id (user_id)
) COMMENT='家庭信息扩展表';

