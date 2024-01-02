package cn.ltzf.sdkjava.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回响应结构
 *
 * @author wuchubuzai
 * @date 2023-12-07 23:27:56
 */
@Data
public class CommonResponseResult<T> implements Serializable {
    
    private static final long serialVersionUID = 927507433882498703L;
    
    private int code;
    
    private String msg;
    
    private T data;
    
    public boolean success() {
        return this.code == 0;
    }
    
    public static <T> CommonResponseResult<T> fromJson(String content, Class<T> classz) {
        return JSON.parseObject(content, new TypeReference<CommonResponseResult<T>>(classz) {
        });
    }
    
}
