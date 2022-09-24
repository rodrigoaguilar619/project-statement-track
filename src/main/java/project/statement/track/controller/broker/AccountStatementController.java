package project.statement.track.controller.broker;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import project.statement.track.business.broker.AccountStatementBusiness;
import project.statement.track.pojos.request.AccountStatementRequestPojo;
import project.statement.track.pojos.response.AccountStatementResponsePojo;

@RestController
public class AccountStatementController {

	private static final Logger log = LoggerFactory.getLogger(AccountStatementController.class);
	
	@Autowired
	AccountStatementBusiness accountStatementBusiness;
	
	@PostMapping(path = "/api/accountStatement/getAccountStatement", consumes = "application/json", produces = "application/json")
	public AccountStatementResponsePojo readStatementSnowBall(HttpServletResponse httpResponse, @RequestBody AccountStatementRequestPojo requestPojo) throws IOException, BusinessException {
		
		return accountStatementBusiness.executeGetAccountStatement(requestPojo);
	}
}
