package project.statement.track.config.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import lib.base.backend.modules.annotations.CatalogConfiguration;
import lib.base.backend.modules.annotations.JwtConfiguration;
import lib.base.backend.modules.annotations.WebConfiguration;

@ComponentScan(basePackages = "${app.config.component.scan}")
@EnableScheduling
@WebConfiguration
@JwtConfiguration
@CatalogConfiguration
@SpringBootApplication
public class AppStatementTrack {

	public static void main(String[] args) {
		SpringApplication.run(AppStatementTrack.class, args);
	}

}
