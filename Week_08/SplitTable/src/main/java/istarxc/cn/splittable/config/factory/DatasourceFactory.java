package istarxc.cn.splittable.config.factory;

import com.alibaba.fastjson.JSON;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2019-05-29 18:06
 */
@Slf4j
public class DatasourceFactory {

    /**
     * 创建一个数据源
     * @param environment
     * @param dbName
     * return
     */
    public static DataSource createDatasource(Environment environment , final String dbName){


        final String driverClassName = environment.getProperty(dbName + ".driver-class-name");
        final String url = environment.getProperty(dbName + ".jdbc-url");
        final String username = environment.getProperty(dbName + ".username");
        final String password = environment.getProperty(dbName + ".password");

        Objects.requireNonNull(driverClassName);
        Objects.requireNonNull(url);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }
}
