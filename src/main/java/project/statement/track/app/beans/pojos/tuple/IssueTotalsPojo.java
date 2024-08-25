package project.statement.track.app.beans.pojos.tuple;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueTotalsPojo {

	private BigDecimal quantityIssues;
	
	private BigDecimal priceTotals;
	
	private Integer idIssue;
	
	private String issueAbreviation;

	private String issueDescription;
}
