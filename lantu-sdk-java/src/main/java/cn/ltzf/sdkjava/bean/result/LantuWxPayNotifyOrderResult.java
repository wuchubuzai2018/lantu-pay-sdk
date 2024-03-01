package cn.ltzf.sdkjava.bean.result;

import cn.ltzf.sdkjava.api.LantuWxPayService;
import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import cn.ltzf.sdkjava.util.LantuWxPaySignUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wuchubuzai
 * @date 2024-01-03 23:51:06
 */
@Data
public class LantuWxPayNotifyOrderResult {

    @JSONField(name = "code")
    private String code;
    
    @JSONField(name = "timestamp")
    private String timestamp;
    
    @JSONField(name = "mch_id")
    private String mchId;
    
    @JSONField(name = "order_no")
    private String orderNo;
    
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    
    @JSONField(name = "pay_no")
    private String payNo;
    
    @JSONField(name = "total_fee")
    private String totalFee;
    
    @JSONField(name = "sign")
    private String sign;
    
    @JSONField(name = "pay_channel\t")
    private String payChannel;
    
    @JSONField(name = "trade_type")
    private String tradeType;
    
    @JSONField(name = "success_time")
    private String successTime;
    
    @JSONField(name = "attach")
    private String attach;
    
    @JSONField(name = "openid")
    private String openId;
    
    /**
     * 检测结果
     * @param lantuWxPayService 业务类
     */
    public void checkResult(LantuWxPayService lantuWxPayService) {
        Map<String, String> map = new HashMap<>(8);
        map.put("code", code);
        map.put("timestamp", timestamp);
        map.put("mch_id", mchId);
        map.put("order_no", orderNo);
        map.put("out_trade_no", outTradeNo);
        map.put("pay_no", payNo);
        map.put("total_fee", totalFee);
        map.put("sign", sign);
        boolean result = LantuWxPaySignUtil.checkSign(map, lantuWxPayService.getLantuWxConfigStorage().getSecretKey());
        if (!result) {
            throw new LantuPayErrorException("参数格式校验错误！");
        }
    }
}
