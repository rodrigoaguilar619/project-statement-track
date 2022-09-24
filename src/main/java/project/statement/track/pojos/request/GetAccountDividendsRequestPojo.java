package project.statement.track.pojos.request;

public class GetAccountDividendsRequestPojo {

	private Integer idBrokerAccount;
	
	private String idIssue;

	public Integer getIdBrokerAccount() {
		return idBrokerAccount;
	}

	public void setIdBrokerAccount(Integer idBrokerAccount) {
		this.idBrokerAccount = idBrokerAccount;
	}

	public String getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(String idIssue) {
		this.idIssue = idIssue;
	}
}
