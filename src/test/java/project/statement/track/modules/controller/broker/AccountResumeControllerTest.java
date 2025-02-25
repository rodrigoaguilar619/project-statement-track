package project.statement.track.modules.controller.broker;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.statement.track.ProjectIntegrationTest;
import project.statement.track.app.beans.pojos.petition.data.GetAccountResumeDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetAccountResumeRequestPojo;
import project.statement.track.app.vo.entities.CatalogBrokerAccountEnum;

@SuppressWarnings("unchecked")
class AccountResumeControllerTest extends ProjectIntegrationTest {
	
	@Autowired
	AccountResumeController accountResumeController;

	@Test
	void testGetAccountResume() throws BusinessException {
		
		GetAccountResumeRequestPojo requestPojo = new GetAccountResumeRequestPojo();
		requestPojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getValue());
		
		ResponseEntity<GenericResponsePojo<GetAccountResumeDataPojo>> response = accountResumeController.getAccountResume(requestPojo);
		
		Assessment.assertResponseData(response);
		assertNotNull(response.getBody().getData().getCurrentBalance());
		
	}

}
