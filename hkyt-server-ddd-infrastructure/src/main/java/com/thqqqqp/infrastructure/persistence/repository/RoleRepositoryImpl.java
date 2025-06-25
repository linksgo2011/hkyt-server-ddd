package com.thqqqqp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.thqqqqp.domain.auth.adapter.repository.RoleRepository;
import com.thqqqqp.domain.auth.model.entity.Role;
import com.thqqqqp.domain.auth.model.valobj.RoleId;
import com.thqqqqp.infrastructure.persistence.converter.RoleConverter;
import com.thqqqqp.infrastructure.persistence.mapper.RoleMapper;
import com.thqqqqp.infrastructure.persistence.mapper.RolePermissionMapper;
import com.thqqqqp.infrastructure.persistence.po.RolePO;
import com.thqqqqp.infrastructure.persistence.po.RolePermissionPO;
import com.thqqqqp.domain.auth.model.entity.RolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleMapper roleMapper;
    private final RolePermissionMapper rolePermissionMapper;

    public RoleRepositoryImpl(RoleMapper roleMapper, RolePermissionMapper rolePermissionMapper) {
        this.roleMapper = roleMapper;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public Role save(Role role) {
        RolePO po = RoleConverter.toPO(role);
        if (po.getId() == null) {
            roleMapper.insert(po);
            // 新增角色权限关联
            if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
                for (RolePermission permission : role.getPermissions()) {
                    RolePermissionPO rolePermissionPO = new RolePermissionPO();
                    rolePermissionPO.setRoleId(po.getId());
                    rolePermissionPO.setPermissionId(permission.getPermissionId());
                    rolePermissionMapper.insert(rolePermissionPO);
                }
            }
        } else {
            roleMapper.updateById(po);
            // 更新角色权限关联
            // 先删除原有关联
            LambdaQueryWrapper<RolePermissionPO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RolePermissionPO::getRoleId, po.getId());
            rolePermissionMapper.delete(wrapper);
            // 重新添加关联
            if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
                for (RolePermission permission : role.getPermissions()) {
                    RolePermissionPO rolePermissionPO = new RolePermissionPO();
                    rolePermissionPO.setRoleId(po.getId());
                    rolePermissionPO.setPermissionId(permission.getPermissionId());
                    rolePermissionMapper.insert(rolePermissionPO);
                }
            }
        }
        // 查询并设置权限
        List<Long> permissionIds = rolePermissionMapper.findPermissionIdsByRoleId(po.getId());
        Set<RolePermission> permissions = permissionIds.stream()
                .map(permissionId -> RolePermission.create(po.getId(), permissionId))
                .collect(Collectors.toSet());
        Role savedRole = RoleConverter.toEntity(po);
        savedRole.setPermissions(permissions);
        return savedRole;
    }

    @Override
    public Optional<Role> findById(RoleId id) {
        RolePO po = roleMapper.selectById(id.getValue());
        if (po == null) {
            return Optional.empty();
        }
        Role role = RoleConverter.toEntity(po);
        // 查询并设置权限
        List<Long> permissionIds = rolePermissionMapper.findPermissionIdsByRoleId(po.getId());
        Set<RolePermission> permissions = permissionIds.stream()
                .map(permissionId -> RolePermission.create(po.getId(), permissionId))
                .collect(Collectors.toSet());
        role.setPermissions(permissions);
        return Optional.of(role);
    }

    @Override
    public Optional<Role> findByCode(String code) {
        LambdaQueryWrapper<RolePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePO::getCode, code);
        RolePO po = roleMapper.selectOne(wrapper);
        return Optional.ofNullable(po).map(RoleConverter::toEntity);
    }

    @Override
    public List<Role> findAll() {
        List<RolePO> pos = roleMapper.selectList(null);
        return pos.stream()
                .map(RoleConverter::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code) {
        LambdaQueryWrapper<RolePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePO::getCode, code);
        return roleMapper.selectCount(wrapper) > 0;
    }

    @Override
    public void delete(Role role) {
        if (role == null || role.getId() == null) {
            throw new IllegalArgumentException("角色或角色ID不能为空");
        }
        Long roleId = role.getId().getValue();
        if (roleMapper.selectById(roleId) == null) {
            throw new IllegalArgumentException("角色不存在，ID: " + roleId);
        }
        // 先删除角色权限关联
        LambdaQueryWrapper<RolePermissionPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermissionPO::getRoleId, roleId);
        rolePermissionMapper.delete(wrapper);
        // 再删除角色
        roleMapper.deleteById(roleId);
    }
}