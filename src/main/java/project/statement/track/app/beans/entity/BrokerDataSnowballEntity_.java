package project.statement.track.app.beans.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BrokerDataSnowballEntity.class)
public abstract class BrokerDataSnowballEntity_ {

	public static volatile SingularAttribute<BrokerDataSnowballEntity, BigDecimal> balanceEntry;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, LocalDateTime> dateTransaction;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, BigDecimal> previousBalance;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, String> movementDescription;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, String> reference;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, BigDecimal> balanceExit;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, Integer> totalIssues;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, BigDecimal> actualBalance;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, String> trackTableId;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, String> company;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, String> id;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, String> trackTable;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, String> typePayment;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, String> status;
	public static volatile SingularAttribute<BrokerDataSnowballEntity, Boolean> statusMovement;

	public static final String BALANCE_ENTRY = "balanceEntry";
	public static final String DATE_TRANSACTION = "dateTransaction";
	public static final String PREVIOUS_BALANCE = "previousBalance";
	public static final String MOVEMENT_DESCRIPTION = "movementDescription";
	public static final String REFERENCE = "reference";
	public static final String BALANCE_EXIT = "balanceExit";
	public static final String TOTAL_ISSUES = "totalIssues";
	public static final String ACTUAL_BALANCE = "actualBalance";
	public static final String TRACK_TABLE_ID = "trackTableId";
	public static final String COMPANY = "company";
	public static final String ID = "id";
	public static final String TRACK_TABLE = "trackTable";
	public static final String TYPE_PAYMENT = "typePayment";
	public static final String STATUS = "status";
	public static final String STATUS_MOVEMENT = "statusMovement";

}

