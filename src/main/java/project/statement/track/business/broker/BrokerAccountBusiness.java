package project.statement.track.business.broker;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.persistance.GenericPersistence;
import project.statement.track.beans.entity.BrokerAccount;
import project.statement.track.pojos.entity.BrokerAccountResumePojo;
import project.statement.track.pojos.request.GetBADateStatementsRequestPojo;
import project.statement.track.pojos.request.GetBrokerAccountsRequestPojo;
import project.statement.track.pojos.response.GetBADateStatementsResponsePojo;
import project.statement.track.pojos.response.GetBADateStatementsResponsePojo.StatementYearPojo;
import project.statement.track.pojos.response.GetBrokerAccountsResponsePojo;
import project.statement.track.utils.BuildEntityToPojoUtil;

@Component
public class BrokerAccountBusiness {

	@SuppressWarnings("rawtypes")
	@Autowired
	GenericPersistence genericCustomPersistance;
	
	@Autowired
	BuildEntityToPojoUtil buildEntityToPojoUtil;
	
	public List<BrokerAccountResumePojo> getBrokerAccounts() {
		
		List<BrokerAccount> brokerAccounts = genericCustomPersistance.findAll(BrokerAccount.class);
		List<BrokerAccountResumePojo> brokerAccountResumePojos = new ArrayList<>();
		
		for (BrokerAccount brokerAccount: brokerAccounts) {
			
			BrokerAccountResumePojo brokerAccountResumePojo = buildEntityToPojoUtil.mapBrokerAccountResumePojo(null, brokerAccount);
			
			brokerAccountResumePojos.add(brokerAccountResumePojo);
		}
		
		return brokerAccountResumePojos;
	}
	
	@SuppressWarnings("deprecation")
	@Transactional(rollbackFor = Exception.class)
	public GetBADateStatementsResponsePojo executeGetDateStatements(GetBADateStatementsRequestPojo requestPojo) {
		
		BrokerAccount brokerAccount = (BrokerAccount) genericCustomPersistance.findById(BrokerAccount.class, requestPojo.getIdBrokerAccount());
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(brokerAccount.getDateCreation());
		
		Integer startYear = startDate.get(Calendar.YEAR);
		Integer startMonth = startDate.get(Calendar.MONTH) + 1;
		
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(new Date());
		
		Integer currentYear = currentDate.get(Calendar.YEAR);
		Integer currentMonth = currentDate.get(Calendar.MONTH) + 1;
		
		List<GetBADateStatementsResponsePojo.StatementYearPojo> yearPojos = new ArrayList<>();
		GetBADateStatementsResponsePojo responsePojo = new GetBADateStatementsResponsePojo();
		
		for (int year = startYear; year <= currentYear; year++) {
			
			GetBADateStatementsResponsePojo.StatementYearPojo statementYear = responsePojo.new StatementYearPojo();
			List<GetBADateStatementsResponsePojo.StatementMonthPojo> monthPojos = new ArrayList<>();
			
			for (int month = ((year == startYear) ? startMonth : 1); month <= ((year == currentYear) ? currentMonth : 12); month++) {
				
				GetBADateStatementsResponsePojo.StatementMonthPojo statementMonth = responsePojo.new StatementMonthPojo();
				statementMonth.setId(month);
				statementMonth.setDescription(StringUtils.capitalize(Month.of(month).name().toLowerCase()));
				
				monthPojos.add(statementMonth);
			}
			
			statementYear.setYear(year);
			statementYear.setMonths(monthPojos);
			
			yearPojos.add(statementYear);
		}
		
		responsePojo.setYears(yearPojos);
		
		return responsePojo;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public GetBrokerAccountsResponsePojo executeGetBrokerAccounts(GetBrokerAccountsRequestPojo requestPojo) {
		
		GetBrokerAccountsResponsePojo responsePojo = new GetBrokerAccountsResponsePojo();
		responsePojo.setBrokerAccounts(getBrokerAccounts());
		
		return responsePojo;
	}
}
