package project.statement.track.business.issues;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import project.statement.track.pojos.request.GetIssuesBuyRequestPojo;
import project.statement.track.pojos.response.GetIssuesBuyResponsePojo;
import project.statement.track.pojos.tuple.IssueTotalsPojo;
import project.statement.track.repository.MovementsIssueRepository;
import project.statement.track.utils.BrokerSnowBallUtil;
import project.statement.track.utils.BuildPojoToEntityUtil;
import project.statement.track.vo.catalogs.CatalogTypeMovementEnum;

@Component
public class IssuesBusiness {

	@SuppressWarnings("rawtypes")
	@Autowired
	GenericPersistence genericCustomPersistance;
	
	@Autowired
	BrokerSnowBallUtil brokerSnowBallUtil;
	
	@Autowired
	BuildPojoToEntityUtil buildPojoToEntityUtil;
	
	@Autowired
	MovementsIssueRepository movementsIssueRepository;
	
	@Transactional
	public GetIssuesBuyResponsePojo executeGetIssuesBuy(GetIssuesBuyRequestPojo requestPojo) throws BusinessException {
		
		//TODO: add minus for sell issues
		List<Integer> idTypeMovementList = new ArrayList<>();
		idTypeMovementList.add(CatalogTypeMovementEnum.BUY.getId());
		idTypeMovementList.add(CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY.getId());
		
		List<IssueTotalsPojo> issueTotalsPojos = movementsIssueRepository.getIssuesTotals(requestPojo.getIdBrokerAccount(), idTypeMovementList);
		
		GetIssuesBuyResponsePojo responsePojo = new GetIssuesBuyResponsePojo();
		responsePojo.setIssuesBuy(issueTotalsPojos);
		
		return responsePojo;
	}
}
