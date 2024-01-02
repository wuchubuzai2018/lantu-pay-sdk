package cn.ltzf.sdkjava.bean.result;

import cn.ltzf.sdkjava.bean.CommonResponseResult;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author Wuchubuzai
 * @date 2024-01-01 17:35:23
 */
@Data
public class LantuWxPayNativeOrderResult {
    
    @JSONField(name = "code_url")
    private String codeUrl;
    
    @JSONField(name = "QRcode_url")
    private String qrCodeUrl;
    
    /**
     * 通用异常转换
     * @param json 返回数据
     * @return 响应结构
     */
    public static LantuWxPayNativeOrderResult fromJson(String json) {
        CommonResponseResult<LantuWxPayNativeOrderResult> result = CommonResponseResult.fromJson(
                json, LantuWxPayNativeOrderResult.class);
        return result.getData();
    }
    
}
