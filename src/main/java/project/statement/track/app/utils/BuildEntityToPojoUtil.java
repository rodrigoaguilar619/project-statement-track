package project.statement.track.app.utils;

import lib.base.backend.utils.date.DateUtil;
import project.statement.track.app.beans.entity.BrokerAccountEntity;
import project.statement.track.app.beans.entity.CatalogIssueEntity;
import project.statement.track.app.beans.entity.MovementsMoneyEntity;
import project.statement.track.app.beans.pojos.entity.BrokerAccountResumePojo;
import project.statement.track.app.beans.pojos.entity.CatalogIssuePojo;
import project.statement.track.app.beans.pojos.entity.MovementMoneyPojo;
import project.statement.track.app.beans.pojos.entity.MovementMoneyResumePojo;

public class BuildEntityToPojoUtil {
	
	private DateUtil dateUtil = new DateUtil();

	public BrokerAccountResumePojo mapBrokerAccountResumePojo(BrokerAccountResumePojo brokerAccountResumePojo, BrokerAccountEntity brokerAccount) {
		
		if (brokerAccountResumePojo == null)
			brokerAccountResumePojo = new BrokerAccountResumePojo();
		
		brokerAccountResumePojo.setAccountDescription(brokerAccount.getDescription());
		brokerAccountResumePojo.setBrokerDescription(brokerAccount.getCatalogBroker().getDescription());
		brokerAccountResumePojo.setCutDay(brokerAccount.getCutDay());
		brokerAccountResumePojo.setDescription(brokerAccount.getDescription());
		brokerAccountResumePojo.setId(brokerAccount.getId());
		
		return brokerAccountResumePojo;
	}
	
	public MovementMoneyPojo mapMovementMoneyPojo(MovementMoneyPojo movementMoneyPojo, MovementsMoneyEntity movementMoney) {
		
		if (movementMoneyPojo == null)
			movementMoneyPojo = new MovementMoneyPojo();
		
		movementMoneyPojo.setAmount(movementMoney.getAmount());
		movementMoneyPojo.setAmountMxn(movementMoney.getAmountMxn());
		movementMoneyPojo.setDateTransactionMillis(dateUtil.getMillis(movementMoney.getDateTransaction()));
		movementMoneyPojo.setIdBrokerAccount(movementMoney.getIdBrokerAccount());
		movementMoneyPojo.setIdIssue(movementMoney.getIdIssue());
		movementMoneyPojo.setIdTypeTransaction(movementMoney.getIdTypeTransaction());
		
		return movementMoneyPojo;
	}
	
	public MovementMoneyResumePojo mapMovementMoneyResumePojo(MovementMoneyResumePojo movementMoneyResumePojo, MovementsMoneyEntity movementMoney) {
		
		movementMoneyResumePojo = (MovementMoneyResumePojo) mapMovementMoneyPojo(movementMoneyResumePojo, movementMoney);
		movementMoneyResumePojo.setIssueDescription(movementMoney.getCatalogIssue().getDescription());
		movementMoneyResumePojo.setTypeTransactionDescription(movementMoney.getCatalogTypeTransaction().getDescription());
		movementMoneyResumePojo.setBrokerAccountDescription(movementMoney.getBrokerAccount().getDescription());
		
		return movementMoneyResumePojo;
	}
	
	public CatalogIssuePojo mapCatalogIssuePojo(CatalogIssuePojo catalogIssuePojo, CatalogIssueEntity catalogIssue) {
		
		if (catalogIssuePojo == null)
			catalogIssuePojo = new CatalogIssuePojo();
		
		catalogIssuePojo.setDescription(catalogIssue.getDescription());
		catalogIssuePojo.setDescriptionCustom(catalogIssue.getDescriptionSnowball());
		catalogIssuePojo.setInitials(catalogIssue.getInitials());
		catalogIssuePojo.setId(catalogIssue.getId());
		
		return catalogIssuePojo;
	}
}
