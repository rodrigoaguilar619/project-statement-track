package project.statement.track.app.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import lib.base.backend.utils.date.DateUtil;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.entity.CatalogIssueEntity;
import project.statement.track.app.beans.entity.MovementsMoneyEntity;
import project.statement.track.app.beans.entity.MovementsMoneyEntity_;
import project.statement.track.app.beans.pojos.tuple.IssueDividendsPojo;
import project.statement.track.app.vo.catalogs.CatalogsEntity;

@RequiredArgsConstructor
@Repository
public class MovementsMoneyRepository {

	private final EntityManager em;
	private DateUtil dateUtil = new DateUtil();
	
	private BigDecimal getMovementsMoneyTotals(SingularAttribute<MovementsMoneyEntity, BigDecimal> column, Integer idBrokerAccount, Integer idTypetransaction, Map<String, String> filters) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<MovementsMoneyEntity> root = cq.from(MovementsMoneyEntity.class);
		cq.select(cb.sum(root.<BigDecimal>get(column)));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idTypeTransaction), idTypetransaction));
		
		buildMovementsMoneyFiltersDates(filters, root, cb, predicatesAnd);
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		BigDecimal result = typedQuery.getSingleResult();
		
		return result != null ? result : new BigDecimal(0);
	}
	
	private void buildMovementsMoneyFiltersDates(Map<String, String> filters, Root<MovementsMoneyEntity> root, CriteriaBuilder cb, List<Predicate> predicatesAnd) {
		
		if (filters != null) {
			
			if (filters.get("filterDateStart") != null) {
				predicatesAnd.add(cb.greaterThanOrEqualTo(root.get(MovementsMoneyEntity_.dateTransaction).as(LocalDateTime.class), dateUtil.getLocalDateTime(Long.parseLong(filters.get("filterDateStart")))));
			}
			if (filters.get("filterDateEnd") != null) {
				predicatesAnd.add(cb.lessThanOrEqualTo(root.get(MovementsMoneyEntity_.dateTransaction).as(LocalDateTime.class), dateUtil.getLocalDateTime(Long.parseLong(filters.get("filterDateEnd")))));
			}
		}
	}
	
	public List<MovementsMoneyEntity> getMovementsMoney(Integer idBrokerAccount, Integer idTypetransaction, Integer year, Integer month) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsMoneyEntity> cq = cb.createQuery(MovementsMoneyEntity.class);
		Root<MovementsMoneyEntity> root = cq.from(MovementsMoneyEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(cb.function("YEAR", Integer.class, root.get(MovementsMoneyEntity_.dateTransaction)), year));
		predicatesAnd.add(cb.equal(cb.function("MONTH", Integer.class, root.get(MovementsMoneyEntity_.dateTransaction)), month));
		predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idBrokerAccount), idBrokerAccount));
		
		if (idTypetransaction != null)
			predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idTypeTransaction), idTypetransaction));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		return em.createQuery(cq).getResultList();
	}
	
	public BigDecimal getMovementsMoneyPreviousTotal(Integer idBrokerAccount, Integer idTypetransaction, LocalDateTime dateEnd) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<MovementsMoneyEntity> root = cq.from(MovementsMoneyEntity.class);
		cq.select(cb.sum(root.<BigDecimal>get(MovementsMoneyEntity_.amount)));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idTypeTransaction), idTypetransaction));
		predicatesAnd.add(cb.lessThan(root.get(MovementsMoneyEntity_.dateTransaction), dateEnd));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		BigDecimal result = typedQuery.getSingleResult();
		
		return result != null ? result : new BigDecimal(0);
	}
	
	public BigDecimal getMovementsMoneyTotals(Integer idBrokerAccount, Integer idTypetransaction, Map<String, String> filters) {
		
		return getMovementsMoneyTotals(MovementsMoneyEntity_.amount, idBrokerAccount, idTypetransaction, filters);
	}
	
	public BigDecimal getMovementsMoneyTotalsMxn(Integer idBrokerAccount, Integer idTypetransaction, Map<String, String> filters) {
		
		return getMovementsMoneyTotals(MovementsMoneyEntity_.amountMxn, idBrokerAccount, idTypetransaction, filters);
	}
	
	public List<MovementsMoneyEntity> getMovementsMoneyDividend(Integer idBrokerAccount, Integer idIssue) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsMoneyEntity> cq = cb.createQuery(MovementsMoneyEntity.class);
		Root<MovementsMoneyEntity> root = cq.from(MovementsMoneyEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idTypeTransaction), CatalogsEntity.CatalogTypeTransaction.DIVIDEND));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		return em.createQuery(cq).getResultList();
	}
	
	public List<MovementsMoneyEntity> getMovementsMoney(Integer idBrokerAccount, List<Integer> idTypTransactionList, Map<String, String> filters) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MovementsMoneyEntity> cq = cb.createQuery(MovementsMoneyEntity.class);
		Root<MovementsMoneyEntity> root = cq.from(MovementsMoneyEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(root.get(MovementsMoneyEntity_.idTypeTransaction).in(idTypTransactionList));
		
		buildMovementsMoneyFiltersDates(filters, root, cb, predicatesAnd);
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		return em.createQuery(cq).getResultList();
	}
	
	public List<IssueDividendsPojo> getMovementsMoneyDividendTotals(Integer idBrokerAccount, Map<String, String> filters) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
		Root<MovementsMoneyEntity> root = cq.from(MovementsMoneyEntity.class);
		cq.multiselect(root.get(MovementsMoneyEntity_.catalogIssue), cb.sum(root.get(MovementsMoneyEntity_.amount)), cb.sum(root.get(MovementsMoneyEntity_.amountMxn)));
		cq.groupBy(root.get(MovementsMoneyEntity_.idIssue));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idBrokerAccount), idBrokerAccount));
		predicatesAnd.add(cb.equal(root.get(MovementsMoneyEntity_.idTypeTransaction), CatalogsEntity.CatalogTypeTransaction.DIVIDEND));
		
		buildMovementsMoneyFiltersDates(filters, root, cb, predicatesAnd);
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<Tuple> resultTuple = em.createQuery(cq).getResultList();
		
		List<IssueDividendsPojo> resultList = new ArrayList<>();
		
		for (Tuple tuple: resultTuple) {
			
			CatalogIssueEntity catalogIssue = (CatalogIssueEntity) tuple.get(0);
			
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
