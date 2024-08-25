package project.statement.track.app.beans.pojos.petition.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.statement.track.app.beans.pojos.business.broker.OperationStatementDataPojo;
import project.statement.track.app.beans.pojos.entity.BrokerAccountResumePojo;

@Getter @Setter
public class AccountStatementDataPojo {

	private BigDecimal previousBalance;
	
	private BigDecimal currentBalance;
	
	private Integer year;
	
	private Integer month;
	
	private BrokerAccountResumePojo brokerAccountResume;
	
	private List<OperationStatementDataPojo> operationsStatement = new ArrayList<>();
}
