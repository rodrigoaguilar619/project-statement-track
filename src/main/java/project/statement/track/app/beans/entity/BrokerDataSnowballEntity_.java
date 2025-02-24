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

}

