package project.statement.track.beans.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CatalogTypeCurrency.class)
public abstract class CatalogTypeCurrency_ {

	public static volatile ListAttribute<CatalogTypeCurrency, CatalogBroker> catalogBrokers;
	public static volatile SingularAttribute<CatalogTypeCurrency, String> description;
	public static volatile SingularAttribute<CatalogTypeCurrency, Integer> id;

	public static final String CATALOG_BROKERS = "catalogBrokers";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

