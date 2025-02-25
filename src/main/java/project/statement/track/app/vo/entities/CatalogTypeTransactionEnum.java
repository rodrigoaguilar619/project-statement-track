package project.statement.track.app.vo.entities;

public enum CatalogTypeTransactionEnum {

	DEPOSIT(1),
    WITHDRAW(2),
    DIVIDEND(3);

    private final Integer value;

    CatalogTypeTransactionEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
