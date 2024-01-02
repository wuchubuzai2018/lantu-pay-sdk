package cn.ltzf.sdkjava.common.http.okhttp;

import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import cn.ltzf.sdkjava.common.http.AbstractGetRequestExecutor;
import cn.ltzf.sdkjava.common.http.BodyTypeEnums;
import cn.ltzf.sdkjava.common.http.RequestHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * .
 *
 * @author ecoolper created on  2017/5/4
 */
public class OkHttpSimpleGetRequestExecutor extends AbstractGetRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    
    public OkHttpSimpleGetRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }
    
    @Override
    public String execute(String uri, String data, BodyTypeEnums bodyTypeEnums)
            throws LantuPayErrorException, IOException {
        return this.execute(uri, data);
    }
    
    @Override
    public String execute(String uri, String queryParam) throws LantuPayErrorException, IOException {
        if (queryParam != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
        }
        
        //得到httpClient
        OkHttpClient client = requestHttp.getRequestHttpClient();
        Request request = new Request.Builder().url(uri).build();
        Response response = client.newCall(request).execute();
        return this.handleResponse(response.body().string());
    }
    
}
