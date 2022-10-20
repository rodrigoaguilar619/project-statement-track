package project.statement.track.app.beans.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the catalog_type_movement database table.
 * 
 */
@Entity
@Table(name="catalog_type_movement")
@NamedQuery(name="CatalogTypeMovement.findAll", query="SELECT c FROM CatalogTypeMovement c")
public class CatalogTypeMovement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String description;

	public CatalogTypeMovement() {
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

}