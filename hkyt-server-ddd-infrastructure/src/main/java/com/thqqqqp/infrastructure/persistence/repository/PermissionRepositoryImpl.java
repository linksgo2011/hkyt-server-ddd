package com.thqqqqp.infrastructure.persistence.repository;

import com.thqqqqp.domain.auth.adapter.repository.PermissionRepository;
import com.thqqqqp.domain.auth.model.entity.Permission;
import com.thqqqqp.domain.auth.model.valobj.PermissionId;
import com.thqqqqp.infrastructure.persistence.converter.PermissionConverter;
import com.thqqqqp.infrastructure.persistence.mapper.PermissionMapper;
import com.thqqqqp.infrastructure.persistence.po.PermissionPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 权限仓储实现类
 */
@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    private final PermissionMapper permissionMapper;

    public PermissionRepositoryImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Permission save(Permission permission) {
        PermissionPO po = PermissionConverter.toPO(permission);
        if (po.getId() == null) {
            permissionMapper.insert(po);
        } else {
            permissionMapper.updateById(po);
        }
        return PermissionConverter.toEntity(po);
    }

    @Override
    public Optional<Permission> findById(PermissionId id) {
        PermissionPO po = permissionMapper.selectById(id.getValue());
        return Optional.ofNullable(po).map(PermissionConverter::toEntity);
    }

    @Override
    public Optional<Permission> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public List<Permission> findAll() {
        List<PermissionPO> pos = permissionMapper.selectList(null);
        return pos.stream()
                .map(PermissionConverter::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code) {
        return false;
    }

    @Override
    public void delete(Long id) {
        permissionMapper.deleteById(id);
    }
}