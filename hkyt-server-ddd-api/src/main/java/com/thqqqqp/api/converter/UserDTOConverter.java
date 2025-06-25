package com.thqqqqp.api.converter;

import com.thqqqqp.api.dto.UserDTO;
import com.thqqqqp.domain.auth.model.entity.User;
import com.thqqqqp.domain.auth.model.entity.UserRole;
import com.thqqqqp.domain.auth.adapter.repository.RoleRepository;
import com.thqqqqp.domain.auth.model.valobj.RoleId;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTOConverter {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        if (user.getId() != null) {
            dto.setId(user.getId().getValue());
        }
        if (user.getRoles() != null) {
            dto.setRoleIds(user.getRoles().stream()
                    .map(role -> role.getRoleId())
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public static List<UserDTO> toDTOs(List<User> users) {
        return users.stream()
                .map(UserDTOConverter::toDTO)
                .collect(Collectors.toList());
    }
}