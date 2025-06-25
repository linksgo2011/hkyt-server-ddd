package com.thqqqqp.api.converter;

import com.thqqqqp.api.dto.PermissionDTO;
import com.thqqqqp.domain.auth.model.entity.Permission;
import org.springframework.beans.BeanUtils;

/**
 * 权限 DTO 转换器
 */
public class PermissionDTOConverter {

    /**
     * 将实体转换为 DTO
     *
     * @param permission 权限实体
     * @return 权限 DTO
     */
    public static PermissionDTO toDTO(Permission permission) {
        if (permission == null) {
            return null;
        }
        PermissionDTO dto = new PermissionDTO();
        BeanUtils.copyProperties(permission, dto);
        return dto;
    }
}