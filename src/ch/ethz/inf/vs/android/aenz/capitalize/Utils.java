package ch.ethz.inf.vs.android.aenz.capitalize;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class provides functions and parameters
 * that should be made available throughout the
 * app
 * @author hong-an
 *
 */
public class Utils {
	public final static String LOG_PATH = "capitalize_log.txt";
	
	/*
	 * Change me... Some useful constants
	 */
	public final static String SERVER_ADDRESS = "129.132.75.194";
	public final static int SERVER_PORT = 4000;
	public final static int RECEIVE_BUFFER_SIZE = 4096;
	public final static int SOCKET_TIMEOUT = -1;
	public final static int RESPONSE_TIMEOUT = 0;
	public final static int MESSAGE_TIMEOUT = -1;
	public final static String USER = "BrutalWargasm";
	public final static String MSGTAG = "Omelette du fromage";
	
	/**
	 * Similary to task 2 and as hint for the type of
	 * events that is triggered.
	 * @author hong-an
	 *
	 */
	public enum MessageEventType {
		MESSAGE_RECEIVED;
	}
	
	/** This function retrieve the current time and formats it
	 * @return Time in the appropriate format
	 */
    public static String getTime(){
    	Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    	return sdf.format(cal.getTime());
    }
}
