package project.statement.track.app.beans.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MovementsMoneyEntity.class)
public abstract class MovementsMoneyEntity_ {

	public static volatile SingularAttribute<MovementsMoneyEntity, BigDecimal> amount;
	public static volatile SingularAttribute<MovementsMoneyEntity, Integer> idTypeTransaction;
	public static volatile SingularAttribute<MovementsMoneyEntity, BigDecimal> amountMxn;
	public static volatile SingularAttribute<MovementsMoneyEntity, Integer> id;
	public static volatile SingularAttribute<MovementsMoneyEntity, Integer> idBrokerAccount;
	public static volatile SingularAttribute<MovementsMoneyEntity, LocalDateTime> dateTransaction;
	public static volatile SingularAttribute<MovementsMoneyEntity, Integer> idIssue;
	public static volatile SingularAttribute<MovementsMoneyEntity, CatalogIssueEntity> catalogIssue;
	public static volatile SingularAttribute<MovementsMoneyEntity, CatalogTypeTransactionEntity> catalogTypeTransaction;
	public static volatile SingularAttribute<MovementsMoneyEntity, BrokerAccountEntity> brokerAccount;

}

