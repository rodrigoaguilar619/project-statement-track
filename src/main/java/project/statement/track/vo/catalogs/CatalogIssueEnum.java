package project.statement.track.vo.catalogs;

public enum CatalogIssueEnum {

	PURE_WATER("SB_PW", "PURE WATER"),
	AGRO_DESERT("SB_AGROD", "AGRODESERT");
	
	private String id;
	
	private String description;

	private CatalogIssueEnum(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
