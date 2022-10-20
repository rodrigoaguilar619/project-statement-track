package project.statement.track.app.pojos.tuple;

import java.math.BigDecimal;

public class IssueDividendsPojo {

	private Integer idIssue;
	
	private String issueInitials;
	
	private String issueDescription;
	
	private BigDecimal TotalDividends;
	
	private BigDecimal TotalDividendsMxn;

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
		return TotalDividends;
	}

	public void setTotalDividends(BigDecimal totalDividends) {
		TotalDividends = totalDividends;
	}

	public BigDecimal getTotalDividendsMxn() {
		return TotalDividendsMxn;
	}

	public void setTotalDividendsMxn(BigDecimal totalDividendsMxn) {
		TotalDividendsMxn = totalDividendsMxn;
	}
}
