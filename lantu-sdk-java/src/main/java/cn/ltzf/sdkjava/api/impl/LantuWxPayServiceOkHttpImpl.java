package cn.ltzf.sdkjava.api.impl;

import cn.ltzf.sdkjava.common.http.HttpTypeEnums;
import cn.ltzf.sdkjava.common.http.okhttp.DefaultOkHttpClientBuilder;
import cn.ltzf.sdkjava.common.http.okhttp.OkHttpProxyInfo;
import cn.ltzf.sdkjava.config.LantuWxConfigStorage;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import java.io.IOException;

/**
 * @author Wuhubuzai
 * @date 2023-12-15 07:59:02
 */
public class LantuWxPayServiceOkHttpImpl extends BaseLantuWxPayServiceImpl<OkHttpClient, OkHttpProxyInfo> {
    
    private OkHttpClient httpClient;
    
    private OkHttpProxyInfo httpProxy;
    
    @Override
    public OkHttpClient getRequestHttpClient() {
        return httpClient;
    }
    
    @Override
    public OkHttpProxyInfo getRequestHttpProxy() {
        return httpProxy;
    }
    
    @Override
    public HttpTypeEnums getRequestType() {
        return HttpTypeEnums.OK_HTTP;
    }
    
    @Override
    public void initHttp() {
        LantuWxConfigStorage configStorage = getLantuWxConfigStorage();
        //设置代理
        if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
            httpProxy = OkHttpProxyInfo.httpProxy(configStorage.getHttpProxyHost(),
                    configStorage.getHttpProxyPort(),
                    configStorage.getHttpProxyUsername(),
                    configStorage.getHttpProxyPassword());
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.proxy(getRequestHttpProxy().getProxy());
            
            //设置授权
            clientBuilder.authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic(httpProxy.getProxyUsername(), httpProxy.getProxyPassword());
                    return response.request().newBuilder()
                            .header("Authorization", credential)
                            .build();
                }
            });
            httpClient = clientBuilder.build();
        } else {
            httpClient = DefaultOkHttpClientBuilder.get().build();
        }
    }
    
}
