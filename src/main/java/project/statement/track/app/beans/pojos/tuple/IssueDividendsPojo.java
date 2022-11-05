package project.statement.track.app.beans.pojos.tuple;

import java.math.BigDecimal;

public class IssueDividendsPojo {

	private Integer idIssue;
	
	private String issueInitials;
	
	private String issueDescription;
	
	private BigDecimal totalDividends;
	
	private BigDecimal totalDividendsMxn;

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}
	public String getIssueInitials() {
		return issueInitials;
	}

	public void setIssueInitials(String issueInitials) {
		this.issueInitials = issueInitials;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public BigDecimal getTotalDividends() {
		return totalDividends;
	}

	public void setTotalDividends(BigDecimal totalDividends) {
		this.totalDividends = totalDividends;
	}

	public BigDecimal getTotalDividendsMxn() {
		return totalDividendsMxn;
	}

	public void setTotalDividendsMxn(BigDecimal totalDividendsMxn) {
		this.totalDividendsMxn = totalDividendsMxn;
	}
}
