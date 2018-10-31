package com.ideas2it.dvdstore.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ideas2it.dvdstore.common.Constants.ALPHA_PATTERN; 

/** 
 * Utility class that contains helper functions for validating input
 */
public final class InputValidator {
    private InputValidator(){}

    public static boolean isAlpha(String input) {
        Pattern alphaPattern = Pattern.compile(ALPHA_PATTERN);
        Matcher alphaMatcher;
        alphaMatcher = alphaPattern.matcher(input);
        return alphaMatcher.matches();
    } 
}
