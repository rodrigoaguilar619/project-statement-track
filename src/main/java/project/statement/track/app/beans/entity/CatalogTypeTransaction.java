package project.statement.track.app.beans.entity;

import javax.persistence.*;

import project.statement.track.app.beans.entity.generic.GenericCatalogIntEntity;


/**
 * The persistent class for the catalog_type_transaction database table.
 * 
 */
@Entity
@Table(name="catalog_type_transaction")
@NamedQuery(name="CatalogTypeTransaction.findAll", query="SELECT c FROM CatalogTypeTransaction c")
public class CatalogTypeTransaction extends GenericCatalogIntEntity {

	private static final long serialVersionUID = 1L;

}