package project.statement.track.app.beans.entity;

import java.io.Serializable;
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
 * The persistent class for the broker_account database table.
 * 
 */
@Entity
@Table(name="broker_account")
@NamedQuery(name="BrokerAccount.findAll", query="SELECT b FROM BrokerAccount b")
public class BrokerAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="cut_day")
	private int cutDay;

	private String description;

	//bi-directional many-to-one association to CatalogBroker
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_broker")
	private CatalogBroker catalogBroker;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	private Date dateCreation;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCutDay() {
		return this.cutDay;
	}

	public void setCutDay(int cutDay) {
		this.cutDay = cutDay;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CatalogBroker getCatalogBroker() {
		return this.catalogBroker;
	}

	public void setCatalogBroker(CatalogBroker catalogBroker) {
		this.catalogBroker = catalogBroker;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

}