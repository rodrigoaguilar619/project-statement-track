package project.statement.track.modules.controller.broker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.statement.track.ProjectIntegrationTest;
import project.statement.track.app.beans.pojos.petition.data.AccountStatementDataPojo;
import project.statement.track.app.beans.pojos.petition.request.AccountStatementRequestPojo;
import project.statement.track.app.vo.catalogs.CatalogsEntity;

@SuppressWarnings("unchecked")
class AccountStatementControllerTest extends ProjectIntegrationTest {
	
	@Autowired
	AccountStatementController accountStatementController;

	@Test
	void testReadStatementSnowBall() throws BusinessException {

		AccountStatementRequestPojo requestPojo = new AccountStatementRequestPojo();
		requestPojo.setIdAccountBroker(CatalogsEntity.CatalogBrokerAccount.SNOWBALL_MAIN);
		requestPojo.setYear(2022);
		requestPojo.setMonth(1);
		
		ResponseEntity<GenericResponsePojo<AccountStatementDataPojo>> response = accountStatementController.readStatementSnowBall(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getOperationsStatement());
	}

}
