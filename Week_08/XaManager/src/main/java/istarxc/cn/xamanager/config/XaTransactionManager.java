package istarxc.cn.xamanager.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class XaTransactionManager {

    @Bean
    public PlatformTransactionManager xaTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
