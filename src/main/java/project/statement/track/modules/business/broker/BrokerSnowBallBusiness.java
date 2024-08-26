package project.statement.track.modules.business.broker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

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
		movementMoneyPojo.setDateTransactionMillis(brokerDataSnowball.getDateTransaction() != null ? brokerDataSnowball.getDateTransaction().getTime() : null);
		movementMoneyPojo.setIdBrokerAccount(CatalogsEntity.CatalogBrokerAccount.SNOWBALL_MAIN);
		movementMoneyPojo.setIdTypeTransaction(CatalogsEntity.CatalogTypeTransaction.DEPOSIT);
		
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
		movementMoneyPojo.setDateTransactionMillis(brokerDataSnowball.getDateTransaction() != null ? brokerDataSnowball.getDateTransaction().getTime() : null);
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
	
	@SuppressWarnings("unchecked")
	public void assignSnowBallData() throws BusinessException {
		
		List<BrokerDataSnowballEntity> brokerDataSnowballs = brokerDataSnowBallRepository.getDataPending();

		swapBuyAndComision(brokerDataSnowballs);
		
		for(BrokerDataSnowballEntity brokerDataSnowball: brokerDataSnowballs) {
			
			Object entityToSave = null;
			String tableTrack = null;
			MovementMoneyPojo movementMoneyPojo = null;
			MovementIssuePojo movementIssuePojo = null;
			MovementsMoneyEntity movementsMoney = null;
			MovementsIssueEntity movementsIssue = null;
			CatalogIssueEntity catalogIssue = null;
			
			if (brokerDataSnowball.getMovementDescription().equals("Deposito")) {
				
				movementMoneyPojo = buildMovementMoneyDeposit(brokerDataSnowball);
				movementsMoney =  buildPojoToEntityUtil.generateMovementsMoneyEntity(null, movementMoneyPojo);
				
				entityToSave = movementsMoney;
				tableTrack = "movements_money";
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Compromiso de Inversión")) {
				
				movementIssuePojo = buildMovementIssueInvestment(brokerDataSnowball);
				movementsIssue = buildPojoToEntityUtil.generateMovementsIssueEntity(null, movementIssuePojo);
				
				entityToSave = movementsIssue;
				tableTrack = jpaUtil.getTableName(MovementsIssueEntity.class);
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Comision por Compromiso de Inversión") ) {

				catalogIssue = getCatalogIssue(brokerDataSnowball.getCompany());
				movementsIssue = movementsIssueRepository.getMovementsIssueByQuantityIssues(brokerDataSnowball.getTotalIssues(), catalogIssue.getId(), CatalogsEntity.CatalogTypeMovement.BUY);
				
				movementsIssue.setPriceTotal(movementsIssue.getPriceTotal().add(brokerDataSnowball.getBalanceExit()));
				movementsIssue.setComisionTotal(brokerDataSnowball.getBalanceExit());
				genericPersistance.update(movementsIssue);
				
				updateSnowBallTrack(brokerDataSnowball, jpaUtil.getTableName(MovementsIssueEntity.class), movementsIssue.getId());
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Pago de comision por compra de acciones en Snowball Market")) {

				catalogIssue = getCatalogIssue(brokerDataSnowball.getCompany());
				movementsIssue = movementsIssueRepository.getMovementsIssueByQuantityIssues(brokerDataSnowball.getTotalIssues(), catalogIssue.getId(), CatalogsEntity.CatalogTypeMovement.BUY_MARKET_SECUNDARY);
				
				movementsIssue.setPriceTotal(movementsIssue.getPriceTotal().add(brokerDataSnowball.getBalanceExit()));
				movementsIssue.setComisionTotal(brokerDataSnowball.getBalanceExit());
				genericPersistance.update(movementsIssue);
				
				updateSnowBallTrack(brokerDataSnowball, jpaUtil.getTableName(MovementsIssueEntity.class), movementsIssue.getId());
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Pago de Rendimiento Mensual") ||
					brokerDataSnowball.getMovementDescription().equals("Bono por Dividendo") ||
					brokerDataSnowball.getMovementDescription().equals("Bono por Rendimiento") ||
					brokerDataSnowball.getMovementDescription().equals("Pago de Comisión Variable") ||
					brokerDataSnowball.getMovementDescription().equals("Pago de RODI")) {
				
				catalogIssue = getCatalogIssue(brokerDataSnowball.getCompany());
				
				movementMoneyPojo = buildMovementMoneyPayDividend(brokerDataSnowball, catalogIssue);
				movementsMoney =  buildPojoToEntityUtil.generateMovementsMoneyEntity(null, movementMoneyPojo);
				
				entityToSave = movementsMoney;
				tableTrack = "movements_money";
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Pago de compra de acciones en Mercado Secundario") ||
					brokerDataSnowball.getMovementDescription().equals("Pago de compra de acciones en Snowball Market")) {

				movementIssuePojo = buildMovementIssueBuyMarketSecundary(brokerDataSnowball);
				
				movementsIssue = buildPojoToEntityUtil.generateMovementsIssueEntity(null, movementIssuePojo);
				entityToSave = movementsIssue;
				tableTrack = jpaUtil.getTableName(MovementsIssueEntity.class);
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Devolución de pago de compra de acciones en Mercado Secundario por oferta no aceptada por parte del dueño de la ODI") ||
					brokerDataSnowball.getMovementDescription().equals("Ajuste por transacción errónea en Snowball Market no se recibió 1 ODI") ||
					brokerDataSnowball.getMovementDescription().equals("Devolución de pago de compra de acciones en Snowball Market por oferta no aceptada por parte del dueño de la ODI") ||
					brokerDataSnowball.getMovementDescription().equals("Devolución de comision de compra de acciones en Snowball Market por oferta no aceptada por parte del dueño de la ODI")) {
				
				catalogIssue = getCatalogIssue(brokerDataSnowball.getCompany());
				movementsIssue = movementsIssueRepository.getMovementsIssueByPriceTotal(brokerDataSnowball.getBalanceEntry(), catalogIssue.getId(), CatalogsEntity.CatalogTypeMovement.BUY_MARKET_SECUNDARY);
				
				if (movementsIssue == null)
					throw new BusinessException(CatalogsErrorMessage.getErrorMsgIssueRedeemNotFound(brokerDataSnowball.getCompany(), brokerDataSnowball.getBalanceEntry()));
				
				movementsIssue.setIdTypeMovement(CatalogsEntity.CatalogTypeMovement.BUY_MARKET_SECUNDARY_CANCELLED);
				genericPersistance.update(movementsIssue);
				
				updateSnowBallTrack(brokerDataSnowball, jpaUtil.getTableName(MovementsIssueEntity.class), movementsIssue.getId());
			}
			else
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgOptionMovementTypeNotImplemented(brokerDataSnowball.getMovementDescription()));
			
			saveSnowBallEntity(brokerDataSnowball, entityToSave, tableTrack);
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
