package project.statement.track.modules.business.broker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import project.statement.track.app.beans.entity.BrokerAccount;
import project.statement.track.app.beans.entity.MovementsMoney;
import project.statement.track.app.beans.pojos.entity.MovementMoneyResumePojo;
import project.statement.track.app.beans.pojos.petition.data.GetAccountResumeDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetAccountResumeRequestPojo;
import project.statement.track.app.beans.pojos.tuple.IssueDividendsPojo;
import project.statement.track.app.repository.MovementsMoneyRepository;
import project.statement.track.app.utils.AccountUtil;
import project.statement.track.app.utils.BuildEntityToPojoUtil;
import project.statement.track.app.vo.catalogs.CatalogTypeTransactionEnum;

@Component
public class AccountResumeBusiness {

	@SuppressWarnings("rawtypes")
	@Autowired
	GenericPersistence genericCustomPersistance;
	
	@Autowired
	BuildEntityToPojoUtil buildEntityToPojoUtil;
	
	@Autowired
	AccountUtil accountUtil;
	
	@Autowired
	MovementsMoneyRepository movementsMoneyRepository;
	
	private List<MovementMoneyResumePojo> getMovementsMoneyResume(Integer idBrokerAccount, Map<String, String> filters) {
		
		List<Integer> idsTypeTransactionList = Arrays.asList(CatalogTypeTransactionEnum.DEPOSIT.getId(), CatalogTypeTransactionEnum.WITHDRAW.getId());
		List<MovementMoneyResumePojo> movementMoneyResumePojos = new ArrayList<>();
		
		List<MovementsMoney> movementsMoneys = movementsMoneyRepository.getMovementsMoney(idBrokerAccount, idsTypeTransactionList, filters);
		
		for(MovementsMoney movementMoney: movementsMoneys) {
			
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
		
		BrokerAccount brokerAccount = (BrokerAccount) genericCustomPersistance.findById(BrokerAccount.class, requestPojo.getIdBrokerAccount());
		
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(requestPojo.getFilters() != null && requestPojo.getFilters().get("filterDateEnd") != null ? new Date(new Long(requestPojo.getFilters().get("filterDateEnd"))) : new Date());
		
		Integer currentYear = currentDate.get(Calendar.YEAR);
		Integer currentMonth = currentDate.get(Calendar.MONTH) + 1;
		
		BigDecimal totalDeposits = movementsMoneyRepository.getMovementsMoneyTotals(requestPojo.getIdBrokerAccount(), CatalogTypeTransactionEnum.DEPOSIT.getId(), requestPojo.getFilters());
		BigDecimal totalWithdraws = movementsMoneyRepository.getMovementsMoneyTotals(requestPojo.getIdBrokerAccount(), CatalogTypeTransactionEnum.WITHDRAW.getId(), requestPojo.getFilters());
		BigDecimal totalDividends = movementsMoneyRepository.getMovementsMoneyTotals(requestPojo.getIdBrokerAccount(), CatalogTypeTransactionEnum.DIVIDEND.getId(), requestPojo.getFilters());
		BigDecimal currentBalance = accountUtil.getTotalPreviousPeriod(brokerAccount, currentYear, currentMonth);
		
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
