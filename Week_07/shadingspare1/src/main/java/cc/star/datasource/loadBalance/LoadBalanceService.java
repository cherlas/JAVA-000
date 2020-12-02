package cc.star.datasource.loadBalance;

import java.util.List;

/**
 * 负载均衡算法实现接口
 */
public interface LoadBalanceService {

    String getDataSourceName(List<LoadBlanceBean> dataSourceList);
}
