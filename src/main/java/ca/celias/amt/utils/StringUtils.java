/**
 * 
 */
package ca.celias.amt.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author Chris Elias
 */
public final class StringUtils {

    private StringUtils() {
    }
    
    /**
     * 
     * @param value
     * @return
     */
    public static boolean isEmptyOrNull(String value) {
        if (value == null || value.trim().length() == 0) 
            return true;
        
        return false;
    }
    
    /**
     * 
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {
        if (value == null ||  value.trim().isEmpty())
            return false;
        
        return true;
    }
    
    /**
     * 
     * @param seperator
     * @param values
     * @return
     */
    public static String join(CharSequence seperator, String ...values) {
        return Stream.of(values).filter(StringUtils::isNotEmpty).collect(Collectors.joining(seperator));
    }
    
    /**
     * 
     * @param values
     * @return
     */
    public static boolean isAllEmpty(String ... values) {
        
    	for (final String value : values) {
    		if (value != null && value.trim().length() > 0)
    			return false;
    	}
    	
        return true;
    }
    
}
