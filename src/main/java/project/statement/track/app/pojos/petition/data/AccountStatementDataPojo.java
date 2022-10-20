package project.statement.track.app.pojos.petition.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import project.statement.track.app.pojos.business.broker.OperationStatementDataPojo;
import project.statement.track.app.pojos.entity.BrokerAccountResumePojo;

public class AccountStatementDataPojo {

	private BigDecimal previousBalance;
	
	private BigDecimal currentBalance;
	
	private Integer year;
	
	private Integer month;
	
	private BrokerAccountResumePojo brokerAccountResume;
	
	private List<OperationStatementDataPojo> operationsStatement = new ArrayList<>();

	public BigDecimal getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(BigDecimal previousBalance) {
		this.previousBalance = previousBalance;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public List<OperationStatementDataPojo> getOperationsStatement() {
		return operationsStatement;
	}

	public void setOperationsStatement(List<OperationStatementDataPojo> operationsStatement) {
		this.operationsStatement = operationsStatement;
	}

	public BrokerAccountResumePojo getBrokerAccountResume() {
		return brokerAccountResume;
	}

	public void setBrokerAccountResume(BrokerAccountResumePojo brokerAccountResume) {
		this.brokerAccountResume = brokerAccountResume;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
}
