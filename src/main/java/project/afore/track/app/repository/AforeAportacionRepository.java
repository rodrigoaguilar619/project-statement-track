package project.afore.track.app.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import project.afore.track.app.beans.entity.AforeContributionEntity;
import project.afore.track.app.beans.entity.AforeContributionEntity_;
import project.afore.track.app.beans.pojos.tuple.PeriodTabTuplePojo;

@RequiredArgsConstructor
@Repository
public class AforeAportacionRepository {
	
	@PersistenceContext(unitName = "aforePersistenceUnit")
	private final EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<PeriodTabTuplePojo> findContributionWithTotal() {
		List<Object[]> result = entityManager.createNativeQuery("""
            WITH running_totals AS (
			  SELECT 
			    period,
			    SUM(amount) AS amount,
			    SUM(SUM(amount)) OVER (ORDER BY period) AS amount_total
			  FROM afore_contribution
			  GROUP BY period
			)
			SELECT *
			FROM running_totals
			ORDER BY period DESC
        """).getResultList();
		
		List<PeriodTabTuplePojo> periodTabPojos = new ArrayList<>();
		
		for (Object[] row : result) {
			Timestamp periodTimestamp = (Timestamp) row[0];
			Long period = periodTimestamp.getTime();
			BigDecimal totalPeriod = (BigDecimal) row[1];
			BigDecimal totalCumulative = (BigDecimal) row[2];
			
			PeriodTabTuplePojo periodTabPojo = new PeriodTabTuplePojo();
			periodTabPojo.setPeriod(period);
			periodTabPojo.setAmountPeriod(totalPeriod);
			periodTabPojo.setAmountCumulative(totalCumulative);
			
			periodTabPojos.add(periodTabPojo);
		}
		
		return periodTabPojos;
        
    }
	
	public List<AforeContributionEntity> findContributionsByPeriod(LocalDate date) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AforeContributionEntity> query = cb.createQuery(AforeContributionEntity.class);
		
		Root<AforeContributionEntity> root = query.from(AforeContributionEntity.class);
		
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.equal(root.get(AforeContributionEntity_.period).as(LocalDate.class), date));
		
		query.where(predicates.toArray(new Predicate[0]));
		
		return entityManager.createQuery(query).getResultList();
	}
	
	public BigDecimal findTotalContributionsBeforeOrSamePeriod(LocalDate date, Integer idSection, List<Integer> idConcepts) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> query = cb.createQuery(BigDecimal.class);
		
		Root<AforeContributionEntity> root = query.from(AforeContributionEntity.class);
		
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.lessThanOrEqualTo(root.get(AforeContributionEntity_.period).as(LocalDate.class), date));
		predicates.add(root.get(AforeContributionEntity_.idConceptUnified).in(idConcepts));
		
		if (idSection != null)
			predicates.add(root.get(AforeContributionEntity_.idSection).in(idSection));
		
		query.select(cb.sum(root.get(AforeContributionEntity_.amount))).where(predicates.toArray(new Predicate[0]));
		
		BigDecimal result = entityManager.createQuery(query).getSingleResult();
	    return result != null ? result : BigDecimal.ZERO;
	}
	
	public BigDecimal findTotalContributionsBeforePeriod(LocalDate date, Integer idSection, List<Integer> idConcepts) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> query = cb.createQuery(BigDecimal.class);
		
		Root<AforeContributionEntity> root = query.from(AforeContributionEntity.class);
		
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.lessThan(root.get(AforeContributionEntity_.period).as(LocalDate.class), date));
		predicates.add(root.get(AforeContributionEntity_.idConceptUnified).in(idConcepts));
		
		if (idSection != null)
			predicates.add(root.get(AforeContributionEntity_.idSection).in(idSection));
		
		query.select(cb.sum(root.get(AforeContributionEntity_.amount))).where(predicates.toArray(new Predicate[0]));
		
		BigDecimal result = entityManager.createQuery(query).getSingleResult();
	    return result != null ? result : BigDecimal.ZERO;
	}
	
	public BigDecimal findTotalContributionsByPeriod(LocalDate date, Integer idSection, List<Integer> idConcepts) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> query = cb.createQuery(BigDecimal.class);
		
		Root<AforeContributionEntity> root = query.from(AforeContributionEntity.class);
		
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.equal(root.get(AforeContributionEntity_.period).as(LocalDate.class), date));
		predicates.add(root.get(AforeContributionEntity_.idConceptUnified).in(idConcepts));
		
		if (idSection != null)
			predicates.add(root.get(AforeContributionEntity_.idSection).in(idSection));
		
		query.select(cb.sum(root.get(AforeContributionEntity_.amount))).where(predicates.toArray(new Predicate[0]));
		
		BigDecimal result = entityManager.createQuery(query).getSingleResult();
	    return result != null ? result : BigDecimal.ZERO;
	}
	
	public List<LocalDate> findPreviousPeriods(LocalDate currentDatePeriod) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<LocalDate> query = cb.createQuery(LocalDate.class);
		
		Root<AforeContributionEntity> root = query.from(AforeContributionEntity.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.lessThanOrEqualTo(root.get(AforeContributionEntity_.period).as(LocalDate.class), currentDatePeriod));
		
		List<Order> orders = new ArrayList<>();
		orders.add(cb.asc(root.get(AforeContributionEntity_.period)));
		
		query.orderBy(orders);
		query.where(predicates.toArray(new Predicate[0]));
		
		query.select(root.get(AforeContributionEntity_.period).as(LocalDate.class)).distinct(true);
		
		return entityManager.createQuery(query).getResultList();
	}
}
