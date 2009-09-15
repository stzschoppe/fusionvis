import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */

/**
 * @author stzschoppe
 * Visualization of data fusion models based on jME 2.0 engine
 * (main class) 
 */
public class FusionVis {
	public static void main(String[] args) {
		Logger logger = Logger.getLogger("MyLog");
	    try {
	      // This block configure the logger 
	      logger.setLevel(Level.ALL);

	      logger.log(Level.INFO, "starting FusionVis");
	      logger.log(Level.INFO, "exiting FusionVis");
	      

	    } catch (SecurityException e) {
	      e.printStackTrace();
	  
	  }
	}
}
