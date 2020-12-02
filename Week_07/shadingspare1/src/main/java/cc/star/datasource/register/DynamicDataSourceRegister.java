package cc.star.datasource.register;

import cc.star.datasource.loadBalance.LoadBlanceBean;
import cc.star.datasource.loadBalance.enums.LoadBalanceEnums;
import cc.star.datasource.DynamicDataSourceContextHolder;
import cc.star.datasource.DynamicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    /**
     * 主数据源MAP
     */
    private Map<String, DataSource> masterDataSources = new HashMap<>();


    /**
     * 从数据源MAP
     */
    private Map<String, DataSource> slaveDataSources = new HashMap<>();

    /**
     * 从库配置头
     */
    private static final String SLAVEDATSOURPREFIXX = "spring.datasource.slave.";


    /**
     * 主库配置头
     */
    private static final String MASTERDATASOURCEPREFIXX = "spring.datasource.master.";


    /**
     * 主库配置名称前缀
     */
    private static final String MASTERNAME = "MASTER";

    /**
     * 从库配置名称前缀
     */
    private static final String SLAVENAME = "SLAVE";


    @Override
    public void setEnvironment(Environment environment) {
        initSlaveDataSource(environment);
        initMasterDataSource(environment);
    }


    /**
     * 注册数据源
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
        //创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        //将数据源合并
        dataSourceMap.putAll(masterDataSources);
        dataSourceMap.putAll(slaveDataSources);
        mpv.addPropertyValue("defaultTargetDataSource", getDefaultDataSource());
        mpv.addPropertyValue("targetDataSources", dataSourceMap);
        registry.registerBeanDefinition("dataSource", beanDefinition);
    }


    /**
     * 初始化主数据源
     *
     * @param env
     */
    private void initMasterDataSource(Environment env) {
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("driver", env.getProperty(MASTERDATASOURCEPREFIXX + ".driver"));
        dsMap.put("url", env.getProperty(MASTERDATASOURCEPREFIXX +  ".url"));
        dsMap.put("username", env.getProperty(MASTERDATASOURCEPREFIXX +  ".username"));
        dsMap.put("password", env.getProperty(MASTERDATASOURCEPREFIXX +  ".password"));
        dsMap.put("type", env.getProperty(MASTERDATASOURCEPREFIXX +  ".type"));
        DataSource ds = buildDataSource(dsMap);
        masterDataSources.put(MASTERNAME, ds);
        DynamicDataSourceContextHolder.MASTERDATASOURCENAMEList.add(new LoadBlanceBean(MASTERNAME,null));
    }


    /**
     * 初始化从数据源
     *
     * @param env
     */
    private void initSlaveDataSource(Environment env) {
        //获取轮询方式
        String loadType = env.getProperty("spring.datasource.slave.loadtype");
        boolean weightTypeFlag = false;
        if (StringUtils.isNoneBlank(loadType)) {
            if (loadType.equals(LoadBalanceEnums.WEIGHTTYPE.getDesc())) {
                //设置为加权轮询方式
                DynamicDataSourceContextHolder.setLoadBalanceType(LoadBalanceEnums.WEIGHTTYPE.getType());
                weightTypeFlag = true;
            }
        }
        // 读取配置文件获取更多数据源
        String dsPrefixs = env.getProperty("spring.datasource.slavenames");
        for (String dsPrefix : dsPrefixs.split(",")) {
            // 多个数据源
            Map<String, Object> dsMap = new HashMap<>();
            dsMap.put("driver", env.getProperty(SLAVEDATSOURPREFIXX + dsPrefix + ".driver"));
            dsMap.put("url", env.getProperty(SLAVEDATSOURPREFIXX + dsPrefix + ".url"));
            dsMap.put("username", env.getProperty(SLAVEDATSOURPREFIXX + dsPrefix + ".username"));
            dsMap.put("password", env.getProperty(SLAVEDATSOURPREFIXX + dsPrefix + ".password"));
            dsMap.put("type", env.getProperty(SLAVEDATSOURPREFIXX + dsPrefix + ".type"));
            DataSource ds = buildDataSource(dsMap);
            String dataSourceName = SLAVENAME + "_" + dsPrefix;
            slaveDataSources.put(dataSourceName, ds);
            LoadBlanceBean loadBlanceBean = null;
            if (weightTypeFlag) {
                //按道理应该做校验 后续补上
                loadBlanceBean = new LoadBlanceBean(dataSourceName, Integer.parseInt(env.getProperty(SLAVEDATSOURPREFIXX + dsPrefix + ".weight")));
            } else {
                loadBlanceBean = new LoadBlanceBean(dataSourceName, null);
            }
            DynamicDataSourceContextHolder.SLAVEDATASOURCENAMELIST.add(loadBlanceBean);
        }
    }


    public DataSource buildDataSource(Map<String, Object> dataSourceMap) {
        try {
            Object type = dataSourceMap.get("type");
            if (type == null) {
                throw new NullPointerException("DRIVER TYPPE IS NULL");
            }
            Class<? extends DataSource> dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            String driverClassName = dataSourceMap.get("driver").toString();
            String url = dataSourceMap.get("url").toString();
            String username = dataSourceMap.get("username").toString();
            String password = dataSourceMap.get("password").toString();
            // 自定义DataSource配置
            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 从主数据源中选择第一个作为默认数据源
     *
     * @return
     */
    private DataSource getDefaultDataSource() {
        for (String s : masterDataSources.keySet()) {
            return masterDataSources.get(s);
        }
        return null;
    }
}
