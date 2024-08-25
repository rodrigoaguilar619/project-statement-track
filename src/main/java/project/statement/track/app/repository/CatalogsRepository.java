package project.statement.track.app.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import project.statement.track.app.beans.entity.CatalogIssueEntity;
import project.statement.track.app.beans.entity.CatalogIssueEntity_;

@Repository
public class CatalogsRepository {
	
	@Autowired
	EntityManager em;

	public CatalogIssueEntity getCatalogIssueSnowBall(String descriptionSnowball) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CatalogIssueEntity> cq = cb.createQuery(CatalogIssueEntity.class);
		Root<CatalogIssueEntity> root = cq.from(CatalogIssueEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(CatalogIssueEntity_.descriptionSnowball), descriptionSnowball));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<CatalogIssueEntity> resultList = em.createQuery(cq).setMaxResults(1).getResultList();
		return (resultList.isEmpty()) ? null : resultList.get(0);
	}
}
