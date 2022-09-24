package project.statement.track.pojos.response;

import java.util.ArrayList;
import java.util.List;

import project.statement.track.pojos.entity.BrokerAccountResumePojo;

public class GetBrokerAccountsResponsePojo {

	 List<BrokerAccountResumePojo> brokerAccounts = new ArrayList<>();

	public List<BrokerAccountResumePojo> getBrokerAccounts() {
		return brokerAccounts;
	}

	public void setBrokerAccounts(List<BrokerAccountResumePojo> brokerAccounts) {
		this.brokerAccounts = brokerAccounts;
	}
}
