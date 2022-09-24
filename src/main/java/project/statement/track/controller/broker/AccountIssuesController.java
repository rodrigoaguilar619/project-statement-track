package project.statement.track.controller.broker;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import project.statement.track.business.broker.AccountIssuesBusiness;
import project.statement.track.pojos.request.GetAccountDividendsRequestPojo;
import project.statement.track.pojos.response.GetAccountDividendsResponsePojo;

@RestController
public class AccountIssuesController {

	private static final Logger log = LoggerFactory.getLogger(AccountIssuesController.class);
	
	@Autowired
	AccountIssuesBusiness accountIssuesBusiness;
	
	@PostMapping(path = "/api/account/issues/getMovementsDividend", consumes = "application/json", produces = "application/json")
	public GetAccountDividendsResponsePojo getMovementsDividend(HttpServletResponse httpResponse, @RequestBody GetAccountDividendsRequestPojo requestPojo) throws BusinessException {
		
		return accountIssuesBusiness.executeGetAccountDividends(requestPojo);
	}
}
