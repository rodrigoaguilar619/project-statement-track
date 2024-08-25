package project.statement.track.app.beans.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name="broker_data_snowball")
public class BrokerDataSnowballEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	
	@Override
	public String toString()
	{
	  return ToStringBuilder.reflectionToString(this);
	}
}
