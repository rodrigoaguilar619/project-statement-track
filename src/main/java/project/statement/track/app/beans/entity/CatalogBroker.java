package project.statement.track.app.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the catalog_broker database table.
 * 
 */
@Entity
@Table(name="catalog_broker")
@NamedQuery(name="CatalogBroker.findAll", query="SELECT c FROM CatalogBroker c")
public class CatalogBroker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String acronym;

	private String description;

	//bi-directional many-to-one association to BrokerAccount
	@OneToMany(mappedBy="catalogBroker")
	private List<BrokerAccount> brokerAccounts;

	//bi-directional many-to-one association to CatalogTypeCurrency
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_type_currency")
	private CatalogTypeCurrency catalogTypeCurrency;

	public CatalogBroker() {
	}

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

	public List<BrokerAccount> getBrokerAccounts() {
		return this.brokerAccounts;
	}

	public void setBrokerAccounts(List<BrokerAccount> brokerAccounts) {
		this.brokerAccounts = brokerAccounts;
	}

	public BrokerAccount addBrokerAccount(BrokerAccount brokerAccount) {
		getBrokerAccounts().add(brokerAccount);
		brokerAccount.setCatalogBroker(this);

		return brokerAccount;
	}

	public BrokerAccount removeBrokerAccount(BrokerAccount brokerAccount) {
		getBrokerAccounts().remove(brokerAccount);
		brokerAccount.setCatalogBroker(null);

		return brokerAccount;
	}

	public CatalogTypeCurrency getCatalogTypeCurrency() {
		return this.catalogTypeCurrency;
	}

	public void setCatalogTypeCurrency(CatalogTypeCurrency catalogTypeCurrency) {
		this.catalogTypeCurrency = catalogTypeCurrency;
	}

}