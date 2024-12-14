package project.statement.track.app.beans.entity;

import java.io.Serializable;
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
@Table(name="broker_account")
public class BrokerAccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="cut_day")
	private int cutDay;
	
	@Column(name="id_broker")
	private Integer idBroker;

	private String description;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_broker", insertable = false, updatable = false)
	private CatalogBrokerEntity catalogBroker;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	private LocalDateTime dateCreation;
}