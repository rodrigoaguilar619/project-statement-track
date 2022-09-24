package project.statement.track.pojos.request;

public class AccountStatementRequestPojo {

	private Integer idAccountBroker;
	
	private Integer year;
	
	private Integer month;

	public Integer getIdAccountBroker() {
		return idAccountBroker;
	}

	public void setIdAccountBroker(Integer idAccountBroker) {
		this.idAccountBroker = idAccountBroker;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
}
