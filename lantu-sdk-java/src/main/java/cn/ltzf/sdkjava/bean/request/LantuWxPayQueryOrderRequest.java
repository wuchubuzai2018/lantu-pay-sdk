package cn.ltzf.sdkjava.bean.request;

import cn.ltzf.sdkjava.bean.AbstractWxPayRequest;
import cn.ltzf.sdkjava.common.annotation.Required;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 蓝兔支付查询订单的请求
 *
 * @author Wuchubuzai
 * @date 2024-01-03 09:21:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LantuWxPayQueryOrderRequest extends AbstractWxPayRequest {
    
    @JSONField(name = "out_trade_no")
    @Required
    private String outTradeNo;
    
    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("out_trade_no", outTradeNo);
    }
    
}
