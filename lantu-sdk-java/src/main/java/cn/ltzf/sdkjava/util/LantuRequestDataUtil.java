package cn.ltzf.sdkjava.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Wuchubuzai
 * @date 2024-01-01 18:35:22
 */
public class LantuRequestDataUtil {
    
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
                try {
                    value = urlEncode(value);
                } catch (UnsupportedEncodingException e) {
                    //
                }
            }
            sb.append(value);
        }
        return sb.toString();
    }
    
    public static String urlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, "utf-8");
    }
}
