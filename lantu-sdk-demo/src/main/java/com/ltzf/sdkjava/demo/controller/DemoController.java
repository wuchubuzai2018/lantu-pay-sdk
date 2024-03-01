package com.ltzf.sdkjava.demo.controller;
import cn.hutool.core.net.URLDecoder;
import cn.ltzf.sdkjava.api.LantuWxPayService;
import cn.ltzf.sdkjava.bean.request.LantuWxPayGetWechatOpenIdRequest;
import cn.ltzf.sdkjava.bean.request.LantuWxPayNativeOrderRequest;
import cn.ltzf.sdkjava.bean.request.LantuWxPayQueryOrderRequest;
import cn.ltzf.sdkjava.bean.request.LantuWxPayRefundOrderRequest;
import cn.ltzf.sdkjava.bean.result.LantuWxPayNativeOrderResult;
import cn.ltzf.sdkjava.bean.result.LantuWxPayNotifyOrderResult;
import cn.ltzf.sdkjava.bean.result.LantuWxPayQueryOrderResult;
import cn.ltzf.sdkjava.bean.result.LantuWxPayRefundOrderResult;
import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import cn.ltzf.sdkjava.constant.LantuPayConstant;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wuchubuzai
 * @date 2024-01-01 18:01:25
 */
@RestController
@RequestMapping("/demo")
@Slf4j
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
     * 测试蓝兔支付 查询订单
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
    public LantuWxPayRefundOrderResult refund(@RequestBody LantuWxPayRefundOrderRequest request)
            throws LantuPayErrorException {
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
        Map<String, String> result = new HashMap<String, String>(2);
        result.put("url", wechatOpenIdAuthorizeUrl);
        return result;
    }
    
    /**
     * 测试蓝兔支付 接收订单的支付回调
     *
     * @return
     */
//    @GetMapping("/notify")
//    public String notify(Map<String, String> params) {
//        try {
//            if (params == null || params.isEmpty()) {
//                return LantuPayConstant.FAIL;
//            }
//            // 将参数转换为JSON
//            String json = JSON.toJSONString(params);
//            log.info("支付回调接口接收到参数:{}", json);
//            LantuWxPayNotifyOrderResult result = this.lantuWxPayService.parseOrderNotifyResult(json);
//            // 计算签名信息
//            log.info("蓝兔微信支付异步通知请求解析后的对象：{}", result);
//            // 模拟业务进行处理
//            return LantuPayConstant.SUCCESS;
//        } catch (Exception e) {
//            return LantuPayConstant.FAIL;
//        }

    /**
     * 支付回调
     * @param requestBody
     * @return
     */
    @PostMapping("/notify")
    public String notify(@RequestBody String requestBody){

        System.out.println();
        log.info("requestBody： {}", requestBody);
        Map<String,String> params = new HashMap<String,String>();
        params = parseQueryString(requestBody);
        // 将参数转换为JSON
        String json = JSON.toJSONString(params);
        log.info("支付回调接口接收到参数:{}", json);
        LantuWxPayNotifyOrderResult result = this.lantuWxPayService.parseOrderNotifyResult(json);
        log.info("校验成功， 处理订单。");
        String orderId = params.get("out_trade_no");
        String transaction_id = params.get("order_no");
        // 模拟业务进行处理
        log.info("支付成功 out_trade_no:{} trade_no:{}", orderId, transaction_id);
        // 计算签名信息
        log.info("蓝兔微信支付异步通知请求解析后的结果：{}", result);
        // 模拟业务进行处理
        return LantuPayConstant.SUCCESS;

    }

    /**
     * 自定义query转为map
     * @param query
     * @return
     */
    private Map<String, String> parseQueryString(String query) {
        return Arrays.stream(query.split("&"))
                .map(param -> param.split("="))
                .collect(Collectors.toMap(
                        e -> URLDecoder.decode(e[0], StandardCharsets.UTF_8), // 解码键
                        e -> e.length > 1 ? URLDecoder.decode(e[1], StandardCharsets.UTF_8) : "", // 解码值，如果存在
                        (prev, next) -> next, // 如果有重复的键，使用最新的值
                        LinkedHashMap::new)); // 保持插入顺序
    }


}

