package project.statement.track.app.beans.pojos.petition.request;

import java.util.Map;

public class GetAccountResumeRequestPojo {

	private Integer idBrokerAccount;
	
	Map<String, String> filters;

	public Integer getIdBrokerAccount() {
		return idBrokerAccount;
	}

	public void setIdBrokerAccount(Integer idBrokerAccount) {
		this.idBrokerAccount = idBrokerAccount;
	}

	public Map<String, String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}
}
