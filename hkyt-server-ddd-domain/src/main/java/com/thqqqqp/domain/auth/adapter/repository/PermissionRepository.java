package com.thqqqqp.domain.auth.adapter.repository;

import com.thqqqqp.domain.auth.model.entity.Permission;
import com.thqqqqp.domain.auth.model.valobj.PermissionId;

import java.util.List;
import java.util.Optional;

/**
 * 权限仓储接口
 */
public interface PermissionRepository {
    /**
     * 保存权限
     */
    Permission save(Permission permission);
    
    /**
     * 根据ID查找权限
     */
    Optional<Permission> findById(PermissionId id);
    
    /**
     * 根据编码查找权限
     */
    Optional<Permission> findByCode(String code);
    
    /**
     * 查询所有权限
     */
    List<Permission> findAll();
    
    /**
     * 检查权限编码是否存在
     */
    boolean existsByCode(String code);
    
    /**
     * 删除权限
     */
    void delete(Long id);
} 