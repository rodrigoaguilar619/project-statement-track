package project.statement.track.controller.broker;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import project.statement.track.business.issues.IssuesBusiness;
import project.statement.track.pojos.request.GetIssuesBuyRequestPojo;
import project.statement.track.pojos.response.GetIssuesBuyResponsePojo;

@RestController
public class IssuesController {

	private static final Logger log = LoggerFactory.getLogger(IssuesController.class);
	
	@Autowired
	IssuesBusiness issuesBusiness;
	
	@PostMapping(path = "/api/issues/getIssuesBuy", consumes = "application/json", produces = "application/json")
	public GetIssuesBuyResponsePojo getIssuesBuy(HttpServletResponse httpResponse, @RequestBody GetIssuesBuyRequestPojo requestPojo) throws BusinessException {
		
		return issuesBusiness.executeGetIssuesBuy(requestPojo);
	}
}
