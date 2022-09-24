package project.statement.track.vo.catalogs;

public enum CatalogBrokerAccountEnum {

	SNOWBALL_MAIN(1);
	
	private Integer id;

	private CatalogBrokerAccountEnum(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
