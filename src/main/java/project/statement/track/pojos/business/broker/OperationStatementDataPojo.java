package project.statement.track.pojos.business.broker;

import java.math.BigDecimal;

import project.statement.track.vo.catalogs.DefinitionTypeOperationEnum;

public class OperationStatementDataPojo implements Comparable<OperationStatementDataPojo> {

	private Long date;
	
	private String dateFormated;
	
	private DefinitionTypeOperationEnum definitionTypeOperationEnum; 
	
	private Integer typeOperationId;
	
	private String typeOperationDescription;
	
	private BigDecimal amount = new BigDecimal(0);
	
	private BigDecimal charge = new BigDecimal(0);
	
	private BigDecimal income = new BigDecimal(0);
	
	private BigDecimal balance = new BigDecimal(0);

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getDateFormated() {
		return dateFormated;
	}

	public void setDateFormated(String dateFormated) {
		this.dateFormated = dateFormated;
	}

	public Integer getTypeOperationId() {
		return typeOperationId;
	}

	public void setTypeOperationId(Integer typeOperationId) {
		this.typeOperationId = typeOperationId;
	}

	public String getTypeOperationDescription() {
		return typeOperationDescription;
	}

	public void setTypeOperationDescription(String typeOperationDescription) {
		this.typeOperationDescription = typeOperationDescription;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public DefinitionTypeOperationEnum getDefinitionTypeOperationEnum() {
		return definitionTypeOperationEnum;
	}

	public void setDefinitionTypeOperationEnum(DefinitionTypeOperationEnum definitionTypeOperationEnum) {
		this.definitionTypeOperationEnum = definitionTypeOperationEnum;
	}

	@Override
	public int compareTo(OperationStatementDataPojo operationStatementDataPojo) {
		return this.getDate().compareTo(operationStatementDataPojo.getDate());
	}
}
