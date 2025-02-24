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

}

