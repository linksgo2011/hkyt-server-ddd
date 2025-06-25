package com.thqqqqp.infrastructure.persistence.converter;

import com.thqqqqp.domain.auth.model.entity.Role;
import com.thqqqqp.domain.auth.model.valobj.RoleId;
import com.thqqqqp.infrastructure.persistence.po.RolePO;
import org.springframework.beans.BeanUtils;

/**
 * 角色转换器
 */
public class RoleConverter {

    /**
     * 将持久化对象转换为实体
     *
     * @param po 持久化对象
     * @return 角色实体
     */
    public static Role toEntity(RolePO po) {
        if (po == null) {
            return null;
        }
        Role role = Role.create(po.getName(), po.getCode(), po.getRemark());
        if (po.getId() != null) {
            role.setId(new RoleId(po.getId()));
        }
        role.setStatus(po.getStatus() == 1);
        role.setCreatedBy(po.getCreatedBy());
        role.setCreatedTime(po.getCreatedTime());
        role.setUpdatedBy(po.getUpdatedBy());
        role.setUpdatedTime(po.getUpdatedTime());
        return role;
    }

    /**
     * 将实体转换为持久化对象
     *
     * @param role 角色实体
     * @return 持久化对象
     */
    public static RolePO toPO(Role role) {
        if (role == null) {
            return null;
        }
        RolePO po = new RolePO();
        BeanUtils.copyProperties(role, po);
        if (role.getId() != null) {
            po.setId(role.getId().getValue());
        }
        po.setStatus(role.getStatus() ? 1 : 0);
        return po;
    }
}