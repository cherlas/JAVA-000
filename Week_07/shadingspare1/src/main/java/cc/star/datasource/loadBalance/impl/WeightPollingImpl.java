package cc.star.datasource.loadBalance.impl;


import cc.star.datasource.loadBalance.LoadBalanceService;
import cc.star.datasource.loadBalance.LoadBlanceBean;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 权重算法实现
 */
public class WeightPollingImpl implements LoadBalanceService {



    @Override
    public String getDataSourceName(List<LoadBlanceBean> dataSourceList) {
        Map<String, Integer> serverMap = dataSourceList.stream().collect(Collectors.toMap(loadBlanceBean -> loadBlanceBean.getDataSourceName(), loadBlanceBean -> loadBlanceBean.getWeight()));
        Set<String> keySet = serverMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        List<String> serverList = new ArrayList<String>();
        while (iterator.hasNext()) {
            String server = iterator.next();
            int weight = serverMap.get(server);
            for (int i = 0; i < weight; i++){
                serverList.add(server);
            }
        }
        int randomPos = new Random().nextInt(serverList.size());
        return serverList.get(randomPos);
    }


}
