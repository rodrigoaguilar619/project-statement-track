package project.statement.track.app.vo.catalogs;

public enum DefinitionTypeOperationEnum {

	MOVEMENT(1, "Operation movement"),
	ISSUE(2, "Operation issue");
	
	private Integer id;
	
	private String description;

	private DefinitionTypeOperationEnum(Integer id, String description) {
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
