package project.afore.track.app.beans.pojos;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PeriodTabResumePojo {

	private long startDate;
	
	private long endDate;
	
	private BigDecimal amountPeriod;
	
	private BigDecimal amountCumulative;
	
	private BigDecimal yieldAnualPeriod;
}
