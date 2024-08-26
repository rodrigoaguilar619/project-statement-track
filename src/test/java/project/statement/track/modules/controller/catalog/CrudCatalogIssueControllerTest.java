package project.statement.track.modules.controller.catalog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.pojo.catalog.CatalogDataPojo;
import lib.base.backend.pojo.catalog.CatalogRequestPojo;
import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.statement.track.ProjectIntegrationTest;
import project.statement.track.app.beans.pojos.entity.CatalogIssuePojo;
import project.statement.track.app.beans.pojos.petition.data.GenericCatalogDataPojo;
import project.statement.track.app.beans.pojos.petition.data.GetCatalogIssueDataPojo;
import project.statement.track.app.beans.pojos.petition.request.catalog.CrudCatalogIssueRequestPojo;
import project.statement.track.app.beans.pojos.petition.request.catalog.GenericCatalogIdRequestPojo;
import project.statement.track.app.vo.catalogs.CatalogsErrorMessage;

@SuppressWarnings("unchecked")
class CrudCatalogIssueControllerTest extends ProjectIntegrationTest {
	
	@Autowired
	CrudCatalogIssueController crudCatalogIssueController;

	@Test
	void testGetCatalogIssues() {
		
		CatalogRequestPojo requestPojo = new CatalogRequestPojo();
		
		ResponseEntity<GenericResponsePojo<CatalogDataPojo>> response = crudCatalogIssueController.getCatalogIssues(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getCatalogs());
	}

	@Test
	void testGetCatalogIssue() {

		GenericCatalogIdRequestPojo requestPojo = new GenericCatalogIdRequestPojo();
		requestPojo.setId(1);
		
		ResponseEntity<GenericResponsePojo<GetCatalogIssueDataPojo>> response = crudCatalogIssueController.getCatalogIssue(requestPojo);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getCatalogIssueData());
	}

	@Test
	void testSaveCatalogIssue() {
		
		CatalogIssuePojo catalogIssuePojo = new CatalogIssuePojo();
		catalogIssuePojo.setId(null);
		catalogIssuePojo.setDescription("AAA T");
		catalogIssuePojo.setDescriptionCustom("AAA TEST");
		catalogIssuePojo.setInitials("AAA");

		CrudCatalogIssueRequestPojo requestPojo = new CrudCatalogIssueRequestPojo();
		requestPojo.setCatalogData(catalogIssuePojo);
		
		ResponseEntity<GenericResponsePojo<GenericCatalogDataPojo>> response = crudCatalogIssueController.saveCatalogIssue(requestPojo);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getId());
	}

	@Test
	void testUpdateCatalogIssue() {

		CatalogIssuePojo catalogIssuePojo = new CatalogIssuePojo();
		catalogIssuePojo.setId(1);
		catalogIssuePojo.setDescription("AAA T");
		catalogIssuePojo.setDescriptionCustom("AAA TEST");
		catalogIssuePojo.setInitials("AAA");

		CrudCatalogIssueRequestPojo requestPojo = new CrudCatalogIssueRequestPojo();
		requestPojo.setCatalogData(catalogIssuePojo);
		
		ResponseEntity<GenericResponsePojo<GenericCatalogDataPojo>> response = crudCatalogIssueController.updateCatalogIssue(requestPojo);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getId());
	}

	@Test
	void testDeleteCatalogIssue() throws BusinessException {

		GenericCatalogIdRequestPojo requestPojo = new GenericCatalogIdRequestPojo();
		requestPojo.setId(1);
		
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			crudCatalogIssueController.deleteCatalogIssue(requestPojo);
		});
		
		assertEquals(exception.getMessage(), CatalogsErrorMessage.getErrorMsgIssueTransactionRegistered());
	}

}
