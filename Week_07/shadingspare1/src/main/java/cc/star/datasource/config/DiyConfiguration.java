package cc.star.datasource.config;

import cc.star.datasource.register.DynamicDataSourceRegister;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;




@Configuration
@Import(DynamicDataSourceRegister.class)
public class DiyConfiguration {
}
