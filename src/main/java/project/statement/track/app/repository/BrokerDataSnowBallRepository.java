package project.statement.track.app.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.statement.track.app.beans.entity.BrokerDataSnowball;
import project.statement.track.app.beans.entity.BrokerDataSnowball_;

@Repository
public class BrokerDataSnowBallRepository {

	@Autowired
	EntityManager em;
	
	public List<BrokerDataSnowball> getDataPending() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BrokerDataSnowball> cq = cb.createQuery(BrokerDataSnowball.class);
		Root<BrokerDataSnowball> root = cq.from(BrokerDataSnowball.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.isNull(root.get(BrokerDataSnowball_.trackTable)));
		predicatesAnd.add(cb.equal(root.get(BrokerDataSnowball_.statusMovement), true));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		return em.createQuery(cq).getResultList();
	}
}
