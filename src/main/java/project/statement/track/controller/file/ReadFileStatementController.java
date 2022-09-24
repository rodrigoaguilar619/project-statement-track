package project.statement.track.controller.file;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import project.statement.track.business.file.ReadFileSnowBallBusiness;
import project.statement.track.pojos.request.LoadFileStatementRequestPojo;

@RestController
public class ReadFileStatementController {

	private static final Logger log = LoggerFactory.getLogger(ReadFileStatementController.class);
	
	@Autowired
	ReadFileSnowBallBusiness readFileSnowBallBusiness;
	
	@PostMapping(path = "/api/readStatement/readStatementSnowBall", consumes = "application/json", produces = "application/json")
	public void readStatementSnowBall(HttpServletResponse httpResponse, @RequestBody LoadFileStatementRequestPojo requestPojo) throws IOException {
		
		try {
			readFileSnowBallBusiness.executeRegisterIssueTransactionFromFile(requestPojo);
		}
		catch (Exception e) 
		{
			log.error("Error loading tranasctions from file base64", e);
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
