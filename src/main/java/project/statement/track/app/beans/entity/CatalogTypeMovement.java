package project.statement.track.app.beans.entity;

import javax.persistence.*;

import project.statement.track.app.beans.entity.generic.GenericCatalogIntEntity;


/**
 * The persistent class for the catalog_type_movement database table.
 * 
 */
@Entity
@Table(name="catalog_type_movement")
@NamedQuery(name="CatalogTypeMovement.findAll", query="SELECT c FROM CatalogTypeMovement c")
public class CatalogTypeMovement extends GenericCatalogIntEntity {

	private static final long serialVersionUID = 1L;

}