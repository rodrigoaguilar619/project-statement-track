package project.afore.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.afore.track.app.beans.pojos.PeriodTabResumePojo;

@Getter @Setter
public class GetAforePeriodTabDataPojo {

	public List<PeriodTabResumePojo> periods = new ArrayList<>();
}
