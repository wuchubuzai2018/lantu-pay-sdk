package cn.ltzf.sdkjava.common.error;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wuchubuzai
 * @date 2023-12-29 10:09:44
 */
@Getter
public enum LantuPayErrorMsgEnum {
    
    CODE_0001(10001, "错误样例1");
    
    private final int code;
    
    private final String msg;
    
    LantuPayErrorMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    static final Map<Integer, String> CACHE_MAP = new HashMap<Integer, String>();
    
    static {
        for (LantuPayErrorMsgEnum value : LantuPayErrorMsgEnum.values()) {
            CACHE_MAP.put(value.code, value.msg);
        }
    }
    
    /**
     * 通过错误代码查找其中文含义.
     */
    public static String findMsgByCode(int code) {
        return CACHE_MAP.getOrDefault(code, null);
    }
    
}
