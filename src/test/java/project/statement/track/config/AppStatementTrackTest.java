package project.statement.track.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("${app.config.jpa.repositories}")
@EntityScan("${app.config.jpa.entity.scan}")
@ComponentScan(basePackages = "${app.config.component.scan}")
public abstract class AppStatementTrackTest {

	public static void main(String[] args) {
		SpringApplication.run(AppStatementTrackTest.class, args);
	}
}
