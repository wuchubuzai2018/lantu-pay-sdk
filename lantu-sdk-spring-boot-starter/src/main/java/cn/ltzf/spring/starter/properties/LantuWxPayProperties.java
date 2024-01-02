package cn.ltzf.spring.starter.properties;

import cn.ltzf.spring.starter.enums.HttpClientType;
import cn.ltzf.spring.starter.enums.StorageType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static cn.ltzf.spring.starter.properties.LantuWxPayProperties.PREFIX;

/**
 * 蓝兔支付属性自动装配类
 *
 * @author Wuchubuzai
 * @date 2023-12-15 07:38:39
 */
@Data
@ConfigurationProperties(prefix = PREFIX)
public class LantuWxPayProperties {
    
    public static final String PREFIX = "ltzf.wx";
    
    /**
     * 商户ID
     */
    private String mchId;
    
    /**
     * 商户秘钥
     */
    private String secretKey;
    
    /**
     * 回调通知URL
     */
    private String notifyUrl;
    
    /**
     * 存储策略
     */
    private final ConfigStorage configStorage = new ConfigStorage();
    
    @Data
    public static class ConfigStorage {
    
        /**
         * 存储类型.
         */
        private StorageType type = StorageType.Memory;
    
        /**
         * http客户端类型.
         */
        private HttpClientType httpClientType = HttpClientType.OkHttp;
        
        private String httpProxyHost;
        
        private Integer httpProxyPort;
        
        private String httpProxyUsername;
        
        private String httpProxyPassword;
        
        private int retrySleepMillis = 1000;
        
        private int maxRetryTimes = 1;
        
    }
    
}
