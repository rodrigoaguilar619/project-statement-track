package project.statement.track.beans.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the catalog_type_transaction database table.
 * 
 */
@Entity
@Table(name="catalog_type_transaction")
@NamedQuery(name="CatalogTypeTransaction.findAll", query="SELECT c FROM CatalogTypeTransaction c")
public class CatalogTypeTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String description;

	public CatalogTypeTransaction() {
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