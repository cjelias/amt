/**
 * 
 */
package ca.celias.amt.utils;

/**
 *
 * @author Chris Elias
 */
public final class NumberUtils {

    /**
     * 
     * @param value
     * @param defaultValue
     * @return
     */
    public static Integer convert(String value, int defaultValue) {
        if (StringUtils.isEmptyOrNull(value))
            return defaultValue;
        
        return Integer.parseInt(value);
    }

    /**
     * 
     * @param value
     * @param defaultValue
     * @return
     */
    public static Long convert(String value, long defaultValue) {
        if (StringUtils.isEmptyOrNull(value))
            return defaultValue;
        
        return Long.parseLong(value);
    }
    
    /**
     * 
     * @param value
     * @param defaultValue
     * @return
     */
    public static Double convert(String value, double defaultValue) {
        if (StringUtils.isEmptyOrNull(value))
            return defaultValue;
        
        return Double.parseDouble(value);
    }
    
}
