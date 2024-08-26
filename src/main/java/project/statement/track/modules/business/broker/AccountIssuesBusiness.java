package project.statement.track.modules.business.broker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.entity.MovementsMoneyEntity;
import project.statement.track.app.beans.pojos.entity.MovementMoneyResumePojo;
import project.statement.track.app.beans.pojos.petition.data.GetAccountDividendsDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetAccountDividendsRequestPojo;
import project.statement.track.app.repository.MovementsMoneyRepository;
import project.statement.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class AccountIssuesBusiness extends MainBusiness {
	
	private final MovementsMoneyRepository movementsMoneyRepository;
	
	private List<MovementMoneyResumePojo> getAccountIssuesDividends(Integer idBrokerAccount, Integer idIssue) {
		
		List<MovementsMoneyEntity> movementsMoneyDividend = movementsMoneyRepository.getMovementsMoneyDividend(idBrokerAccount, idIssue);
		List<MovementMoneyResumePojo> movementMoneyResumePojos = new ArrayList<>();
		
		for(MovementsMoneyEntity movementsMoney: movementsMoneyDividend) {
			
			MovementMoneyResumePojo movementMoneyResumePojo = buildEntityToPojoUtil.mapMovementMoneyResumePojo(new MovementMoneyResumePojo(), movementsMoney);
			movementMoneyResumePojos.add(movementMoneyResumePojo);
		}
		
		return movementMoneyResumePojos;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public GetAccountDividendsDataPojo executeGetAccountDividends(GetAccountDividendsRequestPojo requestPojo) {
		
		List<MovementMoneyResumePojo> movementMoneyPojos = getAccountIssuesDividends(requestPojo.getIdBrokerAccount(), requestPojo.getIdIssue());
		
		GetAccountDividendsDataPojo responsePojo = new GetAccountDividendsDataPojo();
		responsePojo.setMovementMoneyDividends(movementMoneyPojos);
		
		return responsePojo;
	}
}
