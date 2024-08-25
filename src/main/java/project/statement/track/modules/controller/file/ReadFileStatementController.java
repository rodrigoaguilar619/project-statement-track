package project.statement.track.modules.controller.file;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.RestUtil;
import project.statement.track.app.beans.pojos.petition.request.LoadFileBase64StatementRequestPojo;
import project.statement.track.app.beans.pojos.petition.request.LoadFileStatementRequestPojo;
import project.statement.track.app.vo.catalogs.CatalogsUri;
import project.statement.track.modules.business.file.ReadFileSnowBallBusiness;

@RestController
public class ReadFileStatementController {
	
	@Autowired
	ReadFileSnowBallBusiness readFileSnowBallBusiness;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_STATEMENT_READ_FILE_BASE64, consumes = "application/json", produces = "application/json")
	public ResponseEntity readStatementSnowBallFileBase64(@RequestBody LoadFileBase64StatementRequestPojo requestPojo) throws BusinessException {
		
			readFileSnowBallBusiness.executeRegisterIssueTransactionFromFileBase64(requestPojo);
			return new RestUtil().buildResponseSuccess("success", "File statement loaded");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = CatalogsUri.API_STATEMENT_READ_FILE_BYTES, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
	public ResponseEntity readStatementSnowBall(@ModelAttribute LoadFileStatementRequestPojo requestPojo) throws BusinessException, IOException {
		
		readFileSnowBallBusiness.executeRegisterIssueTransactionFromFile(requestPojo);
		return new RestUtil().buildResponseSuccess("success", "File statement loaded");
	}
	
}
