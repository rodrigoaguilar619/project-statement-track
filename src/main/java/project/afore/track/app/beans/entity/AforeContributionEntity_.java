package project.afore.track.app.beans.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import lib.base.backend.entity.generic.GenericCatalogIntEntity_;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AforeContributionEntity.class)
public abstract class AforeContributionEntity_ extends GenericCatalogIntEntity_ {

	public static volatile SingularAttribute<AforeContributionEntity, LocalDate> dateInserted;
	public static volatile SingularAttribute<AforeContributionEntity, Integer> idConcept;
	public static volatile SingularAttribute<AforeContributionEntity, Integer> idConceptUnified;
	public static volatile SingularAttribute<AforeContributionEntity, Integer> idSection;
	public static volatile SingularAttribute<AforeContributionEntity, String> periodReference;
	public static volatile SingularAttribute<AforeContributionEntity, Integer> quotedDays;
	public static volatile SingularAttribute<AforeContributionEntity, BigDecimal> baseSalary;
	public static volatile SingularAttribute<AforeContributionEntity, BigDecimal> amount;
	public static volatile SingularAttribute<AforeContributionEntity, LocalDateTime> period;
	
	public static volatile SingularAttribute<AforeContributionEntity, CatalogAforeSectionEntity> catalogAforeSectionEntity;
	public static volatile SingularAttribute<AforeContributionEntity, CatalogAforeConceptEntity> catalogAforeConceptEntity;
	public static volatile SingularAttribute<AforeContributionEntity, CatalogAforeConceptUnifiedEntity> catalogAforeConceptUnifiedEntity;
}

