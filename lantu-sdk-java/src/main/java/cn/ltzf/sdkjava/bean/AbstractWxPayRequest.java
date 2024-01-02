package cn.ltzf.sdkjava.bean;

import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import cn.ltzf.sdkjava.config.LantuWxConfigStorage;
import cn.ltzf.sdkjava.util.LantuRequestDataUtil;
import cn.ltzf.sdkjava.util.LantuWxPaySignUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wuchubuzai
 * @date 2024-01-01 14:52:49
 */
@Data
public abstract class AbstractWxPayRequest implements Serializable {
    
    @JSONField(name = "mch_id")
    private String mchId;
    
    @JSONField(name = "timestamp")
    private String timestamp;
    
    @JSONField(name = "sign")
    private String sign;
    
    /**
     * 检测并且签名
     *
     * @param config 配置
     * @throws LantuPayErrorException 异常
     */
    public void checkAndSign(LantuWxConfigStorage config) throws LantuPayErrorException {
        // 如果当前请求未设置商户ID,则取出当前配置中的
        if (StringUtils.isBlank(getMchId())) {
            this.setMchId(config.getMchId());
        }
        if (StringUtils.isBlank(getTimestamp())) {
            this.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
        }
        
        //设置签名字段的值
        this.setSign(LantuWxPaySignUtil.createSign(this, config.getSecretKey()));
    }
    
    /**
     * 获取签名时需要的参数. 注意：不含sign属性
     */
    public Map<String, String> getSignParams() {
        Map<String, String> map = new HashMap<>(8);
        map.put("mch_id", mchId);
        map.put("timestamp", timestamp);
        storeMap(map);
        return map;
    }
    
    /**
     * 转换结构
     *
     * @return 字符串
     */
    public String toFormBody() {
        // 根据当前对象生成请求的form body结构字符串
        Map<String, String> requestParams = this.getSignParams();
        requestParams.put("sign", sign);
        return LantuRequestDataUtil.generateUrlEncode(false, requestParams);
    }
    
    /**
     * 将属性组装到一个Map中，不同请求的子类进行实现
     *
     * @param map 传入的属性Map
     */
    protected abstract void storeMap(Map<String, String> map);
    
}
