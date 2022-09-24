package project.statement.track.business.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import project.statement.track.beans.entity.BrokerDataSnowball;
import project.statement.track.business.broker.BrokerSnowBallBusiness;
import project.statement.track.pojos.BrokerSnowBallPojo;
import project.statement.track.pojos.request.LoadFileStatementRequestPojo;
import project.statement.track.utils.BrokerSnowBallUtil;

@Component
public class ReadFileSnowBallBusiness {

	@SuppressWarnings("rawtypes")
	@Autowired
	GenericPersistence genericCustomPersistance;
	
	@Autowired
	BrokerSnowBallUtil brokerSnowBallUtil;
	
	@Autowired
	BrokerSnowBallBusiness brokerSnowBallBusiness;

	@Transactional(rollbackFor = Exception.class)
	public void executeRegisterIssueTransactionFromFile(LoadFileStatementRequestPojo requestPojo) throws IOException, BusinessException, ParseException {
		
		String textHtml = new String(Base64.getDecoder().decode(requestPojo.getFile()));
		String[] textHtmlSplit = textHtml.split("\n", 1);
		
		if(textHtmlSplit.length == 0 || !textHtml.split("\n", 1)[0].contains("<div class=\"row\">"))
			throw new BusinessException("File snowball statement incorrect");
		
		List<BrokerSnowBallPojo> resultList = brokerSnowBallUtil.getDataSnowBallFromText(textHtml);
		
		brokerSnowBallBusiness.storeDataSnowBall(resultList);
		brokerSnowBallBusiness.assignSnowBallData();
	}
}
