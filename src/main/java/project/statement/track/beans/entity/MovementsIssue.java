package project.statement.track.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the movements_issue database table.
 * 
 */
@Entity
@Table(name="movements_issue")
@NamedQuery(name="MovementsIssue.findAll", query="SELECT m FROM MovementsIssue m")
public class MovementsIssue implements Serializable {
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
	private int idTypeMovement;

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
	CatalogTypeMovement catalogTypeMovement;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", insertable = false, updatable= false)
	CatalogIssue catalogIssue;

	public MovementsIssue() {
	}

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

	public CatalogTypeMovement getCatalogTypeMovement() {
		return catalogTypeMovement;
	}

	public void setCatalogTypeMovement(CatalogTypeMovement catalogTypeMovement) {
		this.catalogTypeMovement = catalogTypeMovement;
	}

	public CatalogIssue getCatalogIssue() {
		return catalogIssue;
	}

	public void setCatalogIssue(CatalogIssue catalogIssue) {
		this.catalogIssue = catalogIssue;
	}

	public BigDecimal getComisionTotal() {
		return comisionTotal;
	}

	public void setComisionTotal(BigDecimal comisionTotal) {
		this.comisionTotal = comisionTotal;
	}

}