package project.statement.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.statement.track.app.beans.pojos.entity.BrokerAccountResumePojo;

@Getter @Setter
public class GetBrokerAccountsDataPojo {

	 List<BrokerAccountResumePojo> brokerAccounts = new ArrayList<>();
}
