package com.thqqqqp.domain.auth.model.valobj;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.regex.Pattern;

/**
 * 学生学号值对象
 *
 * @author THqqqqp
 * @date 2025/6/22 15:19
 */
@Getter
@ToString
@EqualsAndHashCode
public class StudentNo {
    // 学号为10位数字或者hzkj开头的10位数字
    private static final Pattern STUDENTON_PATTERN = Pattern.compile("^(hzkj)?\\d{10}$");

    private final String value;

    public StudentNo(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("学号不能为空");
        }
        if (!isValidStudentNo(value)) {
            throw new IllegalArgumentException("无效的学号格式");
        }
        this.value = value;
    }

    private boolean isValidStudentNo(String studentNo) {
        return STUDENTON_PATTERN.matcher(studentNo).matches();
    }
}
