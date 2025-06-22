use `hkyt-server-ddd`;

INSERT INTO sys_role (name,code, status) VALUES
    ('超级管理员', 'admin', 1),
    ('导员', 'counselor', 1),
    ('学生', 'student', 1);

INSERT INTO sys_permission (name, code, type, url, method, status) VALUES
   ('用户管理-查询', 'USER_QUERY', 'api', '/api/user/list', 'GET', 1),
   ('用户管理-新增', 'USER_ADD', 'api', '/api/user/add', 'POST', 1),
   ('角色管理-分配', 'ROLE_ASSIGN', 'api', '/api/role/assign', 'POST', 1),
   ('活动发布', 'ACTIVITY_PUBLISH', 'api', '/api/activity/publish', 'POST', 1),
   ('活动报名审批', 'ACTIVITY_APPROVE', 'api', '/api/activity/approve', 'POST', 1),
   ('个人信息查看', 'PROFILE_VIEW', 'api', '/api/profile', 'GET', 1),
   ('个人信息修改', 'PROFILE_EDIT', 'api', '/api/profile', 'PUT', 1);

INSERT INTO sys_user (username,nickname , password, phone, email, student_no, user_type, status, register_type) VALUES
    ('admin', '超级管理员','admin123', '18888888888', 'admin@test.com', NULL, 'admin', 1, 'system'),
    ('counselor001', '测试导员','counselor123', '18888888887', 'counselor@test.com', NULL, 'teacher', 1, 'system'),
    ('student001','测试学生', 'student123', '18888888886', 'student1@test.com', '2024011007', 'student', 1, 'system');
INSERT INTO sys_user_role (user_id, role_id)
VALUES
    (1, 1), -- admin分配admin角色
    (2, 2), -- counselor分配counselor角色
    (3, 3); -- student分配student角色

-- admin拥有全部权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 1, id FROM sys_permission;

-- counselor拥有活动发布、审批、学生信息查看权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 2, id FROM sys_permission WHERE code IN ('ACTIVITY_PUBLISH', 'ACTIVITY_APPROVE', 'PROFILE_VIEW');

-- student拥有个人信息查看和编辑、活动报名等权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 3, id FROM sys_permission WHERE code IN ('PROFILE_VIEW', 'PROFILE_EDIT');
