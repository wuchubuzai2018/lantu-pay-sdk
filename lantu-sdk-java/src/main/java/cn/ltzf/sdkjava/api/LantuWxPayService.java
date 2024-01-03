package cn.ltzf.sdkjava.api;

import cn.ltzf.sdkjava.bean.request.LantuWxPayGetWechatOpenIdRequest;
import cn.ltzf.sdkjava.bean.request.LantuWxPayNativeOrderRequest;
import cn.ltzf.sdkjava.bean.request.LantuWxPayQueryOrderRequest;
import cn.ltzf.sdkjava.bean.request.LantuWxPayRefundOrderRequest;
import cn.ltzf.sdkjava.bean.result.LantuWxPayNativeOrderResult;
import cn.ltzf.sdkjava.bean.result.LantuWxPayQueryOrderResult;
import cn.ltzf.sdkjava.bean.result.LantuWxPayRefundOrderResult;
import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import cn.ltzf.sdkjava.common.http.RequestExecutor;
import cn.ltzf.sdkjava.common.http.RequestHttp;
import cn.ltzf.sdkjava.config.LantuWxConfigStorage;

/**
 * 蓝兔支付的微信支付的核心Service类
 *
 * @author Wuchubuzai
 * @date 2023-12-15 07:53:16
 */
public interface LantuWxPayService {
    
    /**
     * 获取当前配置的url
     *
     * @return
     */
    public String getBaseUrl();
    
    void setLantuWxConfig(LantuWxConfigStorage wxConfigStorage);
    
    LantuWxConfigStorage getLantuWxConfigStorage();
    
    /**
     * 获取RequestHttp对象.
     *
     * @return RequestHttp对象 request http
     */
    RequestHttp getRequestHttp();
    
    /**
     * 初始化http请求对象.
     */
    public void initHttp();
    
    /**
     * 执行post请求
     *
     * @param url      请求地址
     * @param postData 请求数据
     * @return 返回数据
     * @throws LantuPayErrorException 异常
     */
    public String post(String url, String postData) throws LantuPayErrorException;
    
    /**
     * <pre>
     * Service没有实现某个API的时候，可以用这个，
     * {@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
     * </pre>
     *
     * @param <T>      the type parameter
     * @param <E>      the type parameter
     * @param executor 执行器
     * @param url      接口地址
     * @param data     参数数据
     * @return 结果 t
     * @throws LantuPayErrorException 异常
     */
    <T, E> T execute(RequestExecutor<T, E> executor, String url, E data) throws LantuPayErrorException;
    
    /**
     * 创建蓝兔扫码支付请求
     *
     * @param request 请求对象
     * @return 响应结果
     * @throws LantuPayErrorException 异常
     */
    public LantuWxPayNativeOrderResult createNativeOrder(LantuWxPayNativeOrderRequest request) throws LantuPayErrorException;
    
    /**
     * 订单退款 API
     * @param request 请求信息
     * @return 响应结果
     * @throws LantuPayErrorException 异常
     */
    public LantuWxPayRefundOrderResult refund(LantuWxPayRefundOrderRequest request) throws LantuPayErrorException;
    
    /**
     * 获取微信Openid授权连接的API
     * 授权链接，在微信里访问这个链接，访问后将自动跳转到您填写的回调地址，地址后面会增加openid和attach参数
     * （例如：https://www.ltzf.cn/?openid=XXXXXX&attach=自定义数据）
     * 示例值：https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa3d61201263587a8&redirect_uri
     * =https://api.ltzf.cn/api/wxpay/wx_oauth&response_type=code&scope=snsapi_base&state=
     * AB23F31B5F0C863B344C8D59605B0809#wechat_redirect
     * @param request 请求信息
     * @return 授权连接
     * @throws LantuPayErrorException 异常信息
     */
    String getWechatOpenIdAuthorizeUrl(LantuWxPayGetWechatOpenIdRequest request) throws LantuPayErrorException;
    
    /**
     * 查询蓝兔支付的订单
     * @param request 请求信息
     * @return 查询订单结果
     * @throws LantuPayErrorException 异常信息
     */
    public LantuWxPayQueryOrderResult getPayOrder(LantuWxPayQueryOrderRequest request) throws LantuPayErrorException;
    
}
