package project.statement.track;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import project.statement.track.config.ProjectContextConfig;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { ProjectContextConfig.class })
@EntityScan(basePackages = {"lib.base.backend.modules.security.jwt.entity", "project.statement.track.app.beans.entity"})
@EnableJpaRepositories(basePackages = {"lib.base.backend.modules.security.jwt.repository", "project.statement.track.app.repository"})
@ComponentScan(basePackages = {"lib.base.backend.modules.security.jwt.repository", "project.statement.track.app.repository"})
public abstract class ProjectJpaTest extends ProjectConfigFlywayTest {

}