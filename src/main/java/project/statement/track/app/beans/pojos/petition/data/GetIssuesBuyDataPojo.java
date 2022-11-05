package project.statement.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import project.statement.track.app.beans.pojos.tuple.IssueTotalsPojo;

public class GetIssuesBuyDataPojo {

	List<IssueTotalsPojo> issuesBuy = new ArrayList<>();

	public List<IssueTotalsPojo> getIssuesBuy() {
		return issuesBuy;
	}

	public void setIssuesBuy(List<IssueTotalsPojo> issuesBuy) {
		this.issuesBuy = issuesBuy;
	}
}
