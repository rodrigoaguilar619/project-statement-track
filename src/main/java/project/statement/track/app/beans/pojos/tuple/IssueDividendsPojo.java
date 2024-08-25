package project.statement.track.app.beans.pojos.tuple;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IssueDividendsPojo {

	private Integer idIssue;
	
	private String issueInitials;
	
	private String issueDescription;
	
	private BigDecimal totalDividends;
	
	private BigDecimal totalDividendsMxn;
}
