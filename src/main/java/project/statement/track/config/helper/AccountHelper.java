package project.statement.track.config.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lib.base.backend.exception.data.BusinessException;
import project.statement.track.app.beans.entity.BrokerAccount;
import project.statement.track.app.repository.MovementsIssueRepository;
import project.statement.track.app.repository.MovementsMoneyRepository;
import project.statement.track.app.vo.catalogs.CatalogsEntity;

@Component
public class AccountHelper {
	
	@Autowired
	MovementsMoneyRepository movementsMoneyRepository;
	
	@Autowired
	MovementsIssueRepository movementsIssueRepository;
	
	public BigDecimal getMovementMoneyPreviousTotal(BrokerAccount brokerAccount, Integer idCatalogTypeTransaction, Integer year, Integer month) throws BusinessException {
		
		if(brokerAccount.getCutDay() == -1) {
			Date dateEnd = new GregorianCalendar(year, month - 1, 1).getTime();
			return movementsMoneyRepository.getMovementsMoneyPreviousTotal(brokerAccount.getId(), idCatalogTypeTransaction, dateEnd);
		}
		else
			throw new BusinessException("Function of cut day not implemented");
	}
	
	public BigDecimal getMovementIssuePreviousTotal(BrokerAccount brokerAccount, Integer idCatalogTypeMovement, Integer year, Integer month) throws BusinessException {
		
		if(brokerAccount.getCutDay() == -1) {
			Date dateEnd = new GregorianCalendar(year, month - 1, 1).getTime();
			return movementsIssueRepository.getMovementsIssuePreviousTotal(brokerAccount.getId(), idCatalogTypeMovement, dateEnd);
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
		
		movementsMoneyDepositsTotal = getMovementMoneyPreviousTotal(brokerAccount, CatalogsEntity.CatalogTypeTransaction.DEPOSIT, year, month);
		movementsMoneyDividendTotal = getMovementMoneyPreviousTotal(brokerAccount, CatalogsEntity.CatalogTypeTransaction.DIVIDEND, year, month);
		movementsMoneyWithDrawsTotal = getMovementMoneyPreviousTotal(brokerAccount, CatalogsEntity.CatalogTypeTransaction.WITHDRAW, year, month);
		movementsIssueBuyTotal = getMovementIssuePreviousTotal(brokerAccount, CatalogsEntity.CatalogTypeMovement.BUY, year, month);
		movementsIssueBuyMarketSecundaryTotal = getMovementIssuePreviousTotal(brokerAccount, CatalogsEntity.CatalogTypeMovement.BUY_MARKET_SECUNDARY, year, month);
		movementsIssueSellTotal = getMovementIssuePreviousTotal(brokerAccount, CatalogsEntity.CatalogTypeMovement.SELL, year, month);
		
		return movementsMoneyDepositsTotal.add(movementsIssueSellTotal).add(movementsMoneyDividendTotal).subtract(movementsMoneyWithDrawsTotal).subtract(movementsIssueBuyTotal).subtract(movementsIssueBuyMarketSecundaryTotal).setScale(2, RoundingMode.HALF_UP);
	}
}
