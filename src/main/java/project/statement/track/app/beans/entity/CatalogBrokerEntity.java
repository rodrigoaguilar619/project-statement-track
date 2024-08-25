package project.statement.track.app.beans.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="catalog_broker")
public class CatalogBrokerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="id_type_currency")
	private Integer idTypeCurrency;

	private String acronym;

	private String description;

	@OneToMany(mappedBy="catalogBroker", fetch=FetchType.LAZY)
	private List<BrokerAccountEntity> brokerAccounts = new ArrayList<>();

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_type_currency", insertable=false, updatable=false)
	private CatalogTypeCurrencyEntity catalogTypeCurrency;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAcronym() {
		return this.acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BrokerAccountEntity> getBrokerAccounts() {
		return this.brokerAccounts;
	}

	public void setBrokerAccounts(List<BrokerAccountEntity> brokerAccounts) {
		this.brokerAccounts = brokerAccounts;
	}

	public CatalogTypeCurrencyEntity getCatalogTypeCurrency() {
		return this.catalogTypeCurrency;
	}

	public void setCatalogTypeCurrency(CatalogTypeCurrencyEntity catalogTypeCurrency) {
		this.catalogTypeCurrency = catalogTypeCurrency;
	}

}