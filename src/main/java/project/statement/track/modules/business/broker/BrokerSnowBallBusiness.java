package project.statement.track.modules.business.broker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import project.statement.track.app.beans.entity.BrokerDataSnowball;
import project.statement.track.app.beans.entity.CatalogIssue;
import project.statement.track.app.beans.entity.MovementsIssue;
import project.statement.track.app.beans.entity.MovementsMoney;
import project.statement.track.app.beans.pojos.BrokerSnowBallPojo;
import project.statement.track.app.beans.pojos.entity.MovementIssuePojo;
import project.statement.track.app.beans.pojos.entity.MovementMoneyPojo;
import project.statement.track.app.repository.BrokerDataSnowBallRepository;
import project.statement.track.app.repository.CatalogsRepository;
import project.statement.track.app.repository.MovementsIssueRepository;
import project.statement.track.app.utils.BrokerSnowBallUtil;
import project.statement.track.app.utils.BuildPojoToEntityUtil;
import project.statement.track.app.vo.catalogs.CatalogBrokerAccountEnum;
import project.statement.track.app.vo.catalogs.CatalogTypeMovementEnum;
import project.statement.track.app.vo.catalogs.CatalogTypeTransactionEnum;

@Component
public class BrokerSnowBallBusiness {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericPersistence genericCustomPersistance;
	
	@Autowired
	BrokerSnowBallUtil brokerSnowBallUtil;
	
	@Autowired
	BuildPojoToEntityUtil buildPojoToEntityUtil;
	
	@Autowired
	CatalogsRepository catalogsRepository;
	
	@Autowired
	BrokerDataSnowBallRepository brokerDataSnowBallRepository;
	
	@Autowired
	MovementsIssueRepository movementsIssueRepository;
	
	private static final String TABLE_NAME_MOVEMENTS_ISSUE = "movements_issue";
	
	@SuppressWarnings("unchecked")
	private void saveSnowBallEntity(BrokerDataSnowball brokerDataSnowball, Object entityToSave, String tableTrack) throws BusinessException {
		
		if (entityToSave != null) {
		
			genericCustomPersistance.save(entityToSave);
			
			Method methodId;
			try {
				
				methodId = entityToSave.getClass().getDeclaredMethod("getId");
				updateSnowBallTrack(brokerDataSnowball, tableTrack, Integer.parseInt(methodId.invoke(entityToSave) + "" ));
				
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException |IllegalArgumentException | InvocationTargetException e) {
				throw new BusinessException("Error getting id of table track", e);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updateSnowBallTrack(BrokerDataSnowball brokerDataSnowball, String trackTable, Integer trackId) {
		
		brokerDataSnowball.setTrackTable(trackTable);
		brokerDataSnowball.setTrackTableId(trackId + "");
		genericCustomPersistance.update(brokerDataSnowball);
	}
	
	private Integer getIssueId(String company) throws BusinessException {
		
		CatalogIssue catalogIssue = catalogsRepository.getCatalogIssueSnowBall(company);
		
		if (catalogIssue == null)
			throw new BusinessException("Error company " + company + " not found on mapping");
		else
			return catalogIssue.getId();
	}
	
	private void swapBuyAndComision(List<BrokerDataSnowball> brokerDataSnowballs) {
		
		for(int indexBuy = 0; indexBuy < brokerDataSnowballs.size(); indexBuy++) {
			
			BrokerDataSnowball brokerDataSnowballBuy = brokerDataSnowballs.get(indexBuy);
			
			if (brokerDataSnowballBuy.getMovementDescription().equals("Compromiso de Inversión") || 
					brokerDataSnowballBuy.getMovementDescription().equals("Pago de compra de acciones en Snowball Market")) {
				
				for(int indexCompromise = 0; indexCompromise < brokerDataSnowballs.size(); indexCompromise++) {
					
					BrokerDataSnowball brokerDataSnowballCompromise = brokerDataSnowballs.get(indexCompromise);
					
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
	
	private MovementMoneyPojo buildMovementMoneyDeposit(BrokerDataSnowball brokerDataSnowball) {
		
		MovementMoneyPojo movementMoneyPojo = new MovementMoneyPojo();
		movementMoneyPojo.setAmount(brokerDataSnowball.getBalanceEntry());
		movementMoneyPojo.setAmountMxn(brokerDataSnowball.getBalanceEntry());
		movementMoneyPojo.setDateTransactionMillis(brokerDataSnowball.getDateTransaction() != null ? brokerDataSnowball.getDateTransaction().getTime() : null);
		movementMoneyPojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
		movementMoneyPojo.setIdTypeTransaction(CatalogTypeTransactionEnum.DEPOSIT.getId());
		
		return movementMoneyPojo;
	}
	
	private MovementIssuePojo buildMovementIssueInvestment(BrokerDataSnowball brokerDataSnowball) throws BusinessException {
		
		MovementIssuePojo movementIssuePojo = new MovementIssuePojo();
		movementIssuePojo.setDateTransaction(brokerDataSnowball.getDateTransaction());
		movementIssuePojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
		movementIssuePojo.setIdIssue(getIssueId(brokerDataSnowball.getCompany()));
		movementIssuePojo.setIdTypeMovement(CatalogTypeMovementEnum.BUY.getId());
		movementIssuePojo.setQuantityIssues(brokerDataSnowball.getTotalIssues());
		movementIssuePojo.setPriceTotal(brokerDataSnowball.getBalanceExit());
		movementIssuePojo.setPriceIssueUnity(brokerDataSnowball.getBalanceExit().divide(new BigDecimal(brokerDataSnowball.getTotalIssues()), 2, RoundingMode.HALF_UP));
	
		return movementIssuePojo;
	}
	
	private MovementMoneyPojo buildMovementMoneyPayDividend(BrokerDataSnowball brokerDataSnowball, CatalogIssue catalogIssue) {
		
		MovementMoneyPojo movementMoneyPojo = new MovementMoneyPojo();
		movementMoneyPojo.setAmount(brokerDataSnowball.getBalanceEntry());
		movementMoneyPojo.setAmountMxn(brokerDataSnowball.getBalanceEntry());
		movementMoneyPojo.setDateTransactionMillis(brokerDataSnowball.getDateTransaction() != null ? brokerDataSnowball.getDateTransaction().getTime() : null);
		movementMoneyPojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
		movementMoneyPojo.setIdTypeTransaction(CatalogTypeTransactionEnum.DIVIDEND.getId());
		movementMoneyPojo.setIdIssue(catalogIssue.getId());
		
		return movementMoneyPojo;
	}
	
	private MovementIssuePojo buildMovementIssueBuyMarketSecundary(BrokerDataSnowball brokerDataSnowball) throws BusinessException {
		
		MovementIssuePojo movementIssuePojo = new MovementIssuePojo();
		movementIssuePojo.setDateTransaction(brokerDataSnowball.getDateTransaction());
		movementIssuePojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
		movementIssuePojo.setIdIssue(getIssueId(brokerDataSnowball.getCompany()));
		movementIssuePojo.setIdTypeMovement(CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY.getId());
		movementIssuePojo.setQuantityIssues(brokerDataSnowball.getTotalIssues());
		movementIssuePojo.setPriceTotal(brokerDataSnowball.getBalanceExit());
		movementIssuePojo.setPriceIssueUnity(brokerDataSnowball.getBalanceExit().divide(new BigDecimal(brokerDataSnowball.getTotalIssues()), 2, RoundingMode.HALF_UP));
		
		return movementIssuePojo;
	}

	@SuppressWarnings({ "unchecked" })
	public void storeDataSnowBall(List<BrokerSnowBallPojo> snowBallPojos) throws BusinessException {
		
		List<BrokerDataSnowball> brokerDataSnowballs = brokerSnowBallUtil.mapDataSnowBall(snowBallPojos);
		
		if(!brokerDataSnowballs.isEmpty()) {
			
			BrokerDataSnowball brokerDataSnowball = (BrokerDataSnowball) genericCustomPersistance.findById(BrokerDataSnowball.class, brokerDataSnowballs.get(0).getId());
			
			if(brokerDataSnowball != null)
				throw new BusinessException("First id of statement is registed, statement process cancelled");
		}
		
		genericCustomPersistance.save(brokerDataSnowballs);
	}
	
	@SuppressWarnings("unchecked")
	public void assignSnowBallData() throws BusinessException {
		
		List<BrokerDataSnowball> brokerDataSnowballs = brokerDataSnowBallRepository.getDataPending();

		swapBuyAndComision(brokerDataSnowballs);
		
		for(BrokerDataSnowball brokerDataSnowball: brokerDataSnowballs) {
			
			Object entityToSave = null;
			String tableTrack = null;
			MovementMoneyPojo movementMoneyPojo = null;
			MovementIssuePojo movementIssuePojo = null;
			MovementsMoney movementsMoney = null;
			MovementsIssue movementsIssue = null;
			CatalogIssue catalogIssue = null;
			
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
				tableTrack = TABLE_NAME_MOVEMENTS_ISSUE;
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Comision por Compromiso de Inversión") ) {

				catalogIssue = catalogsRepository.getCatalogIssueSnowBall(brokerDataSnowball.getCompany());
				movementsIssue = movementsIssueRepository.getMovementsIssueByQuantityIssues(brokerDataSnowball.getTotalIssues(), catalogIssue.getId(), CatalogTypeMovementEnum.BUY.getId());
				
				movementsIssue.setPriceTotal(movementsIssue.getPriceTotal().add(brokerDataSnowball.getBalanceExit()));
				movementsIssue.setComisionTotal(brokerDataSnowball.getBalanceExit());
				genericCustomPersistance.update(movementsIssue);
				
				updateSnowBallTrack(brokerDataSnowball, TABLE_NAME_MOVEMENTS_ISSUE, movementsIssue.getId());
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Pago de comision por compra de acciones en Snowball Market")) {

				catalogIssue = catalogsRepository.getCatalogIssueSnowBall(brokerDataSnowball.getCompany());
				movementsIssue = movementsIssueRepository.getMovementsIssueByQuantityIssues(brokerDataSnowball.getTotalIssues(), catalogIssue.getId(), CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY.getId());
				
				movementsIssue.setPriceTotal(movementsIssue.getPriceTotal().add(brokerDataSnowball.getBalanceExit()));
				movementsIssue.setComisionTotal(brokerDataSnowball.getBalanceExit());
				genericCustomPersistance.update(movementsIssue);
				
				updateSnowBallTrack(brokerDataSnowball, TABLE_NAME_MOVEMENTS_ISSUE, movementsIssue.getId());
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Pago de Rendimiento Mensual") ||
					brokerDataSnowball.getMovementDescription().equals("Bono por Dividendo") ||
					brokerDataSnowball.getMovementDescription().equals("Bono por Rendimiento") ||
					brokerDataSnowball.getMovementDescription().equals("Pago de Comisión Variable") ||
					brokerDataSnowball.getMovementDescription().equals("Pago de RODI")) {
				
				catalogIssue = catalogsRepository.getCatalogIssueSnowBall(brokerDataSnowball.getCompany());
				
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
				tableTrack = TABLE_NAME_MOVEMENTS_ISSUE;
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Devolución de pago de compra de acciones en Mercado Secundario por oferta no aceptada por parte del dueño de la ODI") ||
					brokerDataSnowball.getMovementDescription().equals("Ajuste por transacción errónea en Snowball Market no se recibió 1 ODI") ||
					brokerDataSnowball.getMovementDescription().equals("Devolución de pago de compra de acciones en Snowball Market por oferta no aceptada por parte del dueño de la ODI") ||
					brokerDataSnowball.getMovementDescription().equals("Devolución de comision de compra de acciones en Snowball Market por oferta no aceptada por parte del dueño de la ODI")) {
				
				catalogIssue = catalogsRepository.getCatalogIssueSnowBall(brokerDataSnowball.getCompany());
				movementsIssue = movementsIssueRepository.getMovementsIssueByPriceTotal(brokerDataSnowball.getBalanceEntry(), catalogIssue.getId(), CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY.getId());
				
				if (movementsIssue == null)
					throw new BusinessException("Issue for redeem not found, verify. company: " + brokerDataSnowball.getCompany() + " price: " + brokerDataSnowball.getBalanceEntry());
				
				movementsIssue.setIdTypeMovement(CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY_CANCELLED.getId());
				genericCustomPersistance.update(movementsIssue);
				
				updateSnowBallTrack(brokerDataSnowball, TABLE_NAME_MOVEMENTS_ISSUE, movementsIssue.getId());
			}
			else
				throw new BusinessException("Option of movement type not implemented. option: " + brokerDataSnowball.getMovementDescription());
			
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
