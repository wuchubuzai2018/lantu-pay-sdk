package cn.ltzf.sdkjava.bean.result;

import cn.ltzf.sdkjava.bean.CommonResponseResult;
import lombok.Data;

/**
 * 获取微信Openid API响应结果
 *
 * @author Wuchubuzai
 * @date 2024-01-01 17:35:23
 */
@Data
public class LantuWxPayGetWechatOpenIdResult {
    
    /**
     * 通用异常转换
     * @param json 返回数据
     * @return 响应结构
     */
    public static String fromJson(String json) {
        CommonResponseResult<String> result = CommonResponseResult.fromJson(
                json, String.class);
        return result.getData();
    }
    
}
