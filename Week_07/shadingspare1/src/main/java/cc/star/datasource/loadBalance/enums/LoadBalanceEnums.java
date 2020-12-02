package cc.star.datasource.loadBalance.enums;

import org.aspectj.lang.annotation.Aspect;

/**
 * 负载均衡使用规则
 */
public enum  LoadBalanceEnums {
    //轮询
    POLLINGTYPE(1,"POLLING"),
    //权重轮询
    WEIGHTTYPE(2,"WEIGHTPOLLING");

    private Integer type;

    private String desc;

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    LoadBalanceEnums(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
