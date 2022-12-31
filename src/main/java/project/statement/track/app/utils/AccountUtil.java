package project.statement.track.app.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;

import lib.base.backend.exception.data.BusinessException;
import project.statement.track.app.beans.entity.BrokerAccount;
import project.statement.track.app.repository.MovementsIssueRepository;
import project.statement.track.app.repository.MovementsMoneyRepository;
import project.statement.track.app.vo.catalogs.CatalogTypeMovementEnum;
import project.statement.track.app.vo.catalogs.CatalogTypeTransactionEnum;

public class AccountUtil {
	
	@Autowired
	MovementsMoneyRepository movementsMoneyRepository;
	
	@Autowired
	MovementsIssueRepository movementsIssueRepository;
	
	public BigDecimal getMovementMoneyPreviousTotal(BrokerAccount brokerAccount, CatalogTypeTransactionEnum catalogTypeTransaction, Integer year, Integer month) throws BusinessException {
		
		if(brokerAccount.getCutDay() == -1) {
			Date dateEnd = new GregorianCalendar(year, month - 1, 1).getTime();
			return movementsMoneyRepository.getMovementsMoneyPreviousTotal(brokerAccount.getId(), catalogTypeTransaction.getId(), dateEnd);
		}
		else
			throw new BusinessException("Function of cut day not implemented");
	}
	
	public BigDecimal getMovementIssuePreviousTotal(BrokerAccount brokerAccount, CatalogTypeMovementEnum atalogTypeMovement, Integer year, Integer month) throws BusinessException {
		
		if(brokerAccount.getCutDay() == -1) {
			Date dateEnd = new GregorianCalendar(year, month - 1, 1).getTime();
			return movementsIssueRepository.getMovementsIssuePreviousTotal(brokerAccount.getId(), atalogTypeMovement.getId(), dateEnd);
		}
		else
			throw new BusinessException("Function of cut day not implemented");
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
}
