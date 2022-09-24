package project.statement.track.pojos.response;

import java.util.ArrayList;
import java.util.List;

import project.statement.track.pojos.entity.MovementMoneyPojo;
import project.statement.track.pojos.entity.MovementMoneyResumePojo;

public class GetAccountDividendsResponsePojo {

	List<MovementMoneyResumePojo> movementMoneyDividends = new ArrayList<>();

	public List<MovementMoneyResumePojo> getMovementMoneyDividends() {
		return movementMoneyDividends;
	}

	public void setMovementMoneyDividends(List<MovementMoneyResumePojo> movementMoneyDividends) {
		this.movementMoneyDividends = movementMoneyDividends;
	}
}
