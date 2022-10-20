package test.statement.track.utils;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;

import project.statement.track.app.pojos.BrokerSnowBallPojo;
import project.statement.track.app.utils.BrokerSnowBallUtil;
import project.statement.track.app.utils.ReadFileUtil;

//@SpringBootTest(classes = ApplicationDisablingServices.class)
@SpringBootConfiguration
public class BrokerSnowBallUtilTest {
	
	//@Autowired
	BrokerSnowBallUtil brokerSnowBallUtil = new BrokerSnowBallUtil();
	ReadFileUtil readFileUtil = new ReadFileUtil();
	
	private static final Logger log = LoggerFactory.getLogger(BrokerSnowBallUtilTest.class);

	//@Test
	void redFileHtmlTest() {
		
		try {
			
			log.info("--------- START TEST READ HTML FILE --------------");
			
			List<BrokerSnowBallPojo> resultList = brokerSnowBallUtil.getDataSnowBallFromFile("G:/work/configuration/projects/statement_track/estados_cuenta/2021_01.html");
			
			for(BrokerSnowBallPojo snowBallPojo: resultList)
				System.out.println(snowBallPojo.toString());
			 
			log.info("--------- FINISH TEST READ HTML FILE --------------");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Test
	void redTextHtmlTest() {
		
		try {
			
			log.info("--------- START TEST READ HTML TEXT --------------");
			
			String path = "G:/work/configuration/projects/statement_track/estados_cuenta/2021_01.html";
			String fileContent = readFileUtil.readFile(path);
			
			List<BrokerSnowBallPojo> resultList = brokerSnowBallUtil.getDataSnowBallFromText(fileContent);
			
			for(BrokerSnowBallPojo snowBallPojo: resultList)
				System.out.println(snowBallPojo.toString());
			
			log.info("--------- FINISH TEST READ HTML TEXT --------------");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
