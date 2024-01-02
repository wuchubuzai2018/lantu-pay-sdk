package cn.ltzf.sdkjava.common.http.okhttp;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Wuchubuzai
 * @date 2023-12-07 19:37:55
 */
@Slf4j
@Data
public class DefaultOkHttpClientBuilder implements OkHttpClientBuilder {
    
    private final AtomicBoolean prepared = new AtomicBoolean(false);
    
    /**
     * 拦截器
     */
    private final List<Interceptor> interceptorList = new ArrayList<>();
    
    /**
     * 连接建立的超时时长
     */
    private Long connectTimeout;
    
    /**
     * 连接建立的超时时间单位
     */
    private TimeUnit connectTimeUnit;
    
    /**
     * 连接的IO读操作超时时长
     */
    private Long readTimeout;
    
    /**
     * 连接的IO读操作超时时间单位
     */
    private TimeUnit readTimeUnit;
    
    /**
     * 连接的IO写操作超时时长
     */
    private Long writeTimeout;
    
    /**
     * 连接的IO写操作超时时间单位
     */
    private TimeUnit writeTimeUnit;
    
    /**
     * 持有client对象,仅初始化一次,避免多service实例的时候造成重复初始化的问题
     */
    private OkHttpClient okHttpClient;
    
    private DefaultOkHttpClientBuilder() {
    }
    
    public static DefaultOkHttpClientBuilder get() {
        return SingletonHolder.INSTANCE;
    }
    
    @Override
    public OkHttpClient build() {
        if (!prepared.get()) {
            prepare();
        }
        return this.okHttpClient;
    }
    
    @Override
    public OkHttpClientBuilder connectTimeout(Long timeout, TimeUnit unit) {
        this.connectTimeout = timeout;
        this.connectTimeUnit = unit;
        return this;
    }
    
    @Override
    public OkHttpClientBuilder readTimeout(Long timeout, TimeUnit unit) {
        this.readTimeout = timeout;
        this.readTimeUnit = unit;
        return this;
    }
    
    @Override
    public OkHttpClientBuilder writeTimeout(Long timeout, TimeUnit unit) {
        this.writeTimeout = timeout;
        this.writeTimeUnit = unit;
        return this;
    }
    
    private synchronized void prepare() {
        if (prepared.get()) {
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor interceptor : this.interceptorList) {
            builder.addInterceptor(interceptor);
        }
        if (this.connectTimeout != null && this.connectTimeUnit != null) {
            builder.connectTimeout(this.connectTimeout, this.connectTimeUnit);
        }
        if (this.readTimeout != null && this.readTimeUnit != null) {
            builder.readTimeout(this.readTimeout, this.readTimeUnit);
        }
        if (this.writeTimeout != null && this.writeTimeUnit != null) {
            builder.writeTimeout(this.writeTimeout, this.writeTimeUnit);
        }
        this.okHttpClient = builder.build();
        prepared.set(true);
    }
    
    private static class SingletonHolder {
        private static final DefaultOkHttpClientBuilder INSTANCE = new DefaultOkHttpClientBuilder();
    }
}
