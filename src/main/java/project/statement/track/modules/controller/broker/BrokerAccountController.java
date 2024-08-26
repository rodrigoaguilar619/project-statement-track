package project.statement.track.modules.controller.broker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.utils.RestUtil;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.pojos.petition.data.GetBADateStatementsDataPojo;
import project.statement.track.app.beans.pojos.petition.data.GetBrokerAccountsDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetBADateStatementsRequestPojo;
import project.statement.track.app.beans.pojos.petition.request.GetBrokerAccountsRequestPojo;
import project.statement.track.app.vo.catalogs.CatalogsUri;
import project.statement.track.modules.business.broker.BrokerAccountBusiness;

@RequiredArgsConstructor
@RestController
public class BrokerAccountController {
	
	private final BrokerAccountBusiness brokerAccountBusiness;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(path = CatalogsUri.API_BROKER_ACCOUNT_LIST_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getBrokerAccounts(@RequestBody GetBrokerAccountsRequestPojo requestPojo) {
		
		GetBrokerAccountsDataPojo dataPojo = brokerAccountBusiness.executeGetBrokerAccounts(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Broker accounts getted");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(path = CatalogsUri.API_BROKER_STATEMENT_LIST_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getDateStatements(@RequestBody GetBADateStatementsRequestPojo requestPojo) {
		
		GetBADateStatementsDataPojo dataPojo = brokerAccountBusiness.executeGetDateStatements(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Dates statements getted");
	}
}
