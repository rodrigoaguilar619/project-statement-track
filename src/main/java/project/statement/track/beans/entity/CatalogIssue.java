package project.statement.track.beans.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the catalog_issue database table.
 * 
 */
@Entity
@Table(name="catalog_issue")
@NamedQuery(name="CatalogIssue.findAll", query="SELECT c FROM CatalogIssue c")
public class CatalogIssue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private String description;
	
	@Column(name="description_snowball")
	private String descriptionSnowball;

	public CatalogIssue() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionSnowball() {
		return descriptionSnowball;
	}

	public void setDescriptionSnowball(String descriptionSnowball) {
		this.descriptionSnowball = descriptionSnowball;
	}

}