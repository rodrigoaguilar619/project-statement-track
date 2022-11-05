package project.statement.track.modules.controller.broker;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.utils.RestUtil;
import project.statement.track.app.beans.pojos.petition.data.GetBADateStatementsDataPojo;
import project.statement.track.app.beans.pojos.petition.data.GetBrokerAccountsDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetBADateStatementsRequestPojo;
import project.statement.track.app.beans.pojos.petition.request.GetBrokerAccountsRequestPojo;
import project.statement.track.modules.business.broker.BrokerAccountBusiness;

@RestController
public class BrokerAccountController {
	
	@Autowired
	BrokerAccountBusiness brokerAccountBusiness;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(path = "/api/broker/getBrokerAccounts", consumes = "application/json", produces = "application/json")
	public ResponseEntity getBrokerAccounts(HttpServletResponse httpResponse, @RequestBody GetBrokerAccountsRequestPojo requestPojo) {
		
		GetBrokerAccountsDataPojo dataPojo = brokerAccountBusiness.executeGetBrokerAccounts(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Broker accounts getted");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(path = "/api/broker/getDateStatements", consumes = "application/json", produces = "application/json")
	public ResponseEntity getDateStatements(HttpServletResponse httpResponse, @RequestBody GetBADateStatementsRequestPojo requestPojo) {
		
		GetBADateStatementsDataPojo dataPojo = brokerAccountBusiness.executeGetDateStatements(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Dates statements getted");
	}
}
