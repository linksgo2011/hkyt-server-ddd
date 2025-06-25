package com.thqqqqp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.thqqqqp.domain.auth.adapter.repository.UserRepository;
import com.thqqqqp.domain.auth.model.entity.User;
import com.thqqqqp.domain.auth.model.entity.UserRole;
import com.thqqqqp.domain.auth.model.valobj.UserId;
import com.thqqqqp.infrastructure.persistence.converter.UserConverter;
import com.thqqqqp.infrastructure.persistence.mapper.UserMapper;
import com.thqqqqp.infrastructure.persistence.mapper.UserRoleMapper;
import com.thqqqqp.infrastructure.persistence.po.UserPO;
import com.thqqqqp.infrastructure.persistence.po.UserRolePO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    public UserRepositoryImpl(UserMapper userMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public User save(User user) {
        UserPO po = UserConverter.toPO(user);
        if (po.getId() == null) {
            userMapper.insert(po);
            // 新增用户角色关联
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                for (UserRole role : user.getRoles()) {
                    UserRolePO userRolePO = new UserRolePO();
                    userRolePO.setUserId(po.getId());
                    userRolePO.setRoleId(role.getRoleId());
                    userRoleMapper.insert(userRolePO);
                }
            }
        } else {
            userMapper.updateById(po);
            // 更新用户角色关联
            // 先删除原有关联
            LambdaQueryWrapper<UserRolePO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserRolePO::getUserId, po.getId());
            userRoleMapper.delete(wrapper);
            // 重新添加关联
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                for (UserRole role : user.getRoles()) {
                    UserRolePO userRolePO = new UserRolePO();
                    userRolePO.setUserId(po.getId());
                    userRolePO.setRoleId(role.getRoleId());
                    userRoleMapper.insert(userRolePO);
                }
            }
        }
        // 查询并设置角色
        List<Long> roleIds = userRoleMapper.findRoleIdsByUserId(po.getId());
        Set<UserRole> roles = roleIds.stream()
                .map(roleId -> UserRole.create(po.getId(), roleId))
                .collect(Collectors.toSet());
        User savedUser = UserConverter.toEntity(po);
        savedUser.setRoles(roles);
        return savedUser;
    }

    @Override
    public Optional<User> findById(UserId id) {
        UserPO po = userMapper.selectById(id.getValue());
        if (po == null) {
            return Optional.empty();
        }
        User user = UserConverter.toEntity(po);
        // 查询并设置角色
        List<Long> roleIds = userRoleMapper.findRoleIdsByUserId(po.getId());
        Set<UserRole> roles = roleIds.stream()
                .map(roleId -> UserRole.create(po.getId(), roleId))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        List<UserPO> pos = userMapper.selectList(null);
        return pos.stream()
                .map(po -> {
                    User user = UserConverter.toEntity(po);
                    // 查询并设置角色
                    List<Long> roleIds = userRoleMapper.findRoleIdsByUserId(po.getId());
                    Set<UserRole> roles = roleIds.stream()
                            .map(roleId -> UserRole.create(po.getId(), roleId))
                            .collect(Collectors.toSet());
                    user.setRoles(roles);
                    return user;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPO::getUsername, username);
        UserPO po = userMapper.selectOne(wrapper);
        if (po == null) {
            return Optional.empty();
        }
        User user = UserConverter.toEntity(po);
        // 查询并设置角色
        List<Long> roleIds = userRoleMapper.findRoleIdsByUserId(po.getId());
        Set<UserRole> roles = roleIds.stream()
                .map(roleId -> UserRole.create(po.getId(), roleId))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        // TODO
        return Optional.empty();
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        // TODO
        return Optional.empty();
    }

    @Override
    public boolean existsByUsername(String username) {
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPO::getUsername, username);
        return userMapper.selectCount(wrapper) > 0;
    }

    @Override
    public void delete(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("用户或用户ID不能为空");
        }
        Long userId = user.getId().getValue();
        if (userMapper.selectById(userId) == null) {
            throw new IllegalArgumentException("用户不存在，ID: " + userId);
        }
        // 先删除用户角色关联
        LambdaQueryWrapper<UserRolePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRolePO::getUserId, userId);
        userRoleMapper.delete(wrapper);
        // 再删除用户
        userMapper.deleteById(userId);
    }
}