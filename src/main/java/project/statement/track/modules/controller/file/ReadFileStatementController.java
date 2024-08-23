package project.statement.track.modules.controller.file;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lib.base.backend.exception.data.BusinessException;
import project.statement.track.app.beans.pojos.petition.request.LoadFileBase64StatementRequestPojo;
import project.statement.track.app.beans.pojos.petition.request.LoadFileStatementRequestPojo;
import project.statement.track.modules.business.file.ReadFileSnowBallBusiness;

@RestController
public class ReadFileStatementController {
	
	@Autowired
	ReadFileSnowBallBusiness readFileSnowBallBusiness;
	
	@PostMapping(path = "/api/readStatement/readStatementSnowBallFileBase64", consumes = "application/json", produces = "application/json")
	public void readStatementSnowBallFileBase64(HttpServletResponse httpResponse, @RequestBody LoadFileBase64StatementRequestPojo requestPojo) throws BusinessException {
		
			readFileSnowBallBusiness.executeRegisterIssueTransactionFromFileBase64(requestPojo);
	}
	
	@PostMapping(path = "/api/readStatement/readStatementSnowBall", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
	public void readStatementSnowBall(HttpServletResponse httpResponse, @ModelAttribute LoadFileStatementRequestPojo requestPojo) throws BusinessException, IOException {
		
		readFileSnowBallBusiness.executeRegisterIssueTransactionFromFile(requestPojo);
	}
	
}
