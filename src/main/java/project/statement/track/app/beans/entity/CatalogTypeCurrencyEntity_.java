package project.statement.track.app.beans.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CatalogTypeCurrencyEntity.class)
public abstract class CatalogTypeCurrencyEntity_ {

	public static volatile ListAttribute<CatalogTypeCurrencyEntity, CatalogBrokerEntity> catalogBrokers;
	public static volatile SingularAttribute<CatalogTypeCurrencyEntity, String> description;
	public static volatile SingularAttribute<CatalogTypeCurrencyEntity, Integer> id;

	public static final String CATALOG_BROKERS = "catalogBrokers";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

