package test.statement.track.modules.business.broker;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lib.base.backend.utils.ExecuteMethodUtil;
import lib.base.backend.utils.ReadFileUtil;
import project.statement.track.config.AppStatementTrackTest;
import project.statement.track.modules.business.broker.BrokerSnowBallBusiness;

@SpringBootTest(classes = AppStatementTrackTest.class)
class BrokerSnowBallBusinessTest {
	
	@Autowired
	BrokerSnowBallBusiness brokerSnowBallBusiness;
	
	@Autowired
	ReadFileUtil readFileUtil;
	
	@Test
	void storeStatementSnowBallTest() throws Throwable {
		
		ExecuteMethodUtil.execute("STORE STATEMENT SNOWBALL", () -> {

			brokerSnowBallBusiness.executeAssignSnowBallData();
			
			String path = "G:/work/configuration/projects/statement_track/estados_cuenta/2021_01.html";
			String fileContent = readFileUtil.readFile(path);
			
			brokerSnowBallBusiness.executeStatementSnowBall(fileContent);
			
			assertTrue(true);

		});
	}
	
	@Test
	void assignDataSnowBallTest() throws Throwable {
		
		ExecuteMethodUtil.execute("ASSIGN DATA SNOWBALL", () -> {

			brokerSnowBallBusiness.executeAssignSnowBallData();

			assertTrue(true);
		});
	}
}
