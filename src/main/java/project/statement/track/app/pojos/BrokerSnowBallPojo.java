package project.statement.track.app.pojos;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BrokerSnowBallPojo {

	private String date;
	
	private String hour;
	
	private String previousBalance;
	
	private String actualBalance;
	
	private String movementDescription;
	
	private String company;
	
	private String totalIssues;
	
	private String entry;
	
	private String exit;
	
	private String typePayment;
	
	private String status;
	
	private String reference;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(String previousBalance) {
		this.previousBalance = previousBalance;
	}

	public String getActualBalance() {
		return actualBalance;
	}

	public void setActualBalance(String actualBalance) {
		this.actualBalance = actualBalance;
	}

	public String getMovementDescription() {
		return movementDescription;
	}

	public void setMovementDescription(String movementDescription) {
		this.movementDescription = movementDescription;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTotalIssues() {
		return totalIssues;
	}

	public void setTotalIssues(String totalIssues) {
		this.totalIssues = totalIssues;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getExit() {
		return exit;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

	public String getTypePayment() {
		return typePayment;
	}

	public void setTypePayment(String typePayment) {
		this.typePayment = typePayment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public String toString()
	{
	  return ToStringBuilder.reflectionToString(this);
	}
}