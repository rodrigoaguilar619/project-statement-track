package project.statement.track.modules.controller.broker;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.utils.RestUtil;
import project.statement.track.app.beans.pojos.petition.data.GetIssuesBuyDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetIssuesBuyRequestPojo;
import project.statement.track.modules.business.issues.IssuesBusiness;

@RestController
public class IssuesController {
	
	@Autowired
	IssuesBusiness issuesBusiness;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(path = "/api/issues/getIssuesBuy", consumes = "application/json", produces = "application/json")
	public ResponseEntity getIssuesBuy(HttpServletResponse httpResponse, @RequestBody GetIssuesBuyRequestPojo requestPojo) {
		
		GetIssuesBuyDataPojo dataPojo = issuesBusiness.executeGetIssuesBuy(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Issues buy getted");
	}
}
