package project.afore.track.app.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import project.afore.track.app.repository.AforeAportacionRepository;
import project.afore.track.app.utils.CalculatorUtil;
import project.afore.track.app.vo.enums.CatalogConceptUnifiedEnum;
import project.afore.track.app.vo.enums.CatalogSectionEnum;

@RequiredArgsConstructor
@Component
public class AforeHelper {
	
	private CalculatorUtil calculatorUtil = new CalculatorUtil();
	
	private final AforeAportacionRepository aforeAportacionRepository;

	public List<Integer> commissionIds = Arrays.asList(CatalogConceptUnifiedEnum.COMISION.getId());
	
	public List<Integer> interestIds = Arrays.asList(CatalogConceptUnifiedEnum.RENDIMIENTOS.getId(),
			CatalogConceptUnifiedEnum.INTERES_DE_VIVIENDA_INFONAVIT_DEL_PERIODO.getId(),
			CatalogConceptUnifiedEnum.INTERESES_TRANSITO_IMSS.getId(), CatalogConceptUnifiedEnum.DESFASE.getId(),
			CatalogConceptUnifiedEnum.ACTUALIZACIONES_Y_RECARGOS_IMSS_DEL_PERIODO.getId());
	
	public List<Integer> depositIds = Arrays.asList(CatalogConceptUnifiedEnum.APORTACION_PATRONAL.getId(),
			CatalogConceptUnifiedEnum.APORTACION_CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ_IMSS.getId(),
			CatalogConceptUnifiedEnum.APORTACION_VOLUNTARIA.getId(),
			CatalogConceptUnifiedEnum.APORTACION_INFONAVIT_97.getId(),
			CatalogConceptUnifiedEnum.APORTACION_GOBIERNO.getId(),
			CatalogConceptUnifiedEnum.APORTACION_EN_SUBCUENTA_CUOTA_SOCIAL_IMSS.getId());
	
	public List<Integer> withdrawIds = Arrays.asList(CatalogConceptUnifiedEnum.RETIRO_DE_AHORRO_VOLUNTARIO.getId());
	
	public BigDecimal getPeriodAnualYield(LocalDate period, CatalogSectionEnum catalogSectionEnum) {
		
		Integer idSection = catalogSectionEnum != null ? catalogSectionEnum.getId() : null;
		
		BigDecimal withdrawTotal = aforeAportacionRepository.findTotalContributionsBeforeOrSamePeriod(period, idSection, withdrawIds);
		BigDecimal commissionsTotal = aforeAportacionRepository.findTotalContributionsBeforeOrSamePeriod(period, idSection, commissionIds);
		BigDecimal depositTotal = aforeAportacionRepository.findTotalContributionsBeforeOrSamePeriod(period, idSection, depositIds);
		BigDecimal interestBeforePeriodTotal = aforeAportacionRepository.findTotalContributionsBeforePeriod(period, idSection, interestIds);
		
		BigDecimal interestTotal = aforeAportacionRepository.findTotalContributionsByPeriod(period, idSection, interestIds);
		BigDecimal baseTotal = depositTotal.add(commissionsTotal);
		baseTotal = baseTotal.add(interestBeforePeriodTotal);
		baseTotal = baseTotal.add(withdrawTotal);
		BigDecimal valueTotal = baseTotal.add(interestTotal);
		
		BigDecimal yieldTotal = calculatorUtil.calculatePercentageUpDown(baseTotal, valueTotal);
		
		return yieldTotal;
	}
	
	public BigDecimal getTotalPeriodsAnualYield(LocalDate period, CatalogSectionEnum catalogSectionEnum) {
		
		List<LocalDate> periods = aforeAportacionRepository.findPreviousPeriods(period); 
		List<BigDecimal> yields = periods.stream().map(periodDate -> getPeriodAnualYield(periodDate, catalogSectionEnum)).toList();
		
		BigDecimal averageYield = yields.stream().reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(yields.size()), 2, RoundingMode.HALF_DOWN);
		return averageYield.multiply(BigDecimal.valueOf(3));
	}
}
