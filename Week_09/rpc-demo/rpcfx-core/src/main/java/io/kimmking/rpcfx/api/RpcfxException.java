package io.kimmking.rpcfx.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RpcfxException extends RuntimeException {

    private String errCode;

    private Object data;

    public RpcfxException(){
        super();
    }

    public RpcfxException(String errMsg){
        super(errMsg);
    }

    public RpcfxException(String errMsg, Throwable cas){
        super(errMsg,cas);
    }

    public RpcfxException(String errCode,String errMsg, Object data){
        super(errMsg);
        this.errCode = errCode;
        this.data = data;
    }
}
