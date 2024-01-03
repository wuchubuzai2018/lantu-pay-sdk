package cn.ltzf.sdkjava.bean.result;

import cn.ltzf.sdkjava.bean.CommonResponseResult;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 蓝兔支付查询订单结果
 *
 * @author Wuchubuzai
 * @date 2024-01-03 17:35:23
 */
@Data
public class LantuWxPayQueryOrderResult {
    
    @JSONField(name = "mch_id")
    private String mchId;
    
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    
    @JSONField(name = "add_time")
    private String addTime;
    
    @JSONField(name = "order_no")
    private String orderNo;
    
    @JSONField(name = "pay_no")
    private String payNo;
    
    @JSONField(name = "body")
    private String body;
    
    @JSONField(name = "total_fee")
    private String totalFee;
    
    @JSONField(name = "trade_type")
    private String tradeType;
    
    @JSONField(name = "success_time")
    private String successTime;
    
    @JSONField(name = "attach")
    private String attach;
    
    @JSONField(name = "openid")
    private String openid;
    
    @JSONField(name = "pay_status")
    private String payStatus;
    
    @JSONField(name = "msg")
    private String msg;
    
    /**
     * 通用异常转换
     * @param json 返回数据
     * @return 响应结构
     */
    public static LantuWxPayQueryOrderResult fromJson(String json) {
        CommonResponseResult<LantuWxPayQueryOrderResult> result = CommonResponseResult.fromJson(
                json, LantuWxPayQueryOrderResult.class);
        return result.getData();
    }
    
}
