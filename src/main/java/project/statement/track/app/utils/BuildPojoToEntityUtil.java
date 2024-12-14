package project.statement.track.app.utils;

import lib.base.backend.utils.date.DateUtil;
import project.statement.track.app.beans.entity.CatalogIssueEntity;
import project.statement.track.app.beans.entity.MovementsIssueEntity;
import project.statement.track.app.beans.entity.MovementsMoneyEntity;
import project.statement.track.app.beans.pojos.entity.CatalogIssuePojo;
import project.statement.track.app.beans.pojos.entity.MovementIssuePojo;
import project.statement.track.app.beans.pojos.entity.MovementMoneyPojo;

public class BuildPojoToEntityUtil {
	
	private DateUtil dateUtil = new DateUtil();

	public MovementsMoneyEntity generateMovementsMoneyEntity(MovementsMoneyEntity movementsMoney, MovementMoneyPojo movementMoneyPojo) {
		
		if (movementsMoney == null)
			movementsMoney = new MovementsMoneyEntity();
		
		movementsMoney.setAmount(movementMoneyPojo.getAmount());
		movementsMoney.setAmountMxn(movementMoneyPojo.getAmountMxn());
		movementsMoney.setDateTransaction(dateUtil.getLocalDateTime(movementMoneyPojo.getDateTransactionMillis()));
		movementsMoney.setIdBrokerAccount(movementMoneyPojo.getIdBrokerAccount());
		movementsMoney.setIdTypeTransaction(movementMoneyPojo.getIdTypeTransaction());
		movementsMoney.setIdIssue(movementMoneyPojo.getIdIssue());
		
		return movementsMoney;
	}
	
	public MovementsIssueEntity generateMovementsIssueEntity(MovementsIssueEntity movementsIssue, MovementIssuePojo movementIssuePojo) {
		
		if (movementsIssue == null)
			movementsIssue = new MovementsIssueEntity();
		
		movementsIssue.setDateTransaction(movementIssuePojo.getDateTransaction());
		movementsIssue.setIdBrokerAccount(movementIssuePojo.getIdBrokerAccount());
		movementsIssue.setIdIssue(movementIssuePojo.getIdIssue());
		movementsIssue.setIdTypeMovement(movementIssuePojo.getIdTypeMovement());
		movementsIssue.setQuantityIssues(movementIssuePojo.getQuantityIssues());
		movementsIssue.setPriceTotal(movementIssuePojo.getPriceTotal());
		movementsIssue.setPriceIssueUnity(movementIssuePojo.getPriceIssueUnity());
		
		return movementsIssue;
	}
	
	public CatalogIssueEntity generateCatalogIssueEntity(CatalogIssueEntity catalogIssue, CatalogIssuePojo catalogIssuePojo) {
		
		if (catalogIssue == null)
			catalogIssue = new CatalogIssueEntity();
		
		catalogIssue.setInitials(catalogIssuePojo.getInitials());
		catalogIssue.setDescription(catalogIssuePojo.getDescription());
		catalogIssue.setDescriptionSnowball(catalogIssuePojo.getDescriptionCustom());
		
		return catalogIssue;
	}
}
