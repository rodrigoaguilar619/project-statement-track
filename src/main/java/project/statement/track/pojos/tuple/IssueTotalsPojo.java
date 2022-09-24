package project.statement.track.pojos.tuple;

import java.math.BigDecimal;

public class IssueTotalsPojo {

	private BigDecimal quantityIssues;
	
	private BigDecimal priceTotals;
	
	private String issue;
	
	private String issueAbreviation;

	public BigDecimal getQuantityIssues() {
		return quantityIssues;
	}

	public void setQuantityIssues(BigDecimal quantityIssues) {
		this.quantityIssues = quantityIssues;
	}

	public BigDecimal getPriceTotals() {
		return priceTotals;
	}

	public void setPriceTotals(BigDecimal priceTotals) {
		this.priceTotals = priceTotals;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getIssueAbreviation() {
		return issueAbreviation;
	}

	public void setIssueAbreviation(String issueAbreviation) {
		this.issueAbreviation = issueAbreviation;
	}
}
