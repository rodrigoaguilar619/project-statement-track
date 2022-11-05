package project.statement.track.app.beans.pojos.petition.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import project.statement.track.app.beans.pojos.entity.MovementMoneyResumePojo;
import project.statement.track.app.beans.pojos.tuple.IssueDividendsPojo;

public class GetAccountResumeDataPojo {

	private BigDecimal totalDeposits;
	
	private BigDecimal totalWithdraws;
	
	private BigDecimal totalDividends;
	
	private BigDecimal currentBalance;
	
	private List<MovementMoneyResumePojo> movementsMoney = new ArrayList<>();
	
	private List<IssueDividendsPojo> movementsMoneyDividend = new ArrayList<>();

	public BigDecimal getTotalDeposits() {
		return totalDeposits;
	}

	public void setTotalDeposits(BigDecimal totalDeposits) {
		this.totalDeposits = totalDeposits;
	}

	public BigDecimal getTotalWithdraws() {
		return totalWithdraws;
	}

	public void setTotalWithdraws(BigDecimal totalWithdraws) {
		this.totalWithdraws = totalWithdraws;
	}

	public BigDecimal getTotalDividends() {
		return totalDividends;
	}

	public void setTotalDividends(BigDecimal totalDividends) {
		this.totalDividends = totalDividends;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public List<MovementMoneyResumePojo> getMovementsMoney() {
		return movementsMoney;
	}

	public void setMovementsMoney(List<MovementMoneyResumePojo> movementsMoney) {
		this.movementsMoney = movementsMoney;
	}

	public List<IssueDividendsPojo> getMovementsMoneyDividend() {
		return movementsMoneyDividend;
	}

	public void setMovementsMoneyDividend(List<IssueDividendsPojo> movementsMoneyDividend) {
		this.movementsMoneyDividend = movementsMoneyDividend;
	}
}
