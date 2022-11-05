package project.statement.track.app.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.statement.track.app.beans.entity.CatalogIssue;
import project.statement.track.app.beans.entity.MovementsMoney;
import project.statement.track.app.beans.entity.MovementsMoney_;
import project.statement.track.app.beans.pojos.tuple.IssueDividendsPojo;
import project.statement.track.app.vo.catalogs.CatalogTypeTransactionEnum;

@Repository
public class MovementsMoneyRepository {

	@Autowired
	EntityManager em;
	
	private BigDecimal getMovementsMoneyTotals(SingularAttribute<MovementsMoney, BigDecimal> column, Integer idBrokerAccount, Integer idTypetransaction) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<MovementsMoney> root = cq.from(MovementsMoney.class);
		cq.select(cb.sum(root.<BigDecimal>get(column)));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idTypeTransaction), idTypetransaction));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		BigDecimal result = typedQuery.getSingleResult();
		
		return result != null ? result : new BigDecimal(0);
	}
	
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
	
	public BigDecimal getMovementsMoneyTotals(Integer idBrokerAccount, Integer idTypetransaction) {
		
		return getMovementsMoneyTotals(MovementsMoney_.amount, idBrokerAccount, idTypetransaction);
	}
	
	public BigDecimal getMovementsMoneyTotalsMxn(Integer idBrokerAccount, Integer idTypetransaction) {
		
		return getMovementsMoneyTotals(MovementsMoney_.amountMxn, idBrokerAccount, idTypetransaction);
	}
	
	public List<MovementsMoney> getMovementsMoneyDividend(Integer idBrokerAccount, Integer idIssue) {
		
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
	
	public List<MovementsMoney> getMovementsMoney(Integer idBrokerAccount, List<Integer> idTypTransactionList) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsMoney> cq = cb.createQuery(MovementsMoney.class);
		Root<MovementsMoney> root = cq.from(MovementsMoney.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(root.get(MovementsMoney_.idTypeTransaction).in(idTypTransactionList));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		return em.createQuery(cq).getResultList();
	}
	
	public List<IssueDividendsPojo> getMovementsMoneyDividendTotals(Integer idBrokerAccount) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
		Root<MovementsMoney> root = cq.from(MovementsMoney.class);
		cq.multiselect(root.get(MovementsMoney_.catalogIssue), cb.sum(root.get(MovementsMoney_.amount)), cb.sum(root.get(MovementsMoney_.amountMxn)));
		cq.groupBy(root.get(MovementsMoney_.idIssue));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(cb.equal(root.get(MovementsMoney_.idTypeTransaction), CatalogTypeTransactionEnum.DIVIDEND.getId()));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<Tuple> resultTuple = em.createQuery(cq).getResultList();
		
		List<IssueDividendsPojo> resultList = new ArrayList<>();
		
		for (Tuple tuple: resultTuple) {
			
			CatalogIssue catalogIssue = (CatalogIssue) tuple.get(0);
			
			IssueDividendsPojo issueDividendsPojo = new IssueDividendsPojo();
			issueDividendsPojo.setIdIssue(catalogIssue.getId());
			issueDividendsPojo.setIssueInitials(catalogIssue.getInitials());
			issueDividendsPojo.setTotalDividends((BigDecimal) tuple.get(1));
			issueDividendsPojo.setTotalDividendsMxn(new BigDecimal(tuple.get(2).toString()));
			issueDividendsPojo.setIssueDescription(catalogIssue.getDescription());
			
			resultList.add(issueDividendsPojo);
		}
		
		return resultList;
	}
}
