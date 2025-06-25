package com.thqqqqp.infrastructure.persistence.converter;

import com.thqqqqp.domain.auth.model.entity.User;
import com.thqqqqp.domain.auth.model.valobj.UserId;
import com.thqqqqp.infrastructure.persistence.po.UserPO;
import org.springframework.beans.BeanUtils;

public class UserConverter {

    public static UserPO toPO(User user) {
        if (user == null) {
            return null;
        }
        UserPO po = new UserPO();
        BeanUtils.copyProperties(user, po);
        if (user.getId() != null) {
            po.setId(user.getId().getValue());
        }
        po.setStatus(user.getStatus() ? 1 : 0);
        return po;
    }

    public static User toEntity(UserPO po) {
        if (po == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(po, user);
        if (po.getId() != null) {
            user.setId(new UserId(po.getId()));
        }
        return user;
    }
}