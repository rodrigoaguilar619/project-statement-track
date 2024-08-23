package project.statement.track.app.beans.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import project.statement.track.app.beans.entity.generic.GenericCatalogIntEntity;


/**
 * The persistent class for the catalog_type_currency database table.
 * 
 */
@Entity
@Table(name="catalog_type_currency")
@NamedQuery(name="CatalogTypeCurrency.findAll", query="SELECT c FROM CatalogTypeCurrency c")
public class CatalogTypeCurrency extends GenericCatalogIntEntity {

	private static final long serialVersionUID = 1L;

}