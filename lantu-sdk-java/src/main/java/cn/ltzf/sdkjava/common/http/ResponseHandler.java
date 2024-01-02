package cn.ltzf.sdkjava.common.http;

/**
 * <pre>
 * http请求响应回调处理接口.
 * </pre>
 *
 * @param <T> 返回值类型
 * @author Wuchubuzai
 */
public interface ResponseHandler<T> {
    
    /**
     * 响应结果处理.
     *
     * @param t 要处理的对象
     */
    void handle(T t);
    
}
