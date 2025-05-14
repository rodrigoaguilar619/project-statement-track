package project.afore.track.app.beans.pojos;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PeriodTransactionPojo {

	private int idConcept;
	
	private int idSection;
	
	private String descriptionConcept;
	
	private String descriptionSection;
	
	private BigDecimal amount;
}
