package project.statement.track.app.beans.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BrokerDataSnowball.class)
public abstract class BrokerDataSnowball_ {

	public static volatile SingularAttribute<BrokerDataSnowball, BigDecimal> balanceEntry;
	public static volatile SingularAttribute<BrokerDataSnowball, Date> dateTransaction;
	public static volatile SingularAttribute<BrokerDataSnowball, BigDecimal> previousBalance;
	public static volatile SingularAttribute<BrokerDataSnowball, String> movementDescription;
	public static volatile SingularAttribute<BrokerDataSnowball, String> reference;
	public static volatile SingularAttribute<BrokerDataSnowball, BigDecimal> balanceExit;
	public static volatile SingularAttribute<BrokerDataSnowball, Integer> totalIssues;
	public static volatile SingularAttribute<BrokerDataSnowball, BigDecimal> actualBalance;
	public static volatile SingularAttribute<BrokerDataSnowball, String> trackTableId;
	public static volatile SingularAttribute<BrokerDataSnowball, String> company;
	public static volatile SingularAttribute<BrokerDataSnowball, String> id;
	public static volatile SingularAttribute<BrokerDataSnowball, String> trackTable;
	public static volatile SingularAttribute<BrokerDataSnowball, String> typePayment;
	public static volatile SingularAttribute<BrokerDataSnowball, String> status;
	public static volatile SingularAttribute<BrokerDataSnowball, Boolean> statusMovement;

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

