package project.statement.track.controller.file;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import project.statement.track.business.file.ReadFileSnowBallBusiness;
import project.statement.track.pojos.request.LoadFileBase64StatementRequestPojo;
import project.statement.track.pojos.request.LoadFileStatementRequestPojo;

@RestController
public class ReadFileStatementController {

	private static final Logger log = LoggerFactory.getLogger(ReadFileStatementController.class);
	
	@Autowired
	ReadFileSnowBallBusiness readFileSnowBallBusiness;
	
	@PostMapping(path = "/api/readStatement/readStatementSnowBallFileBase64", consumes = "application/json", produces = "application/json")
	public void readStatementSnowBallFileBase64(HttpServletResponse httpResponse, @RequestBody LoadFileBase64StatementRequestPojo requestPojo) throws IOException, BusinessException, ParseException {
		
			readFileSnowBallBusiness.executeRegisterIssueTransactionFromFileBase64(requestPojo);
	}
	
	@PostMapping(path = "/api/readStatement/readStatementSnowBall", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
	public void readStatementSnowBall(HttpServletResponse httpResponse, @ModelAttribute LoadFileStatementRequestPojo requestPojo) throws IOException, BusinessException, ParseException {
		
		readFileSnowBallBusiness.executeRegisterIssueTransactionFromFile(requestPojo);
	}
	
}
