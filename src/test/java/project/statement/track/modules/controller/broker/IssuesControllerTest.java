package project.statement.track.modules.controller.broker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.statement.track.ProjectIntegrationTest;
import project.statement.track.app.beans.pojos.petition.data.GetIssuesBuyDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetIssuesBuyRequestPojo;
import project.statement.track.app.vo.catalogs.CatalogsEntity;

@SuppressWarnings("unchecked")
class IssuesControllerTest extends ProjectIntegrationTest {
	
	@Autowired
	IssuesController issuesController;

	@Test
	void testGetIssuesBuy() {
		
		GetIssuesBuyRequestPojo requestPojo = new GetIssuesBuyRequestPojo();
		requestPojo.setIdBrokerAccount(CatalogsEntity.CatalogBrokerAccount.SNOWBALL_MAIN);

		ResponseEntity<GenericResponsePojo<GetIssuesBuyDataPojo>> response = issuesController.getIssuesBuy(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getIssuesBuy());
	}

}
