package project.statement.track.app.beans.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MovementsIssue.class)
public abstract class MovementsIssue_ {

	public static volatile SingularAttribute<MovementsIssue, Integer> idTypeMovement;
	public static volatile SingularAttribute<MovementsIssue, BigDecimal> priceIssueUnity;
	public static volatile SingularAttribute<MovementsIssue, BigDecimal> priceTotal;
	public static volatile SingularAttribute<MovementsIssue, Integer> idIssue;
	public static volatile SingularAttribute<MovementsIssue, Integer> id;
	public static volatile SingularAttribute<MovementsIssue, Integer> idBrokerAccount;
	public static volatile SingularAttribute<MovementsIssue, Date> dateTransaction;
	public static volatile SingularAttribute<MovementsIssue, BigDecimal> quantityIssues;
	public static volatile SingularAttribute<MovementsIssue, BigDecimal> comisionTotal;
	public static volatile SingularAttribute<MovementsIssue, CatalogIssue> catalogIssue;
	public static volatile SingularAttribute<MovementsIssue, CatalogTypeMovement> catalogTypeMovement;

	public static final String ID_TYPE_MOVEMENT = "idTypeMovement";
	public static final String PRICE_ISSUE_UNITY = "priceIssueUnity";
	public static final String PRICE_TOTAL = "priceTotal";
	public static final String ID_ISSUE = "idIssue";
	public static final String ID = "id";
	public static final String ID_BROKER_ACCOUNT = "idBrokerAccount";
	public static final String DATE_TRANSACTION = "dateTransaction";
	public static final String QUANTITY_ISSUES = "quantityIssues";
	public static final String COMISION_TOTAL = "comisionTotal";
	public static final String CATALOG_ISSUE = "catalogIssue";
	public static final String CATALOG_TYPE_MOVEMENT = "catalogTypeMovement";

}

