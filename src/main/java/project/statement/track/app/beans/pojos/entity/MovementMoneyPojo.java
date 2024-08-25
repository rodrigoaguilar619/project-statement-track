package project.statement.track.app.beans.pojos.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovementMoneyPojo {

	private BigDecimal amount;

	private BigDecimal amountMxn;
	
	private Long dateTransactionMillis;

	private Integer idBrokerAccount;

	private Integer idTypeTransaction;
	
	private Integer idIssue;
}
