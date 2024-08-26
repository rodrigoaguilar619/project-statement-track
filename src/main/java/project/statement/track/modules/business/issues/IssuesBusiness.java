package project.statement.track.modules.business.issues;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.pojos.petition.data.GetIssuesBuyDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetIssuesBuyRequestPojo;
import project.statement.track.app.beans.pojos.tuple.IssueTotalsPojo;
import project.statement.track.app.repository.MovementsIssueRepository;
import project.statement.track.app.vo.catalogs.CatalogsEntity;
import project.statement.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class IssuesBusiness extends MainBusiness {

	private final MovementsIssueRepository movementsIssueRepository;
	
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
