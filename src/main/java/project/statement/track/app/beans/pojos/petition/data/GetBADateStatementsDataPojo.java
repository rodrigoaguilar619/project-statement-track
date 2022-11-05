package project.statement.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

public class GetBADateStatementsDataPojo {
	
	private List<StatementYearPojo> years = new ArrayList<>();

	public List<StatementYearPojo> getYears() {
		return years;
	}

	public void setYears(List<StatementYearPojo> years) {
		this.years = years;
	}

	public class StatementYearPojo {
		
		private Integer year;
		
		private List<StatementMonthPojo> months = new ArrayList<>();

		public Integer getYear() {
			return year;
		}

		public void setYear(Integer year) {
			this.year = year;
		}

		public List<StatementMonthPojo> getMonths() {
			return months;
		}

		public void setMonths(List<StatementMonthPojo> months) {
			this.months = months;
		}
	}
	
	public class StatementMonthPojo {
		
		private Integer id;
		
		private String description;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
}
