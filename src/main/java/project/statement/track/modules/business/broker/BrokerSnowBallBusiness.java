package project.statement.track.modules.business.broker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.entity.BrokerDataSnowballEntity;
import project.statement.track.app.beans.entity.CatalogIssueEntity;
import project.statement.track.app.beans.entity.MovementsIssueEntity;
import project.statement.track.app.beans.entity.MovementsMoneyEntity;
import project.statement.track.app.beans.pojos.BrokerSnowBallPojo;
import project.statement.track.app.beans.pojos.entity.MovementIssuePojo;
import project.statement.track.app.beans.pojos.entity.MovementMoneyPojo;
import project.statement.track.app.repository.BrokerDataSnowBallRepository;
import project.statement.track.app.repository.CatalogsRepository;
import project.statement.track.app.repository.MovementsIssueRepository;
import project.statement.track.app.vo.catalogs.CatalogsEntity;
import project.statement.track.app.vo.catalogs.CatalogsErrorMessage;
import project.statement.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class BrokerSnowBallBusiness extends MainBusiness {
	
	@SuppressWarnings("rawtypes")
	private final GenericPersistence genericPersistance;
	private final CatalogsRepository catalogsRepository;
	private final BrokerDataSnowBallRepository brokerDataSnowBallRepository;
	private final MovementsIssueRepository movementsIssueRepository;
	
	private static final Set<String> SECTION_DEPOSIT_LIST = Set.of("Deposito");
	private static final Set<String> SECTION_INVESTMENT_COMPROMISE_LIST = Set.of("Compromiso de Inversión");
	private static final Set<String> SECTION_INVESTMENT_COMPROMISE_COMMISSION_LIST = Set.of("Comision por Compromiso de Inversión");
	private static final Set<String> SECTION_PAYMENT_SECOND_MARKET_COMISSION_LIST = Set.of("Pago de comision por compra de acciones en Snowball Market");
	private static final Set<String> SECTION_PAYMENT_YIELD_DIVIDEND_LIST = Set.of("Pago de Rendimiento Mensual", "Bono por Dividendo",
			"Bono por Rendimiento", "Pago de Comisión Variable", "Pago de RODI");
	private static final Set<String> SECTION_PAYMENT_SECOND_MARKET_LIST = Set.of("Pago de compra de acciones en Mercado Secundario",
			"Pago de compra de acciones en Snowball Market");
	private static final Set<String> SECTION_PAYMENT_RETURN_LIST = Set.of("Ajuste por transacción errónea en Snowball Market no se recibió 1 ODI",
			"Devolución de pago de compra de acciones en Mercado Secundario por oferta no aceptada por parte del dueño de la ODI",
			"Devolución de pago de compra de acciones en Snowball Market por oferta no aceptada por parte del dueño de la ODI",
			"Devolución de comision de compra de acciones en Snowball Market por oferta no aceptada por parte del dueño de la ODI");
	private static final Set<String> SECTION_WITHDRAW_LIST = Set.of("Retiro");
	
	@SuppressWarnings("unchecked")
	private void saveSnowBallEntity(BrokerDataSnowballEntity brokerDataSnowball, Object entityToSave, String tableTrack) throws BusinessException {
		
		if (entityToSave != null) {
		
			genericPersistance.save(entityToSave);
			
			Method methodId;
			try {
				
				methodId = entityToSave.getClass().getDeclaredMethod("getId");
				updateSnowBallTrack(brokerDataSnowball, tableTrack, Integer.parseInt(methodId.invoke(entityToSave) + "" ));
				
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException |IllegalArgumentException | InvocationTargetException e) {
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgBadIdTableTrack(), e);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updateSnowBallTrack(BrokerDataSnowballEntity brokerDataSnowball, String trackTable, Integer trackId) {
		
		brokerDataSnowball.setTrackTable(trackTable);
		brokerDataSnowball.setTrackTableId(trackId);
		genericPersistance.update(brokerDataSnowball);
	}
	
	private Integer getIssueId(String company) throws BusinessException {
		
		CatalogIssueEntity catalogIssue = catalogsRepository.getCatalogIssueSnowBall(company);
		
		if (catalogIssue == null)
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgMapCompanyNotFound(company));
		else
			return catalogIssue.getId();
	}
	
	private void swapBuyAndComision(List<BrokerDataSnowballEntity> brokerDataSnowballs) {
		
		for(int indexBuy = 0; indexBuy < brokerDataSnowballs.size(); indexBuy++) {
			
			BrokerDataSnowballEntity brokerDataSnowballBuy = brokerDataSnowballs.get(indexBuy);
			
			if (brokerDataSnowballBuy.getMovementDescription().equals("Compromiso de Inversión") || 
					brokerDataSnowballBuy.getMovementDescription().equals("Pago de compra de acciones en Snowball Market")) {
				
				for(int indexCompromise = 0; indexCompromise < brokerDataSnowballs.size(); indexCompromise++) {
					
					BrokerDataSnowballEntity brokerDataSnowballCompromise = brokerDataSnowballs.get(indexCompromise);
					
					if ((brokerDataSnowballCompromise.getMovementDescription().equals("Comision por Compromiso de Inversión") || brokerDataSnowballCompromise.getMovementDescription().equals("Pago de comision por compra de acciones en Snowball Market") ) &&
						indexCompromise < indexBuy && 
						brokerDataSnowballCompromise.getDateTransaction().compareTo(brokerDataSnowballBuy.getDateTransaction()) == 0 &&
								brokerDataSnowballCompromise.getCompany().equals(brokerDataSnowballBuy.getCompany()) &&
								brokerDataSnowballCompromise.getTotalIssues().equals(brokerDataSnowballBuy.getTotalIssues()) )
							Collections.swap(brokerDataSnowballs, indexBuy, indexCompromise);
				}
			}
		}
	}
	
	private MovementMoneyPojo buildMovementMoneyDeposit(BrokerDataSnowballEntity brokerDataSnowball) {
		
		MovementMoneyPojo movementMoneyPojo = new MovementMoneyPojo();
		movementMoneyPojo.setAmount(brokerDataSnowball.getBalanceEntry());
		movementMoneyPojo.setAmountMxn(brokerDataSnowball.getBalanceEntry());
		movementMoneyPojo.setDateTransactionMillis(dateUtil.getMillis(brokerDataSnowball.getDateTransaction()));
		movementMoneyPojo.setIdBrokerAccount(CatalogsEntity.CatalogBrokerAccount.SNOWBALL_MAIN);
		movementMoneyPojo.setIdTypeTransaction(CatalogsEntity.CatalogTypeTransaction.DEPOSIT);
		
		return movementMoneyPojo;
	}
	
	private MovementMoneyPojo buildMovementMoneyWithdraw(BrokerDataSnowballEntity brokerDataSnowball) {
		
		MovementMoneyPojo movementMoneyPojo = new MovementMoneyPojo();
		movementMoneyPojo.setAmount(brokerDataSnowball.getBalanceExit());
		movementMoneyPojo.setAmountMxn(brokerDataSnowball.getBalanceExit());
		movementMoneyPojo.setDateTransactionMillis(dateUtil.getMillis(brokerDataSnowball.getDateTransaction()));
		movementMoneyPojo.setIdBrokerAccount(CatalogsEntity.CatalogBrokerAccount.SNOWBALL_MAIN);
		movementMoneyPojo.setIdTypeTransaction(CatalogsEntity.CatalogTypeTransaction.WITHDRAW);
		
		return movementMoneyPojo;
	}
	
	private MovementIssuePojo buildMovementIssueInvestment(BrokerDataSnowballEntity brokerDataSnowball) throws BusinessException {
		
		MovementIssuePojo movementIssuePojo = new MovementIssuePojo();
		movementIssuePojo.setDateTransaction(brokerDataSnowball.getDateTransaction());
		movementIssuePojo.setIdBrokerAccount(CatalogsEntity.CatalogBrokerAccount.SNOWBALL_MAIN);
		movementIssuePojo.setIdIssue(getIssueId(brokerDataSnowball.getCompany()));
		movementIssuePojo.setIdTypeMovement(CatalogsEntity.CatalogTypeMovement.BUY);
		movementIssuePojo.setQuantityIssues(brokerDataSnowball.getTotalIssues());
		movementIssuePojo.setPriceTotal(brokerDataSnowball.getBalanceExit());
		movementIssuePojo.setPriceIssueUnity(brokerDataSnowball.getBalanceExit().divide(new BigDecimal(brokerDataSnowball.getTotalIssues()), 2, RoundingMode.HALF_UP));
	
		return movementIssuePojo;
	}
	
	private MovementMoneyPojo buildMovementMoneyPayDividend(BrokerDataSnowballEntity brokerDataSnowball, CatalogIssueEntity catalogIssue) {
		
		MovementMoneyPojo movementMoneyPojo = new MovementMoneyPojo();
		movementMoneyPojo.setAmount(brokerDataSnowball.getBalanceEntry());
		movementMoneyPojo.setAmountMxn(brokerDataSnowball.getBalanceEntry());
		movementMoneyPojo.setDateTransactionMillis(dateUtil.getMillis(brokerDataSnowball.getDateTransaction()));
		movementMoneyPojo.setIdBrokerAccount(CatalogsEntity.CatalogBrokerAccount.SNOWBALL_MAIN);
		movementMoneyPojo.setIdTypeTransaction(CatalogsEntity.CatalogTypeTransaction.DIVIDEND);
		movementMoneyPojo.setIdIssue(catalogIssue.getId());
		
		return movementMoneyPojo;
	}
	
	private MovementIssuePojo buildMovementIssueBuyMarketSecundary(BrokerDataSnowballEntity brokerDataSnowball) throws BusinessException {
		
		MovementIssuePojo movementIssuePojo = new MovementIssuePojo();
		movementIssuePojo.setDateTransaction(brokerDataSnowball.getDateTransaction());
		movementIssuePojo.setIdBrokerAccount(CatalogsEntity.CatalogBrokerAccount.SNOWBALL_MAIN);
		movementIssuePojo.setIdIssue(getIssueId(brokerDataSnowball.getCompany()));
		movementIssuePojo.setIdTypeMovement(CatalogsEntity.CatalogTypeMovement.BUY_MARKET_SECUNDARY);
		movementIssuePojo.setQuantityIssues(brokerDataSnowball.getTotalIssues());
		movementIssuePojo.setPriceTotal(brokerDataSnowball.getBalanceExit());
		movementIssuePojo.setPriceIssueUnity(brokerDataSnowball.getBalanceExit().divide(new BigDecimal(brokerDataSnowball.getTotalIssues()), 2, RoundingMode.HALF_UP));
		
		return movementIssuePojo;
	}
	
	private CatalogIssueEntity getCatalogIssue(String company) throws BusinessException {
		
		CatalogIssueEntity catalogIssue = catalogsRepository.getCatalogIssueSnowBall(company);
		
		if (catalogIssue == null)
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgCatalogNotFoundForCompany(company));
		
		return catalogIssue;
	}
	
	private void handleDeposit(BrokerDataSnowballEntity brokerDataSnowball) throws BusinessException {
		
		MovementMoneyPojo movementMoneyPojo = buildMovementMoneyDeposit(brokerDataSnowball);
		MovementsMoneyEntity movementsMoney =  buildPojoToEntityUtil.generateMovementsMoneyEntity(null, movementMoneyPojo);
		
		saveSnowBallEntity(brokerDataSnowball, movementsMoney, jpaUtil.getTableName(MovementsMoneyEntity.class));
	}
	
	private void handleWithdraw(BrokerDataSnowballEntity brokerDataSnowball) throws BusinessException {
		
		MovementMoneyPojo movementMoneyPojo = buildMovementMoneyWithdraw(brokerDataSnowball);
		MovementsMoneyEntity movementsMoney = buildPojoToEntityUtil.generateMovementsMoneyEntity(null, movementMoneyPojo);
		
		saveSnowBallEntity(brokerDataSnowball, movementsMoney, jpaUtil.getTableName(MovementsMoneyEntity.class));
	}
	
	private void handleInvestmentCompromise(BrokerDataSnowballEntity brokerDataSnowball) throws BusinessException {
		
		MovementIssuePojo movementIssuePojo = buildMovementIssueInvestment(brokerDataSnowball);
		MovementsIssueEntity movementsIssue = buildPojoToEntityUtil.generateMovementsIssueEntity(null, movementIssuePojo);
		
		saveSnowBallEntity(brokerDataSnowball, movementsIssue, jpaUtil.getTableName(MovementsIssueEntity.class));
	}
	
	@SuppressWarnings("unchecked")
	private void handleInvestmentCompromiseCommission(BrokerDataSnowballEntity brokerDataSnowball) throws BusinessException {
		
		CatalogIssueEntity catalogIssue = getCatalogIssue(brokerDataSnowball.getCompany());
		MovementsIssueEntity movementsIssue = movementsIssueRepository.getMovementsIssueByQuantityIssues(brokerDataSnowball.getTotalIssues(), catalogIssue.getId(), CatalogsEntity.CatalogTypeMovement.BUY);
		
		movementsIssue.setPriceTotal(movementsIssue.getPriceTotal().add(brokerDataSnowball.getBalanceExit()));
		movementsIssue.setComisionTotal(brokerDataSnowball.getBalanceExit());
		genericPersistance.update(movementsIssue);
		
		updateSnowBallTrack(brokerDataSnowball, jpaUtil.getTableName(MovementsIssueEntity.class), movementsIssue.getId());
	}
	
	@SuppressWarnings("unchecked")
	private void handleInvestmentSecondMarketCommission(BrokerDataSnowballEntity brokerDataSnowball) throws BusinessException {
		
		CatalogIssueEntity catalogIssue = getCatalogIssue(brokerDataSnowball.getCompany());
		MovementsIssueEntity movementsIssue = movementsIssueRepository.getMovementsIssueByQuantityIssues(brokerDataSnowball.getTotalIssues(), catalogIssue.getId(), CatalogsEntity.CatalogTypeMovement.BUY_MARKET_SECUNDARY);
		
		movementsIssue.setPriceTotal(movementsIssue.getPriceTotal().add(brokerDataSnowball.getBalanceExit()));
		movementsIssue.setComisionTotal(brokerDataSnowball.getBalanceExit());
		genericPersistance.update(movementsIssue);
		
		updateSnowBallTrack(brokerDataSnowball, jpaUtil.getTableName(MovementsIssueEntity.class), movementsIssue.getId());
	}
	
	private void handlePaymentYieldDividend(BrokerDataSnowballEntity brokerDataSnowball) throws BusinessException {
		
		CatalogIssueEntity catalogIssue = getCatalogIssue(brokerDataSnowball.getCompany());
		MovementMoneyPojo movementMoneyPojo = buildMovementMoneyPayDividend(brokerDataSnowball, catalogIssue);
		MovementsMoneyEntity movementsMoney =  buildPojoToEntityUtil.generateMovementsMoneyEntity(null, movementMoneyPojo);
		
		saveSnowBallEntity(brokerDataSnowball, movementsMoney, jpaUtil.getTableName(MovementsMoneyEntity.class));
	}
	
	private void handlePaymentSecondMarket(BrokerDataSnowballEntity brokerDataSnowball) throws BusinessException {
		
		MovementIssuePojo movementIssuePojo = buildMovementIssueBuyMarketSecundary(brokerDataSnowball);
		MovementsIssueEntity movementsIssue = buildPojoToEntityUtil.generateMovementsIssueEntity(null, movementIssuePojo);
		
		saveSnowBallEntity(brokerDataSnowball, movementsIssue, jpaUtil.getTableName(MovementsIssueEntity.class));
	}
	
	@SuppressWarnings("unchecked")
	private void handlePaymentReturn(BrokerDataSnowballEntity brokerDataSnowball) throws BusinessException {
		
		CatalogIssueEntity catalogIssue = getCatalogIssue(brokerDataSnowball.getCompany());
		MovementsIssueEntity movementsIssue = movementsIssueRepository.getMovementsIssueByPriceTotal(brokerDataSnowball.getBalanceEntry(), catalogIssue.getId(), CatalogsEntity.CatalogTypeMovement.BUY_MARKET_SECUNDARY);
		
		if (movementsIssue == null)
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgIssueRedeemNotFound(brokerDataSnowball.getCompany(), brokerDataSnowball.getBalanceEntry()));
		
		movementsIssue.setIdTypeMovement(CatalogsEntity.CatalogTypeMovement.BUY_MARKET_SECUNDARY_CANCELLED);
		genericPersistance.update(movementsIssue);
		
		updateSnowBallTrack(brokerDataSnowball, jpaUtil.getTableName(MovementsIssueEntity.class), movementsIssue.getId());
	}

	@SuppressWarnings({ "unchecked" })
	public void storeDataSnowBall(List<BrokerSnowBallPojo> snowBallPojos) throws BusinessException {
		
		List<BrokerDataSnowballEntity> brokerDataSnowballs = brokerSnowBallUtil.mapDataSnowBall(snowBallPojos);
		
		if(!brokerDataSnowballs.isEmpty()) {
			
			BrokerDataSnowballEntity brokerDataSnowball = (BrokerDataSnowballEntity) genericPersistance.findById(BrokerDataSnowballEntity.class, brokerDataSnowballs.get(0).getId());
			
			if(brokerDataSnowball != null)
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgStatementIdRegistered());
		}
		
		genericPersistance.save(brokerDataSnowballs);
	}
	
	public void assignSnowBallData() throws BusinessException {
		
		List<BrokerDataSnowballEntity> brokerDataSnowballs = brokerDataSnowBallRepository.getDataPending();

		swapBuyAndComision(brokerDataSnowballs);
		
		for(BrokerDataSnowballEntity brokerDataSnowball: brokerDataSnowballs) {
			
			if (SECTION_DEPOSIT_LIST.contains(brokerDataSnowball.getMovementDescription()))
				handleDeposit(brokerDataSnowball);
			
			else if (SECTION_INVESTMENT_COMPROMISE_LIST.contains(brokerDataSnowball.getMovementDescription()))
				handleInvestmentCompromise(brokerDataSnowball);
				
			else if (SECTION_INVESTMENT_COMPROMISE_COMMISSION_LIST.contains(brokerDataSnowball.getMovementDescription()))
				handleInvestmentCompromiseCommission(brokerDataSnowball);
			
			else if (SECTION_PAYMENT_SECOND_MARKET_COMISSION_LIST.contains(brokerDataSnowball.getMovementDescription()))
				handleInvestmentSecondMarketCommission(brokerDataSnowball);
			
			else if (SECTION_PAYMENT_YIELD_DIVIDEND_LIST.contains(brokerDataSnowball.getMovementDescription()))
				handlePaymentYieldDividend(brokerDataSnowball);
				
			else if (SECTION_PAYMENT_SECOND_MARKET_LIST.contains(brokerDataSnowball.getMovementDescription()))
				handlePaymentSecondMarket(brokerDataSnowball);
			
			else if (SECTION_PAYMENT_RETURN_LIST.contains(brokerDataSnowball.getMovementDescription()))
				handlePaymentReturn(brokerDataSnowball);
			
			else if (SECTION_WITHDRAW_LIST.contains(brokerDataSnowball.getMovementDescription()))
				handleWithdraw(brokerDataSnowball);
			
			else
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgOptionMovementTypeNotImplemented(brokerDataSnowball.getMovementDescription()));
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void executeStatementSnowBall(String textHtml) throws BusinessException {
		
		List<BrokerSnowBallPojo> resultList = brokerSnowBallUtil.getDataSnowBallFromText(textHtml);
		storeDataSnowBall(resultList);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void executeAssignSnowBallData() throws BusinessException {
		
		assignSnowBallData();
	}
}
