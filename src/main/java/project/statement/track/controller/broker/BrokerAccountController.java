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
import project.statement.track.business.broker.BrokerAccountBusiness;
import project.statement.track.pojos.request.AccountStatementRequestPojo;
import project.statement.track.pojos.request.GetBADateStatementsRequestPojo;
import project.statement.track.pojos.request.GetBrokerAccountsRequestPojo;
import project.statement.track.pojos.response.AccountStatementResponsePojo;
import project.statement.track.pojos.response.GetBADateStatementsResponsePojo;
import project.statement.track.pojos.response.GetBrokerAccountsResponsePojo;

@RestController
public class BrokerAccountController {

	private static final Logger log = LoggerFactory.getLogger(BrokerAccountController.class);
	
	@Autowired
	BrokerAccountBusiness brokerAccountBusiness;
	
	@PostMapping(path = "/api/broker/getBrokerAccounts", consumes = "application/json", produces = "application/json")
	public GetBrokerAccountsResponsePojo getBrokerAccounts(HttpServletResponse httpResponse, @RequestBody GetBrokerAccountsRequestPojo requestPojo) throws BusinessException {
		
		return brokerAccountBusiness.executeGetBrokerAccounts(requestPojo);
	}
	
	@PostMapping(path = "/api/broker/getDateStatements", consumes = "application/json", produces = "application/json")
	public GetBADateStatementsResponsePojo getDateStatements(HttpServletResponse httpResponse, @RequestBody GetBADateStatementsRequestPojo requestPojo) throws BusinessException {
		
		return brokerAccountBusiness.executeGetDateStatements(requestPojo);
	}
}
