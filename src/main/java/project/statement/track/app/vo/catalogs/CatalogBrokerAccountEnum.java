package project.statement.track.app.vo.catalogs;

public enum CatalogBrokerAccountEnum {

	SNOWBALL_MAIN(1);
	
	private Integer id;

	private CatalogBrokerAccountEnum(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

}
