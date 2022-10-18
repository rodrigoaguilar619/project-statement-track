package project.statement.track.pojos.request;

public class GetAccountDividendsRequestPojo {

	private Integer idBrokerAccount;
	
	private Integer idIssue;

	public Integer getIdBrokerAccount() {
		return idBrokerAccount;
	}

	public void setIdBrokerAccount(Integer idBrokerAccount) {
		this.idBrokerAccount = idBrokerAccount;
	}

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}
}
