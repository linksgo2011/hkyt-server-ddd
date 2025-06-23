package com.thqqqqp.domain.auth.model.valobj;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 用户类型值对象
 *
 * @author THqqqqp
 * @date 2025/6/22 15:25
 */
@Getter
@ToString
@EqualsAndHashCode
public class UserType {
    private final Type value;

    public UserType(String code) {
        this.value = Type.fromCode(code);
    }

    public UserType(Type type) {
        this.value = type;
    }

    public String getCode() {
        return value.getCode();
    }

    public String getLabel() {
        return value.getLabel();
    }

    @Getter
    public enum Type {
        STUDENT("student", "学生"),
        TEACHER("teacher", "教师"),
        ADMIN("admin", "管理员"),
        PARENT("parent", "家长"),
        VOLUNTEER("volunteer", "志愿者");
        // 可继续扩展

        private final String code;
        private final String label;

        Type(String code, String label) {
            this.code = code;
            this.label = label;
        }

        public static Type fromCode(String code) {
            for (Type type : values()) {
                if (type.code.equalsIgnoreCase(code)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("未知的用户类型: " + code);
        }

    }
}
