package cn.ltzf.sdkjava.bean.request;

import cn.ltzf.sdkjava.bean.AbstractWxPayRequest;
import cn.ltzf.sdkjava.common.annotation.Required;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 获取微信Openid API 请求对象
 *
 * @author Wuchubuzai
 * @date 2024-01-02 14:21:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LantuWxPayGetWechatOpenIdRequest extends AbstractWxPayRequest {
    
    @JSONField(name = "callback_url")
    @Required
    private String callbackUrl;
    
    @JSONField(name = "attach")
    private String attach;
    
    @Override
    protected String[] getIgnoredParamsForSign() {
        return new String[]{"attach"};
    }
    
    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("callback_url", callbackUrl);
        map.put("attach", attach);
    }
    
}
