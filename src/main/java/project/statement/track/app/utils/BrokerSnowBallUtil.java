package project.statement.track.app.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.utils.date.DateFormatUtil;
import project.statement.track.app.beans.entity.BrokerDataSnowballEntity;
import project.statement.track.app.beans.pojos.BrokerSnowBallPojo;
import project.statement.track.app.vo.catalogs.CatalogsErrorMessage;

public class BrokerSnowBallUtil {
	
	private WebScrapUtil webScrapUtil = new WebScrapUtil();
	
	private DateFormatUtil dateFormatUtil = new DateFormatUtil();
	
	private String getNumberFormat(String number) {
		return number.replace("$", "").replace(" MXN", "").replace(",", "");
	}
	
	private void assignSnowBallValue(BrokerSnowBallPojo brokerSnowBallPojo, Map<String, String> movementDataMap) throws BusinessException {
		
		for (Map.Entry<String,String> entry : movementDataMap.entrySet()) {
			
			switch (entry.getKey()) {
			case "Saldo Anterior":
				brokerSnowBallPojo.setPreviousBalance(getNumberFormat(entry.getValue()));
				break;
			case "Egreso":
				brokerSnowBallPojo.setExit(getNumberFormat(entry.getValue()));
				break;
			case "Ingreso":
				brokerSnowBallPojo.setEntry(getNumberFormat(entry.getValue()));
				break;
			case "Saldo Actual":
				brokerSnowBallPojo.setActualBalance(getNumberFormat(entry.getValue()));
				break;
			case "Hora":
				brokerSnowBallPojo.setHour(entry.getValue());
				break;
			case "Movimiento":
				brokerSnowBallPojo.setMovementDescription(entry.getValue());
				break;
			case "Operación":
				brokerSnowBallPojo.setMovementDescription(entry.getValue());
				break;
			case "Empresa":
				brokerSnowBallPojo.setCompany(entry.getValue());
				break;
			case "Acciones":
				brokerSnowBallPojo.setTotalIssues(entry.getValue());
				break;
			case "ODIs Adquiridas":
				brokerSnowBallPojo.setTotalIssues(entry.getValue());
				break;
			case "Forma de pago":
				brokerSnowBallPojo.setTypePayment(entry.getValue());
				break;
			case "Estatus":
				brokerSnowBallPojo.setStatus(entry.getValue());
				break;
			case "Referencia":
				brokerSnowBallPojo.setReference(entry.getValue());
				break;
			case "Transfiere":
				//ignore parameter	
				break;
			case "Recibe":
				//ignore parameter	
				break;
			case "Fecha de IE":
				//ignore parameter	
				break;
			default:
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgParameterNotRecognized(entry.getKey(), entry.getValue()));
			}
		}
	}
	
	private List<BrokerSnowBallPojo> getDataSnowBall(HtmlPage page) throws BusinessException {
		
		BrokerSnowBallPojo brokerSnowBallPojo = null;
		List<BrokerSnowBallPojo> snowBallPojos = new ArrayList<>();
		
		HtmlElement mainElement = (HtmlElement) page.getByXPath("//div[@class='row']").get(0);
		Iterable<DomElement> childrensElements = mainElement.getChildElements();
		String currentDate = null;
		
		while (childrensElements.iterator().hasNext()) {
			
			DomElement element = childrensElements.iterator().next();
			
			String nodeData = element.asText();
			
			if (nodeData.matches("\\d{4}-\\d{2}-\\d{2}")) {
				
				currentDate = nodeData;
			}
			else if (currentDate != null) {
				
				brokerSnowBallPojo = new BrokerSnowBallPojo();
				brokerSnowBallPojo.setDate(currentDate);
				
				String movementData = nodeData.replace("\n\r", "");
				String[] movementDataList = movementData.split("\r\n");
				
				List<String> movementDataListTmp = new ArrayList<>(Arrays.asList(movementDataList));
				movementDataListTmp.remove(" Descargar");
				movementDataListTmp.remove("Descargar");
				movementDataList = movementDataListTmp.toArray(new String[0]);
				
				Map<String, String> movementDataMap = new LinkedHashMap<>();
				
				if (movementDataList.length % 2 != 0)
					throw new BusinessException(CatalogsErrorMessage.getErrorMsgListNotOdd(movementDataList));
				
				for(int i = 0; i < movementDataList.length; i = i + 2) {
					
					movementDataMap.put(movementDataList[i].trim(), movementDataList[i + 1].trim());
				}
				
				assignSnowBallValue(brokerSnowBallPojo, movementDataMap);
		        
				snowBallPojos.add(brokerSnowBallPojo);
			}
		}
		
		return snowBallPojos;
	}

	public List<BrokerSnowBallPojo> getDataSnowBallFromFile(String path) throws BusinessException {
		
		HtmlPage page = webScrapUtil.readHtmlFile(path);
		return getDataSnowBall(page);
	}
	
	public List<BrokerSnowBallPojo> getDataSnowBallFromText(String text) throws BusinessException {
		
		HtmlPage page = webScrapUtil.readHtmlText(text);
		return getDataSnowBall(page);
	}
	
	public List<BrokerDataSnowballEntity> mapDataSnowBall(List<BrokerSnowBallPojo> snowBallPojos) throws BusinessException {
		
		List<BrokerDataSnowballEntity> brokerDataSnowballs = new ArrayList<>();
		
		try {
			for(BrokerSnowBallPojo brokerSnowBallPojo: snowBallPojos) {
				
				BrokerDataSnowballEntity brokerDataSnowball = new BrokerDataSnowballEntity();
				
				brokerDataSnowball.setId(brokerSnowBallPojo.getDate().replace("-", "_") + "_" + brokerSnowBallPojo.getHour().replace(":", "_") + "_" + brokerSnowBallPojo.getPreviousBalance() + "_" + brokerSnowBallPojo.getActualBalance());
				brokerDataSnowball.setActualBalance(brokerSnowBallPojo.getActualBalance() != null ? new BigDecimal(brokerSnowBallPojo.getActualBalance()) : null);
				brokerDataSnowball.setPreviousBalance(brokerSnowBallPojo.getPreviousBalance() != null ? new BigDecimal(brokerSnowBallPojo.getPreviousBalance()) : null);
				brokerDataSnowball.setBalanceEntry(brokerSnowBallPojo.getEntry() != null ? new BigDecimal(brokerSnowBallPojo.getEntry()) : null);
				brokerDataSnowball.setBalanceExit(brokerSnowBallPojo.getExit() != null ? new BigDecimal(brokerSnowBallPojo.getExit()) : null);
				brokerDataSnowball.setCompany(brokerSnowBallPojo.getCompany());
				brokerDataSnowball.setMovementDescription(brokerSnowBallPojo.getMovementDescription());
				brokerDataSnowball.setReference(brokerSnowBallPojo.getReference());
				brokerDataSnowball.setStatus(brokerSnowBallPojo.getStatus());
				brokerDataSnowball.setTotalIssues(brokerSnowBallPojo.getTotalIssues() != null ? Integer.parseInt(brokerSnowBallPojo.getTotalIssues()) : null);
				brokerDataSnowball.setTypePayment(brokerSnowBallPojo.getTypePayment());
				brokerDataSnowball.setDateTransaction(dateFormatUtil.formatDate(brokerSnowBallPojo.getDate() + " " + brokerSnowBallPojo.getHour(), "yyyy-MM-dd HH:mm:ss"));
				brokerDataSnowball.setStatusMovement(true);
				
				brokerDataSnowballs.add(brokerDataSnowball);
			}
			
			return brokerDataSnowballs;
		}
		catch (ParseException pe) {
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgParsingDate(), pe);
		}
	}
}
