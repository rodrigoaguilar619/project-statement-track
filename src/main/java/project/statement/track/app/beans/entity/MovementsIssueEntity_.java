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

}

