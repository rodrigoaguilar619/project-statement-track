package project.statement.track.pojos.tuple;

import java.math.BigDecimal;

public class IssueTotalsPojo {

	private BigDecimal quantityIssues;
	
	private BigDecimal priceTotals;
	
	private Integer idIssue;
	
	private String issueAbreviation;

	private String issueDescription;
	
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

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public String getIssueAbreviation() {
		return issueAbreviation;
	}

	public void setIssueAbreviation(String issueAbreviation) {
		this.issueAbreviation = issueAbreviation;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}
}
