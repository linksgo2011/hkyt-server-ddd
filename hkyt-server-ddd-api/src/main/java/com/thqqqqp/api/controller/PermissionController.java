package com.thqqqqp.api.controller;

import com.thqqqqp.api.converter.PermissionDTOConverter;
import com.thqqqqp.api.dto.PermissionDTO;
import com.thqqqqp.api.response.ApiResponse;
import com.thqqqqp.domain.auth.adapter.repository.PermissionRepository;
import com.thqqqqp.domain.auth.model.entity.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限管理接口
 */
@Tag(name = "权限管理", description = "权限管理相关接口")
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionRepository permissionRepository;

    public PermissionController(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Operation(summary = "获取权限列表", description = "获取系统中所有的权限列表")
    @GetMapping
    public ApiResponse<List<PermissionDTO>> getPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        List<PermissionDTO> dtos = permissions.stream()
                .map(PermissionDTOConverter::toDTO)
                .collect(Collectors.toList());
        return ApiResponse.success(dtos);
    }
}