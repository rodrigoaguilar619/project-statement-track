package project.statement.track.app.beans.pojos;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BrokerSnowBallPojo {

	private String date;
	
	private String hour;
	
	private String previousBalance;
	
	private String actualBalance;
	
	private String movementDescription;
	
	private String company;
	
	private String totalIssues;
	
	private String entry;
	
	private String exit;
	
	private String typePayment;
	
	private String status;
	
	private String reference;

	@Override
	public String toString()
	{
	  return ToStringBuilder.reflectionToString(this);
	}
}