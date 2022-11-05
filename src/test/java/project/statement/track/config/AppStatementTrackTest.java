package project.statement.track.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;

import lib.base.backend.web.config.beans.DataBaseBeans;
import project.statement.track.config.starter.AppStatementTrack;

import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("${app.config.jpa.repositories}")
@EntityScan("${app.config.jpa.entity.scan}")
@ComponentScan(basePackages = "${app.config.component.scan}")
public abstract class AppStatementTrackTest {

	public static void main(String[] args) {
		SpringApplication.run(AppStatementTrackTest.class, args);
	}
}
