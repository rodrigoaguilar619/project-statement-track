package project.statement.track.beans.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BrokerAccount.class)
public abstract class BrokerAccount_ {

	public static volatile SingularAttribute<BrokerAccount, String> description;
	public static volatile SingularAttribute<BrokerAccount, Integer> id;
	public static volatile SingularAttribute<BrokerAccount, Integer> cutDay;
	public static volatile SingularAttribute<BrokerAccount, CatalogBroker> catalogBroker;

	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String CUT_DAY = "cutDay";
	public static final String CATALOG_BROKER = "catalogBroker";

}

