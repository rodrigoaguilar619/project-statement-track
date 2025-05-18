package project.statement.track.config.db;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import lib.base.backend.utils.FlywayUtil;

@Configuration
public class MultiFlywayConfig {

	FlywayUtil flywayUtil = new FlywayUtil();
	
	@Bean
    @Primary
    @ConfigurationProperties("snowball.datasource")
    DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("afore.datasource")
    DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

    @Bean(initMethod = "migrate")
    Flyway flywayDbSnowball(@Qualifier("snowballDataSource") DataSource dataSource1) {
        return flywayUtil.migrate(dataSource1, "classpath:db/migration/snowball", "classpath:db/migration_data/snowball");
    }

    @Bean(initMethod = "migrate")
    Flyway flywayDbAfore(@Qualifier("aforeDataSource") DataSource dataSource2) {
        return flywayUtil.migrate(dataSource2, "classpath:db/migration/afore", "classpath:db/migration_data/afore");
    }
}
