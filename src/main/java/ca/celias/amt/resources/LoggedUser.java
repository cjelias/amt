package ca.celias.amt.resources;

/**
 *
 * @author Chris Elias
 */
public class LoggedUser {
    
    private static final ThreadLocal<String> USER = new ThreadLocal<>();

    public static void logIn(String user) {
        USER.set(user);
    }

    public static void logOut() {
        USER.remove();
    }

    public static String get() {
        return USER.get();
    }
}
