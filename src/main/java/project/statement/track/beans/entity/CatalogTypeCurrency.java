package project.statement.track.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the catalog_type_currency database table.
 * 
 */
@Entity
@Table(name="catalog_type_currency")
@NamedQuery(name="CatalogTypeCurrency.findAll", query="SELECT c FROM CatalogTypeCurrency c")
public class CatalogTypeCurrency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String description;

	//bi-directional many-to-one association to CatalogBroker
	@OneToMany(mappedBy="catalogTypeCurrency")
	private List<CatalogBroker> catalogBrokers;

	public CatalogTypeCurrency() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CatalogBroker> getCatalogBrokers() {
		return this.catalogBrokers;
	}

	public void setCatalogBrokers(List<CatalogBroker> catalogBrokers) {
		this.catalogBrokers = catalogBrokers;
	}

	public CatalogBroker addCatalogBroker(CatalogBroker catalogBroker) {
		getCatalogBrokers().add(catalogBroker);
		catalogBroker.setCatalogTypeCurrency(this);

		return catalogBroker;
	}

	public CatalogBroker removeCatalogBroker(CatalogBroker catalogBroker) {
		getCatalogBrokers().remove(catalogBroker);
		catalogBroker.setCatalogTypeCurrency(null);

		return catalogBroker;
	}

}