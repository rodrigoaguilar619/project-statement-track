package project.statement.track;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.statement.track.config.starter.AppStatementTrack;

@ActiveProfiles("test")
@SpringBootTest(classes = AppStatementTrack.class)
public abstract class ProjectIntegrationTest extends ProjectConfigFlywayTest {

}
