package project.statement.track.app.beans.pojos.petition.request;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetAccountResumeRequestPojo {

	private Integer idBrokerAccount;
	
	Map<String, String> filters;
}
