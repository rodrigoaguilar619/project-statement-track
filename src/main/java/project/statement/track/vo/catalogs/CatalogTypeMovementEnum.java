package project.statement.track.vo.catalogs;

public enum CatalogTypeMovementEnum {

	BUY(1),
	SELL(2),
	BUY_MARKET_SECUNDARY(3),
	BUY_MARKET_SECUNDARY_CANCELLED(4);
	
	private Integer id;

	private CatalogTypeMovementEnum(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
