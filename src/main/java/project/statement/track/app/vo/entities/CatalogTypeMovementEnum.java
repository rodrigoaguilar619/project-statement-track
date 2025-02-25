package project.statement.track.app.vo.entities;

public enum CatalogTypeMovementEnum {

	BUY(1),
    SELL(2),
    BUY_MARKET_SECUNDARY(3),
    BUY_MARKET_SECUNDARY_CANCELLED(4);

    private final Integer value;

    CatalogTypeMovementEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
