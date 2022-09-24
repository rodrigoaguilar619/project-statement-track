package project.statement.track.vo.catalogs;

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

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
