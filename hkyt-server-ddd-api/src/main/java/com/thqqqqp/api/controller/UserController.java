package com.thqqqqp.api.controller;

import com.thqqqqp.api.converter.RoleDTOConverter;
import com.thqqqqp.api.converter.UserDTOConverter;
import com.thqqqqp.api.dto.CreateUserRequest;
import com.thqqqqp.api.dto.UpdateUserRequest;
import com.thqqqqp.api.dto.UserDTO;
import com.thqqqqp.api.response.ApiResponse;
import com.thqqqqp.domain.auth.adapter.repository.PermissionRepository;
import com.thqqqqp.domain.auth.adapter.repository.UserRepository;
import com.thqqqqp.domain.auth.adapter.repository.RoleRepository;
import com.thqqqqp.domain.auth.model.entity.User;
import com.thqqqqp.domain.auth.model.valobj.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.thqqqqp.domain.auth.model.entity.Role;
import com.thqqqqp.domain.auth.model.valobj.RoleId;
import com.thqqqqp.api.dto.RoleDTO;

@Tag(name = "用户管理", description = "用户管理相关接口")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleDTOConverter roleDTOConverter;

    public UserController(UserRepository userRepository, PermissionRepository permissionRepository1, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleDTOConverter = new RoleDTOConverter(permissionRepository);
    }

    @Operation(summary = "获取用户列表")
    @GetMapping
    public ApiResponse<List<UserDTO>> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = UserDTOConverter.toDTOs(users);
        
        // 批量查询并组装角色信息
        Set<Long> allRoleIds = userDTOs.stream()
            .flatMap(dto -> dto.getRoleIds().stream())
            .collect(Collectors.toSet());
            
        Map<Long, Role> roleMap = allRoleIds.stream()
            .map(roleId -> roleRepository.findById(new RoleId(roleId)))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toMap(role -> role.getId().getValue(), role -> role));

        userDTOs.forEach(userDTO -> {
            if (userDTO.getRoleIds() != null && !userDTO.getRoleIds().isEmpty()) {
                List<RoleDTO> roleDTOs = userDTO.getRoleIds().stream()
                    .map(roleMap::get)
                    .filter(Objects::nonNull)
                    .map(roleDTOConverter::toDTO)
                    .collect(Collectors.toList());
                userDTO.setRoles(roleDTOs);
            }
        });
        
        return ApiResponse.success(userDTOs);
    }

    @Operation(summary = "获取单个用户")
    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUser(@PathVariable Long id) {
        return userRepository.findById(new UserId(id))
                .map(user -> {
                    UserDTO userDTO = UserDTOConverter.toDTO(user);
                    if (userDTO.getRoleIds() != null && !userDTO.getRoleIds().isEmpty()) {
                        List<RoleDTO> roleDTOs = userDTO.getRoleIds().stream()
                            .map(roleId -> roleRepository.findById(new RoleId(roleId)))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .map(roleDTOConverter::toDTO)
                            .collect(Collectors.toList());
                        userDTO.setRoles(roleDTOs);
                    }
                    return ApiResponse.success(userDTO);
                })
                .orElse(ApiResponse.error(404, "用户不存在"));
    }

    @Operation(summary = "创建用户")
    @PostMapping
    public ApiResponse<UserDTO> createUser(@RequestBody CreateUserRequest request) {
        User user = User.create(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                "",
                "",
                "",
                "",
                ""
        );
        user = userRepository.save(user);
        UserDTO userDTO = UserDTOConverter.toDTO(user);
        if (userDTO.getRoleIds() != null && !userDTO.getRoleIds().isEmpty()) {
            List<RoleDTO> roleDTOs = userDTO.getRoleIds().stream()
                .map(roleId -> roleRepository.findById(new RoleId(roleId)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(roleDTOConverter::toDTO)
                .collect(Collectors.toList());
            userDTO.setRoles(roleDTOs);
        }
        return ApiResponse.success(userDTO);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public ApiResponse<UserDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return userRepository.findById(new UserId(id))
                .map(user -> {
                    if (request.getEmail() != null) {
                        user.setEmail(request.getEmail());
                    }
                    if (request.getEnabled() != null) {
                        if (request.getEnabled()) {
                            user.enable();
                        } else {
                            user.disable();
                        }
                    }
                    user = userRepository.save(user);
                    UserDTO userDTO = UserDTOConverter.toDTO(user);
                    if (userDTO.getRoleIds() != null && !userDTO.getRoleIds().isEmpty()) {
                        List<RoleDTO> roleDTOs = userDTO.getRoleIds().stream()
                            .map(roleId -> roleRepository.findById(new RoleId(roleId)))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .map(roleDTOConverter::toDTO)
                            .collect(Collectors.toList());
                        userDTO.setRoles(roleDTOs);
                    }
                    return ApiResponse.success(userDTO);
                })
                .orElse(ApiResponse.error(404, "用户不存在"));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteUser(@PathVariable Long id) {
        return userRepository.findById(new UserId(id))
                .map(user -> {
                    userRepository.delete(user);
                    return ApiResponse.success(null);
                })
                .orElse(ApiResponse.error(404, "用户不存在"));
    }
}