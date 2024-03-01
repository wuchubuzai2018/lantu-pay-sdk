package cn.ltzf.sdkjava.util;

import cn.ltzf.sdkjava.bean.AbstractWxPayRequest;
import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
     *
     * @param bean                 对象
     * @param signKey              秘钥
     * @param ignoredParams 签名时需要忽略的特殊参数
     * @return 签名字符串
     */
    public static String createSign(Object bean, String signKey, String[] ignoredParams) {
        Map<String, String> map = null;
        if (bean instanceof AbstractWxPayRequest) {
            map = ((AbstractWxPayRequest) bean).getSignParams();
        } else {
            throw new LantuPayErrorException("不支持的请求对象，非AbstractWxPayRequest实现类");
        }
        if (map == null) {
            throw new LantuPayErrorException("未成功获取签名Map参数信息");
        }
        return createMapSign(map, signKey, ignoredParams);
    }

    /**
     * 支付参数签名
     *
     * @param params        需要参与签名的参数
     * @param signKey       商户密钥
     * @param ignoredParams 签名时需要忽略的特殊参数
     * @return {String} 参数签名
     */
    public static String createMapSign(Map<String, String> params, String signKey, String[] ignoredParams) {
        // 生成签名前先去除sign
        params.remove("sign");
        StringBuilder toSign = new StringBuilder();
        Set<String> keySet = new TreeMap<>(params).keySet();
        for (String key : keySet) {
            String value = params.get(key);
            boolean shouldSign = false;
            if (StringUtils.isNotEmpty(value) && !ArrayUtils.contains(ignoredParams, key)) {
                shouldSign = true;
            }

            if (shouldSign) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }
        toSign.append("key=").append(signKey);
        return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
    }

    /**
     * 校验签名是否正确.
     *
     * @param params   需要校验的参数Map
     * @param signKey  校验的签名Key
     * @return true - 签名校验成功，false - 签名校验失败
     */
    public static boolean checkSign(Map<String, String> params, String signKey) {
        System.out.println("查看key: " + signKey);
        System.out.println("查看sign: " + params.get("sign"));
        String signUsed = params.get("sign");
        String sign = createMapSign(params, signKey, new String[0]);
        String testSign = params.get("sign");
        // 问题原因： 在进入createMapSign之后， 已经删除了sign。所以通过params.get("sign")肯定无法得到
        System.out.println(testSign);
        return sign.equals(signUsed);
    }
}
