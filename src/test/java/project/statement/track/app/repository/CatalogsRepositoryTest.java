package project.statement.track.app.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import project.statement.track.ProjectIntegrationTest;
import project.statement.track.app.beans.entity.CatalogIssueEntity;

class CatalogsRepositoryTest extends ProjectIntegrationTest {
	
	@Autowired
	CatalogsRepository catalogsRepository;

	@Test
	void testGetCatalogIssueSnowBall() {

		CatalogIssueEntity catalogIssue = catalogsRepository.getCatalogIssueSnowBall("AGRODESERT");
		
		assertNotNull(catalogIssue);
	}

}
