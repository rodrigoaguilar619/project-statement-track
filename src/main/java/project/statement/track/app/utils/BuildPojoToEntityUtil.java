package project.statement.track.app.utils;

import java.util.Date;

import project.statement.track.app.beans.entity.CatalogIssue;
import project.statement.track.app.beans.entity.MovementsIssue;
import project.statement.track.app.beans.entity.MovementsMoney;
import project.statement.track.app.beans.pojos.entity.CatalogIssuePojo;
import project.statement.track.app.beans.pojos.entity.MovementIssuePojo;
import project.statement.track.app.beans.pojos.entity.MovementMoneyPojo;

public class BuildPojoToEntityUtil {

	public MovementsMoney generateMovementsMoneyEntity(MovementsMoney movementsMoney, MovementMoneyPojo movementMoneyPojo) {
		
		if (movementsMoney == null)
			movementsMoney = new MovementsMoney();
		
		movementsMoney.setAmount(movementMoneyPojo.getAmount());
		movementsMoney.setAmountMxn(movementMoneyPojo.getAmountMxn());
		movementsMoney.setDateTransaction(movementMoneyPojo.getDateTransactionMillis() != null ? new Date(movementMoneyPojo.getDateTransactionMillis()) : null);
		movementsMoney.setIdBrokerAccount(movementMoneyPojo.getIdBrokerAccount());
		movementsMoney.setIdTypeTransaction(movementMoneyPojo.getIdTypeTransaction());
		movementsMoney.setIdIssue(movementMoneyPojo.getIdIssue());
		
		return movementsMoney;
	}
	
	public MovementsIssue generateMovementsIssueEntity(MovementsIssue movementsIssue, MovementIssuePojo movementIssuePojo) {
		
		if (movementsIssue == null)
			movementsIssue = new MovementsIssue();
		
		movementsIssue.setDateTransaction(movementIssuePojo.getDateTransaction());
		movementsIssue.setIdBrokerAccount(movementIssuePojo.getIdBrokerAccount());
		movementsIssue.setIdIssue(movementIssuePojo.getIdIssue());
		movementsIssue.setIdTypeMovement(movementIssuePojo.getIdTypeMovement());
		movementsIssue.setQuantityIssues(movementIssuePojo.getQuantityIssues());
		movementsIssue.setPriceTotal(movementIssuePojo.getPriceTotal());
		movementsIssue.setPriceIssueUnity(movementIssuePojo.getPriceIssueUnity());
		
		return movementsIssue;
	}
	
	public CatalogIssue generateCatalogIssueEntity(CatalogIssue catalogIssue, CatalogIssuePojo catalogIssuePojo) {
		
		if (catalogIssue == null)
			catalogIssue = new CatalogIssue();
		
		catalogIssue.setInitials(catalogIssuePojo.getInitials());
		catalogIssue.setDescription(catalogIssuePojo.getDescription());
		catalogIssue.setDescriptionSnowball(catalogIssuePojo.getDescriptionCustom());
		
		return catalogIssue;
	}
}
