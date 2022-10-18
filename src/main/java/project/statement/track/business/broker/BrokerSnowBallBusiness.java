package project.statement.track.business.broker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import project.statement.track.beans.entity.BrokerDataSnowball;
import project.statement.track.beans.entity.CatalogIssue;
import project.statement.track.beans.entity.MovementsIssue;
import project.statement.track.beans.entity.MovementsMoney;
import project.statement.track.pojos.BrokerSnowBallPojo;
import project.statement.track.pojos.entity.MovementIssuePojo;
import project.statement.track.pojos.entity.MovementMoneyPojo;
import project.statement.track.repository.BrokerDataSnowBallRepository;
import project.statement.track.repository.CatalogsRepository;
import project.statement.track.repository.MovementsIssueRepository;
import project.statement.track.utils.BrokerSnowBallUtil;
import project.statement.track.utils.BuildPojoToEntityUtil;
import project.statement.track.vo.catalogs.CatalogBrokerAccountEnum;
import project.statement.track.vo.catalogs.CatalogIssueEnum;
import project.statement.track.vo.catalogs.CatalogTypeMovementEnum;
import project.statement.track.vo.catalogs.CatalogTypeTransactionEnum;

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
	
	private Integer getIssueId(String company) throws BusinessException {
		
		CatalogIssue catalogIssue = catalogsRepository.getCatalogIssueSnowBall(company);
		
		if (catalogIssue == null)
			throw new BusinessException("Error company " + company + " not found on mapping");
		else
			return catalogIssue.getId();
	}
	
	private void swapBuyAndComision(List<BrokerDataSnowball> brokerDataSnowballs) {
		
		for(int indexBuy = 0; indexBuy < brokerDataSnowballs.size(); indexBuy++) {
			
			if (brokerDataSnowballs.get(indexBuy).getMovementDescription().equals("Compromiso de Inversión")) {
				
				for(int indexCompromise = 0; indexCompromise < brokerDataSnowballs.size(); indexCompromise++) {
					
					if (brokerDataSnowballs.get(indexCompromise).getMovementDescription().equals("Comision por Compromiso de Inversión") &&
						indexCompromise < indexBuy && 
						brokerDataSnowballs.get(indexCompromise).getDateTransaction().compareTo(brokerDataSnowballs.get(indexBuy).getDateTransaction()) == 0 &&
						brokerDataSnowballs.get(indexCompromise).getCompany().equals(brokerDataSnowballs.get(indexBuy).getCompany()) &&
						brokerDataSnowballs.get(indexCompromise).getTotalIssues() == brokerDataSnowballs.get(indexBuy).getTotalIssues() )
							Collections.swap(brokerDataSnowballs, indexBuy, indexCompromise);
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void storeDataSnowBall(List<BrokerSnowBallPojo> snowBallPojos) throws BusinessException {
		
		List<BrokerDataSnowball> brokerDataSnowballs = brokerSnowBallUtil.mapDataSnowBall(snowBallPojos);
		
		if(brokerDataSnowballs.size() > 0) {
			
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
			
			System.out.println("test broker snowball " + brokerDataSnowball.toString());
		}
		
		for(BrokerDataSnowball brokerDataSnowball: brokerDataSnowballs) {
			
			Object entityToSave = null;
			String table_track = null;
			MovementMoneyPojo movementMoneyPojo = null;
			MovementIssuePojo movementIssuePojo = null;
			MovementsMoney movementsMoney = null;
			MovementsIssue movementsIssue = null;
			CatalogIssue catalogIssue = null;
			
			if (brokerDataSnowball.getMovementDescription().equals("Deposito")) {
				
				movementMoneyPojo = new MovementMoneyPojo();
				movementMoneyPojo.setAmount(brokerDataSnowball.getBalanceEntry());
				movementMoneyPojo.setAmountMxn(brokerDataSnowball.getBalanceEntry());
				movementMoneyPojo.setDateTransactionMillis(brokerDataSnowball.getDateTransaction() != null ? brokerDataSnowball.getDateTransaction().getTime() : null);
				movementMoneyPojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
				movementMoneyPojo.setIdTypeTransaction(CatalogTypeTransactionEnum.DEPOSIT.getId());
				
				movementsMoney =  buildPojoToEntityUtil.generateMovementsMoneyEntity(null, movementMoneyPojo);
				entityToSave = movementsMoney;
				table_track = "movements_money";
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Compromiso de Inversión")) {
				
				movementIssuePojo = new MovementIssuePojo();
				movementIssuePojo.setDateTransaction(brokerDataSnowball.getDateTransaction());
				movementIssuePojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
				movementIssuePojo.setIdIssue(getIssueId(brokerDataSnowball.getCompany()));
				movementIssuePojo.setIdTypeMovement(CatalogTypeMovementEnum.BUY.getId());
				movementIssuePojo.setQuantityIssues(brokerDataSnowball.getTotalIssues());
				movementIssuePojo.setPriceTotal(brokerDataSnowball.getBalanceExit());
				movementIssuePojo.setPriceIssueUnity(brokerDataSnowball.getBalanceExit().divide(new BigDecimal(brokerDataSnowball.getTotalIssues()), 2, RoundingMode.HALF_UP));
				
				movementsIssue = buildPojoToEntityUtil.generateMovementsIssueEntity(null, movementIssuePojo);
				entityToSave = movementsIssue;
				table_track = "movements_issue";
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Comision por Compromiso de Inversión")) {

				catalogIssue = catalogsRepository.getCatalogIssueSnowBall(brokerDataSnowball.getCompany());
				movementsIssue = movementsIssueRepository.getMovementsIssueByQuantityIssues(brokerDataSnowball.getTotalIssues(), catalogIssue.getId(), CatalogTypeMovementEnum.BUY.getId());
				
				movementsIssue.setPriceTotal(movementsIssue.getPriceTotal().add(brokerDataSnowball.getBalanceExit()));
				movementsIssue.setComisionTotal(brokerDataSnowball.getBalanceExit());
				genericCustomPersistance.update(movementsIssue);
				
				brokerDataSnowball.setTrackTable("movements_issue");
				brokerDataSnowball.setTrackTableId(movementsIssue.getId() + "");
				genericCustomPersistance.update(brokerDataSnowball);
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Pago de Rendimiento Mensual") ||
					brokerDataSnowball.getMovementDescription().equals("Bono por Dividendo") ||
					brokerDataSnowball.getMovementDescription().equals("Bono por Rendimiento") ||
					brokerDataSnowball.getMovementDescription().equals("Pago de Comisión Variable")) {
				
				catalogIssue = catalogsRepository.getCatalogIssueSnowBall(brokerDataSnowball.getCompany());
				
				movementMoneyPojo = new MovementMoneyPojo();
				movementMoneyPojo.setAmount(brokerDataSnowball.getBalanceEntry());
				movementMoneyPojo.setAmountMxn(brokerDataSnowball.getBalanceEntry());
				movementMoneyPojo.setDateTransactionMillis(brokerDataSnowball.getDateTransaction() != null ? brokerDataSnowball.getDateTransaction().getTime() : null);
				movementMoneyPojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
				movementMoneyPojo.setIdTypeTransaction(CatalogTypeTransactionEnum.DIVIDEND.getId());
				movementMoneyPojo.setIdIssue(catalogIssue.getId());
				
				movementsMoney =  buildPojoToEntityUtil.generateMovementsMoneyEntity(null, movementMoneyPojo);
				entityToSave = movementsMoney;
				table_track = "movements_money";
			}
			else if (brokerDataSnowball.getMovementDescription().equals("Pago de compra de acciones en Mercado Secundario") ||
					brokerDataSnowball.getMovementDescription().equals("Pago de compra de acciones en Snowball Market") ||
					brokerDataSnowball.getMovementDescription().equals("Pago de comision por compra de acciones en Snowball Market")) {
				System.out.println("test BUY_MARKET_SECUNDARY " + brokerDataSnowball.getBalanceExit());
				movementIssuePojo = new MovementIssuePojo();
				movementIssuePojo.setDateTransaction(brokerDataSnowball.getDateTransaction());
				movementIssuePojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
				movementIssuePojo.setIdIssue(getIssueId(brokerDataSnowball.getCompany()));
				movementIssuePojo.setIdTypeMovement(CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY.getId());
				movementIssuePojo.setQuantityIssues(brokerDataSnowball.getTotalIssues());
				movementIssuePojo.setPriceTotal(brokerDataSnowball.getBalanceExit());
				movementIssuePojo.setPriceIssueUnity(brokerDataSnowball.getBalanceExit().divide(new BigDecimal(brokerDataSnowball.getTotalIssues()), 2, RoundingMode.HALF_UP));
				
				movementsIssue = buildPojoToEntityUtil.generateMovementsIssueEntity(null, movementIssuePojo);
				entityToSave = movementsIssue;
				table_track = "movements_issue";
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
				
				brokerDataSnowball.setTrackTable("movements_issue");
				brokerDataSnowball.setTrackTableId(movementsIssue.getId() + "");
				genericCustomPersistance.update(brokerDataSnowball);
			}
			else
				throw new BusinessException("Option of movement type not implemented. option: " + brokerDataSnowball.getMovementDescription());
			
			/*switch (brokerDataSnowball.getMovementDescription()) {
			case "Deposito":
				movementMoneyPojo = new MovementMoneyPojo();
				movementMoneyPojo.setAmount(brokerDataSnowball.getBalanceEntry());
				movementMoneyPojo.setAmountMxn(brokerDataSnowball.getBalanceEntry());
				movementMoneyPojo.setDateTransactionMillis(brokerDataSnowball.getDateTransaction() != null ? brokerDataSnowball.getDateTransaction().getTime() : null);
				movementMoneyPojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
				movementMoneyPojo.setIdTypeTransaction(CatalogTypeTransactionEnum.DEPOSIT.getId());
				
				movementsMoney =  buildPojoToEntityUtil.generateMovementsMoneyEntity(null, movementMoneyPojo);
				entityToSave = movementsMoney;
				table_track = "movements_money";
				break;
			case "Compromiso de Inversión":
				movementIssuePojo = new MovementIssuePojo();
				movementIssuePojo.setDateTransaction(brokerDataSnowball.getDateTransaction());
				movementIssuePojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
				movementIssuePojo.setIdIssue(getIssueId(brokerDataSnowball.getCompany()));
				movementIssuePojo.setIdTypeMovement(CatalogTypeMovementEnum.BUY.getId());
				movementIssuePojo.setQuantityIssues(brokerDataSnowball.getTotalIssues());
				movementIssuePojo.setPriceTotal(brokerDataSnowball.getBalanceExit());
				movementIssuePojo.setPriceIssueUnity(brokerDataSnowball.getBalanceExit().divide(new BigDecimal(brokerDataSnowball.getTotalIssues()), 2, RoundingMode.HALF_UP));
				
				movementsIssue = buildPojoToEntityUtil.generateMovementsIssueEntity(null, movementIssuePojo);
				entityToSave = movementsIssue;
				table_track = "movements_issue";
				break;
			case "Pago de Rendimiento Mensual":
				catalogIssue = catalogsRepository.getCatalogIssueSnowBall(brokerDataSnowball.getCompany());
				
				movementMoneyPojo = new MovementMoneyPojo();
				movementMoneyPojo.setAmount(brokerDataSnowball.getBalanceEntry());
				movementMoneyPojo.setAmountMxn(brokerDataSnowball.getBalanceEntry());
				movementMoneyPojo.setDateTransactionMillis(brokerDataSnowball.getDateTransaction() != null ? brokerDataSnowball.getDateTransaction().getTime() : null);
				movementMoneyPojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
				movementMoneyPojo.setIdTypeTransaction(CatalogTypeTransactionEnum.DIVIDEND.getId());
				movementMoneyPojo.setIdIssue(catalogIssue.getId());
				
				movementsMoney =  buildPojoToEntityUtil.generateMovementsMoneyEntity(null, movementMoneyPojo);
				entityToSave = movementsMoney;
				table_track = "movements_money";
				break;
			case "Bono por Dividendo":
				catalogIssue = catalogsRepository.getCatalogIssueSnowBall(brokerDataSnowball.getCompany());
				
				movementMoneyPojo = new MovementMoneyPojo();
				movementMoneyPojo.setAmount(brokerDataSnowball.getBalanceEntry());
				movementMoneyPojo.setAmountMxn(brokerDataSnowball.getBalanceEntry());
				movementMoneyPojo.setDateTransactionMillis(brokerDataSnowball.getDateTransaction() != null ? brokerDataSnowball.getDateTransaction().getTime() : null);
				movementMoneyPojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
				movementMoneyPojo.setIdTypeTransaction(CatalogTypeTransactionEnum.DIVIDEND.getId());
				movementMoneyPojo.setIdIssue(catalogIssue.getId());
				
				movementsMoney =  buildPojoToEntityUtil.generateMovementsMoneyEntity(null, movementMoneyPojo);
				entityToSave = movementsMoney;
				table_track = "movements_money";
				break;
			case "Pago de compra de acciones en Mercado Secundario":
				movementIssuePojo = new MovementIssuePojo();
				movementIssuePojo.setDateTransaction(brokerDataSnowball.getDateTransaction());
				movementIssuePojo.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getId());
				movementIssuePojo.setIdIssue(getIssueId(brokerDataSnowball.getCompany()));
				movementIssuePojo.setIdTypeMovement(CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY.getId());
				movementIssuePojo.setQuantityIssues(brokerDataSnowball.getTotalIssues());
				movementIssuePojo.setPriceTotal(brokerDataSnowball.getBalanceExit());
				movementIssuePojo.setPriceIssueUnity(brokerDataSnowball.getBalanceExit().divide(new BigDecimal(brokerDataSnowball.getTotalIssues()), 2, RoundingMode.HALF_UP));
				
				movementsIssue = buildPojoToEntityUtil.generateMovementsIssueEntity(null, movementIssuePojo);
				entityToSave = movementsIssue;
				table_track = "movements_issue";
				break;
			case "Devolución de pago de compra de acciones en Mercado Secundario por oferta no aceptada por parte del dueño de la ODI":
				//genericCustomPersistance.flush();
				catalogIssue = catalogsRepository.getCatalogIssueSnowBall(brokerDataSnowball.getCompany());
				movementsIssue = movementsIssueRepository.getMovementsIssueByPriceTotal(brokerDataSnowball.getBalanceEntry(), catalogIssue.getId());
				
				if (movementsIssue == null)
					throw new BusinessException("Issue for redeem not found, verify. company: " + brokerDataSnowball.getCompany() + " price: " + brokerDataSnowball.getBalanceEntry());
				
				movementsIssue.setIdTypeMovement(CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY_CANCELLED.getId());
				genericCustomPersistance.update(movementsIssue);
				break;
			case "Ajuste por transacción errónea en Snowball Market no se recibió 1 ODI":
				//genericCustomPersistance.flush();
				catalogIssue = catalogsRepository.getCatalogIssueSnowBall(brokerDataSnowball.getCompany());
				movementsIssue = movementsIssueRepository.getMovementsIssueByPriceTotal(brokerDataSnowball.getBalanceEntry(), catalogIssue.getId());
				
				if (movementsIssue == null)
					throw new BusinessException("Issue for redeem not found, verify. company: " + brokerDataSnowball.getCompany() + " price: " + brokerDataSnowball.getBalanceEntry());
				
				movementsIssue.setIdTypeMovement(CatalogTypeMovementEnum.BUY_MARKET_SECUNDARY_CANCELLED.getId());
				genericCustomPersistance.update(movementsIssue);
				break;
			default:
				throw new BusinessException("Option of movement type not implemented. option: " + brokerDataSnowball.getMovementDescription());
			}*/
			
			if (entityToSave != null) {
				
				genericCustomPersistance.save(entityToSave);
				
				Method methodId;
				try {
					
					methodId = entityToSave.getClass().getDeclaredMethod("getId");
					brokerDataSnowball.setTrackTable(table_track);
					brokerDataSnowball.setTrackTableId(methodId.invoke(entityToSave) + "");
					genericCustomPersistance.update(brokerDataSnowball);
					
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException |IllegalArgumentException | InvocationTargetException e) {
					throw new BusinessException("Error getting id of table track", e);
				}
			}
		}
	}
	
	@Transactional
	public void executeStatementSnowBall(String textHtml) throws BusinessException {
		
		List<BrokerSnowBallPojo> resultList = brokerSnowBallUtil.getDataSnowBallFromText(textHtml);
		storeDataSnowBall(resultList);
	}
	
	@Transactional
	public void executeAssignSnowBallData() throws BusinessException {
		
		assignSnowBallData();
	}
}
