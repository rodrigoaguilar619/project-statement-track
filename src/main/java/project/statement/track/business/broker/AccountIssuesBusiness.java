package project.statement.track.business.broker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import project.statement.track.beans.entity.BrokerAccount;
import project.statement.track.beans.entity.MovementsMoney;
import project.statement.track.pojos.entity.MovementMoneyPojo;
import project.statement.track.pojos.entity.MovementMoneyResumePojo;
import project.statement.track.pojos.request.AccountStatementRequestPojo;
import project.statement.track.pojos.request.GetAccountDividendsRequestPojo;
import project.statement.track.pojos.response.AccountStatementResponsePojo;
import project.statement.track.pojos.response.GetAccountDividendsResponsePojo;
import project.statement.track.repository.MovementsMoneyRepository;
import project.statement.track.utils.BuildEntityToPojoUtil;

@Component
public class AccountIssuesBusiness {

	@SuppressWarnings("rawtypes")
	@Autowired
	GenericPersistence genericCustomPersistance;
	
	@Autowired
	BuildEntityToPojoUtil buildEntityToPojoUtil;
	
	@Autowired
	MovementsMoneyRepository movementsMoneyRepository;
	
	private List<MovementMoneyResumePojo> getAccountIssuesDividends(Integer idBrokerAccount, Integer idIssue) throws BusinessException {
		
		List<MovementsMoney> movementsMoneyDividend = movementsMoneyRepository.getMovementsMoneyDividend(idBrokerAccount, idIssue);
		List<MovementMoneyResumePojo> movementMoneyResumePojos = new ArrayList<>();
		
		for(MovementsMoney movementsMoney: movementsMoneyDividend) {
			
			MovementMoneyResumePojo movementMoneyResumePojo = buildEntityToPojoUtil.mapMovementMoneyResumePojo(null, movementsMoney);
			movementMoneyResumePojos.add(movementMoneyResumePojo);
		}
		
		return movementMoneyResumePojos;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public GetAccountDividendsResponsePojo executeGetAccountDividends(GetAccountDividendsRequestPojo requestPojo) throws BusinessException {
		
		List<MovementMoneyResumePojo> movementMoneyPojos = getAccountIssuesDividends(requestPojo.getIdBrokerAccount(), requestPojo.getIdIssue());
		
		GetAccountDividendsResponsePojo responsePojo = new GetAccountDividendsResponsePojo();
		responsePojo.setMovementMoneyDividends(movementMoneyPojos);
		
		return responsePojo;
	}
}
