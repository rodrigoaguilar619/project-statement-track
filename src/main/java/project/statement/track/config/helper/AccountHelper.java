package project.statement.track.config.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

import lib.base.backend.exception.data.BusinessException;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.entity.BrokerAccountEntity;
import project.statement.track.app.repository.MovementsIssueRepository;
import project.statement.track.app.repository.MovementsMoneyRepository;
import project.statement.track.app.vo.catalogs.CatalogsErrorMessage;
import project.statement.track.app.vo.entities.CatalogTypeMovementEnum;
import project.statement.track.app.vo.entities.CatalogTypeTransactionEnum;

@RequiredArgsConstructor
@Component
public class AccountHelper {
	
	private final MovementsMoneyRepository movementsMoneyRepository;
	
	private final MovementsIssueRepository movementsIssueRepository;
	
	public BigDecimal getMovementMoneyPreviousTotal(BrokerAccountEntity brokerAccount, Integer idCatalogTypeTransaction, Integer year, Integer month) throws BusinessException {
		
		if(brokerAccount.getCutDay() == -1) {
			LocalDateTime dateEnd = LocalDate.of(year, month, 1).atStartOfDay();
			return movementsMoneyRepository.getMovementsMoneyPreviousTotal(brokerAccount.getId(), idCatalogTypeTransaction, dateEnd);
		}
		else
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFunctionCutDayNotImplemented());
	}
	
	public BigDecimal getMovementIssuePreviousTotal(BrokerAccountEntity brokerAccount, Integer idCatalogTypeMovement, Integer year, Integer month) throws BusinessException {
		
		if(brokerAccount.getCutDay() == -1) {
			LocalDateTime dateEnd = LocalDate.of(year, month, 1).atStartOfDay();
			return movementsIssueRepository.getMovementsIssuePreviousTotal(brokerAccount.getId(), idCatalogTypeMovement, dateEnd);
		}
		else
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgFunctionCutDayNotImplemented());
	}

	public BigDecimal getTotalPreviousPeriod(BrokerAccountEntity brokerAccount, Integer year, Integer month) throws BusinessException {
		
		BigDecimal movementsMoneyDepositsTotal;
		BigDecimal movementsMoneyWithDrawsTotal;
		BigDecimal movementsMoneyDividendTotal;
		BigDecimal movementsIssueBuyTotal;
		BigDecimal movementsIssueBuyMarketSecundaryTotal;
		BigDecimal movementsIssueSellTotal;
		
		movementsMoneyDepositsTotal = getMovementMoneyPreviousTotal(brokerAccount, CatalogTypeTransactionEnum.DEPOSIT.getValue(), year, month);
		movementsMoneyDividendTotal = getMovementMoneyPreviousTotal(brokerAccount, CatalogTypeTransactionEnum.DIVIDEND.getValue(), year, month);
		movementsMoneyWithDrawsTotal = getMovementMoneyPreviousTotal(brokerAccount, CatalogTypeTransactionEnum.WITHDRAW.getValue(), year, month);
		movementsIssueBuyTotal = getMovementIssuePreviousTotal(brokerAccount, CatalogTypeMovementEnum.BUY.getValue(), year, month);
		movementsIssueBuyMarketSecundaryTotal = getMovementIssuePreviousTotal(brokerAccount, CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY.getValue(), year, month);
		movementsIssueSellTotal = getMovementIssuePreviousTotal(brokerAccount, CatalogTypeMovementEnum.SELL.getValue(), year, month);
		
		return movementsMoneyDepositsTotal.add(movementsIssueSellTotal).add(movementsMoneyDividendTotal).subtract(movementsMoneyWithDrawsTotal).subtract(movementsIssueBuyTotal).subtract(movementsIssueBuyMarketSecundaryTotal).setScale(2, RoundingMode.HALF_UP);
	}
}
