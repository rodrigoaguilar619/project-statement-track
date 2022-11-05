package project.statement.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import project.statement.track.app.beans.pojos.entity.MovementMoneyResumePojo;

public class GetAccountDividendsDataPojo {

	List<MovementMoneyResumePojo> movementMoneyDividends = new ArrayList<>();

	public List<MovementMoneyResumePojo> getMovementMoneyDividends() {
		return movementMoneyDividends;
	}

	public void setMovementMoneyDividends(List<MovementMoneyResumePojo> movementMoneyDividends) {
		this.movementMoneyDividends = movementMoneyDividends;
	}
}
