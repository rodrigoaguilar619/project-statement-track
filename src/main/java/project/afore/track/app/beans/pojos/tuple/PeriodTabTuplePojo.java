package project.afore.track.app.beans.pojos.tuple;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PeriodTabTuplePojo {
	
	private long period;
	
	private BigDecimal amountPeriod;
	
	private BigDecimal amountCumulative;
}
