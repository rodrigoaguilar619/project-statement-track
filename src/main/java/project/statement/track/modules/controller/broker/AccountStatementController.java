package project.statement.track.modules.controller.broker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.RestUtil;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.pojos.petition.data.AccountStatementDataPojo;
import project.statement.track.app.beans.pojos.petition.request.AccountStatementRequestPojo;
import project.statement.track.app.vo.catalogs.CatalogsUri;
import project.statement.track.modules.business.broker.AccountStatementBusiness;

@RequiredArgsConstructor
@RestController
public class AccountStatementController {
	
	private final AccountStatementBusiness accountStatementBusiness;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_ACCOUNT_STATEMENT_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity readStatementSnowBall(@RequestBody AccountStatementRequestPojo requestPojo) throws BusinessException {
		
		AccountStatementDataPojo dataPojo = accountStatementBusiness.executeGetAccountStatement(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Account statement getted");
	}
}
