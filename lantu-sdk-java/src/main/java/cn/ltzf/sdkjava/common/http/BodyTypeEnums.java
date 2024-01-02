package cn.ltzf.sdkjava.common.http;

import lombok.Getter;

/**
 * @author wuchubuzai
 * @date 2024-01-01 16:15:18
 */
@Getter
public enum BodyTypeEnums {
    
    /**
     * json.
     */
    JSON("json", "application/json; charset=utf-8"),
    
    /**
     * form.
     */
    FORM("form", "application/x-www-form-urlencoded");
    
    private final String value;
    
    private final String contentType;
    
    BodyTypeEnums(String value, String contentType) {
        this.value = value;
        this.contentType = contentType;
    }
    
}
