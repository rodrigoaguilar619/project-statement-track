package project.afore.track.modules.business.afore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.afore.track.app.beans.entity.AforeContributionEntity;
import project.afore.track.app.beans.pojos.PeriodResumePojo;
import project.afore.track.app.beans.pojos.PeriodTabResumePojo;
import project.afore.track.app.beans.pojos.PeriodTransactionPojo;
import project.afore.track.app.beans.pojos.petition.data.GetAforePeriodTabDataPojo;
import project.afore.track.app.beans.pojos.petition.data.GetAforePeriodTransactionDataPojo;
import project.afore.track.app.beans.pojos.petition.request.GetAforePeriodTabRequestPojo;
import project.afore.track.app.beans.pojos.petition.request.GetAforePeriodTransactionRequestPojo;
import project.afore.track.app.beans.pojos.tuple.PeriodTabTuplePojo;
import project.afore.track.app.helpers.AforeHelper;
import project.afore.track.app.repository.AforeAportacionRepository;
import project.afore.track.app.utils.PeriodUtil;
import project.afore.track.app.vo.enums.CatalogSectionEnum;
import project.statement.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class AforeResumeBusiness extends MainBusiness {

	private final AforeHelper aforeHelper;
	
	private final AforeAportacionRepository aforeAportacionRepository;
	
	private PeriodUtil periodUtil = new PeriodUtil();
	
	private PeriodResumePojo buildQuarterPeriodResume(LocalDate period, CatalogSectionEnum catalogSectionEnum) {
		
		Integer idSection = catalogSectionEnum != null ? catalogSectionEnum.getId() : null;
		BigDecimal quarterAllSectionsCommissions = aforeAportacionRepository.findTotalContributionsByPeriod(period, idSection, aforeHelper.commissionIds);
		BigDecimal quarterAllSectionsInterest = aforeAportacionRepository.findTotalContributionsByPeriod(period, idSection, aforeHelper.interestIds);
		BigDecimal quarterAllSectionsDeposit = aforeAportacionRepository.findTotalContributionsByPeriod(period, idSection, aforeHelper.depositIds);
		BigDecimal quarterAllSectionsWithdraw = aforeAportacionRepository.findTotalContributionsByPeriod(period, idSection, aforeHelper.withdrawIds);
		BigDecimal quarterAllSectionsYield = aforeHelper.getPeriodAnualYield(period, catalogSectionEnum);
		
		PeriodResumePojo periodResumeQuarterAllSections = new PeriodResumePojo();
		periodResumeQuarterAllSections.setCommissions(quarterAllSectionsCommissions);
		periodResumeQuarterAllSections.setInterests(quarterAllSectionsInterest);
		periodResumeQuarterAllSections.setDeposits(quarterAllSectionsDeposit);
		periodResumeQuarterAllSections.setWithdraws(quarterAllSectionsWithdraw);
		periodResumeQuarterAllSections.setYield(quarterAllSectionsYield);
		
		return periodResumeQuarterAllSections;
	}
	
	private PeriodResumePojo buildAllPeriodsResume(LocalDate period, CatalogSectionEnum catalogSectionEnum) {
		
		Integer idSection = catalogSectionEnum != null ? catalogSectionEnum.getId() : null;
		BigDecimal periodCommissions = aforeAportacionRepository.findTotalContributionsBeforeOrSamePeriod(period, idSection, aforeHelper.commissionIds);
		BigDecimal periodInterest = aforeAportacionRepository.findTotalContributionsBeforeOrSamePeriod(period, idSection, aforeHelper.interestIds);
		BigDecimal periodDeposit = aforeAportacionRepository.findTotalContributionsBeforeOrSamePeriod(period, idSection, aforeHelper.depositIds);
		BigDecimal periodWithdraw = aforeAportacionRepository.findTotalContributionsBeforeOrSamePeriod(period, idSection, aforeHelper.withdrawIds);
		BigDecimal periodYield = aforeHelper.getTotalPeriodsAnualYield(period, catalogSectionEnum);
		
		PeriodResumePojo periodResume = new PeriodResumePojo();
		periodResume.setCommissions(periodCommissions);
		periodResume.setInterests(periodInterest);
		periodResume.setDeposits(periodDeposit);
		periodResume.setWithdraws(periodWithdraw);
		periodResume.setYield(periodYield);
		
		return periodResume;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public GetAforePeriodTabDataPojo executeGetPeriodsResume(GetAforePeriodTabRequestPojo requestPojo) {
		
		List<PeriodTabTuplePojo> periodTabPojos = aforeAportacionRepository.findContributionWithTotal();
		List<PeriodTabResumePojo> periodTabResumePojos = new ArrayList<>();
		
		periodTabPojos.forEach(periodTabPojo -> {
			
			LocalDate period = dateUtil.getLocalDate(periodTabPojo.getPeriod());
			BigDecimal yield = aforeHelper.getPeriodAnualYield(period, null);
			
			PeriodTabResumePojo periodTabResumePojo = new PeriodTabResumePojo();
			periodTabResumePojo.setAmountPeriod(periodTabPojo.getAmountPeriod());
			periodTabResumePojo.setAmountCumulative(periodTabPojo.getAmountCumulative());
			periodTabResumePojo.setStartDate(periodUtil.getQuarterStartMillis(periodTabPojo.getPeriod()));
			periodTabResumePojo.setEndDate(periodTabPojo.getPeriod());
			periodTabResumePojo.setYieldAnualPeriod(yield);
			
			periodTabResumePojos.add(periodTabResumePojo);
			
		});
		
		GetAforePeriodTabDataPojo responsePojo = new GetAforePeriodTabDataPojo();
		responsePojo.setPeriods(periodTabResumePojos);
		
		return responsePojo;
	}
	
	public GetAforePeriodTransactionDataPojo executeGetAforePeriodTransactions(GetAforePeriodTransactionRequestPojo requestPojo) {
		
		LocalDate period = dateUtil.getLocalDate(requestPojo.getDatePeriod());
		List<AforeContributionEntity> contributions = aforeAportacionRepository.findContributionsByPeriod(period);
		
		PeriodResumePojo periodResumeQuarterAllSections = buildQuarterPeriodResume(period, null);
		PeriodResumePojo periodResumeQuarterVoluntarySave = buildQuarterPeriodResume(period, CatalogSectionEnum.AHORRO_VOLUNTARIO);
		PeriodResumePojo periodResumeQuarterRetiredSave = buildQuarterPeriodResume(period, CatalogSectionEnum.AHORRO_RETIRO);
		PeriodResumePojo periodResumeQuarterInfonavitSave = buildQuarterPeriodResume(period, CatalogSectionEnum.AHORRO_VIVIENDA);
		
		PeriodResumePojo periodResumeTotalAllSections = buildAllPeriodsResume(period, null);
		PeriodResumePojo periodResumeTotalVoluntarySave = buildAllPeriodsResume(period, CatalogSectionEnum.AHORRO_VOLUNTARIO);
		PeriodResumePojo periodResumeTotalRetiredSave = buildAllPeriodsResume(period, CatalogSectionEnum.AHORRO_RETIRO);
		PeriodResumePojo periodResumeTotalInfonavitSave = buildAllPeriodsResume(period, CatalogSectionEnum.AHORRO_VIVIENDA);
		
		List<PeriodTransactionPojo> periodTransactions = new ArrayList<>();
		
		contributions.forEach(contribution -> {
			
			PeriodTransactionPojo periodTransactionPojo = new PeriodTransactionPojo();
			periodTransactionPojo.setIdConcept(contribution.getIdConcept());
			periodTransactionPojo.setIdSection(contribution.getIdSection());
			periodTransactionPojo.setDescriptionConcept(contribution.getCatalogAforeConceptUnifiedEntity().getDescription());
			periodTransactionPojo.setDescriptionSection(contribution.getCatalogAforeSectionEntity().getDescription());
			periodTransactionPojo.setAmount(contribution.getAmount());
			
			periodTransactions.add(periodTransactionPojo);
		});
		
		Map<String, PeriodResumePojo> periodQuarterResumes = new HashMap<>();
		periodQuarterResumes.put("periodResumeAllSections", periodResumeQuarterAllSections);
		periodQuarterResumes.put("periodResumeVoluntarySave", periodResumeQuarterVoluntarySave);
		periodQuarterResumes.put("periodResumeRetiredSave", periodResumeQuarterRetiredSave); 
		periodQuarterResumes.put("periodResumeInfonavitSave", periodResumeQuarterInfonavitSave);
		
		Map<String, PeriodResumePojo> periodAllResumes = new HashMap<>();
		periodAllResumes.put("periodResumeAllSections", periodResumeTotalAllSections);
		periodAllResumes.put("periodResumeVoluntarySave", periodResumeTotalVoluntarySave);
		periodAllResumes.put("periodResumeRetiredSave", periodResumeTotalRetiredSave);
		periodAllResumes.put("periodResumeInfonavitSave", periodResumeTotalInfonavitSave);
		
		GetAforePeriodTransactionDataPojo responsePojo = new GetAforePeriodTransactionDataPojo();
		responsePojo.setPeriodTransactions(periodTransactions);
		responsePojo.setPeriodQuarterResumes(periodQuarterResumes);
		responsePojo.setPeriodAllResumes(periodAllResumes);
		
		return responsePojo;
	}
}
