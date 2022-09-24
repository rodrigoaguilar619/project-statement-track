package project.statement.track.beans.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MovementsMoney.class)
public abstract class MovementsMoney_ {

	public static volatile SingularAttribute<MovementsMoney, BigDecimal> amount;
	public static volatile SingularAttribute<MovementsMoney, Integer> idTypeTransaction;
	public static volatile SingularAttribute<MovementsMoney, BigDecimal> amountMxn;
	public static volatile SingularAttribute<MovementsMoney, Integer> id;
	public static volatile SingularAttribute<MovementsMoney, Integer> idBrokerAccount;
	public static volatile SingularAttribute<MovementsMoney, Date> dateTransaction;
	public static volatile SingularAttribute<MovementsMoney, String> idIssue;

	public static final String AMOUNT = "amount";
	public static final String ID_TYPE_TRANSACTION = "idTypeTransaction";
	public static final String AMOUNT_MXN = "amountMxn";
	public static final String ID = "id";
	public static final String ID_BROKER_ACCOUNT = "idBrokerAccount";
	public static final String DATE_TRANSACTION = "dateTransaction";
	public static final String ID_ISSUE = "idIssue";

}

