package project.statement.track.modules.controller.broker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.statement.track.ProjectIntegrationTest;
import project.statement.track.app.beans.pojos.petition.data.GetAccountDividendsDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetAccountDividendsRequestPojo;
import project.statement.track.app.vo.catalogs.CatalogsEntity;

class AccountIssuesControllerTest extends ProjectIntegrationTest {

	@Autowired
	AccountIssuesController accountIssuesController;
	
	@SuppressWarnings("unchecked")
	@Test
	void testGetMovementsDividend() {
		
		GetAccountDividendsRequestPojo requestPojo = new GetAccountDividendsRequestPojo();
		requestPojo.setIdIssue(1);
		requestPojo.setIdBrokerAccount(CatalogsEntity.CatalogBrokerAccount.SNOWBALL_MAIN);
		
		ResponseEntity<GenericResponsePojo<GetAccountDividendsDataPojo>> response = accountIssuesController.getMovementsDividend(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getMovementMoneyDividends());
	}

}
