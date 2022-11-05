package project.statement.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import project.statement.track.app.beans.pojos.entity.CatalogIssuePojo;

public class GetAccountIssuesDataPojo {

	private List<CatalogIssuePojo> catalogIssues = new ArrayList<>();

	public List<CatalogIssuePojo> getCatalogIssues() {
		return catalogIssues;
	}

	public void setCatalogIssues(List<CatalogIssuePojo> catalogIssues) {
		this.catalogIssues = catalogIssues;
	} 
}
