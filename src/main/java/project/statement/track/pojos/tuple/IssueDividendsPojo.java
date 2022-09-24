package project.statement.track.pojos.tuple;

import java.math.BigDecimal;

public class IssueDividendsPojo {

	private String idIssue;
	
	private String issue;
	
	private BigDecimal TotalDividends;
	
	private BigDecimal TotalDividendsMxn;

	public String getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(String idIssue) {
		this.idIssue = idIssue;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
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
