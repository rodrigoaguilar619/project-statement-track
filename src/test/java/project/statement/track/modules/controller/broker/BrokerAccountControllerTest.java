package project.statement.track.modules.controller.broker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.statement.track.ProjectIntegrationTest;
import project.statement.track.app.beans.pojos.petition.data.GetBADateStatementsDataPojo;
import project.statement.track.app.beans.pojos.petition.data.GetBrokerAccountsDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetBADateStatementsRequestPojo;
import project.statement.track.app.beans.pojos.petition.request.GetBrokerAccountsRequestPojo;
import project.statement.track.app.vo.catalogs.CatalogsEntity;

@SuppressWarnings("unchecked")
class BrokerAccountControllerTest extends ProjectIntegrationTest {
	
	@Autowired
	BrokerAccountController brokerAccountController;

	@Test
	void testGetBrokerAccounts() {

		GetBrokerAccountsRequestPojo requestPojo = new GetBrokerAccountsRequestPojo();
		
		ResponseEntity<GenericResponsePojo<GetBrokerAccountsDataPojo>> response = brokerAccountController.getBrokerAccounts(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getBrokerAccounts());

	}

	@Test
	void testGetDateStatements() {
		
		GetBADateStatementsRequestPojo requestPojo = new GetBADateStatementsRequestPojo();
		requestPojo.setIdBrokerAccount(CatalogsEntity.CatalogBrokerAccount.SNOWBALL_MAIN);
		
		ResponseEntity<GenericResponsePojo<GetBADateStatementsDataPojo>> response = brokerAccountController.getDateStatements(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getYears());
	}

}
