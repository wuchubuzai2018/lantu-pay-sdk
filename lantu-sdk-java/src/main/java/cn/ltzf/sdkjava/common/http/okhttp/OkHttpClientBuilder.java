package cn.ltzf.sdkjava.common.http.okhttp;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * @author Wuchubuzai
 * @date 2023-12-07 19:31:39
 */
public interface OkHttpClientBuilder {
    
    /**
     * 构建OkHttpClient实例.
     *
     * @return OkHttpClient
     */
    OkHttpClient build();
    
    /**
     * 连接建立的超时时间
     *
     * @param timeout 时长
     * @param unit    时间单位
     * @return OkHttpClientBuilder
     */
    OkHttpClientBuilder connectTimeout(Long timeout, TimeUnit unit);
    
    
    /**
     * 连接的IO读操作超时时间
     *
     * @param timeout 时长
     * @param unit    时间单位
     * @return OkHttpClientBuilder
     */
    OkHttpClientBuilder readTimeout(Long timeout, TimeUnit unit);
    
    /**
     * 连接的IO写操作超时时间
     *
     * @param timeout 时长
     * @param unit    时间单位
     * @return OkHttpClientBuilder
     */
    OkHttpClientBuilder writeTimeout(Long timeout, TimeUnit unit);
}
