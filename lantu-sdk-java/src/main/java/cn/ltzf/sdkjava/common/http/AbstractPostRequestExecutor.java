package cn.ltzf.sdkjava.common.http;

import cn.ltzf.sdkjava.common.error.LantuError;
import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import cn.ltzf.sdkjava.common.http.okhttp.OkHttpSimplePostRequestExecutor;

import java.io.IOException;

/**
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 *
 * @author Long Yu
 */
public abstract class AbstractPostRequestExecutor<H, P> implements RequestExecutor<String, String> {
    
    protected RequestHttp<H, P> requestHttp;
    
    public AbstractPostRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }
    
    @Override
    public void execute(String uri, String data, ResponseHandler<String> handler)
            throws LantuPayErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }
    
    /**
     * 创建POST请求执行器
     * @param requestHttp 请求类型
     * @return 请求执行器
     */
    public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case OK_HTTP:
                return new OkHttpSimplePostRequestExecutor(requestHttp);
            default:
                throw new IllegalArgumentException("非法请求参数");
        }
    }
    
    /**
     * 处理响应
     * @param responseContent 响应结果
     * @return 响应内容
     * @throws LantuPayErrorException 判断是否有异常
     */
    public String handleResponse(String responseContent) throws LantuPayErrorException {
        if (responseContent.isEmpty()) {
            throw new LantuPayErrorException("无响应内容");
        }
        LantuError error = LantuError.fromJson(responseContent);
        if (error.getCode() != 0) {
            throw new LantuPayErrorException(error.getMsg());
        }
        return responseContent;
    }
    
}
