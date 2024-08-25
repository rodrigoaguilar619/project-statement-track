package project.statement.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetBADateStatementsDataPojo {
	
	private List<StatementYearPojo> years = new ArrayList<>();

	@Getter @Setter
	public class StatementYearPojo {
		
		private Integer year;
		
		private List<StatementMonthPojo> months = new ArrayList<>();
	}
	
	@Getter @Setter
	public class StatementMonthPojo {
		
		private Integer id;
		
		private String description;
	}
}
