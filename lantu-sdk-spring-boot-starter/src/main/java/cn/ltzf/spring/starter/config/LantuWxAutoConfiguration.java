package cn.ltzf.spring.starter.config;

import cn.ltzf.spring.starter.properties.LantuWxPayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 蓝兔支付自动装配类
 */
@Configuration
@EnableConfigurationProperties(LantuWxPayProperties.class)
@Import({
        LantuWxServiceAutoConfiguration.class
})
public class LantuWxAutoConfiguration {
}
