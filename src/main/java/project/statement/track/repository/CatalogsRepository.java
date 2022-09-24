package project.statement.track.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.statement.track.beans.entity.BrokerDataSnowball;
import project.statement.track.beans.entity.BrokerDataSnowball_;
import project.statement.track.beans.entity.CatalogIssue;
import project.statement.track.beans.entity.CatalogIssue_;

@Repository
public class CatalogsRepository {
	
	@Autowired
	EntityManager em;

	public CatalogIssue getCatalogIssueSnowBall(String descriptionSnowball) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CatalogIssue> cq = cb.createQuery(CatalogIssue.class);
		Root<CatalogIssue> root = cq.from(CatalogIssue.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(CatalogIssue_.descriptionSnowball), descriptionSnowball));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<CatalogIssue> resultList = em.createQuery(cq).setMaxResults(1).getResultList();
		return (resultList.isEmpty()) ? null : resultList.get(0);
	}
}
