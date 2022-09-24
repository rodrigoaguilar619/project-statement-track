package project.statement.track.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.statement.track.beans.entity.MovementsMoney;
import project.statement.track.beans.entity.MovementsMoney_;
import project.statement.track.vo.catalogs.CatalogTypeTransactionEnum;

@Repository
public class MovementsMoneyRepository {

	@Autowired
	EntityManager em;
	
	public List<MovementsMoney> getMovementsMoney(Integer idBrokerAccount, Integer idTypetransaction, Integer year, Integer month) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsMoney> cq = cb.createQuery(MovementsMoney.class);
		Root<MovementsMoney> root = cq.from(MovementsMoney.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(cb.function("YEAR", Integer.class, root.get(MovementsMoney_.dateTransaction)), year));
		predicatesAnd.add(cb.equal(cb.function("MONTH", Integer.class, root.get(MovementsMoney_.dateTransaction)), month));
		predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idBrokerAccount), idBrokerAccount));
		
		if (idTypetransaction != null)
			predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idTypeTransaction), idTypetransaction));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		return em.createQuery(cq).getResultList();
	}
	
	public BigDecimal getMovementsMoneyPreviousTotal(Integer idBrokerAccount, Integer idTypetransaction, Date dateEnd) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<MovementsMoney> root = cq.from(MovementsMoney.class);
		cq.select(cb.sum(root.<BigDecimal>get(MovementsMoney_.amount)));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(cb.lessThan(root.get(MovementsMoney_.dateTransaction), dateEnd));
		predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idTypeTransaction), idTypetransaction));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		BigDecimal result = typedQuery.getSingleResult();
		
		return result != null ? result : new BigDecimal(0);
	}
	
	public List<MovementsMoney> getMovementsMoneyDividend(Integer idBrokerAccount, String idIssue) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsMoney> cq = cb.createQuery(MovementsMoney.class);
		Root<MovementsMoney> root = cq.from(MovementsMoney.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idTypeTransaction), CatalogTypeTransactionEnum.DIVIDEND.getId()));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		return em.createQuery(cq).getResultList();
	}
}
