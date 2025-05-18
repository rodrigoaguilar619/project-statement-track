package project.afore.track.modules.business.afore.controller.afore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.utils.RestUtil;
import lombok.RequiredArgsConstructor;
import project.afore.track.app.beans.pojos.petition.data.GetAforePeriodTabDataPojo;
import project.afore.track.app.beans.pojos.petition.data.GetAforePeriodTransactionDataPojo;
import project.afore.track.app.beans.pojos.petition.request.GetAforePeriodTabRequestPojo;
import project.afore.track.app.beans.pojos.petition.request.GetAforePeriodTransactionRequestPojo;
import project.afore.track.modules.business.afore.AforeResumeBusiness;
import project.statement.track.app.vo.catalogs.CatalogsUri;

@RequiredArgsConstructor
@RestController
public class AforeResumeController {
	
	private final AforeResumeBusiness aforeResumeBusiness;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_AFORE_PERIODS_RESUME_LIST_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getAforePeriodResume(@RequestBody GetAforePeriodTabRequestPojo requestPojo) {
		
		GetAforePeriodTabDataPojo dataPojo = aforeResumeBusiness.executeGetPeriodsResume(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Afore periods resume gotten");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_AFORE_PERIOD_DATA_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getAforePeriodData(@RequestBody GetAforePeriodTransactionRequestPojo requestPojo) {
		
		GetAforePeriodTransactionDataPojo dataPojo = aforeResumeBusiness.executeGetAforePeriodTransactions(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Afore period data gotten");
	}
}
