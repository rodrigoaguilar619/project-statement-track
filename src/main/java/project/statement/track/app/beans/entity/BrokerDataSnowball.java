package project.statement.track.app.beans.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="broker_data_snowball")
public class BrokerDataSnowball {
	
	@Id
	@Column(name="id")
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_transaction")
	private Date dateTransaction;
	
	@Column(name="previous_balance")
	private BigDecimal previousBalance;
	
	@Column(name="actual_balance")
	private BigDecimal actualBalance;
	
	@Column(name="movement_description")
	private String movementDescription;
	
	@Column(name="company")
	private String company;
	
	@Column(name="total_issues")
	private Integer totalIssues;
	
	@Column(name="balance_entry")
	private BigDecimal balanceEntry;
	
	@Column(name="balance_exit")
	private BigDecimal balanceExit;
	
	@Column(name="type_payment")
	private String typePayment;
	
	@Column(name="status")
	private String status;
	
	@Column(name="reference")
	private String reference;
	
	@Column(name="track_table")
	private String trackTable;
	
	@Column(name="track_table_id")
	private Integer trackTableId;
	
	@Column(name="status_movement")
	private Boolean statusMovement;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateTransaction() {
		return dateTransaction;
	}

	public void setDateTransaction(Date dateTransaction) {
		this.dateTransaction = dateTransaction;
	}

	public BigDecimal getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(BigDecimal previousBalance) {
		this.previousBalance = previousBalance;
	}

	public BigDecimal getActualBalance() {
		return actualBalance;
	}

	public void setActualBalance(BigDecimal actualBalance) {
		this.actualBalance = actualBalance;
	}

	public String getMovementDescription() {
		return movementDescription;
	}

	public void setMovementDescription(String movementDescription) {
		this.movementDescription = movementDescription;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getTotalIssues() {
		return totalIssues;
	}

	public void setTotalIssues(Integer totalIssues) {
		this.totalIssues = totalIssues;
	}

	public BigDecimal getBalanceEntry() {
		return balanceEntry;
	}

	public void setBalanceEntry(BigDecimal balanceEntry) {
		this.balanceEntry = balanceEntry;
	}

	public BigDecimal getBalanceExit() {
		return balanceExit;
	}

	public void setBalanceExit(BigDecimal balanceExit) {
		this.balanceExit = balanceExit;
	}

	public String getTypePayment() {
		return typePayment;
	}

	public void setTypePayment(String typePayment) {
		this.typePayment = typePayment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getTrackTable() {
		return trackTable;
	}

	public void setTrackTable(String trackTable) {
		this.trackTable = trackTable;
	}

	public Integer getTrackTableId() {
		return trackTableId;
	}

	public void setTrackTableId(Integer trackTableId) {
		this.trackTableId = trackTableId;
	}

	public Boolean getStatusMovement() {
		return statusMovement;
	}

	public void setStatusMovement(Boolean statusMovement) {
		this.statusMovement = statusMovement;
	}
	
	@Override
	public String toString()
	{
	  return ToStringBuilder.reflectionToString(this);
	}
}
