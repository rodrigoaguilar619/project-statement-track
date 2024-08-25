package project.statement.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.statement.track.app.beans.pojos.entity.MovementMoneyResumePojo;

@Getter @Setter
public class GetAccountDividendsDataPojo {

	List<MovementMoneyResumePojo> movementMoneyDividends = new ArrayList<>();
}
