package project.statement.track.modules.business.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.pojos.BrokerSnowBallPojo;
import project.statement.track.app.beans.pojos.petition.request.LoadFileBase64StatementRequestPojo;
import project.statement.track.app.beans.pojos.petition.request.LoadFileStatementRequestPojo;
import project.statement.track.app.vo.catalogs.CatalogsErrorMessage;
import project.statement.track.modules.business.MainBusiness;
import project.statement.track.modules.business.broker.BrokerSnowBallBusiness;

@RequiredArgsConstructor
@Component
public class ReadFileSnowBallBusiness extends MainBusiness {

	private final BrokerSnowBallBusiness brokerSnowBallBusiness;
	
	private void registerIssueTransaction(String fileBase64) throws BusinessException {
		
		String textHtml = new String(Base64.getDecoder().decode(fileBase64), StandardCharsets.UTF_8);
		String[] textHtmlSplit = textHtml.split("\n", 1);
		
		if(textHtmlSplit.length == 0 || !textHtml.split("\n", 1)[0].contains("<div class=\"row\">"))
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgStatementFileIncorrect());
		
		List<BrokerSnowBallPojo> resultList = brokerSnowBallUtil.getDataSnowBallFromText(textHtml);
		
		brokerSnowBallBusiness.storeDataSnowBall(resultList);
		brokerSnowBallBusiness.assignSnowBallData();
	}

	@Transactional(rollbackFor = Exception.class)
	public void executeRegisterIssueTransactionFromFileBase64(LoadFileBase64StatementRequestPojo requestPojo) throws BusinessException {
		
		registerIssueTransaction(requestPojo.getFile());
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void executeRegisterIssueTransactionFromFile(LoadFileStatementRequestPojo requestPojo) throws IOException, BusinessException {
		
		String fileBase64 = new String(Base64.getEncoder().encode(requestPojo.getFile().getBytes()), StandardCharsets.UTF_8);
		
		registerIssueTransaction(fileBase64);
	}
}
