package project.statement.track.app.beans.pojos.petition.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.statement.track.app.beans.pojos.entity.MovementMoneyResumePojo;
import project.statement.track.app.beans.pojos.tuple.IssueDividendsPojo;

@Getter @Setter
public class GetAccountResumeDataPojo {

	private BigDecimal totalDeposits;
	
	private BigDecimal totalWithdraws;
	
	private BigDecimal totalDividends;
	
	private BigDecimal currentBalance;
	
	private List<MovementMoneyResumePojo> movementsMoney = new ArrayList<>();
	
	private List<IssueDividendsPojo> movementsMoneyDividend = new ArrayList<>();
}
