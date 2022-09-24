package project.statement.track.business.broker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import lib.utils.backend.format.DateUtil;
import project.statement.track.beans.entity.BrokerAccount;
import project.statement.track.beans.entity.CatalogTypeMovement;
import project.statement.track.beans.entity.MovementsIssue;
import project.statement.track.beans.entity.MovementsMoney;
import project.statement.track.pojos.business.broker.OperationStatementDataPojo;
import project.statement.track.pojos.request.AccountStatementRequestPojo;
import project.statement.track.pojos.response.AccountStatementResponsePojo;
import project.statement.track.repository.MovementsIssueRepository;
import project.statement.track.repository.MovementsMoneyRepository;
import project.statement.track.utils.BuildEntityToPojoUtil;
import project.statement.track.vo.catalogs.CatalogTypeMovementEnum;
import project.statement.track.vo.catalogs.CatalogTypeTransactionEnum;
import project.statement.track.vo.catalogs.DefinitionTypeOperationEnum;

@Component
public class AccountStatementBusiness {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericPersistence genericCustomPersistance;
	
	@Autowired
	BuildEntityToPojoUtil buildEntityToPojoUtil;
	
	@Autowired
	MovementsMoneyRepository movementsMoneyRepository;
	
	@Autowired
	MovementsIssueRepository movementsIssueRepository;
	
	private BigDecimal getMovementMoneyPreviousTotal(BrokerAccount brokerAccount, CatalogTypeTransactionEnum catalogTypeTransaction, Integer year, Integer month) throws BusinessException {
		
		if(brokerAccount.getCutDay() == -1) {
			Date dateEnd = new GregorianCalendar(year, month - 1, 1).getTime();
			return movementsMoneyRepository.getMovementsMoneyPreviousTotal(brokerAccount.getId(), catalogTypeTransaction.getId(), dateEnd);
		}
		else
			throw new BusinessException("Function of cut day not implemented");
	}
	
	private BigDecimal getMovementIssuePreviousTotal(BrokerAccount brokerAccount, CatalogTypeMovementEnum atalogTypeMovement, Integer year, Integer month) throws BusinessException {
		
		if(brokerAccount.getCutDay() == -1) {
			Date dateEnd = new GregorianCalendar(year, month - 1, 1).getTime();
			return movementsIssueRepository.getMovementsIssuePreviousTotal(brokerAccount.getId(), atalogTypeMovement.getId(), dateEnd);
		}
		else
			throw new BusinessException("Function of cut day not implemented");
	}

	private List<MovementsMoney> getPeriodMoneyMovements(BrokerAccount brokerAccount, Integer year, Integer month) throws BusinessException {
		
		List<MovementsMoney> movementsMoneys;
		
		if(brokerAccount.getCutDay() == -1) {
			movementsMoneys = movementsMoneyRepository.getMovementsMoney(brokerAccount.getId(), null, year, month);
		}
		else
			throw new BusinessException("Function of cut day not implemented");
		
		return movementsMoneys;
	}
	
	private List<MovementsIssue> getPeriodIssueMovements(BrokerAccount brokerAccount, Integer year, Integer month) throws BusinessException {
		
		List<MovementsIssue> movementsIssues;
		
		if(brokerAccount.getCutDay() == -1) {
			movementsIssues = movementsIssueRepository.getMovementsIssue(brokerAccount.getId(), null, year, month);
		}
		else
			throw new BusinessException("Function of cut day not implemented");
		
		return movementsIssues;
	}
	
	private List<OperationStatementDataPojo> getPeriodMoneyMovementsPojos(BrokerAccount brokerAccount, Integer year, Integer month) throws BusinessException {
		
		List<MovementsMoney> movementsMoneys = getPeriodMoneyMovements(brokerAccount, year, month);
		List<OperationStatementDataPojo> operationStatementDataPojos = new ArrayList<>();
		
		for(MovementsMoney movementsMoney: movementsMoneys) {
			
			OperationStatementDataPojo operationstatementDataPojo = new OperationStatementDataPojo();
			
			operationstatementDataPojo.setDate(movementsMoney.getDateTransaction().getTime());
			operationstatementDataPojo.setDateFormated(movementsMoney.getDateTransaction().toString());
			operationstatementDataPojo.setTypeOperationId(movementsMoney.getIdTypeTransaction());
			operationstatementDataPojo.setTypeOperationDescription(movementsMoney.getCatalogTypeTransaction().getDescription());
			operationstatementDataPojo.setAmount(movementsMoney.getAmount());
			operationstatementDataPojo.setDefinitionTypeOperationEnum(DefinitionTypeOperationEnum.MOVEMENT);
			
			operationStatementDataPojos.add(operationstatementDataPojo);
		}
		
		return operationStatementDataPojos;
	}
	
	private List<OperationStatementDataPojo> getPeriodIssueMovementsPojos(BrokerAccount brokerAccount, Integer year, Integer month) throws BusinessException {
		
		List<MovementsIssue> movementsIssues = getPeriodIssueMovements(brokerAccount, year, month);
		List<OperationStatementDataPojo> operationStatementDataPojos = new ArrayList<>();
		
		for(MovementsIssue movementsIssue: movementsIssues) {
			
			if (movementsIssue.getCatalogTypeMovement().getId() == CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY_CANCELLED.getId()) {
				continue;
			}
			
			OperationStatementDataPojo operationstatementDataPojo = new OperationStatementDataPojo();
			
			operationstatementDataPojo.setDate(movementsIssue.getDateTransaction().getTime());
			operationstatementDataPojo.setDateFormated(movementsIssue.getDateTransaction().toString());
			operationstatementDataPojo.setTypeOperationId(movementsIssue.getIdTypeMovement());
			operationstatementDataPojo.setTypeOperationDescription(movementsIssue.getCatalogTypeMovement().getDescription());
			operationstatementDataPojo.setAmount(movementsIssue.getPriceTotal());
			operationstatementDataPojo.setDefinitionTypeOperationEnum(DefinitionTypeOperationEnum.ISSUE);
			
			operationStatementDataPojos.add(operationstatementDataPojo);
		}
		
		return operationStatementDataPojos;
	}
	
	private List<OperationStatementDataPojo> getPeriodOperations(BrokerAccount brokerAccount, Integer year, Integer month, BigDecimal previousBalance) throws BusinessException {
		
		List<OperationStatementDataPojo> operationStatementDataPojos = new ArrayList<>();
		operationStatementDataPojos.addAll(getPeriodMoneyMovementsPojos(brokerAccount, year, month));
		operationStatementDataPojos.addAll(getPeriodIssueMovementsPojos(brokerAccount, year, month));
		
		Collections.sort(operationStatementDataPojos);
		
		BigDecimal currentBalance = previousBalance;
		
		for (OperationStatementDataPojo operationStatementDataPojo: operationStatementDataPojos) {
			
			if (operationStatementDataPojo.getDefinitionTypeOperationEnum().equals(DefinitionTypeOperationEnum.MOVEMENT)) {
				
				if (operationStatementDataPojo.getTypeOperationId() == CatalogTypeTransactionEnum.DEPOSIT.getId()) {
					
					currentBalance = currentBalance.add(operationStatementDataPojo.getAmount());
					operationStatementDataPojo.setIncome(operationStatementDataPojo.getAmount());
					operationStatementDataPojo.setBalance(currentBalance);
				}
				else if (operationStatementDataPojo.getTypeOperationId() == CatalogTypeTransactionEnum.DIVIDEND.getId()) {
					
					currentBalance = currentBalance.add(operationStatementDataPojo.getAmount());
					operationStatementDataPojo.setIncome(operationStatementDataPojo.getAmount());
					operationStatementDataPojo.setBalance(currentBalance);
				}
				else if (operationStatementDataPojo.getTypeOperationId() == CatalogTypeTransactionEnum.WITHDRAW.getId()) {
					
					currentBalance = currentBalance.subtract(operationStatementDataPojo.getAmount());
					operationStatementDataPojo.setCharge(operationStatementDataPojo.getAmount().multiply(new BigDecimal(-1)));
					operationStatementDataPojo.setBalance(currentBalance);
				}
				else
					throw new BusinessException("For balance definition type operation not found. type operation: " + operationStatementDataPojo.getTypeOperationId() + " table: " + operationStatementDataPojo.getDefinitionTypeOperationEnum().getDescription());
			}
			else if (operationStatementDataPojo.getDefinitionTypeOperationEnum().equals(DefinitionTypeOperationEnum.ISSUE)) {
				
				if (operationStatementDataPojo.getTypeOperationId() == CatalogTypeMovementEnum.BUY.getId() | operationStatementDataPojo.getTypeOperationId() == CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY.getId()) {
					
					currentBalance = currentBalance.subtract(operationStatementDataPojo.getAmount());
					operationStatementDataPojo.setCharge(operationStatementDataPojo.getAmount().multiply(new BigDecimal(-1)));
					operationStatementDataPojo.setBalance(currentBalance);
				}
				else if (operationStatementDataPojo.getTypeOperationId() == CatalogTypeMovementEnum.SELL.getId()) {
					
					currentBalance = currentBalance.add(operationStatementDataPojo.getAmount());
					operationStatementDataPojo.setIncome(operationStatementDataPojo.getAmount());
					operationStatementDataPojo.setBalance(currentBalance);
				}
				else
					throw new BusinessException("For balance definition type operation not found. type operation: " + operationStatementDataPojo.getTypeOperationId() + " table: " + operationStatementDataPojo.getDefinitionTypeOperationEnum().getDescription());
			}
			else
				throw new BusinessException("For balance definition type operation not found. type operation: " + operationStatementDataPojo.getTypeOperationId() + " table: " + operationStatementDataPojo.getDefinitionTypeOperationEnum().getDescription());
		}
		
		return operationStatementDataPojos;
	}
	
	public BigDecimal getTotalPreviousPeriod(BrokerAccount brokerAccount, Integer year, Integer month) throws BusinessException {
		
		BigDecimal movementsMoneyDepositsTotal;
		BigDecimal movementsMoneyWithDrawsTotal;
		BigDecimal movementsMoneyDividendTotal;
		BigDecimal movementsIssueBuyTotal;
		BigDecimal movementsIssueBuyMarketSecundaryTotal;
		BigDecimal movementsIssueSellTotal;
		
		movementsMoneyDepositsTotal = getMovementMoneyPreviousTotal(brokerAccount, CatalogTypeTransactionEnum.DEPOSIT, year, month);
		movementsMoneyDividendTotal = getMovementMoneyPreviousTotal(brokerAccount, CatalogTypeTransactionEnum.DIVIDEND, year, month);
		movementsMoneyWithDrawsTotal = getMovementMoneyPreviousTotal(brokerAccount, CatalogTypeTransactionEnum.WITHDRAW, year, month);
		movementsIssueBuyTotal = getMovementIssuePreviousTotal(brokerAccount, CatalogTypeMovementEnum.BUY, year, month);
		movementsIssueBuyMarketSecundaryTotal = getMovementIssuePreviousTotal(brokerAccount, CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY, year, month);
		movementsIssueSellTotal = getMovementIssuePreviousTotal(brokerAccount, CatalogTypeMovementEnum.SELL, year, month);
		
		return movementsMoneyDepositsTotal.add(movementsIssueSellTotal).add(movementsMoneyDividendTotal).subtract(movementsMoneyWithDrawsTotal).subtract(movementsIssueBuyTotal).subtract(movementsIssueBuyMarketSecundaryTotal).setScale(2, RoundingMode.HALF_UP);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public AccountStatementResponsePojo executeGetAccountStatement(AccountStatementRequestPojo requestPojo) throws BusinessException {
		
		BrokerAccount brokerAccount = (BrokerAccount) genericCustomPersistance.findById(BrokerAccount.class, requestPojo.getIdAccountBroker());
		
		AccountStatementResponsePojo accountStatementResponsePojo = new AccountStatementResponsePojo();
		accountStatementResponsePojo.setYear(requestPojo.getYear());
		accountStatementResponsePojo.setMonth(requestPojo.getMonth());
		accountStatementResponsePojo.setPreviousBalance(getTotalPreviousPeriod(brokerAccount, requestPojo.getYear(), requestPojo.getMonth()));
		accountStatementResponsePojo.setCurrentBalance(getTotalPreviousPeriod(brokerAccount, requestPojo.getYear(), requestPojo.getMonth() + 1));
		accountStatementResponsePojo.setOperationsStatement(getPeriodOperations(brokerAccount, requestPojo.getYear(), requestPojo.getMonth(), accountStatementResponsePojo.getPreviousBalance()));
		accountStatementResponsePojo.setBrokerAccountResume(buildEntityToPojoUtil.mapBrokerAccountResumePojo(null, brokerAccount));
		
		return accountStatementResponsePojo;
	}
}
