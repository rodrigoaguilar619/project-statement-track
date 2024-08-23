package project.statement.track.modules.controller.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.RestUtil;
import project.statement.track.app.beans.pojos.petition.data.AccountStatementDataPojo;
import project.statement.track.app.beans.pojos.petition.request.AccountStatementRequestPojo;
import project.statement.track.modules.business.broker.AccountStatementBusiness;

@RestController
public class AccountStatementController {
	
	@Autowired
	AccountStatementBusiness accountStatementBusiness;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = "/api/accountStatement/getAccountStatement", consumes = "application/json", produces = "application/json")
	public ResponseEntity readStatementSnowBall(HttpServletResponse httpResponse, @RequestBody AccountStatementRequestPojo requestPojo) throws BusinessException {
		
		AccountStatementDataPojo dataPojo = accountStatementBusiness.executeGetAccountStatement(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Account statement getted");
	}
}
