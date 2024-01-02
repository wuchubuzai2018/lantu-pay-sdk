package com.ltzf.sdkjava.demo.controller;

import cn.ltzf.sdkjava.api.LantuWxPayService;
import cn.ltzf.sdkjava.bean.request.LantuWxPayNativeOrderRequest;
import cn.ltzf.sdkjava.bean.result.LantuWxPayNativeOrderResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
}
