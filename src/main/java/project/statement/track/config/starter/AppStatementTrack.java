package project.statement.track.config.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories("${app.config.jpa.repositories}")
@EntityScan("${app.config.jpa.entity.scan}")
@ComponentScan(basePackages = "${app.config.component.scan}")
//@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
public class AppStatementTrack {

	public static void main(String[] args) {
		SpringApplication.run(AppStatementTrack.class, args);
	}

}
