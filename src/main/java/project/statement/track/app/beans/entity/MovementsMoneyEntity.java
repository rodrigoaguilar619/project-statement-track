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
@Table(name="movements_money")
public class MovementsMoneyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private BigDecimal amount;

	@Column(name="amount_mxn")
	private BigDecimal amountMxn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_transaction")
	private LocalDateTime dateTransaction;

	@Column(name="id_broker_account")
	private int idBrokerAccount;

	@Column(name="id_type_transaction")
	private int idTypeTransaction;
	
	@Column(name="id_issue")
	private Integer idIssue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_transaction", insertable = false, updatable= false)
	CatalogTypeTransactionEntity catalogTypeTransaction;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_broker_account", insertable = false, updatable= false)
	BrokerAccountEntity brokerAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_issue", insertable = false, updatable= false)
	CatalogIssueEntity catalogIssue;
}