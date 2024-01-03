package com.ltzf.sdkjava.demo.controller;

import cn.ltzf.sdkjava.api.LantuWxPayService;
import cn.ltzf.sdkjava.bean.request.LantuWxPayGetWechatOpenIdRequest;
import cn.ltzf.sdkjava.bean.request.LantuWxPayNativeOrderRequest;
import cn.ltzf.sdkjava.bean.request.LantuWxPayQueryOrderRequest;
import cn.ltzf.sdkjava.bean.request.LantuWxPayRefundOrderRequest;
import cn.ltzf.sdkjava.bean.result.LantuWxPayNativeOrderResult;
import cn.ltzf.sdkjava.bean.result.LantuWxPayQueryOrderResult;
import cn.ltzf.sdkjava.bean.result.LantuWxPayRefundOrderResult;
import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuchubuzai
 * @date 2024-01-01 18:01:25
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    
    @Autowired
    private LantuWxPayService lantuWxPayService;
    
    /**
     * 测试蓝兔支付 PC端扫描请求
     *
     * @return
     */
    @GetMapping("/native")
    public LantuWxPayNativeOrderResult nativeOrder() {
        LantuWxPayNativeOrderRequest request = new LantuWxPayNativeOrderRequest();
        String tradeNo = "2023" + System.currentTimeMillis();
        request.setOutTradeNo(tradeNo);
        request.setTotalFee("0.01");
        request.setBody("测试商品");
        LantuWxPayNativeOrderResult result = lantuWxPayService.createNativeOrder(request);
        return result;
    }
    
    /**
     * 测试蓝兔支付 PC端扫描请求
     *
     * @return
     */
    @GetMapping("/query")
    public LantuWxPayQueryOrderResult query(String num) {
        LantuWxPayQueryOrderRequest request = new LantuWxPayQueryOrderRequest();
        request.setOutTradeNo(num);
        LantuWxPayQueryOrderResult result = lantuWxPayService.getPayOrder(request);
        return result;
    }
    
    /**
     * 微信支付-申请退款
     *
     * @param request 请求对象
     * @return 退款操作结果
     */
    @PostMapping("/refund")
    public LantuWxPayRefundOrderResult refund(@RequestBody LantuWxPayRefundOrderRequest request) throws LantuPayErrorException {
        return this.lantuWxPayService.refund(request);
    }
    
    /**
     * 测试蓝兔支付 获取微信授权连接
     *
     * @return
     */
    @GetMapping("/authorize")
    public Map<String, String> authorize() {
        LantuWxPayGetWechatOpenIdRequest request = new LantuWxPayGetWechatOpenIdRequest();
        request.setCallbackUrl("http://publicdomainname");
        String wechatOpenIdAuthorizeUrl = lantuWxPayService.getWechatOpenIdAuthorizeUrl(request);
        Map<String, String> result = new HashMap<String, String>();
        result.put("url", wechatOpenIdAuthorizeUrl);
        return result;
    }
    
}
