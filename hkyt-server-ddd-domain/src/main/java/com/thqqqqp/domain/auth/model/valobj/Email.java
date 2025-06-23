package com.thqqqqp.domain.auth.model.valobj;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.regex.Pattern;

/**
 * 邮箱值对象
 *
 * @author THqqqqp
 * @date 2025/6/22 15:16
 */
@EqualsAndHashCode
@ToString
@Getter
public class Email {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9.-]+$");

    private final String value;

    public Email(String value) {
        if (value != null && !value.isEmpty() && !isValidEmail(value)) {
            throw new IllegalArgumentException("无效的Email格式");
        }
        this.value = value;
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

}
