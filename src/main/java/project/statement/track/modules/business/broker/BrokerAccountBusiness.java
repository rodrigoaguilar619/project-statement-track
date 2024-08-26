package project.statement.track.modules.business.broker;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.persistance.GenericPersistence;
import lombok.RequiredArgsConstructor;
import project.statement.track.app.beans.entity.BrokerAccountEntity;
import project.statement.track.app.beans.pojos.entity.BrokerAccountResumePojo;
import project.statement.track.app.beans.pojos.petition.data.GetBADateStatementsDataPojo;
import project.statement.track.app.beans.pojos.petition.data.GetBrokerAccountsDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetBADateStatementsRequestPojo;
import project.statement.track.app.beans.pojos.petition.request.GetBrokerAccountsRequestPojo;
import project.statement.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class BrokerAccountBusiness extends MainBusiness {

	@SuppressWarnings("rawtypes")
	private final GenericPersistence genericPersistance;
	
	@SuppressWarnings("unchecked")
	public List<BrokerAccountResumePojo> getBrokerAccounts() {
		
		List<BrokerAccountEntity> brokerAccounts = genericPersistance.findAll(BrokerAccountEntity.class);
		List<BrokerAccountResumePojo> brokerAccountResumePojos = new ArrayList<>();
		
		for (BrokerAccountEntity brokerAccount: brokerAccounts) {
			
			BrokerAccountResumePojo brokerAccountResumePojo = buildEntityToPojoUtil.mapBrokerAccountResumePojo(null, brokerAccount);
			
			brokerAccountResumePojos.add(brokerAccountResumePojo);
		}
		
		return brokerAccountResumePojos;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public GetBADateStatementsDataPojo executeGetDateStatements(GetBADateStatementsRequestPojo requestPojo) {
		
		BrokerAccountEntity brokerAccount = (BrokerAccountEntity) genericPersistance.findById(BrokerAccountEntity.class, requestPojo.getIdBrokerAccount());
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(brokerAccount.getDateCreation());
		
		Integer startYear = startDate.get(Calendar.YEAR);
		Integer startMonth = startDate.get(Calendar.MONTH) + 1;
		
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(new Date());
		
		Integer currentYear = currentDate.get(Calendar.YEAR);
		Integer currentMonth = currentDate.get(Calendar.MONTH) + 1;
		
		List<GetBADateStatementsDataPojo.StatementYearPojo> yearPojos = new ArrayList<>();
		GetBADateStatementsDataPojo responsePojo = new GetBADateStatementsDataPojo();
		
		for (int year = startYear; year <= currentYear; year++) {
			
			GetBADateStatementsDataPojo.StatementYearPojo statementYear = responsePojo.new StatementYearPojo();
			List<GetBADateStatementsDataPojo.StatementMonthPojo> monthPojos = new ArrayList<>();
			
			for (int month = ((year == startYear) ? startMonth : 1); month <= ((year == currentYear) ? currentMonth : 12); month++) {
				
				GetBADateStatementsDataPojo.StatementMonthPojo statementMonth = responsePojo.new StatementMonthPojo();
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
	public GetBrokerAccountsDataPojo executeGetBrokerAccounts(GetBrokerAccountsRequestPojo requestPojo) {
		
		GetBrokerAccountsDataPojo responsePojo = new GetBrokerAccountsDataPojo();
		responsePojo.setBrokerAccounts(getBrokerAccounts());
		
		return responsePojo;
	}
}
