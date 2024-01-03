package cn.ltzf.sdkjava.bean;

import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import cn.ltzf.sdkjava.config.LantuWxConfigStorage;
import cn.ltzf.sdkjava.util.LantuBeanUtils;
import cn.ltzf.sdkjava.util.LantuRequestDataUtil;
import cn.ltzf.sdkjava.util.LantuWxPaySignUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 请求对象共用的参数存放类
 *
 * @author Wuchubuzai
 * @date 2024-01-01 14:52:49
 */
@Data
public abstract class AbstractWxPayRequest implements Serializable {
    
    /**
     * <pre>
     * 字段名：商户号.
     * 变量名：mch_id
     * 是否必填：是
     * 示例值：1230000109
     * 描述：支付平台分配商户号
     * </pre>
     */
    @JSONField(name = "mch_id")
    private String mchId;
    
    /**
     * <pre>
     * 字段名：秒数时间戳.
     * 变量名：timestamp
     * 是否必填：是
     * 示例值：123231411
     * 描述：2分钟内的时间戳
     * </pre>
     */
    @JSONField(name = "timestamp")
    private String timestamp;
    
    /**
     * <pre>
     * 字段名：签名.
     * 变量名：sign
     * 是否必填：是
     * 描述：签名，详见签名生成算法
     * </pre>
     */
    @JSONField(name = "sign")
    private String sign;
    
    /**
     * 检查请求参数内容，包括必填参数以及特殊约束.
     */
    private void checkFields() throws LantuPayErrorException {
        //check required fields
        try {
            LantuBeanUtils.checkRequiredFields(this);
        } catch (LantuPayErrorException e) {
            throw new LantuPayErrorException(e.getError(), e);
        }
        
        //check other parameters
        this.checkConstraints();
    }
    
    /**
     * 检测并且签名
     *
     * @param config 配置
     * @throws LantuPayErrorException 异常
     */
    public void checkAndSign(LantuWxConfigStorage config) throws LantuPayErrorException {
        this.checkFields();
        
        // 如果当前请求未设置商户ID,则取出当前配置中的
        if (StringUtils.isBlank(getMchId())) {
            this.setMchId(config.getMchId());
        }
        if (StringUtils.isBlank(getTimestamp())) {
            this.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
        }
        
        //设置签名字段的值
        this.setSign(LantuWxPaySignUtil.createSign(this, config.getSecretKey(), this.getIgnoredParamsForSign()));
    }
    
    /**
     * 签名时，忽略的参数.
     *
     * @return the string [ ]
     */
    protected String[] getIgnoredParamsForSign() {
        return new String[0];
    }
    
    /**
     * 获取签名时需要的参数. 注意：不含sign属性
     * 签名的时候，只能对必填项进行签名处理
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
        Map<String, String> requestParams = new TreeMap<>(this.getSignParams());
        requestParams.put("sign", sign);
        return LantuRequestDataUtil.generateUrlEncode(false, requestParams);
    }
    
    /**
     * 将属性组装到一个Map中，不同请求的子类进行实现
     * 这里需要将所有的属性全部保存进来，签名的时候会自动调用getIgnoredParamsForSign进行忽略，
     *
     * @param map 传入的属性Map
     */
    protected abstract void storeMap(Map<String, String> map);
    
    /**
     * 检查约束情况 子类可以根据情况进行处理.
     *
     * @throws LantuPayErrorException the wx pay exception
     */
    protected void checkConstraints() throws LantuPayErrorException {
    }
    
}
