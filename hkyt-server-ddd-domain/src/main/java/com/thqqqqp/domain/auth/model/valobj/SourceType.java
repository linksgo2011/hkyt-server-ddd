package com.thqqqqp.domain.auth.model.valobj;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 来源类型值对象
 * 用于标识用户来源，如注册、第三方登录等
 *
 * @author THqqqqp
 * @date 2025/6/22 14:46
 */
@Getter
@EqualsAndHashCode
@ToString
public class SourceType {

    private final Type value;

    public SourceType(String code) {
        this.value = Type.fromCode(code);
    }

    public SourceType(Type type) {
        if (type == null) {
            throw new IllegalArgumentException("来源类型不能为空");
        }
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
        MINI_PROGRAM("mini_program", "小程序"),
        PUBLIC_ACCOUNT("public_account", "公众号"),
        ACTIVITY("activity", "活动"),
        IMPORT("import", "批量导入"),
        SYSTEM("system", "系统手动添加"),
        // 你可以继续补充，比如 PC_WEB("pc_web", "PC端网站"), APP("app", "App客户端")
        ;

        private final String code;
        private final String label;

        Type(String code, String label) {
            this.code = code;
            this.label = label;
        }

        @JsonCreator
        public static Type fromCode(String code) {
            if (code == null) {
                throw new IllegalArgumentException("来源类型不能为空");
            }
            for (Type type : values()) {
                if (type.code.equalsIgnoreCase(code)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("未知的来源类型: " + code);
        }

        @JsonValue
        public String toValue() {
            return code;
        }
    }
}
