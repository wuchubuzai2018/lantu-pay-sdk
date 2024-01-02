package cn.ltzf.sdkjava.util;

import cn.ltzf.sdkjava.bean.AbstractWxPayRequest;
import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Wuchubuai
 * @date 2024-01-01 15:06:06
 */
public class LantuWxPaySignUtil {
    
    private static final List<String> NO_SIGN_PARAMS = Arrays.asList("sign");
    
    private LantuWxPaySignUtil() {
    }
    
    /**
     * 生成签名
     * @param bean 对象
     * @param signKey 秘钥
     * @return 签名字符串
     */
    public static String createSign(Object bean, String signKey) {
        Map<String, String> map = null;
        if (bean instanceof AbstractWxPayRequest) {
            map = ((AbstractWxPayRequest) bean).getSignParams();
        } else {
            throw new LantuPayErrorException("不支持的请求对象，非AbstractWxPayRequest实现类");
        }
        if (map == null) {
            throw new LantuPayErrorException("未成功获取签名Map参数信息");
        }
        return createMapSign(map, signKey);
    }
    
    /**
     * 支付参数签名
     *
     * @param params  需要参与签名的参数
     * @param signKey 商户密钥
     * @return {String} 参数签名
     */
    public static String createMapSign(Map<String, String> params, String signKey) {
        // 生成签名前先去除sign
        params.remove("sign");
        String stringA = packageSign(params, false);
        String stringSignTemp = stringA + "&key=" + signKey;
        return DigestUtils.md5Hex(stringSignTemp).toUpperCase();
    }
    
    /**
     * 组装签名的字段
     *
     * @param params     参数
     * @param urlEncoder 是否urlEncoder
     * @return {String}
     */
    public static String packageSign(Map<String, String> params, boolean urlEncoder) {
        // 先将参数以其参数名的字典序升序进行排序
        TreeMap<String, String> sortedParams = new TreeMap<String, String>(params);
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        return LantuRequestDataUtil.generateUrlEncode(urlEncoder, sortedParams);
    }
    
}
