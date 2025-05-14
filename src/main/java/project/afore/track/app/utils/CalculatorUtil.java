package project.afore.track.app.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorUtil {


	public BigDecimal calculatePercentageUpDown(BigDecimal valueTotal, BigDecimal valueToEstimatePercentage) {
		
		if (valueTotal == null || valueToEstimatePercentage == null || valueTotal.equals(BigDecimal.ZERO) || valueToEstimatePercentage.equals(BigDecimal.ZERO))
			return new BigDecimal("0");
		
		BigDecimal total = valueToEstimatePercentage.multiply(BigDecimal.valueOf(100)).divide(valueTotal, 5, RoundingMode.HALF_DOWN);
		
		return total.subtract(BigDecimal.valueOf(100));
	}
}
