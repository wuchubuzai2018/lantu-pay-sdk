package cn.ltzf.sdkjava.common.http.okhttp;

import cn.ltzf.sdkjava.common.error.LantuPayErrorException;
import cn.ltzf.sdkjava.common.http.AbstractPostRequestExecutor;
import cn.ltzf.sdkjava.common.http.BodyTypeEnums;
import cn.ltzf.sdkjava.common.http.RequestHttp;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Wuchubuzai
 */
@Slf4j
public class OkHttpSimplePostRequestExecutor extends AbstractPostRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    
    public OkHttpSimplePostRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }
    
    @Override
    public String execute(String uri, String postEntity, BodyTypeEnums bodyTypeEnums)
            throws LantuPayErrorException, IOException {
        RequestBody body = RequestBody.Companion.create(postEntity,
                MediaType.parse(bodyTypeEnums.getContentType()));
        Request request = new Request.Builder().url(uri).post(body).build();
        Response response = requestHttp.getRequestHttpClient().newCall(request).execute();
        return this.handleResponse(Objects.requireNonNull(response.body()).string());
    }
    
    @Override
    public String execute(String uri, String postEntity) throws LantuPayErrorException, IOException {
        RequestBody body = RequestBody.Companion.create(postEntity,
                MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(uri).post(body).build();
        Response response = requestHttp.getRequestHttpClient().newCall(request).execute();
        return this.handleResponse(Objects.requireNonNull(response.body()).string());
    }
    
}
