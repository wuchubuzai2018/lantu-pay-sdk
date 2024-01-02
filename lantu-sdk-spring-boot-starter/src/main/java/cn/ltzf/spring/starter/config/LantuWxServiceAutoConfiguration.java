package cn.ltzf.spring.starter.config;

import cn.ltzf.sdkjava.api.LantuWxPayService;
import cn.ltzf.sdkjava.api.impl.LantuWxPayServiceOkHttpImpl;
import cn.ltzf.sdkjava.config.LantuWxConfigStorage;
import cn.ltzf.sdkjava.config.impl.LantuWxDefaultConfigImpl;
import cn.ltzf.spring.starter.enums.HttpClientType;
import cn.ltzf.spring.starter.enums.StorageType;
import cn.ltzf.spring.starter.properties.LantuWxPayProperties;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 蓝兔微信支付自动装配类
 *
 * @author Wuchubuzai
 */
@Configuration
@AllArgsConstructor
public class LantuWxServiceAutoConfiguration {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LantuWxServiceAutoConfiguration.class);
    
    private final LantuWxPayProperties lantuWxPayProperties;
    
    @Bean
    @ConditionalOnMissingBean(LantuWxConfigStorage.class)
    public LantuWxConfigStorage lantuWxConfigStorage() {
        StorageType type = lantuWxPayProperties.getConfigStorage().getType();
        LantuWxConfigStorage config = defaultConfigStorage();
        return config;
    }
    
    private LantuWxConfigStorage defaultConfigStorage() {
        LantuWxDefaultConfigImpl config = new LantuWxDefaultConfigImpl();
        initConfig(config);
        return config;
    }
    
    private void initConfig(LantuWxDefaultConfigImpl config) {
        LantuWxPayProperties properties = lantuWxPayProperties;
        LantuWxPayProperties.ConfigStorage configStorageProperties = properties.getConfigStorage();
        config.setMchId(properties.getMchId());
        config.setSecretKey(properties.getSecretKey());
        config.setNotifyUrl(properties.getNotifyUrl());
        
        config.setHttpProxyHost(configStorageProperties.getHttpProxyHost());
        config.setHttpProxyUsername(configStorageProperties.getHttpProxyUsername());
        config.setHttpProxyPassword(configStorageProperties.getHttpProxyPassword());
        if (configStorageProperties.getHttpProxyPort() != null) {
            config.setHttpProxyPort(configStorageProperties.getHttpProxyPort());
        }
        
        config.setMaxRetryTimes(configStorageProperties.getMaxRetryTimes());
        config.setRetrySleepMillis(configStorageProperties.getRetrySleepMillis());
    }
    
    @Bean
    @ConditionalOnMissingBean(LantuWxPayService.class)
    public LantuWxPayService lantuWxPayService(LantuWxConfigStorage lantuWxConfigStorage) {
        HttpClientType httpClientType = lantuWxPayProperties.getConfigStorage().getHttpClientType();
        LantuWxPayService lantuWxPayService;
        switch (httpClientType) {
            case OkHttp:
                lantuWxPayService = new LantuWxPayServiceOkHttpImpl();
                break;
            default:
                lantuWxPayService = new LantuWxPayServiceOkHttpImpl();
                break;
        }
        lantuWxPayService.setLantuWxConfig(lantuWxConfigStorage);
        LOGGER.info("success load init Lantu SDK Spring Boot Starter......");
        return lantuWxPayService;
    }
}
