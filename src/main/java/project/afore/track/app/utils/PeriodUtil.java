package project.afore.track.app.utils;

import java.time.LocalDateTime;
import java.time.Month;

import lib.base.backend.utils.date.DateUtil;

public class PeriodUtil {
	
	DateUtil dateUtil = new DateUtil();

	public LocalDateTime getQuarterStart(LocalDateTime dateTime) {
        int month = dateTime.getMonthValue() - 1;
        int year = dateTime.getYear();
        Month startMonth;

        if (month <= 3) {
            startMonth = Month.JANUARY;
        } else if (month <= 6) {
            startMonth = Month.APRIL;
        } else if (month <= 9) {
            startMonth = Month.JULY;
        } else {
            startMonth = Month.OCTOBER;
        }

        return LocalDateTime.of(year, startMonth, 1, 0, 0);
    }
	
	public LocalDateTime getQuarterStart(long millis) {
		LocalDateTime dateTime = dateUtil.getLocalDateTime(millis);
		return getQuarterStart(dateTime);
	}
	
	public long getQuarterStartMillis(long millis) {
		LocalDateTime dateTime = getQuarterStart(millis);
		return dateUtil.getMillis(dateTime);
	}
}
