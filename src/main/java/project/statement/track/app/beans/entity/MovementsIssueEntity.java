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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="movements_issue")
public class MovementsIssueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_transaction")
	private Date dateTransaction;

	@Column(name="id_broker_account")
	private int idBrokerAccount;

	@Column(name="id_issue")
	private Integer idIssue;

	@Column(name="id_type_movement")
	private Integer idTypeMovement;

	@Column(name="price_issue_unity")
	private BigDecimal priceIssueUnity;
	
	@Column(name="price_total")
	private BigDecimal priceTotal;
	
	@Column(name="comision_total")
	private BigDecimal comisionTotal;

	@Column(name="quantity_issues")
	private Integer quantityIssues;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_movement", insertable = false, updatable= false)
	CatalogTypeMovementEntity catalogTypeMovement;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", insertable = false, updatable= false)
	CatalogIssueEntity catalogIssue;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Integer getIdIssue() {
		return this.idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public int getIdTypeMovement() {
		return this.idTypeMovement;
	}

	public void setIdTypeMovement(int idTypeMovement) {
		this.idTypeMovement = idTypeMovement;
	}

	public BigDecimal getPriceIssueUnity() {
		return this.priceIssueUnity;
	}

	public void setPriceIssueUnity(BigDecimal priceIssueUnity) {
		this.priceIssueUnity = priceIssueUnity;
	}

	public Integer getQuantityIssues() {
		return this.quantityIssues;
	}

	public void setQuantityIssues(Integer quantityIssues) {
		this.quantityIssues = quantityIssues;
	}

	public BigDecimal getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}

	public CatalogTypeMovementEntity getCatalogTypeMovement() {
		return catalogTypeMovement;
	}

	public void setCatalogTypeMovement(CatalogTypeMovementEntity catalogTypeMovement) {
		this.catalogTypeMovement = catalogTypeMovement;
	}

	public CatalogIssueEntity getCatalogIssue() {
		return catalogIssue;
	}

	public void setCatalogIssue(CatalogIssueEntity catalogIssue) {
		this.catalogIssue = catalogIssue;
	}

	public BigDecimal getComisionTotal() {
		return comisionTotal;
	}

	public void setComisionTotal(BigDecimal comisionTotal) {
		this.comisionTotal = comisionTotal;
	}

}