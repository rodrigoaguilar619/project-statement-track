package project.statement.track.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.statement.track.beans.entity.CatalogIssue;
import project.statement.track.beans.entity.MovementsIssue;
import project.statement.track.beans.entity.MovementsIssue_;
import project.statement.track.beans.entity.MovementsMoney;
import project.statement.track.beans.entity.MovementsMoney_;
import project.statement.track.pojos.tuple.IssueTotalsPojo;

@Repository
public class MovementsIssueRepository {

	@Autowired
	EntityManager em;
	
	public List<MovementsIssue> getMovementsIssue(Integer idBrokerAccount, Integer idTypeMovement, Integer year, Integer month) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsIssue> cq = cb.createQuery(MovementsIssue.class);
		Root<MovementsIssue> root = cq.from(MovementsIssue.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(cb.function("YEAR", Integer.class, root.get(MovementsIssue_.dateTransaction)), year));
		predicatesAnd.add(cb.equal(cb.function("MONTH", Integer.class, root.get(MovementsIssue_.dateTransaction)), month));
		predicatesAnd.add(cb.equal(root.get(MovementsIssue_.idBrokerAccount), idBrokerAccount));
		
		if (idTypeMovement != null)
			predicatesAnd.add(cb.equal(root.get(MovementsIssue_.idTypeMovement), idTypeMovement));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		return em.createQuery(cq).getResultList();
	}
	
	public BigDecimal getMovementsIssuePreviousTotal(Integer idBrokerAccount, Integer idTypeMovement, Date dateEnd) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<MovementsIssue> root = cq.from(MovementsIssue.class);
		cq.select(cb.sum(root.<BigDecimal>get(MovementsIssue_.priceTotal)));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsIssue_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(cb.lessThan(root.get(MovementsIssue_.dateTransaction), dateEnd));
		predicatesAnd.add(cb.equal(root.get(MovementsIssue_.idTypeMovement), idTypeMovement));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		BigDecimal result = typedQuery.getSingleResult();
		
		return result != null ? result : new BigDecimal(0);
	}
	
	public MovementsIssue getMovementsIssueByPriceTotal(BigDecimal priceTotal, String idIssue, Integer idTypeMovement) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsIssue> cq = cb.createQuery(MovementsIssue.class);
		Root<MovementsIssue> root = cq.from(MovementsIssue.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsIssue_.priceTotal), priceTotal));
		predicatesAnd.add(cb.equal(root.get(MovementsIssue_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(MovementsIssue_.idTypeMovement), idTypeMovement));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<MovementsIssue> resultList =  em.createQuery(cq).getResultList();
		
		return resultList.size() > 0 ? resultList.get(0) : null;
	}
	
	public MovementsIssue getMovementsIssueByQuantityIssues(Integer quantityIssues, String idIssue, Integer idTypeMovement) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsIssue> cq = cb.createQuery(MovementsIssue.class);
		Root<MovementsIssue> root = cq.from(MovementsIssue.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsIssue_.quantityIssues), quantityIssues));
		predicatesAnd.add(cb.equal(root.get(MovementsIssue_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(MovementsIssue_.idTypeMovement), idTypeMovement));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<MovementsIssue> resultList =  em.createQuery(cq).getResultList();
		
		return resultList.size() > 0 ? resultList.get(0) : null;
	}
	
	public List<IssueTotalsPojo> getIssuesTotals(Integer idBrokerAccount, List<Integer> idTypeMovementList) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<MovementsIssue> root = cq.from(MovementsIssue.class);
		cq.multiselect(root.get(MovementsIssue_.catalogIssue), cb.sum(root.get(MovementsIssue_.priceTotal)), cb.sum(root.get(MovementsIssue_.quantityIssues)));
		cq.groupBy(root.get(MovementsIssue_.idIssue));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsIssue_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(root.get(MovementsIssue_.idTypeMovement).in(idTypeMovementList));

		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<Tuple> resultTuple = em.createQuery(cq).getResultList();
		
		List<IssueTotalsPojo> resultList = new ArrayList<>();
		
		for (Tuple tuple: resultTuple) {
			
			CatalogIssue catalogIssue = (CatalogIssue) tuple.get(0);
			
			IssueTotalsPojo issueTotalsPojo = new IssueTotalsPojo();
			issueTotalsPojo.setIssue(catalogIssue.getDescription());
			issueTotalsPojo.setIssueAbreviation(catalogIssue.getId());
			issueTotalsPojo.setPriceTotals((BigDecimal) tuple.get(1));
			issueTotalsPojo.setQuantityIssues(new BigDecimal(tuple.get(2).toString()));
			
			resultList.add(issueTotalsPojo);
		}
		
		return resultList;
	}
	
	//TODO: verify if remove
	/*public List<IssueTotalsPojo> getIssuesBuyTotals(Integer idBrokerAccount, List<Integer> idEstatusList) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<MovementsIssue> root = cq.from(MovementsIssue.class);
		cq.multiselect(root.get(MovementsIssue_.catalogIssue), cb.sum(root.get(MovementsIssue_.priceTotal)), cb.sum(root.get(MovementsIssue_.quantityIssues)));
		cq.groupBy(root.get(MovementsIssue_.idIssue));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsIssue_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(root.get(MovementsIssue_.idBrokerAccount).in(idEstatusList));

		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<Tuple> resultTuple = em.createQuery(cq).getResultList();
		
		List<IssueTotalsPojo> resultList = new ArrayList<>();
		
		for (Tuple tuple: resultTuple) {
			
			CatalogIssue catalogIssue = (CatalogIssue) tuple.get(0);
			
			IssueTotalsPojo issueTotalsPojo = new IssueTotalsPojo();
			issueTotalsPojo.setIssue(catalogIssue.getDescription());
			issueTotalsPojo.setIssueAbreviation(catalogIssue.getId());
			issueTotalsPojo.setPriceTotals((BigDecimal) tuple.get(1));
			issueTotalsPojo.setQuantityIssues(new BigDecimal(tuple.get(2).toString()));
			
			resultList.add(issueTotalsPojo);
		}
		
		return resultList;
	}*/
}
