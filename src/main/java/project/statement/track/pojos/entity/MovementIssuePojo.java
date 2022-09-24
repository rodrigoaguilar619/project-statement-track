package project.statement.track.pojos.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class MovementIssuePojo {

	private Date dateTransaction;

	private int idBrokerAccount;

	private String idIssue;

	private int idTypeMovement;

	private BigDecimal priceIssueUnity;
	
	private BigDecimal priceTotal;

	private Integer quantityIssues;

	public Date getDateTransaction() {
		return dateTransaction;
	}

	public void setDateTransaction(Date dateTransaction) {
		this.dateTransaction = dateTransaction;
	}

	public int getIdBrokerAccount() {
		return idBrokerAccount;
	}

	public void setIdBrokerAccount(int idBrokerAccount) {
		this.idBrokerAccount = idBrokerAccount;
	}

	public String getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(String idIssue) {
		this.idIssue = idIssue;
	}

	public int getIdTypeMovement() {
		return idTypeMovement;
	}

	public void setIdTypeMovement(int idTypeMovement) {
		this.idTypeMovement = idTypeMovement;
	}

	public BigDecimal getPriceIssueUnity() {
		return priceIssueUnity;
	}

	public void setPriceIssueUnity(BigDecimal priceIssueUnity) {
		this.priceIssueUnity = priceIssueUnity;
	}

	public BigDecimal getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}

	public Integer getQuantityIssues() {
		return quantityIssues;
	}

	public void setQuantityIssues(Integer quantityIssues) {
		this.quantityIssues = quantityIssues;
	}
}
