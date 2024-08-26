package project.statement.track.modules.controller.broker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.utils.RestUtil;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.pojos.petition.data.GetIssuesBuyDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetIssuesBuyRequestPojo;
import project.statement.track.app.vo.catalogs.CatalogsUri;
import project.statement.track.modules.business.issues.IssuesBusiness;

@RequiredArgsConstructor
@RestController
public class IssuesController {
	
	private final IssuesBusiness issuesBusiness;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(path = CatalogsUri.API_ISSUES_BUY_LIST_GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getIssuesBuy(@RequestBody GetIssuesBuyRequestPojo requestPojo) {
		
		GetIssuesBuyDataPojo dataPojo = issuesBusiness.executeGetIssuesBuy(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Issues buy getted");
	}
}
