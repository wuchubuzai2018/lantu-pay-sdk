package cn.ltzf.sdkjava.common.error;

/**
 * 蓝兔支付异常类
 *
 * @author Wuchubuzai
 * @date 2023-12-07 19:13:25
 */
public class LantuPayErrorException extends RuntimeException {
    
    public LantuPayErrorException(String message) {
        super(message);
    }
    
    public LantuPayErrorException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public LantuPayErrorException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
