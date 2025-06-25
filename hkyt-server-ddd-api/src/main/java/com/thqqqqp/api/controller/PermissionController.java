package com.thqqqqp.api.controller;

import com.thqqqqp.api.converter.PermissionDTOConverter;
import com.thqqqqp.api.dto.CreatePermissionRequest;
import com.thqqqqp.api.dto.PermissionDTO;
import com.thqqqqp.api.dto.UpdatePermissionRequest;
import com.thqqqqp.api.response.ApiResponse;
import com.thqqqqp.domain.auth.adapter.repository.PermissionRepository;
import com.thqqqqp.domain.auth.model.entity.Permission;
import com.thqqqqp.domain.auth.model.valobj.PermissionId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "创建权限", description = "创建新的权限")
    @PostMapping
    public ApiResponse<PermissionDTO> createPermission(@RequestBody CreatePermissionRequest request) {
        Permission permission = Permission.create(
                request.getName(),
                request.getCode(),
                request.getType(),
                request.getUrl(),
                request.getMethod(),
                request.getParentId(),
                request.getRemark()
        );

        if (permissionRepository.existsByCode(request.getCode())) {
            return ApiResponse.error(400, "权限编码已存在");
        }

        Permission savedPermission = permissionRepository.save(permission);
        return ApiResponse.success(PermissionDTOConverter.toDTO(savedPermission));
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

    @Operation(summary = "获取单个权限", description = "根据ID获取权限详情")
    @GetMapping("/{id}")
    public ApiResponse<PermissionDTO> getPermission(@PathVariable Long id) {
        return permissionRepository.findById(new PermissionId(id))
                .map(permission -> ApiResponse.success(PermissionDTOConverter.toDTO(permission)))
                .orElse(ApiResponse.error(404, "权限不存在"));
    }

    @Operation(summary = "更新权限", description = "更新指定ID的权限信息")
    @PutMapping("/{id}")
    public ApiResponse<PermissionDTO> updatePermission(
            @PathVariable Long id,
            @RequestBody UpdatePermissionRequest request) {
        return permissionRepository.findById(new PermissionId(id))
                .map(permission -> {
                    if (request.getName() != null) {
                        permission.setName(request.getName());
                    }
                    if (request.getType() != null) {
                        permission.setType(request.getType());
                    }
                    if (request.getUrl() != null) {
                        permission.setUrl(request.getUrl());
                    }
                    if (request.getMethod() != null) {
                        permission.setMethod(request.getMethod());
                    }
                    if (request.getParentId() != null) {
                        permission.setParentId(request.getParentId());
                    }
                    if (request.getRemark() != null) {
                        permission.setRemark(request.getRemark());
                    }

                    Permission updatedPermission = permissionRepository.save(permission);
                    return ApiResponse.success(PermissionDTOConverter.toDTO(updatedPermission));
                })
                .orElse(ApiResponse.error(404, "权限不存在"));
    }

    @Operation(summary = "删除权限", description = "删除指定ID的权限")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePermission(@PathVariable Long id) {
        return permissionRepository.findById(new PermissionId(id))
                .map(permission -> {
                    permissionRepository.delete(id);
                    return ApiResponse.success("权限删除成功");
                })
                .orElse(ApiResponse.error(404, "权限不存在"));
    }
}