package project.afore.track.app.vo.enums;

public enum CatalogSectionEnum {
	
	AHORRO_RETIRO(1, "Ahorro para el Retiro"),
	AHORRO_VIVIENDA(2, "Ahorro para la Vivienda"),
	AHORRO_VOLUNTARIO(3, "Ahorro Voluntario/Solidario");
	
	private Integer id;
	
	private String description;
	
	private CatalogSectionEnum(Integer id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
}
