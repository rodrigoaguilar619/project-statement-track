package project.afore.track.app.beans.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lib.base.backend.entity.generic.GenericCatalogIntEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name="catalog_afore_section")
public class CatalogAforeSectionEntity extends GenericCatalogIntEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
}