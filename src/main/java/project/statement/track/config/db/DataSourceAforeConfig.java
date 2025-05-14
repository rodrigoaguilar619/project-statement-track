package project.statement.track.config.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import lib.base.backend.modules.web.config.custom.DataSourceConfig;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "${afore.app.config.jpa.repositories}",
	    entityManagerFactoryRef = "entityManagerFactory",
	    transactionManagerRef = "primaryTransactionManager"
)
public class DataSourceAforeConfig extends DataSourceConfig {

	@Bean(name = "aforeDataSource")
    @ConfigurationProperties("afore.spring.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "aforeManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, 
    		@Qualifier("aforeDataSource") DataSource dataSource,
            @Value("${afore.app.config.jpa.entity.scan}") String basePackage) {
		String[] basePackages = basePackage.split(",");
		return createEntityManagerFactory(builder, dataSource, basePackages, "aforePersistenceUnit");
    }

    @Bean(name = "aforeTransactionManager")
    PlatformTransactionManager transactionManager(@Qualifier("aforeManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
