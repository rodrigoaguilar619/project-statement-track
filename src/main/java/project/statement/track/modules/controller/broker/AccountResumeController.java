package project.statement.track.modules.controller.broker;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.RestUtil;
import project.statement.track.app.beans.pojos.petition.data.GetAccountResumeDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetAccountResumeRequestPojo;
import project.statement.track.modules.business.broker.AccountResumeBusiness;

@RestController
public class AccountResumeController {

	@Autowired
	AccountResumeBusiness accountResumeBusiness;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = "/api/account/getAccountResume", consumes = "application/json", produces = "application/json")
	public ResponseEntity getAccountResume(HttpServletResponse httpResponse, @RequestBody GetAccountResumeRequestPojo requestPojo) throws BusinessException {
		
		GetAccountResumeDataPojo dataPojo = accountResumeBusiness.executeGetAccountResume(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Account resume getted");
	}
}
