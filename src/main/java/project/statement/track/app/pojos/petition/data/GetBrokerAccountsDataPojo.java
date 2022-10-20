package project.statement.track.app.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import project.statement.track.app.pojos.entity.BrokerAccountResumePojo;

public class GetBrokerAccountsDataPojo {

	 List<BrokerAccountResumePojo> brokerAccounts = new ArrayList<>();

	public List<BrokerAccountResumePojo> getBrokerAccounts() {
		return brokerAccounts;
	}

	public void setBrokerAccounts(List<BrokerAccountResumePojo> brokerAccounts) {
		this.brokerAccounts = brokerAccounts;
	}
}
