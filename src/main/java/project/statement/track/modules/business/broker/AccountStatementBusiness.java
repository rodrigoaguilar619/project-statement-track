package project.statement.track.modules.business.broker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.entity.BrokerAccountEntity;
import project.statement.track.app.beans.entity.MovementsIssueEntity;
import project.statement.track.app.beans.entity.MovementsMoneyEntity;
import project.statement.track.app.beans.pojos.business.broker.OperationStatementDataPojo;
import project.statement.track.app.beans.pojos.petition.data.AccountStatementDataPojo;
import project.statement.track.app.beans.pojos.petition.request.AccountStatementRequestPojo;
import project.statement.track.app.repository.MovementsIssueRepository;
import project.statement.track.app.repository.MovementsMoneyRepository;
import project.statement.track.app.vo.catalogs.CatalogsErrorMessage;
import project.statement.track.app.vo.entities.CatalogTypeMovementEnum;
import project.statement.track.app.vo.entities.CatalogTypeTransactionEnum;
import project.statement.track.app.vo.enums.DefinitionTypeOperationEnum;
import project.statement.track.config.helper.AccountHelper;
import project.statement.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class AccountStatementBusiness extends MainBusiness {
	
	@SuppressWarnings("rawtypes")
	private final GenericPersistence genericPersistance;
	private final AccountHelper accountHelper;
	private final MovementsMoneyRepository movementsMoneyRepository;
	private final MovementsIssueRepository movementsIssueRepository;

	private List<MovementsMoneyEntity> getPeriodMoneyMovements(BrokerAccountEntity brokerAccount, Integer year, Integer month) throws BusinessException {
		
		List<MovementsMoneyEntity> movementsMoneys;
		
		if(brokerAccount.getCutDay() == -1) {
			movementsMoneys = movementsMoneyRepository.getMovementsMoney(brokerAccount.getId(), null, year, month);
		}
		else
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFunctionCutDayNotImplemented());
		
		return movementsMoneys;
	}
	
	private List<MovementsIssueEntity> getPeriodIssueMovements(BrokerAccountEntity brokerAccount, Integer year, Integer month) throws BusinessException {
		
		List<MovementsIssueEntity> movementsIssues;
		
		if(brokerAccount.getCutDay() == -1) {
			movementsIssues = movementsIssueRepository.getMovementsIssue(brokerAccount.getId(), null, year, month);
		}
		else
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFunctionCutDayNotImplemented());
		
		return movementsIssues;
	}
	
	private List<OperationStatementDataPojo> getPeriodMoneyMovementsPojos(BrokerAccountEntity brokerAccount, Integer year, Integer month) throws BusinessException {
		
		List<MovementsMoneyEntity> movementsMoneys = getPeriodMoneyMovements(brokerAccount, year, month);
		List<OperationStatementDataPojo> operationStatementDataPojos = new ArrayList<>();
		
		for(MovementsMoneyEntity movementsMoney: movementsMoneys) {
			
			OperationStatementDataPojo operationstatementDataPojo = new OperationStatementDataPojo();
			
			String typeOperationDescription = movementsMoney.getCatalogTypeTransaction().getDescription();
			
			if (movementsMoney.getIdTypeTransaction() == CatalogTypeTransactionEnum.DIVIDEND.getValue())
				typeOperationDescription += " / " + ((movementsMoney.getCatalogIssue() != null) ? movementsMoney.getCatalogIssue().getInitials() : "");
			
			operationstatementDataPojo.setDate(dateUtil.getMillis(movementsMoney.getDateTransaction()));
			operationstatementDataPojo.setDateFormated(movementsMoney.getDateTransaction().toString());
			operationstatementDataPojo.setTypeOperationId(movementsMoney.getIdTypeTransaction());
			operationstatementDataPojo.setTypeOperationDescription(typeOperationDescription);
			operationstatementDataPojo.setAmount(movementsMoney.getAmount());
			operationstatementDataPojo.setDefinitionTypeOperationEnum(DefinitionTypeOperationEnum.MOVEMENT);
			
			operationStatementDataPojos.add(operationstatementDataPojo);
		}
		
		return operationStatementDataPojos;
	}
	
	private List<OperationStatementDataPojo> getPeriodIssueMovementsPojos(BrokerAccountEntity brokerAccount, Integer year, Integer month) throws BusinessException {
		
		List<MovementsIssueEntity> movementsIssues = getPeriodIssueMovements(brokerAccount, year, month);
		List<OperationStatementDataPojo> operationStatementDataPojos = new ArrayList<>();
		
		for(MovementsIssueEntity movementsIssue: movementsIssues) {
			
			if (movementsIssue.getCatalogTypeMovement().getId() == CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY_CANCELLED.getValue()) {
				continue;
			}
			
			OperationStatementDataPojo operationstatementDataPojo = new OperationStatementDataPojo();
			
			operationstatementDataPojo.setDate(dateUtil.getMillis(movementsIssue.getDateTransaction()));
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
		
		if (operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeTransactionEnum.DEPOSIT.getValue()) ||
				operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeTransactionEnum.DIVIDEND.getValue()) ) {
				
				currentBalance = currentBalance.add(operationStatementDataPojo.getAmount());
				operationStatementDataPojo.setIncome(operationStatementDataPojo.getAmount());
				operationStatementDataPojo.setBalance(currentBalance);
			}
			else if (operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeTransactionEnum.WITHDRAW.getValue())) {
				
				currentBalance = currentBalance.subtract(operationStatementDataPojo.getAmount());
				operationStatementDataPojo.setCharge(operationStatementDataPojo.getAmount().multiply(new BigDecimal(-1)));
				operationStatementDataPojo.setBalance(currentBalance);
			}
			else
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgDefinitionBalanceNotFound(operationStatementDataPojo.getTypeOperationId(), operationStatementDataPojo.getDefinitionTypeOperationEnum().getDescription()));
		
		return operationStatementDataPojo;
	}
	
	private OperationStatementDataPojo getPeriodOperationIssue(OperationStatementDataPojo operationStatementDataPojo, BigDecimal currentBalance) throws BusinessException {
		
		if (operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeMovementEnum.BUY.getValue()) ||
			operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY.getValue())) {
			
			currentBalance = currentBalance.subtract(operationStatementDataPojo.getAmount());
			operationStatementDataPojo.setCharge(operationStatementDataPojo.getAmount().multiply(new BigDecimal(-1)));
			operationStatementDataPojo.setBalance(currentBalance);
		}
		else if (operationStatementDataPojo.getTypeOperationId().equals(CatalogTypeMovementEnum.SELL.getValue())) {
			
			currentBalance = currentBalance.add(operationStatementDataPojo.getAmount());
			operationStatementDataPojo.setIncome(operationStatementDataPojo.getAmount());
			operationStatementDataPojo.setBalance(currentBalance);
		}
		else
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgDefinitionBalanceNotFound(operationStatementDataPojo.getTypeOperationId(), operationStatementDataPojo.getDefinitionTypeOperationEnum().getDescription()));
		
		return operationStatementDataPojo;
	}
	
	private List<OperationStatementDataPojo> getPeriodOperations(BrokerAccountEntity brokerAccount, Integer year, Integer month, BigDecimal previousBalance) throws BusinessException {
		
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
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgDefinitionBalanceNotFound(operationStatementDataPojo.getTypeOperationId(), operationStatementDataPojo.getDefinitionTypeOperationEnum().getDescription()));
		}
		
		return operationStatementDataPojos;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public AccountStatementDataPojo executeGetAccountStatement(AccountStatementRequestPojo requestPojo) throws BusinessException {
		
		BrokerAccountEntity brokerAccount = (BrokerAccountEntity) genericPersistance.findById(BrokerAccountEntity.class, requestPojo.getIdAccountBroker());
		
		AccountStatementDataPojo accountStatementResponsePojo = new AccountStatementDataPojo();
		accountStatementResponsePojo.setYear(requestPojo.getYear());
		accountStatementResponsePojo.setMonth(requestPojo.getMonth());
		accountStatementResponsePojo.setPreviousBalance(accountHelper.getTotalPreviousPeriod(brokerAccount, requestPojo.getYear(), requestPojo.getMonth()));
		accountStatementResponsePojo.setCurrentBalance(accountHelper.getTotalPreviousPeriod(brokerAccount, requestPojo.getYear(), requestPojo.getMonth() + 1));
		accountStatementResponsePojo.setOperationsStatement(getPeriodOperations(brokerAccount, requestPojo.getYear(), requestPojo.getMonth(), accountStatementResponsePojo.getPreviousBalance()));
		accountStatementResponsePojo.setBrokerAccountResume(buildEntityToPojoUtil.mapBrokerAccountResumePojo(null, brokerAccount));
		
		return accountStatementResponsePojo;
	}
}
