package project.statement.track.modules.controller.broker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.RestUtil;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.pojos.petition.data.GetAccountResumeDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetAccountResumeRequestPojo;
import project.statement.track.app.vo.catalogs.CatalogsUri;
import project.statement.track.modules.business.broker.AccountResumeBusiness;

@RequiredArgsConstructor
@RestController
public class AccountResumeController {

	private final AccountResumeBusiness accountResumeBusiness;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ACCOUNT_RESUME_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getAccountResume(@RequestBody GetAccountResumeRequestPojo requestPojo) throws BusinessException {
		
		GetAccountResumeDataPojo dataPojo = accountResumeBusiness.executeGetAccountResume(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Account resume getted");
	}
}
