package project.afore.track.modules.controller.afore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import lib.base.backend.pojo.rest.GenericResponsePojo;
import lib.base.backend.test.assessment.Assessment;
import project.afore.track.app.beans.pojos.petition.data.GetAforePeriodTabDataPojo;
import project.afore.track.app.beans.pojos.petition.data.GetAforePeriodTransactionDataPojo;
import project.afore.track.app.beans.pojos.petition.request.GetAforePeriodTabRequestPojo;
import project.afore.track.app.beans.pojos.petition.request.GetAforePeriodTransactionRequestPojo;
import project.statement.track.ProjectIntegrationTest;

class AforeResumeControllerTest extends ProjectIntegrationTest {

	@Autowired
	AforeResumeController aforeResumeController;
	
	@SuppressWarnings("unchecked")
	@Test
	void testGetAforePeriodData() {
		
		GetAforePeriodTransactionRequestPojo requestPojo = new GetAforePeriodTransactionRequestPojo();
		requestPojo.setDatePeriod(1514700000000L);
		
		ResponseEntity<GenericResponsePojo<GetAforePeriodTransactionDataPojo>> response = aforeResumeController.getAforePeriodData(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getPeriodTransactions());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testGetAforePeriodResume() {
		
		GetAforePeriodTabRequestPojo requestPojo = new GetAforePeriodTabRequestPojo();
		
		ResponseEntity<GenericResponsePojo<GetAforePeriodTabDataPojo>> response = aforeResumeController.getAforePeriodResume(requestPojo);
		
		Assessment.assertResponseData(response);
		Assessment.assertDataList(response.getBody().getData().getPeriods());
	}

}
