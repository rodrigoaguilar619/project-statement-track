package project.statement.track.app.beans.pojos.entity;

import java.math.BigDecimal;

public class MovementMoneyPojo {

	private BigDecimal amount;

	private BigDecimal amountMxn;
	
	private Long dateTransactionMillis;

	private Integer idBrokerAccount;

	private Integer idTypeTransaction;
	
	private Integer idIssue;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountMxn() {
		return amountMxn;
	}

	public void setAmountMxn(BigDecimal amountMxn) {
		this.amountMxn = amountMxn;
	}

	public Integer getIdBrokerAccount() {
		return idBrokerAccount;
	}

	public void setIdBrokerAccount(Integer idBrokerAccount) {
		this.idBrokerAccount = idBrokerAccount;
	}

	public Integer getIdTypeTransaction() {
		return idTypeTransaction;
	}

	public void setIdTypeTransaction(Integer idTypeTransaction) {
		this.idTypeTransaction = idTypeTransaction;
	}

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public Long getDateTransactionMillis() {
		return dateTransactionMillis;
	}

	public void setDateTransactionMillis(Long dateTransactionMillis) {
		this.dateTransactionMillis = dateTransactionMillis;
	}
}
