package project.statement.track.modules.controller.broker;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import project.statement.track.app.pojos.petition.data.AccountStatementDataPojo;
import project.statement.track.app.pojos.petition.data.GetBADateStatementsDataPojo;
import project.statement.track.app.pojos.petition.data.GetBrokerAccountsDataPojo;
import project.statement.track.app.pojos.petition.request.AccountStatementRequestPojo;
import project.statement.track.app.pojos.petition.request.GetBADateStatementsRequestPojo;
import project.statement.track.app.pojos.petition.request.GetBrokerAccountsRequestPojo;
import project.statement.track.modules.business.broker.AccountStatementBusiness;
import project.statement.track.modules.business.broker.BrokerAccountBusiness;

@RestController
public class BrokerAccountController {

	private static final Logger log = LoggerFactory.getLogger(BrokerAccountController.class);
	
	@Autowired
	BrokerAccountBusiness brokerAccountBusiness;
	
	@PostMapping(path = "/api/broker/getBrokerAccounts", consumes = "application/json", produces = "application/json")
	public GetBrokerAccountsDataPojo getBrokerAccounts(HttpServletResponse httpResponse, @RequestBody GetBrokerAccountsRequestPojo requestPojo) throws BusinessException {
		
		return brokerAccountBusiness.executeGetBrokerAccounts(requestPojo);
	}
	
	@PostMapping(path = "/api/broker/getDateStatements", consumes = "application/json", produces = "application/json")
	public GetBADateStatementsDataPojo getDateStatements(HttpServletResponse httpResponse, @RequestBody GetBADateStatementsRequestPojo requestPojo) throws BusinessException {
		
		return brokerAccountBusiness.executeGetDateStatements(requestPojo);
	}
}
