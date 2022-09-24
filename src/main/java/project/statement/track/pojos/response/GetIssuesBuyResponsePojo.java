package project.statement.track.pojos.response;

import java.util.ArrayList;
import java.util.List;

import project.statement.track.pojos.tuple.IssueTotalsPojo;

public class GetIssuesBuyResponsePojo {

	List<IssueTotalsPojo> issuesBuy = new ArrayList<>();

	public List<IssueTotalsPojo> getIssuesBuy() {
		return issuesBuy;
	}

	public void setIssuesBuy(List<IssueTotalsPojo> issuesBuy) {
		this.issuesBuy = issuesBuy;
	}
}
