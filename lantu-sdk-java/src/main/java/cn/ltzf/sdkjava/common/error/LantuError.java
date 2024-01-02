package cn.ltzf.sdkjava.common.error;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Wuchubuzai
 * @date 2024-01-01 17:18:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LantuError implements Serializable {
    
    /**
     * 错误代码.
     */
    private int code;
    
    /**
     * 错误信息.
     */
    private String msg;
    
    private String json;
    
    public LantuError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public static LantuError fromJson(String json) {
        LantuError lantuError = JSON.parseObject(json, LantuError.class);
        return lantuError;
    }
    
    @Override
    public String toString() {
        return "错误代码：" + this.code + ", 错误信息：" + this.msg;
    }
}
