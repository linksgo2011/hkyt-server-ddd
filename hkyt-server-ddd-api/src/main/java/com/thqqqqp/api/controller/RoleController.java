package com.thqqqqp.api.controller;

import com.thqqqqp.api.converter.RoleDTOConverter;
import com.thqqqqp.api.dto.CreateRoleRequest;
import com.thqqqqp.api.dto.RoleDTO;
import com.thqqqqp.api.dto.UpdateRoleRequest;
import com.thqqqqp.api.response.ApiResponse;
import com.thqqqqp.domain.auth.adapter.repository.RoleRepository;
import com.thqqqqp.domain.auth.adapter.repository.PermissionRepository;
import com.thqqqqp.domain.auth.model.entity.Role;
import com.thqqqqp.domain.auth.model.entity.RolePermission;
import com.thqqqqp.domain.auth.model.valobj.RoleId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色管理", description = "角色管理相关接口")
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleRepository roleRepository;
    private final RoleDTOConverter roleDTOConverter;

    public RoleController(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.roleDTOConverter = new RoleDTOConverter(permissionRepository);
    }

    @Operation(summary = "创建角色")
    @PostMapping
    public ApiResponse<RoleDTO> createRole(@RequestBody CreateRoleRequest request) {
        Role role = Role.create(request.getName(), request.getCode(), request.getRemark());
        role = roleRepository.save(role);
        return ApiResponse.success(roleDTOConverter.toDTO(role));
    }

    @Operation(summary = "获取角色列表")
    @GetMapping
    public ApiResponse<List<RoleDTO>> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return ApiResponse.success(roleDTOConverter.toDTOs(roles));
    }

    @Operation(summary = "获取单个角色")
    @GetMapping("/{id}")
    public ApiResponse<RoleDTO> getRole(@PathVariable Long id) {
        return roleRepository.findById(new RoleId(id))
                .map(role -> ApiResponse.success(roleDTOConverter.toDTO(role)))
                .orElse(ApiResponse.error(404, "角色不存在"));
    }

    @Operation(summary = "更新角色")
    @PutMapping("/{id}")
    public ApiResponse<RoleDTO> updateRole(@PathVariable Long id, @RequestBody UpdateRoleRequest request) {
        return roleRepository.findById(new RoleId(id))
                .map(role -> {
                    if (request.getName() != null) {
                        role.setName(request.getName());
                    }
                    if (request.getRemark() != null) {
                        role.setRemark(request.getRemark());
                    }
                    role = roleRepository.save(role);
                    return ApiResponse.success(roleDTOConverter.toDTO(role));
                })
                .orElse(ApiResponse.error(404,"角色不存在"));
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteRole(@PathVariable Long id) {
        return roleRepository.findById(new RoleId(id))
                .map(role -> {
                    roleRepository.delete(role);
                    return ApiResponse.success(null);
                })
                .orElse(ApiResponse.error(404, "角色不存在"));
    }

    @Operation(summary = "添加权限给角色")
    @PostMapping("/{roleId}/permissions/{permissionId}")
    public ApiResponse<Object> addPermissionToRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        return roleRepository.findById(new RoleId(roleId))
                .map(role -> {
                    role.addPermission(RolePermission.create(roleId,permissionId));
                    roleRepository.save(role);
                    return ApiResponse.success(null);
                })
                .orElse(ApiResponse.error(404,"角色不存在"));
    }

    @Operation(summary = "移除角色权限")
    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    public ApiResponse<Object> removePermissionFromRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        return roleRepository.findById(new RoleId(roleId))
                .map(role -> {
                    role.removePermission(RolePermission.create(roleId,permissionId));
                    roleRepository.save(role);
                    return ApiResponse.success(null);
                })
                .orElse(ApiResponse.error(404,"角色不存在"));
    }
}