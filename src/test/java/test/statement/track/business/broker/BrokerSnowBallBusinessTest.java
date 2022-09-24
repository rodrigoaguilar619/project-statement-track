package test.statement.track.business.broker;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.statement.track.business.broker.BrokerSnowBallBusiness;
import project.statement.track.config.starter.ApplicationDisablingServices;
import project.statement.track.pojos.BrokerSnowBallPojo;
import project.statement.track.utils.BrokerSnowBallUtil;
import project.statement.track.utils.ReadFileUtil;

@SpringBootTest(classes = ApplicationDisablingServices.class)
public class BrokerSnowBallBusinessTest {
	
	@Autowired
	BrokerSnowBallBusiness brokerSnowBallBusiness = new BrokerSnowBallBusiness();
	
	ReadFileUtil readFileUtil = new ReadFileUtil();
	
	private static final Logger log = LoggerFactory.getLogger(BrokerSnowBallBusinessTest.class);
	
	//@Test
	void storeStatementSnowBallTest() {
		
		try {
			
			log.info("--------- START TEST STORE STATEMENT SNOWBALL --------------");
			
			String path = "G:/work/configuration/projects/statement_track/estados_cuenta/2021_01.html";
			String fileContent = readFileUtil.readFile(path);
			
			brokerSnowBallBusiness.executeStatementSnowBall(fileContent);
			
			log.info("--------- FINISH TEST STORE STATEMENT SNOWBALL --------------");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Test
	void assignDataSnowBallTest() {
		
		try {
			
			log.info("--------- START TEST ASSIGN DATA SNOWBALL --------------");
			
			brokerSnowBallBusiness.executeAssignSnowBallData();
			
			log.info("--------- FINISH TESTASSIGN DATAT SNOWBALL --------------");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
