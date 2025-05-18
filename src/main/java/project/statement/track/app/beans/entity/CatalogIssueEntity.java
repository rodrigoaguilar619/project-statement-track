package project.statement.track.app.beans.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lib.base.backend.entity.generic.GenericCatalogIntEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name="catalog_issue")
public class CatalogIssueEntity extends GenericCatalogIntEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String initials;
	
	@Column(name="description_snowball")
	private String descriptionSnowball;
}