package project.statement.track.app.beans.pojos.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovementMoneyResumePojo extends MovementMoneyPojo {

	private String brokerAccountDescription;
	
	private String typeTransactionDescription;
	
	private String issueDescription;
}
