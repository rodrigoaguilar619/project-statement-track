package project.statement.track.app.beans.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import project.statement.track.app.beans.entity.generic.GenericCatalogIntEntity;

@Entity
@Table(name="catalog_type_transaction")
public class CatalogTypeTransactionEntity extends GenericCatalogIntEntity {

	private static final long serialVersionUID = 1L;

}