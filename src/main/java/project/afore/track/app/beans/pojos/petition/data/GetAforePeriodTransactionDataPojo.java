package project.afore.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import project.afore.track.app.beans.pojos.PeriodResumePojo;
import project.afore.track.app.beans.pojos.PeriodTransactionPojo;

@Getter @Setter
public class GetAforePeriodTransactionDataPojo {

	private Map<String, PeriodResumePojo> periodQuarterResumes = new LinkedHashMap<String, PeriodResumePojo>();
	
	private Map<String, PeriodResumePojo> periodAllResumes = new LinkedHashMap<String, PeriodResumePojo>();
	
	private List<PeriodTransactionPojo> periodTransactions = new ArrayList<>();
}
