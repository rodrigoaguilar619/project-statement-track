package test.statement.track.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;

import project.statement.track.config.starter.AppStatementTrack;

import org.springframework.boot.test.context.SpringBootTest;

@ComponentScan(basePackages = {"project.statement.track.config", "project.statement.track.services"}, 
excludeFilters= {@Filter(type = FilterType.REGEX, pattern="project.statement.track.test.*"),
		@Filter(type = FilterType.REGEX, pattern="project.statement.track.repository.*"),
		@Filter(type = FilterType.REGEX, pattern="project.statement.track.business.*"),
		@Filter(type = FilterType.REGEX, pattern="project.statement.track.config.starter.*"),
		@Filter(type = FilterType.REGEX, pattern="project.statement.track.config.beans.DataBaseBeans")})
@SpringBootTest(classes = AppStatementTrack.class)
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class, 
    DataSourceTransactionManagerAutoConfiguration.class, 
    HibernateJpaAutoConfiguration.class
})
public class MainNotJpaTest {

}
