package project.statement.track.app.beans.pojos.entity;

public class MovementMoneyResumePojo extends MovementMoneyPojo {

	private String brokerAccountDescription;
	
	private String typeTransactionDescription;
	
	private String issueDescription;

	public String getBrokerAccountDescription() {
		return brokerAccountDescription;
	}

	public void setBrokerAccountDescription(String brokerAccountDescription) {
		this.brokerAccountDescription = brokerAccountDescription;
	}

	public String getTypeTransactionDescription() {
		return typeTransactionDescription;
	}

	public void setTypeTransactionDescription(String typeTransactionDescription) {
		this.typeTransactionDescription = typeTransactionDescription;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}
	
	
}
