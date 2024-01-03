package cn.ltzf.sdkjava.bean.request;

import cn.ltzf.sdkjava.bean.AbstractWxPayRequest;
import cn.ltzf.sdkjava.common.annotation.Required;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 订单退款请求
 *
 * @author wuchubuzai
 * @date 2024-01-01 15:55:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LantuWxPayRefundOrderRequest extends AbstractWxPayRequest {
    
    @JSONField(name = "out_trade_no")
    @Required
    private String outTradeNo;
    
    @JSONField(name = "out_refund_no")
    @Required
    private String outRefundNo;
    
    @JSONField(name = "refund_fee")
    @Required
    private String refundFee;
    
    @JSONField(name = "refund_desc")
    private String refundDesc;
    
    @JSONField(name = "notify_url")
    private String notifyUrl;
    
    @Override
    protected String[] getIgnoredParamsForSign() {
        return new String[]{"refundDesc", "notifyUrl"};
    }
    
    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("out_trade_no", outTradeNo);
        map.put("out_refund_no", outRefundNo);
        map.put("notify_url", notifyUrl);
        map.put("refund_fee", refundFee);
        map.put("refund_desc", refundDesc);
    }
    
}
