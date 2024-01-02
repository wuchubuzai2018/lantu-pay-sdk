package cn.ltzf.sdkjava.common.http.okhttp;

import lombok.Getter;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author Wuchubuzai
 * @date 2023-12-25 12:49:45
 */
@Getter
public class OkHttpProxyInfo {
    
    private final String proxyAddress;
    
    private final int proxyPort;
    
    private final String proxyUsername;
    
    private final String proxyPassword;
    
    public OkHttpProxyInfo(String proxyHost, int proxyPort, String proxyUser, String proxyPassword) {
        this.proxyAddress = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyUsername = proxyUser;
        this.proxyPassword = proxyPassword;
    }
    
    /**
     * Creates HTTP proxy.
     */
    public static OkHttpProxyInfo httpProxy(String proxyAddress, int proxyPort, String proxyUser, String proxyPassword) {
        return new OkHttpProxyInfo(proxyAddress, proxyPort, proxyUser, proxyPassword);
    }
    
    /**
     * 返回 java.net.Proxy
     */
    public Proxy getProxy() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(getProxyAddress(), getProxyPort()));
        return proxy;
    }
    
}
