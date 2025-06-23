package com.thqqqqp.domain.auth.model.valobj;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.regex.Pattern;

@Getter
@ToString
@EqualsAndHashCode
public class PhoneNumber {
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\d{11}$");

    private final String value;

    public PhoneNumber(String value) {
        if (value != null && !value.isEmpty() && !isValidPhone(value)) {
            throw new IllegalArgumentException("无效的手机号格式");
        }
        this.value = value;
    }

    private boolean isValidPhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }

}
