package cn.ltzf.sdkjava.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Wuchubuzai
 * @date 2024-01-01 18:35:22
 */
public class LantuRequestDataUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LantuRequestDataUtil.class);
    
    private LantuRequestDataUtil(){
    
    }
    
    /**
     * 生成URL的请求格式字符串
     * @param urlEncoder 是否编码
     * @param params 请求的map参数
     * @return
     */
    public static String generateUrlEncode(boolean urlEncoder, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> param : params.entrySet()) {
            String value = param.getValue();
            if (StringUtils.isBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(param.getKey()).append("=");
            if (urlEncoder) {
                value = urlEncode(value);
            }
            sb.append(value);
        }
        return sb.toString();
    }
    
    /**
     * URL编码
     * @param value 参数值
     * @return 编码值
     */
    public static String urlEncode(String value)  {
        String newVal = null;
        try {
            newVal = URLEncoder.encode(value, "utf-8");
        } catch (Exception e) {
            LOGGER.warn("value编码出现异常,值为:{}", value);
            newVal = value;
        }
        return newVal;
    }
}
