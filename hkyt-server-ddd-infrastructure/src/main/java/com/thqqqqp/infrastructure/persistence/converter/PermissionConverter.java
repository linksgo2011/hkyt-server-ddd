package com.thqqqqp.infrastructure.persistence.converter;

import com.thqqqqp.domain.auth.model.entity.Permission;
import com.thqqqqp.infrastructure.persistence.po.PermissionPO;
import org.springframework.beans.BeanUtils;

/**
 * 权限转换器
 */
public class PermissionConverter {

    /**
     * 将持久化对象转换为实体
     *
     * @param po 持久化对象
     * @return 权限实体
     */
    public static Permission toEntity(PermissionPO po) {
        if (po == null) {
            return null;
        }
        Permission permission = new Permission();
        BeanUtils.copyProperties(po, permission);
        return permission;
    }

    /**
     * 将实体转换为持久化对象
     *
     * @param permission 权限实体
     * @return 持久化对象
     */
    public static PermissionPO toPO(Permission permission) {
        if (permission == null) {
            return null;
        }
        PermissionPO po = new PermissionPO();
        BeanUtils.copyProperties(permission, po);
        return po;
    }
}