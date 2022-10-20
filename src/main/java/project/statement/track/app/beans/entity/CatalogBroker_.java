package project.statement.track.app.beans.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CatalogBroker.class)
public abstract class CatalogBroker_ {

	public static volatile SingularAttribute<CatalogBroker, CatalogTypeCurrency> catalogTypeCurrency;
	public static volatile SingularAttribute<CatalogBroker, String> acronym;
	public static volatile ListAttribute<CatalogBroker, BrokerAccount> brokerAccounts;
	public static volatile SingularAttribute<CatalogBroker, String> description;
	public static volatile SingularAttribute<CatalogBroker, Integer> id;

	public static final String CATALOG_TYPE_CURRENCY = "catalogTypeCurrency";
	public static final String ACRONYM = "acronym";
	public static final String BROKER_ACCOUNTS = "brokerAccounts";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

