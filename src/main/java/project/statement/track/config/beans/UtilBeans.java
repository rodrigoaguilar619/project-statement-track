package project.statement.track.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lib.base.backend.utils.CatalogUtil;
import lib.utils.backend.format.DateFormatUtil;
import project.statement.track.utils.AccountUtil;
import project.statement.track.utils.BrokerSnowBallUtil;
import project.statement.track.utils.BuildEntityToPojoUtil;
import project.statement.track.utils.BuildPojoToEntityUtil;
import project.statement.track.utils.WebScrapUtil;

@Configuration
public class UtilBeans {

	@Bean
	public DateFormatUtil generateDateFormatUtil() {
		return new DateFormatUtil();
	}
	
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
	public CatalogUtil buildCatalogUtil() {
		return new CatalogUtil();
	}
	
	@Bean
	public AccountUtil buildAccountUtil() {
		return new AccountUtil();
	}
}
