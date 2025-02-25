package project.statement.track.modules.business.broker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.entity.BrokerAccountEntity;
import project.statement.track.app.beans.entity.MovementsMoneyEntity;
import project.statement.track.app.beans.pojos.entity.MovementMoneyResumePojo;
import project.statement.track.app.beans.pojos.petition.data.GetAccountResumeDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetAccountResumeRequestPojo;
import project.statement.track.app.beans.pojos.tuple.IssueDividendsPojo;
import project.statement.track.app.repository.MovementsMoneyRepository;
import project.statement.track.app.vo.entities.CatalogTypeTransactionEnum;
import project.statement.track.config.helper.AccountHelper;
import project.statement.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class AccountResumeBusiness extends MainBusiness {

	@SuppressWarnings("rawtypes")
	private final GenericPersistence genericPersistance;
	private final AccountHelper accountHelper;
	private final MovementsMoneyRepository movementsMoneyRepository;
	
	private List<MovementMoneyResumePojo> getMovementsMoneyResume(Integer idBrokerAccount, Map<String, String> filters) {
		
		List<Integer> idsTypeTransactionList = Arrays.asList(CatalogTypeTransactionEnum.DEPOSIT.getValue(), CatalogTypeTransactionEnum.WITHDRAW.getValue());
		List<MovementMoneyResumePojo> movementMoneyResumePojos = new ArrayList<>();
		
		List<MovementsMoneyEntity> movementsMoneys = movementsMoneyRepository.getMovementsMoney(idBrokerAccount, idsTypeTransactionList, filters);
		
		for(MovementsMoneyEntity movementMoney: movementsMoneys) {
			
			MovementMoneyResumePojo movementMoneyResumePojo = (MovementMoneyResumePojo) buildEntityToPojoUtil.mapMovementMoneyPojo(new MovementMoneyResumePojo(), movementMoney);
			movementMoneyResumePojo.setTypeTransactionDescription(movementMoney.getCatalogTypeTransaction().getDescription());
			movementMoneyResumePojo.setBrokerAccountDescription(movementMoney.getBrokerAccount().getDescription());
			
			movementMoneyResumePojos.add(movementMoneyResumePojo);
		}
		
		return movementMoneyResumePojos;
	}
	
	private List<IssueDividendsPojo> getMovementsMoneyDividendsResume(Integer idBrokerAccount, Map<String, String> filters) {
		
		return movementsMoneyRepository.getMovementsMoneyDividendTotals(idBrokerAccount, filters);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public GetAccountResumeDataPojo executeGetAccountResume(GetAccountResumeRequestPojo requestPojo) throws BusinessException {
		
		BrokerAccountEntity brokerAccount = (BrokerAccountEntity) genericPersistance.findById(BrokerAccountEntity.class, requestPojo.getIdBrokerAccount());
		
		LocalDateTime currentDate = requestPojo.getFilters() != null && requestPojo.getFilters().get("filterDateEnd") != null ? dateUtil.getLocalDateTime(Long.parseLong(requestPojo.getFilters().get("filterDateEnd"))) : LocalDateTime.now();
		
		Integer currentYear = currentDate.getYear();
		Integer currentMonth = currentDate.getMonthValue();
		
		BigDecimal totalDeposits = movementsMoneyRepository.getMovementsMoneyTotals(requestPojo.getIdBrokerAccount(), CatalogTypeTransactionEnum.DEPOSIT.getValue(), requestPojo.getFilters());
		BigDecimal totalWithdraws = movementsMoneyRepository.getMovementsMoneyTotals(requestPojo.getIdBrokerAccount(), CatalogTypeTransactionEnum.WITHDRAW.getValue(), requestPojo.getFilters());
		BigDecimal totalDividends = movementsMoneyRepository.getMovementsMoneyTotals(requestPojo.getIdBrokerAccount(), CatalogTypeTransactionEnum.DIVIDEND.getValue(), requestPojo.getFilters());
		BigDecimal currentBalance = accountHelper.getTotalPreviousPeriod(brokerAccount, currentYear, currentMonth);
		
		GetAccountResumeDataPojo responsePojo = new GetAccountResumeDataPojo();
		responsePojo.setTotalDeposits(totalDeposits);
		responsePojo.setTotalWithdraws(totalWithdraws);
		responsePojo.setCurrentBalance(currentBalance);
		responsePojo.setTotalDividends(totalDividends);
		responsePojo.setMovementsMoney(getMovementsMoneyResume(requestPojo.getIdBrokerAccount(), requestPojo.getFilters()));
		responsePojo.setMovementsMoneyDividend(getMovementsMoneyDividendsResume(requestPojo.getIdBrokerAccount(), requestPojo.getFilters()));
		
		return responsePojo;
	}
}
