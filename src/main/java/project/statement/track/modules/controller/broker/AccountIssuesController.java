package project.statement.track.modules.controller.broker;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.utils.RestUtil;
import project.statement.track.app.beans.pojos.petition.data.GetAccountDividendsDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetAccountDividendsRequestPojo;
import project.statement.track.modules.business.broker.AccountIssuesBusiness;

@RestController
public class AccountIssuesController {
	
	@Autowired
	AccountIssuesBusiness accountIssuesBusiness;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = "/api/account/issues/getMovementsDividend", consumes = "application/json", produces = "application/json")
	public ResponseEntity getMovementsDividend(HttpServletResponse httpResponse, @RequestBody GetAccountDividendsRequestPojo requestPojo) {
		
		GetAccountDividendsDataPojo dataPojo = accountIssuesBusiness.executeGetAccountDividends(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Movements dividends getted");
	}
}
