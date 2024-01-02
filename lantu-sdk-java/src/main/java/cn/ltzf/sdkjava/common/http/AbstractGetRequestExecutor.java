package cn.ltzf.sdkjava.common.http;

import cn.ltzf.sdkjava.common.error.LantuError;
import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import cn.ltzf.sdkjava.common.http.okhttp.OkHttpSimpleGetRequestExecutor;

import java.io.IOException;

/**
 * 简单的GET请求执行器. 请求的参数是String, 返回的结果也是String
 *
 * @author Daniel Qian
 */
public abstract class AbstractGetRequestExecutor<H, P> implements RequestExecutor<String, String> {
    
    protected RequestHttp<H, P> requestHttp;
    
    public AbstractGetRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }
    
    @Override
    public void execute(String uri, String data, ResponseHandler<String> handler)
            throws LantuPayErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }
    
    /**
     * 创建请求的执行器
     *
     * @param requestHttp 请求类型
     * @return 请求执行器
     */
    public static RequestExecutor<String, String> create(RequestHttp<?, ?> requestHttp) {
        switch (requestHttp.getRequestType()) {
            case OK_HTTP:
                return new OkHttpSimpleGetRequestExecutor(requestHttp);
            default:
                throw new IllegalArgumentException("非法请求参数");
        }
    }
    
    protected String handleResponse(String responseContent) throws LantuPayErrorException {
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
