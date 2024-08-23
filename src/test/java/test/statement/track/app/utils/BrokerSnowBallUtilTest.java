package test.statement.track.app.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lib.base.backend.utils.ExecuteMethodUtil;
import lib.base.backend.utils.FileUtil;
import project.statement.track.app.beans.pojos.BrokerSnowBallPojo;
import project.statement.track.app.utils.BrokerSnowBallUtil;
import test.statement.track.config.AppStatementTrackTest;

@SpringBootTest(classes = AppStatementTrackTest.class)
class BrokerSnowBallUtilTest {
	
	@Autowired
	BrokerSnowBallUtil brokerSnowBallUtil;
	
	FileUtil readFileUtil = new FileUtil();

	@Test
	void redFileHtmlTest() throws Throwable {
		
		ExecuteMethodUtil.execute("READ HTML FILE", () -> {

			String path = "G:/work/configuration/projects/statement_track/estados_cuenta/2021_01.html";
			String fileContent = readFileUtil.readFile(path);
			
			List<BrokerSnowBallPojo> resultList = brokerSnowBallUtil.getDataSnowBallFromText(fileContent);
			
			for(BrokerSnowBallPojo snowBallPojo: resultList)
				System.out.println(snowBallPojo.toString());
			
			assertTrue(true);

		});
	}
}
