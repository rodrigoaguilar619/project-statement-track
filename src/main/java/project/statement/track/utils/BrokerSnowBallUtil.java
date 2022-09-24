package project.statement.track.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import lib.base.backend.exception.data.BusinessException;
import lib.utils.backend.format.DateUtil;
import project.statement.track.beans.entity.BrokerDataSnowball;
import project.statement.track.pojos.BrokerSnowBallPojo;

public class BrokerSnowBallUtil {
	
	private WebScrapUtil webScrapUtil;
	private DateUtil dateUtil;
	
	public BrokerSnowBallUtil() {
		
		webScrapUtil = new WebScrapUtil();
		dateUtil = new DateUtil();
	}
	
	private String getNumberFormat(String number) {
		return number.replace("$", "").replace(" MXN", "").replace(",", "");
	}
	
	private List<BrokerSnowBallPojo> getDataSnowBall(HtmlPage page) throws BusinessException {
		
		BrokerSnowBallPojo brokerSnowBallPojo = null;
		List<BrokerSnowBallPojo> snowBallPojos = new ArrayList<>();
		
		HtmlElement mainElement = (HtmlElement) page.getByXPath("//div[@class='row']").get(0);
		Iterable<DomElement> childrensElements = mainElement.getChildElements();
		String currentDate = null;
		
		while (childrensElements.iterator().hasNext()) {
			
			DomElement element = childrensElements.iterator().next();
			//System.out.println("Node element text " + element.asText());
			
			String nodeData = element.asText();
			
			if (nodeData.matches("\\d{4}-\\d{2}-\\d{2}")) {
				
				//System.out.println("Node date element " + dateNode.get(0).asText());
				currentDate = nodeData;
			}
			else if (currentDate != null) {
				//System.out.println("Node element " + element.asXml());
				
				brokerSnowBallPojo = new BrokerSnowBallPojo();
				brokerSnowBallPojo.setDate(currentDate);
				
				String movementData = nodeData.replace("\n\r", "");
				String[] movementDataList = movementData.split("\r\n");
				
				Map<String, String> movementDataMap = new LinkedHashMap<>();
				
				if (movementDataList.length % 2 != 0)
					throw new BusinessException("list is not odd. " + StringUtils.join(movementDataList, "|"));
				
				for(int i = 0; i < movementDataList.length; i = i + 2) {
					
					movementDataMap.put(movementDataList[i].trim(), movementDataList[i + 1].trim());
				}
				
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
						throw new BusinessException("Parameter not recognized key: " + entry.getKey() + " value: " + entry.getValue());
					}
				}
		        
				snowBallPojos.add(brokerSnowBallPojo);
				
				//movementDataMap.forEach((key, value) -> System.out.println(key + ": " + value));
				//System.out.println("-----");
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
	
	public List<BrokerDataSnowball> mapDataSnowBall(List<BrokerSnowBallPojo> snowBallPojos) throws BusinessException {
		
		List<BrokerDataSnowball> brokerDataSnowballs = new ArrayList<>();
		
		try {
			for(BrokerSnowBallPojo brokerSnowBallPojo: snowBallPojos) {
				
				BrokerDataSnowball brokerDataSnowball = new BrokerDataSnowball();
				
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
				brokerDataSnowball.setDateTransaction(dateUtil.parseDate(brokerSnowBallPojo.getDate() + " " + brokerSnowBallPojo.getHour(), "yyyy-MM-dd HH:mm:ss"));
				brokerDataSnowball.setStatusMovement(true);
				
				brokerDataSnowballs.add(brokerDataSnowball);
			}
			
			return brokerDataSnowballs;
		}
		catch (ParseException pe) {
			throw new BusinessException("Error parsing date", pe);
		}
	}
}
