package com.thqqqqp.domain.auth.model.valobj;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class RegisterType {
    private final Type value;

    public RegisterType(String code) {
        this.value = Type.fromCode(code);
    }

    public RegisterType(Type type) {
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
        SYSTEM("system", "系统注册"),
        WECHAT_MP("wechat_mp", "微信小程序"),
        WECHAT_GZH("wechat_gzh", "微信公众号");

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
            throw new IllegalArgumentException("未知的注册类型: " + code);
        }
    }
}
