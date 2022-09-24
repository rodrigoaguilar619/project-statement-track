package project.statement.track.pojos.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MovementMoneyPojo {

	private BigDecimal amount;

	private BigDecimal amountMxn;
	
	private Long dateTransactionMillis;

	private Integer idBrokerAccount;

	private Integer idTypeTransaction;
	
	private String idIssue;

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

	public String getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(String idIssue) {
		this.idIssue = idIssue;
	}

	public Long getDateTransactionMillis() {
		return dateTransactionMillis;
	}

	public void setDateTransactionMillis(Long dateTransactionMillis) {
		this.dateTransactionMillis = dateTransactionMillis;
	}
}
