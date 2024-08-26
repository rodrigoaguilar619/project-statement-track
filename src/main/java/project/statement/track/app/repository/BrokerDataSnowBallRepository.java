package project.statement.track.app.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.entity.BrokerDataSnowballEntity;
import project.statement.track.app.beans.entity.BrokerDataSnowballEntity_;

@RequiredArgsConstructor
@Repository
public class BrokerDataSnowBallRepository {

	private final EntityManager em;
	
	public List<BrokerDataSnowballEntity> getDataPending() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BrokerDataSnowballEntity> cq = cb.createQuery(BrokerDataSnowballEntity.class);
		Root<BrokerDataSnowballEntity> root = cq.from(BrokerDataSnowballEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.isNull(root.get(BrokerDataSnowballEntity_.trackTable)));
		predicatesAnd.add(cb.equal(root.get(BrokerDataSnowballEntity_.statusMovement), true));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		return em.createQuery(cq).getResultList();
	}
}
