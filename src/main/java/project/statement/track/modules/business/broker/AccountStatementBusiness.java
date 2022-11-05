package project.statement.track.modules.business.broker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import project.statement.track.app.beans.entity.BrokerAccount;
import project.statement.track.app.beans.entity.MovementsIssue;
import project.statement.track.app.beans.entity.MovementsMoney;
import project.statement.track.app.beans.pojos.business.broker.OperationStatementDataPojo;
import project.statement.track.app.beans.pojos.petition.data.AccountStatementDataPojo;
import project.statement.track.app.beans.pojos.petition.request.AccountStatementRequestPojo;
import project.statement.track.app.repository.MovementsIssueRepository;
import project.statement.track.app.repository.MovementsMoneyRepository;
import project.statement.track.app.utils.AccountUtil;
import project.statement.track.app.utils.BuildEntityToPojoUtil;
import project.statement.track.app.vo.catalogs.CatalogTypeMovementEnum;
import project.statement.track.app.vo.catalogs.CatalogTypeTransactionEnum;
import project.statement.track.app.vo.catalogs.DefinitionTypeOperationEnum;

@Component
public class AccountStatementBusiness {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericPersistence genericCustomPersistance;
	
	@Autowired
	BuildEntityToPojoUtil buildEntityToPojoUtil;
	
	@Autowired
	AccountUtil accountUtil;
	
	@Autowired
	MovementsMoneyRepository movementsMoneyRepository;
	
	@Autowired
	MovementsIssueRepository movementsIssueRepository;
	
	private String messageOperationNotFound(OperationStatementDataPojo operationStatementDataPojo) {
		
		return String.format("For balance definition type operation not found. type operation: %s table: %s", operationStatementDataPojo.getTypeOperationId(), operationStatementDataPojo.getDefinitionTypeOperationEnum().getDescription());
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
			
			String typeOperationDescription = movementsMoney.getCatalogTypeTransaction().getDescription();
			
			if (movementsMoney.getIdTypeTransaction() == CatalogTypeTransactionEnum.DIVIDEND.getId())
				typeOperationDescription += " / " + ((movementsMoney.getCatalogIssue() != null) ? movementsMoney.getCatalogIssue().getInitials() : "");
			
			operationstatementDataPojo.setDate(movementsMoney.getDateTransaction().getTime());
			operationstatementDataPojo.setDateFormated(movementsMoney.getDateTransaction().toString());
			operationstatementDataPojo.setTypeOperationId(movementsMoney.getIdTypeTransaction());
			operationstatementDataPojo.setTypeOperationDescription(typeOperationDescription);
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
			operationstatementDataPojo.setTypeOperationDescription(movementsIssue.getCatalogTypeMovement().getDescription() + " / " + movementsIssue.getCatalogIssue().getInitials());
			operationstatementDataPojo.setAmount(movementsIssue.getPriceTotal());
			operationstatementDataPojo.setDefinitionTypeOperationEnum(DefinitionTypeOperationEnum.ISSUE);
			
			operationStatementDataPojos.add(operationstatementDataPojo);
		}
		
		return operationStatementDataPojos;
	}
	
	private OperationStatementDataPojo getPeriodOperationMovement(OperationStatementDataPojo operationStatementDataPojo, BigDecimal currentBalance) throws BusinessException {
		
		if (operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeTransactionEnum.DEPOSIT.getId()) ||
				operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeTransactionEnum.DIVIDEND.getId()) ) {
				
				currentBalance = currentBalance.add(operationStatementDataPojo.getAmount());
				operationStatementDataPojo.setIncome(operationStatementDataPojo.getAmount());
				operationStatementDataPojo.setBalance(currentBalance);
			}
			else if (operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeTransactionEnum.WITHDRAW.getId())) {
				
				currentBalance = currentBalance.subtract(operationStatementDataPojo.getAmount());
				operationStatementDataPojo.setCharge(operationStatementDataPojo.getAmount().multiply(new BigDecimal(-1)));
				operationStatementDataPojo.setBalance(currentBalance);
			}
			else
				throw new BusinessException(messageOperationNotFound(operationStatementDataPojo));
		
		return operationStatementDataPojo;
	}
	
	private OperationStatementDataPojo getPeriodOperationIssue(OperationStatementDataPojo operationStatementDataPojo, BigDecimal currentBalance) throws BusinessException {
		
		if (operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeMovementEnum.BUY.getId()) ||
			operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY.getId())) {
			
			currentBalance = currentBalance.subtract(operationStatementDataPojo.getAmount());
			operationStatementDataPojo.setCharge(operationStatementDataPojo.getAmount().multiply(new BigDecimal(-1)));
			operationStatementDataPojo.setBalance(currentBalance);
		}
		else if (operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeMovementEnum.SELL.getId())) {
			
			currentBalance = currentBalance.add(operationStatementDataPojo.getAmount());
			operationStatementDataPojo.setIncome(operationStatementDataPojo.getAmount());
			operationStatementDataPojo.setBalance(currentBalance);
		}
		else
			throw new BusinessException(messageOperationNotFound(operationStatementDataPojo));
		
		return operationStatementDataPojo;
	}
	
	private List<OperationStatementDataPojo> getPeriodOperations(BrokerAccount brokerAccount, Integer year, Integer month, BigDecimal previousBalance) throws BusinessException {
		
		List<OperationStatementDataPojo> operationStatementDataPojos = new ArrayList<>();
		operationStatementDataPojos.addAll(getPeriodMoneyMovementsPojos(brokerAccount, year, month));
		operationStatementDataPojos.addAll(getPeriodIssueMovementsPojos(brokerAccount, year, month));
		
		Collections.sort(operationStatementDataPojos);
		
		BigDecimal currentBalance = previousBalance;
		
		for (OperationStatementDataPojo operationStatementDataPojo: operationStatementDataPojos) {
			
			if (operationStatementDataPojo.getDefinitionTypeOperationEnum().equals(DefinitionTypeOperationEnum.MOVEMENT)) {
				
				getPeriodOperationMovement(operationStatementDataPojo, currentBalance);
				currentBalance = operationStatementDataPojo.getBalance();
			}
			else if (operationStatementDataPojo.getDefinitionTypeOperationEnum().equals(DefinitionTypeOperationEnum.ISSUE)) {
				
				getPeriodOperationIssue(operationStatementDataPojo, currentBalance);
				currentBalance = operationStatementDataPojo.getBalance();
			}
			else
				throw new BusinessException(messageOperationNotFound(operationStatementDataPojo));
		}
		
		return operationStatementDataPojos;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public AccountStatementDataPojo executeGetAccountStatement(AccountStatementRequestPojo requestPojo) throws BusinessException {
		
		BrokerAccount brokerAccount = (BrokerAccount) genericCustomPersistance.findById(BrokerAccount.class, requestPojo.getIdAccountBroker());
		
		AccountStatementDataPojo accountStatementResponsePojo = new AccountStatementDataPojo();
		accountStatementResponsePojo.setYear(requestPojo.getYear());
		accountStatementResponsePojo.setMonth(requestPojo.getMonth());
		accountStatementResponsePojo.setPreviousBalance(accountUtil.getTotalPreviousPeriod(brokerAccount, requestPojo.getYear(), requestPojo.getMonth()));
		accountStatementResponsePojo.setCurrentBalance(accountUtil.getTotalPreviousPeriod(brokerAccount, requestPojo.getYear(), requestPojo.getMonth() + 1));
		accountStatementResponsePojo.setOperationsStatement(getPeriodOperations(brokerAccount, requestPojo.getYear(), requestPojo.getMonth(), accountStatementResponsePojo.getPreviousBalance()));
		accountStatementResponsePojo.setBrokerAccountResume(buildEntityToPojoUtil.mapBrokerAccountResumePojo(null, brokerAccount));
		
		return accountStatementResponsePojo;
	}
}
