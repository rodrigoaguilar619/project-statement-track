package project.afore.track.app.beans.pojos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PeriodResumePojo {
	
	private BigDecimal yield;
	
	private BigDecimal deposits;
	
	private BigDecimal interests;
	
	private BigDecimal withdraws;
	
	private BigDecimal commissions;
}
