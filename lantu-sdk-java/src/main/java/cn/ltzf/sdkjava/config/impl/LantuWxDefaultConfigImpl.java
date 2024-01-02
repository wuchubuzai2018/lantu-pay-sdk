package cn.ltzf.sdkjava.config.impl;

import cn.ltzf.sdkjava.config.LantuWxConfigStorage;
import lombok.Data;

import java.io.Serializable;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化.
 *
 * @author admin
 */
@Data
public class LantuWxDefaultConfigImpl implements LantuWxConfigStorage, Serializable {
    
    private static final String DEFAULT_PAY_BASE_URL = "https://api.ltzf.cn";
    
    /**
     * 微信支付接口请求地址域名部分.
     */
    private String baseUrl = DEFAULT_PAY_BASE_URL;
    
    protected volatile String mchId;
    
    protected volatile String secretKey;
    
    protected volatile String notifyUrl;
    
    protected volatile String httpProxyHost;
    
    protected volatile int httpProxyPort;
    
    protected volatile String httpProxyUsername;
    
    protected volatile String httpProxyPassword;
    
    protected volatile int retrySleepMillis = 1000;
    
    protected volatile int maxRetryTimes = 3;
    
}
