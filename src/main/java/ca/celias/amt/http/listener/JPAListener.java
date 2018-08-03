package ca.celias.amt.http.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import ca.celias.amt.resources.HasLogger;

/**
 * Application Lifecycle Listener implementation class JPAListener
 *
 */
@WebListener
public class JPAListener 
implements ServletContextListener, HasLogger {


    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger().trace("ENTER contextDestroyed(sce)");
        logger().trace("EXIT contextDestroyed(sce)");
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger().trace("ENTER contextInitialized(sce)");
        
        
        logger().trace("EXIT contextInitialized(sce)");
    }

}
