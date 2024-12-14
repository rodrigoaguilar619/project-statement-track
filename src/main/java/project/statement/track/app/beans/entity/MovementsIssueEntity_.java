package project.statement.track.app.beans.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MovementsIssueEntity.class)
public abstract class MovementsIssueEntity_ {

	public static volatile SingularAttribute<MovementsIssueEntity, Integer> idTypeMovement;
	public static volatile SingularAttribute<MovementsIssueEntity, BigDecimal> priceIssueUnity;
	public static volatile SingularAttribute<MovementsIssueEntity, BigDecimal> priceTotal;
	public static volatile SingularAttribute<MovementsIssueEntity, Integer> idIssue;
	public static volatile SingularAttribute<MovementsIssueEntity, Integer> id;
	public static volatile SingularAttribute<MovementsIssueEntity, Integer> idBrokerAccount;
	public static volatile SingularAttribute<MovementsIssueEntity, LocalDateTime> dateTransaction;
	public static volatile SingularAttribute<MovementsIssueEntity, BigDecimal> quantityIssues;
	public static volatile SingularAttribute<MovementsIssueEntity, BigDecimal> comisionTotal;
	public static volatile SingularAttribute<MovementsIssueEntity, CatalogIssueEntity> catalogIssue;
	public static volatile SingularAttribute<MovementsIssueEntity, CatalogTypeMovementEntity> catalogTypeMovement;

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

