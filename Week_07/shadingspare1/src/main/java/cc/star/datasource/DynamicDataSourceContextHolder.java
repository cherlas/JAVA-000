package cc.star.datasource;


import cc.star.datasource.loadBalance.LoadBalanceService;
import cc.star.datasource.loadBalance.LoadBlanceBean;
import cc.star.datasource.loadBalance.enums.LoadBalanceEnums;
import cc.star.datasource.loadBalance.impl.PollingImpl;
import cc.star.datasource.loadBalance.impl.WeightPollingImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class DynamicDataSourceContextHolder {


    /**
     * 负载算法策略MAP
     */
    private static Map<Integer, LoadBalanceService> loadBalanceServiceMap= Maps.newHashMap();

    static {
        PollingImpl polling = new PollingImpl();
        WeightPollingImpl weightPolling = new WeightPollingImpl();
        loadBalanceServiceMap.put(LoadBalanceEnums.POLLINGTYPE.getType(),polling);
        loadBalanceServiceMap.put(LoadBalanceEnums.WEIGHTTYPE.getType(),weightPolling);
    }

    //从库负载方式
    public static Integer loadBalanceType= LoadBalanceEnums.POLLINGTYPE.getType();

    //存放线程使用的数据源信息
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();


    /**
     * 主数据源存放集合
     */
    public static List<LoadBlanceBean> MASTERDATASOURCENAMEList= Lists.newArrayList();

    /**
     * 从数据源存放集合
     */
    public static List<LoadBlanceBean> SLAVEDATASOURCENAMELIST = Lists.newArrayList();


    /**
     * 设置数据源
     */
    public static void setSlaveDataSourceType() {
        contextHolder.set(loadBalanceServiceMap.get(loadBalanceType).getDataSourceName(SLAVEDATASOURCENAMELIST));
    }


    public static void setMasterDataSourceType() {
        contextHolder.set(MASTERDATASOURCENAMEList.get(0).getDataSourceName());
    }

    /**
     * 获取数据源
     * @return
     */
    public static String getDataSourceType() {
        return contextHolder.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSourceType() {
        contextHolder.remove();
    }



    public static void setLoadBalanceType(Integer loadBalanceType) {
        DynamicDataSourceContextHolder.loadBalanceType = loadBalanceType;
    }
}
