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

	public static final String AMOUNT = "amount";
	public static final String ID_TYPE_TRANSACTION = "idTypeTransaction";
	public static final String AMOUNT_MXN = "amountMxn";
	public static final String ID = "id";
	public static final String ID_BROKER_ACCOUNT = "idBrokerAccount";
	public static final String DATE_TRANSACTION = "dateTransaction";
	public static final String ID_ISSUE = "idIssue";
	public static final String CATALOG_ISSUE = "catalogIssue";
	public static final String CATALOG_TYPE_TRANSACTION = "catalogTypeTransaction";
	public static final String BROKER_ACCOUNT = "brokerAccount";

}

