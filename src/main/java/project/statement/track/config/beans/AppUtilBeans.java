package project.statement.track.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import project.statement.track.app.utils.AccountUtil;
import project.statement.track.app.utils.BrokerSnowBallUtil;
import project.statement.track.app.utils.BuildEntityToPojoUtil;
import project.statement.track.app.utils.BuildPojoToEntityUtil;
import project.statement.track.app.utils.WebScrapUtil;

@Configuration
public class AppUtilBeans {

	@Bean
	public WebScrapUtil generateWebScrapUtil() {
		return new WebScrapUtil();
	}
	
	@Bean
	public BrokerSnowBallUtil generateBrokerSnowBallUtil() {
		return new BrokerSnowBallUtil();
	}
	
	@Bean
	public BuildPojoToEntityUtil generateBuildPojoToEntityUtil() {
		return new BuildPojoToEntityUtil();
	}
	
	@Bean
	public BuildEntityToPojoUtil buildEntityToPojoUtil() {
		return new BuildEntityToPojoUtil();
	}
	
	@Bean
	public AccountUtil buildAccountUtil() {
		return new AccountUtil();
	}
}
