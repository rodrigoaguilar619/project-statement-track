package project.statement.track.app.beans.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CatalogBrokerEntity.class)
public abstract class CatalogBrokerEntity_ {

	public static volatile SingularAttribute<CatalogBrokerEntity, CatalogTypeCurrencyEntity> catalogTypeCurrency;
	public static volatile SingularAttribute<CatalogBrokerEntity, String> acronym;
	public static volatile ListAttribute<CatalogBrokerEntity, BrokerAccountEntity> brokerAccounts;
	public static volatile SingularAttribute<CatalogBrokerEntity, String> description;
	public static volatile SingularAttribute<CatalogBrokerEntity, Integer> id;
	public static volatile SingularAttribute<CatalogBrokerEntity, Integer> idTypeCurrency;

}

