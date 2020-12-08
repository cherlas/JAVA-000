package istarxc.cn.splittable.config;
import istarxc.cn.splittable.config.factory.DatasourceFactory;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-05 12:24
 */
@Configuration
public class DatasourceConfiguration  implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource shardingDataSource() throws SQLException {
        DataSource dataSource1 = DatasourceFactory.createDatasource(environment,"spring.datasource.db1");
        DataSource dataSource2 = DatasourceFactory.createDatasource(environment,"spring.datasource.db2");

        // 数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0", dataSource1);
        dataSourceMap.put("ds1", dataSource2);

        ShardingTableRuleConfiguration tableRuleConfiguration = new ShardingTableRuleConfiguration("user", "ds${0..1}.user_${0..15}");
        tableRuleConfiguration.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("identity_id", "dbAlgorithm"));
        tableRuleConfiguration.setTableShardingStrategy(new StandardShardingStrategyConfiguration("id", "tableAlgorithm"));

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTables().add(tableRuleConfiguration);

        Properties dbAlgorithm = new Properties();
        dbAlgorithm.setProperty("algorithm-expression", "ds${identity_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("dbAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", dbAlgorithm));

        Properties algorithmProps = new Properties();
        algorithmProps.setProperty("algorithm-expression", "user_${id % 16}");
        shardingRuleConfig.getShardingAlgorithms().put("tableAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", algorithmProps));

        return ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, Collections.singleton(shardingRuleConfig), new Properties());
    }
}
