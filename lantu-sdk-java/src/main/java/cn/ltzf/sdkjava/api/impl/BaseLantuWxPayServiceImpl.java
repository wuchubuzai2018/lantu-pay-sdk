package cn.ltzf.sdkjava.api.impl;

import cn.ltzf.sdkjava.api.LantuWxPayService;
import cn.ltzf.sdkjava.bean.request.LantuWxPayGetWechatOpenIdRequest;
import cn.ltzf.sdkjava.bean.request.LantuWxPayNativeOrderRequest;
import cn.ltzf.sdkjava.bean.request.LantuWxPayRefundOrderRequest;
import cn.ltzf.sdkjava.bean.result.LantuWxPayGetWechatOpenIdResult;
import cn.ltzf.sdkjava.bean.result.LantuWxPayNativeOrderResult;
import cn.ltzf.sdkjava.bean.result.LantuWxPayRefundOrderResult;
import cn.ltzf.sdkjava.common.error.LantuError;
import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import cn.ltzf.sdkjava.common.http.AbstractPostRequestExecutor;
import cn.ltzf.sdkjava.common.http.BodyTypeEnums;
import cn.ltzf.sdkjava.common.http.RequestExecutor;
import cn.ltzf.sdkjava.common.http.RequestHttp;
import cn.ltzf.sdkjava.config.LantuWxConfigStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 蓝兔微信支付抽象的实现类
 *
 * @author Wuchubuzai
 * @date 2023-12-15 07:54:20
 */
public abstract class BaseLantuWxPayServiceImpl<H, P> implements LantuWxPayService, RequestHttp<H, P> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseLantuWxPayServiceImpl.class);
    
    private Map<String, LantuWxConfigStorage> configMap = new HashMap<String, LantuWxConfigStorage>();
    
    @Override
    public RequestHttp getRequestHttp() {
        return this;
    }
    
    @Override
    public LantuWxConfigStorage getLantuWxConfigStorage() {
        if (this.configMap.size() == 1) {
            // 只有一个小程序，直接返回其配置即可
            return this.configMap.values().iterator().next();
        }
        throw new LantuPayErrorException("暂不支持");
    }
    
    @Override
    public void setLantuWxConfig(LantuWxConfigStorage wxConfigStorage) {
        final String appid = wxConfigStorage.getMchId();
        configMap.put(appid, wxConfigStorage);
        this.initHttp();
    }
    
    @Override
    public String getBaseUrl() {
        return this.getLantuWxConfigStorage().getBaseUrl();
    }
    
    @Override
    public String post(String url, String postData) throws LantuPayErrorException {
        return execute(AbstractPostRequestExecutor.create(this), url, postData);
    }
    
    @Override
    public <T, E> T execute(RequestExecutor<T, E> executor, String url, E data) throws LantuPayErrorException {
        LantuWxConfigStorage curConfig = this.getLantuWxConfigStorage();
        int maxRetryTimes = curConfig.getMaxRetryTimes();
        int retrySleepMillis = curConfig.getRetrySleepMillis();
        // 核心执行的方法
        int retryTimes = 0;
        do {
            try {
                return this.executeInternal(executor, url, data);
            } catch (LantuPayErrorException e) {
                LantuError error = e.getError();
                
                if (error.getCode() == 1) {
                    // 判断是否已经超了最大重试次数
                    if (retryTimes + 1 > maxRetryTimes) {
                        LOGGER.warn("重试达到最大次数【{}】", maxRetryTimes);
                        //最后一次重试失败后，直接抛出异常，不再等待
                        throw new LantuPayErrorException("服务端异常，超出重试次数");
                    }
                    int sleepMillis = retrySleepMillis * (1 << retryTimes);
                    try {
                        LOGGER.warn("系统繁忙，{} ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException ex) {
                        throw new LantuPayErrorException(ex);
                    }
                } else {
                    throw e;
                }
            }
        } while (retryTimes++ < maxRetryTimes);
        LOGGER.warn("重试达到最大次数【{}】", maxRetryTimes);
        throw new LantuPayErrorException("服务端异常，超出重试次数");
    }
    
    protected <T, E> T executeInternal(RequestExecutor<T, E> executor, String uri, E data)
            throws LantuPayErrorException {
        try {
            T result = executor.execute(uri, data, BodyTypeEnums.FORM);
            LOGGER.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", uri, data, result);
            return result;
        } catch (LantuPayErrorException e) {
            LantuError error = e.getError();
            if (error.getCode() != 0) {
                LOGGER.warn("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", uri, data, error);
                throw new LantuPayErrorException(error, e);
            }
            return null;
        } catch (IOException e) {
            LOGGER.warn("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", uri, data, e.getMessage());
            throw new LantuPayErrorException(e);
        }
    }
    
    @Override
    public LantuWxPayNativeOrderResult createNativeOrder(LantuWxPayNativeOrderRequest request)
            throws LantuPayErrorException {
        if (StringUtils.isBlank(request.getNotifyUrl())) {
            request.setNotifyUrl(this.getLantuWxConfigStorage().getNotifyUrl());
        }
        // 检测参数并生成签名信息
        request.checkAndSign(this.getLantuWxConfigStorage());
        // 定义请求URL
        String url = this.getBaseUrl() + "/api/wxpay/native";
        // 转换对象请求为form结构形式
        String formBody = request.toFormBody();
        String resultText = this.post(url, formBody);
        return LantuWxPayNativeOrderResult.fromJson(resultText);
    }
    
    @Override
    public LantuWxPayRefundOrderResult refund(LantuWxPayRefundOrderRequest request) throws LantuPayErrorException {
        // 检测参数并生成签名信息
        request.checkAndSign(this.getLantuWxConfigStorage());
        // 定义请求URL
        String url = this.getBaseUrl() + "/api/wxpay/refund_order";
        // 转换对象请求为form结构形式
        String formBody = request.toFormBody();
        String resultText = this.post(url, formBody);
        return LantuWxPayRefundOrderResult.fromJson(resultText);
    }
    
    @Override
    public String getWechatOpenIdAuthorizeUrl(LantuWxPayGetWechatOpenIdRequest request) throws LantuPayErrorException {
        // 检测参数并生成签名信息
        request.checkAndSign(this.getLantuWxConfigStorage());
        // 定义请求URL
        String url = this.getBaseUrl() + "/api/wxpay/get_wechat_openid";
        // 转换对象请求为form结构形式
        String formBody = request.toFormBody();
        String resultText = this.post(url, formBody);
        return LantuWxPayGetWechatOpenIdResult.fromJson(resultText);
    }
    
}
