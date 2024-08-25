package project.statement.track.app.beans.entity.generic;

import java.io.Serializable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public class GenericCatalogIntEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public GenericCatalogIntEntity() {
	}
	
	public GenericCatalogIntEntity(int id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int id;
	
	protected String description;
}
