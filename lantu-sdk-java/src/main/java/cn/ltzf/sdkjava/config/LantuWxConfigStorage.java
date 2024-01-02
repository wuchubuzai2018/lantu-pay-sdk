package cn.ltzf.sdkjava.config;

/**
 * @author Wuchubuzai
 * @date 2023-12-19 12:50:50
 */
public interface LantuWxConfigStorage {
    
    /**
     * 基础URL信息
     * @return
     */
    public String getBaseUrl();
    
    /**
     * 商户号
     * @return
     */
    public String getMchId();
    
    /**
     * 秘钥
     * @return
     */
    public String getSecretKey();
    
    /**
     * 回调URL
     * @return
     */
    public String getNotifyUrl();
    
    /**
     * 重试时间
     * @return
     */
    public int getRetrySleepMillis();
    
    /**
     * 重试次数
     * @return
     */
    public  int getMaxRetryTimes();
    
    /**
     * Gets http proxy host.
     *
     * @return the http proxy host
     */
    public  String getHttpProxyHost();
    
    /**
     * Gets http proxy port.
     *
     * @return the http proxy port
     */
    public int getHttpProxyPort();
    
    /**
     * Gets http proxy username.
     *
     * @return the http proxy username
     */
    public String getHttpProxyUsername();
    
    /**
     * Gets http proxy password.
     *
     * @return the http proxy password
     */
    public String getHttpProxyPassword();
}
