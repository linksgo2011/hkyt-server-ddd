package com.thqqqqp.api.converter;

import com.thqqqqp.api.dto.RoleDTO;
import com.thqqqqp.domain.auth.model.entity.Role;
import com.thqqqqp.domain.auth.model.entity.RolePermission;
import com.thqqqqp.domain.auth.adapter.repository.PermissionRepository;
import com.thqqqqp.domain.auth.model.valobj.PermissionId;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class RoleDTOConverter {

    private final PermissionRepository permissionRepository;

    public RoleDTOConverter(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }
        RoleDTO dto = new RoleDTO();
        BeanUtils.copyProperties(role, dto);
        if (role.getId() != null) {
            dto.setId(role.getId().getValue());
        }
        if (role.getPermissions() != null) {
            dto.setPermissions(role.getPermissions().stream()
                    .map(rolePermission -> {
                        return permissionRepository.findById(new PermissionId(rolePermission.getPermissionId()))
                                .map(PermissionDTOConverter::toDTO)
                                .orElse(null);
                    })
                    .filter(permissionDTO -> permissionDTO != null)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public List<RoleDTO> toDTOs(List<Role> roles) {
        return roles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}