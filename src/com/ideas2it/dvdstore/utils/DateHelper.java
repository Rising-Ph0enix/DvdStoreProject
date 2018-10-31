package com.ideas2it.dvdstore.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/** 
 * Utility class that contains helper functions for date related operations
 */
public final class DateHelper {
    private DateHelper(){}

    public static Period calculateDateDifference(LocalDate olderDate, 
                                                 LocalDate newerDate) {

        Period period = Period.between(olderDate, newerDate);
        return period;
    }   
}
