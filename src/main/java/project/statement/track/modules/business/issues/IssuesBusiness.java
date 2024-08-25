package project.statement.track.modules.business.issues;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.persistance.GenericPersistence;
import project.statement.track.app.beans.pojos.petition.data.GetIssuesBuyDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetIssuesBuyRequestPojo;
import project.statement.track.app.beans.pojos.tuple.IssueTotalsPojo;
import project.statement.track.app.repository.MovementsIssueRepository;
import project.statement.track.app.vo.catalogs.CatalogsEntity;
import project.statement.track.modules.business.MainBusiness;

@Component
public class IssuesBusiness extends MainBusiness {

	@SuppressWarnings("rawtypes")
	@Autowired
	GenericPersistence genericCustomPersistance;
	
	@Autowired
	MovementsIssueRepository movementsIssueRepository;
	
	@Transactional(rollbackFor = Exception.class)
	public GetIssuesBuyDataPojo executeGetIssuesBuy(GetIssuesBuyRequestPojo requestPojo) {
		
		//TODO: add minus for sell issues
		List<Integer> idTypeMovementList = new ArrayList<>();
		idTypeMovementList.add(CatalogsEntity.CatalogTypeMovement.BUY);
		idTypeMovementList.add(CatalogsEntity.CatalogTypeMovement.BUY_MARKET_SECUNDARY);
		
		List<IssueTotalsPojo> issueTotalsPojos = movementsIssueRepository.getIssuesTotals(requestPojo.getIdBrokerAccount(), idTypeMovementList);
		
		GetIssuesBuyDataPojo responsePojo = new GetIssuesBuyDataPojo();
		responsePojo.setIssuesBuy(issueTotalsPojos);
		
		return responsePojo;
	}
}
