package project.statement.track.app.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import project.statement.track.app.beans.entity.CatalogIssueEntity;
import project.statement.track.app.beans.entity.MovementsIssueEntity;
import project.statement.track.app.beans.entity.MovementsIssueEntity_;
import project.statement.track.app.beans.pojos.tuple.IssueTotalsPojo;

@Repository
public class MovementsIssueRepository {

	@Autowired
	EntityManager em;
	
	public List<MovementsIssueEntity> getMovementsIssue(Integer idBrokerAccount, Integer idTypeMovement, Integer year, Integer month) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsIssueEntity> cq = cb.createQuery(MovementsIssueEntity.class);
		Root<MovementsIssueEntity> root = cq.from(MovementsIssueEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(cb.function("YEAR", Integer.class, root.get(MovementsIssueEntity_.dateTransaction)), year));
		predicatesAnd.add(cb.equal(cb.function("MONTH", Integer.class, root.get(MovementsIssueEntity_.dateTransaction)), month));
		predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.idBrokerAccount), idBrokerAccount));
		
		if (idTypeMovement != null)
			predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.idTypeMovement), idTypeMovement));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		return em.createQuery(cq).getResultList();
	}
	
	public BigDecimal getMovementsIssuePreviousTotal(Integer idBrokerAccount, Integer idTypeMovement, Date dateEnd) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<MovementsIssueEntity> root = cq.from(MovementsIssueEntity.class);
		cq.select(cb.sum(root.<BigDecimal>get(MovementsIssueEntity_.priceTotal)));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(cb.lessThan(root.get(MovementsIssueEntity_.dateTransaction), dateEnd));
		predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.idTypeMovement), idTypeMovement));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		BigDecimal result = typedQuery.getSingleResult();
		
		return result != null ? result : new BigDecimal(0);
	}
	
	public MovementsIssueEntity getMovementsIssueByPriceTotal(BigDecimal priceTotal, Integer idIssue, Integer idTypeMovement) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsIssueEntity> cq = cb.createQuery(MovementsIssueEntity.class);
		Root<MovementsIssueEntity> root = cq.from(MovementsIssueEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.priceTotal), priceTotal));
		predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.idTypeMovement), idTypeMovement));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<MovementsIssueEntity> resultList =  em.createQuery(cq).getResultList();
		
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}
	
	public MovementsIssueEntity getMovementsIssueByQuantityIssues(Integer quantityIssues, Integer idIssue, Integer idTypeMovement) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsIssueEntity> cq = cb.createQuery(MovementsIssueEntity.class);
		Root<MovementsIssueEntity> root = cq.from(MovementsIssueEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.quantityIssues), quantityIssues));
		predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.idTypeMovement), idTypeMovement));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<MovementsIssueEntity> resultList =  em.createQuery(cq).getResultList();
		
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}
	
	public List<IssueTotalsPojo> getIssuesTotals(Integer idBrokerAccount, List<Integer> idTypeMovementList) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<MovementsIssueEntity> root = cq.from(MovementsIssueEntity.class);
		cq.multiselect(root.get(MovementsIssueEntity_.catalogIssue), cb.sum(root.get(MovementsIssueEntity_.priceTotal)), cb.sum(root.get(MovementsIssueEntity_.quantityIssues)));
		cq.groupBy(root.get(MovementsIssueEntity_.idIssue));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(root.get(MovementsIssueEntity_.idTypeMovement).in(idTypeMovementList));

		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<Tuple> resultTuple = em.createQuery(cq).getResultList();
		
		List<IssueTotalsPojo> resultList = new ArrayList<>();
		
		for (Tuple tuple: resultTuple) {
			
			CatalogIssueEntity catalogIssue = (CatalogIssueEntity) tuple.get(0);
			
			IssueTotalsPojo issueTotalsPojo = new IssueTotalsPojo();
			issueTotalsPojo.setIdIssue(catalogIssue.getId());
			issueTotalsPojo.setIssueAbreviation(catalogIssue.getInitials());
			issueTotalsPojo.setPriceTotals((BigDecimal) tuple.get(1));
			issueTotalsPojo.setQuantityIssues(new BigDecimal(tuple.get(2).toString()));
			issueTotalsPojo.setIssueDescription(catalogIssue.getDescription());
			
			resultList.add(issueTotalsPojo);
		}
		
		return resultList;
	}
	
	public boolean verifyIssueRegistered(Integer idIssue) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<MovementsIssueEntity> root = cq.from(MovementsIssueEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsIssueEntity_.idIssue), idIssue));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		cq.select(cb.count(root.get(MovementsIssueEntity_.id)));

		return em.createQuery(cq).getResultList().get(0) > 0;
	}
	
}
