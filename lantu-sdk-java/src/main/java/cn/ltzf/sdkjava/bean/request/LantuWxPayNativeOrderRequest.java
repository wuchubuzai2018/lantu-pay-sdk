package cn.ltzf.sdkjava.bean.request;

import cn.ltzf.sdkjava.bean.AbstractWxPayRequest;
import cn.ltzf.sdkjava.common.annotation.Required;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author wuchubuzai
 * @date 2024-01-01 15:55:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LantuWxPayNativeOrderRequest extends AbstractWxPayRequest {
    
    @JSONField(name = "out_trade_no")
    @Required
    private String outTradeNo;
    
    @JSONField(name = "total_fee")
    @Required
    private String totalFee;
    
    @JSONField(name = "body")
    @Required
    private String body;
    
    @JSONField(name = "notify_url")
    @Required
    private String notifyUrl;
    
    @JSONField(name = "attach")
    private String attach;
    
    @JSONField(name = "time_expire")
    private String timeExpire;
    
    @Override
    protected String[] getIgnoredParamsForSign() {
        return new String[]{"attach", "timeExpire"};
    }
    
    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("out_trade_no", outTradeNo);
        map.put("total_fee", totalFee);
        map.put("body", body);
        map.put("notify_url", notifyUrl);
        map.put("attach", attach);
        map.put("time_expire", timeExpire);
    }
    
    /**
     * 下单请求增加URL编码，可能请求参数中包含特殊字符
     * @return 返回true
     */
    protected boolean shouldUrlEncoder() {
        return true;
    }
}
