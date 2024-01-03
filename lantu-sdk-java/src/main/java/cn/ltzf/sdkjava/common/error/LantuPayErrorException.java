package cn.ltzf.sdkjava.common.error;

/**
 * 蓝兔支付异常类
 *
 * @author Wuchubuzai
 * @date 2023-12-07 19:13:25
 */
public class LantuPayErrorException extends RuntimeException {
    
    private static final int DEFAULT_ERROR_CODE = -99;
    
    private final LantuError error;
    
    public LantuPayErrorException(String message) {
        this(LantuError.builder().code(DEFAULT_ERROR_CODE).msg(message).build());
    }
    
    public LantuPayErrorException(LantuError error) {
        super(error.toString());
        this.error = error;
    }
    
    public LantuPayErrorException(LantuError error, Throwable cause) {
        super(error.toString(), cause);
        this.error = error;
    }
    
    public LantuPayErrorException(Throwable cause) {
        super(cause.getMessage(), cause);
        this.error = LantuError.builder().code(DEFAULT_ERROR_CODE).msg(cause.getMessage()).build();
    }
    
    public LantuError getError() {
        return this.error;
    }
}
