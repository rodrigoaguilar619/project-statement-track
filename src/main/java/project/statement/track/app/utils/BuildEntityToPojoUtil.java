package project.statement.track.app.utils;

import project.statement.track.app.beans.entity.BrokerAccount;
import project.statement.track.app.beans.entity.MovementsMoney;
import project.statement.track.app.pojos.entity.BrokerAccountResumePojo;
import project.statement.track.app.pojos.entity.MovementMoneyPojo;
import project.statement.track.app.pojos.entity.MovementMoneyResumePojo;

public class BuildEntityToPojoUtil {

	public BrokerAccountResumePojo mapBrokerAccountResumePojo(BrokerAccountResumePojo brokerAccountResumePojo, BrokerAccount brokerAccount) {
		
		if (brokerAccountResumePojo == null)
			brokerAccountResumePojo = new BrokerAccountResumePojo();
		
		brokerAccountResumePojo.setAccountDescription(brokerAccount.getDescription());
		brokerAccountResumePojo.setBrokerDescription(brokerAccount.getCatalogBroker().getDescription());
		brokerAccountResumePojo.setCutDay(brokerAccount.getCutDay());
		brokerAccountResumePojo.setDescription(brokerAccount.getDescription());
		brokerAccountResumePojo.setId(brokerAccount.getId());
		
		return brokerAccountResumePojo;
	}
	
	public MovementMoneyPojo mapMovementMoneyPojo(MovementMoneyPojo movementMoneyPojo, MovementsMoney movementMoney) {
		
		if (movementMoneyPojo == null)
			movementMoneyPojo = new MovementMoneyPojo();
		
		movementMoneyPojo.setAmount(movementMoney.getAmount());
		movementMoneyPojo.setAmountMxn(movementMoney.getAmountMxn());
		movementMoneyPojo.setDateTransactionMillis(movementMoney.getDateTransaction() != null ? movementMoney.getDateTransaction().getTime() : null);
		movementMoneyPojo.setIdBrokerAccount(movementMoney.getIdBrokerAccount());
		movementMoneyPojo.setIdIssue(movementMoney.getIdIssue());
		movementMoneyPojo.setIdTypeTransaction(movementMoney.getIdTypeTransaction());
		
		return movementMoneyPojo;
	}
	
	public MovementMoneyResumePojo mapMovementMoneyResumePojo(MovementMoneyResumePojo movementMoneyResumePojo, MovementsMoney movementMoney) {
		
		movementMoneyResumePojo = (MovementMoneyResumePojo) mapMovementMoneyPojo(new MovementMoneyResumePojo(), movementMoney);
		movementMoneyResumePojo.setIssueDescription(movementMoney.getCatalogIssue().getDescription());
		movementMoneyResumePojo.setTypeTransactionDescription(movementMoney.getCatalogTypeTransaction().getDescription());
		movementMoneyResumePojo.setBrokerAccountDescription(movementMoney.getBrokerAccount().getDescription());
		
		return movementMoneyResumePojo;
	}
}
