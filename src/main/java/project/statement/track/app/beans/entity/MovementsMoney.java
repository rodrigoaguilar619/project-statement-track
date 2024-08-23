package project.statement.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/**
 * The persistent class for the movements_money database table.
 * 
 */
@Entity
@Table(name="movements_money")
@NamedQuery(name="MovementsMoney.findAll", query="SELECT m FROM MovementsMoney m")
public class MovementsMoney implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private BigDecimal amount;

	@Column(name="amount_mxn")
	private BigDecimal amountMxn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_transaction")
	private Date dateTransaction;

	@Column(name="id_broker_account")
	private int idBrokerAccount;

	@Column(name="id_type_transaction")
	private int idTypeTransaction;
	
	@Column(name="id_issue")
	private Integer idIssue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_transaction", insertable = false, updatable= false)
	CatalogTypeTransaction catalogTypeTransaction;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_broker_account", insertable = false, updatable= false)
	BrokerAccount brokerAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", insertable = false, updatable= false)
	CatalogIssue catalogIssue;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountMxn() {
		return this.amountMxn;
	}

	public void setAmountMxn(BigDecimal amountMxn) {
		this.amountMxn = amountMxn;
	}

	public Date getDateTransaction() {
		return this.dateTransaction;
	}

	public void setDateTransaction(Date dateTransaction) {
		this.dateTransaction = dateTransaction;
	}

	public int getIdBrokerAccount() {
		return this.idBrokerAccount;
	}

	public void setIdBrokerAccount(int idBrokerAccount) {
		this.idBrokerAccount = idBrokerAccount;
	}

	public int getIdTypeTransaction() {
		return this.idTypeTransaction;
	}

	public void setIdTypeTransaction(int idTypeTransaction) {
		this.idTypeTransaction = idTypeTransaction;
	}

	public CatalogTypeTransaction getCatalogTypeTransaction() {
		return catalogTypeTransaction;
	}

	public void setCatalogTypeTransaction(CatalogTypeTransaction catalogTypeTransaction) {
		this.catalogTypeTransaction = catalogTypeTransaction;
	}

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public BrokerAccount getBrokerAccount() {
		return brokerAccount;
	}

	public void setBrokerAccount(BrokerAccount brokerAccount) {
		this.brokerAccount = brokerAccount;
	}

	public CatalogIssue getCatalogIssue() {
		return catalogIssue;
	}

	public void setCatalogIssue(CatalogIssue catalogIssue) {
		this.catalogIssue = catalogIssue;
	}

}