package project.statement.track;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import project.statement.track.config.db.MultiFlywayConfig;
import project.statement.track.config.starter.AppStatementTrack;

@ActiveProfiles("test")
@SpringBootTest(classes = {AppStatementTrack.class})
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MultiFlywayConfig.class))
public abstract class ProjectIntegrationTest extends ProjectConfigFlywayTest {

}
