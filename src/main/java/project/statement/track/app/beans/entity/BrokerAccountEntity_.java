package project.statement.track.app.beans.entity;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BrokerAccountEntity.class)
public abstract class BrokerAccountEntity_ {

	public static volatile SingularAttribute<BrokerAccountEntity, String> description;
	public static volatile SingularAttribute<BrokerAccountEntity, Integer> id;
	public static volatile SingularAttribute<BrokerAccountEntity, Integer> idBroker;
	public static volatile SingularAttribute<BrokerAccountEntity, Integer> cutDay;
	public static volatile SingularAttribute<BrokerAccountEntity, LocalDateTime> dateCreation;
	public static volatile SingularAttribute<BrokerAccountEntity, CatalogBrokerEntity> catalogBroker;

	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String ID_BROKER = "idBroker";
	public static final String CUT_DAY = "cutDay";
	public static final String DATE_CREATION = "dateCreation";
	public static final String CATALOG_BROKER = "catalogBroker";

}

