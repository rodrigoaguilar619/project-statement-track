package project.afore.track.app.vo.enums;

public enum CatalogConceptUnifiedEnum {
	
	COMISION(1, "Comisión del periodo"),
	RENDIMIENTOS(2, "Rendimientos del periodo"),
	INTERESES_TRANSITO_IMSS(3, "Intereses en Transito IMSS del periodo"),
	APORTACION_PATRONAL(4, "Aportación patronal"),
	APORTACION_CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ_IMSS(5, "Aportación cesantia en edad avanzada y vejez IMSS"),
	APORTACION_GOBIERNO(6, "Aportación gobierno"),
	APORTACION_EN_SUBCUENTA_CUOTA_SOCIAL_IMSS(7, "Aportación en Subcuenta CUOTA SOCIAL IMSS"),
	APORTACION_VOLUNTARIA(8, "Aportación voluntaria"),
	INTERES_DE_VIVIENDA_INFONAVIT_DEL_PERIODO(9, "Interes de Vivienda INFONAVIT del periodo"),
	APORTACION_INFONAVIT_97(10, "Aportacion INFONAVIT 97"),
	DESFASE(11, "Desfase"),
	ACTUALIZACIONES_Y_RECARGOS_IMSS_DEL_PERIODO(13, "Actualizaciones y Recargos IMSS del periodo"),
	RETIRO_DE_AHORRO_VOLUNTARIO(15, "Retiro de Ahorro Voluntario");
	
	private Integer id;
	
	private String description;
	
	private CatalogConceptUnifiedEnum(Integer id, String description) {
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
