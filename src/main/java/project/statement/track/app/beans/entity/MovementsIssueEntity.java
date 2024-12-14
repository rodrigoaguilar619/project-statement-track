package project.statement.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name="movements_issue")
public class MovementsIssueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_transaction")
	private LocalDateTime dateTransaction;

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
}