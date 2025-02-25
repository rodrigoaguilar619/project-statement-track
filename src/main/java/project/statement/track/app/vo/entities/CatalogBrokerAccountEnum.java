package project.statement.track.app.vo.entities;

public enum CatalogBrokerAccountEnum {

	SNOWBALL_MAIN(1);

    private final Integer value;

    CatalogBrokerAccountEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
