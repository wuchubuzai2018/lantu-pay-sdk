package cn.ltzf.sdkjava.bean.result;

import cn.ltzf.sdkjava.bean.CommonResponseResult;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author Wuchubuzai
 * @date 2024-01-01 17:35:23
 */
@Data
public class LantuWxPayRefundOrderResult {
    
    @JSONField(name = "mch_id")
    private String mchId;
    
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    
    @JSONField(name = "out_refund_no")
    private String outRefundNo;
    
    @JSONField(name = "order_no")
    private String orderNo;
    
    @JSONField(name = "pay_refund_no")
    private String payRefundNo;
    
    /**
     * 通用异常转换
     * @param json 返回数据
     * @return 响应结构
     */
    public static LantuWxPayRefundOrderResult fromJson(String json) {
        CommonResponseResult<LantuWxPayRefundOrderResult> result = CommonResponseResult.fromJson(
                json, LantuWxPayRefundOrderResult.class);
        return result.getData();
    }
    
}
