package project.statement.track.app.vo.catalogs;

public enum CatalogTypeTransactionEnum {

	DEPOSIT(1),
	WITHDRAW(2),
	DIVIDEND(3);
	
	private Integer id;

	private CatalogTypeTransactionEnum(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
