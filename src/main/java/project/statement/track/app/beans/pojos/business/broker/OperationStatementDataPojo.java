package project.statement.track.app.beans.pojos.business.broker;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import project.statement.track.app.vo.enums.DefinitionTypeOperationEnum;

@Getter @Setter
public class OperationStatementDataPojo implements Comparable<OperationStatementDataPojo> {

	private Long date;
	
	private String dateFormated;
	
	private DefinitionTypeOperationEnum definitionTypeOperationEnum; 
	
	private Integer typeOperationId;
	
	private String typeOperationDescription;
	
	private BigDecimal amount = new BigDecimal(0);
	
	private BigDecimal charge = new BigDecimal(0);
	
	private BigDecimal income = new BigDecimal(0);
	
	private BigDecimal balance = new BigDecimal(0);

	@Override
	public int compareTo(OperationStatementDataPojo operationStatementDataPojo) {
		return this.getDate().compareTo(operationStatementDataPojo.getDate());
	}
}
