package project.statement.track.app.beans.pojos.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovementIssuePojo {

	private Date dateTransaction;

	private int idBrokerAccount;

	private Integer idIssue;

	private int idTypeMovement;

	private BigDecimal priceIssueUnity;
	
	private BigDecimal priceTotal;

	private Integer quantityIssues;
}
