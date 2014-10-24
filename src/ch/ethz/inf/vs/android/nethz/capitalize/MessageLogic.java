package ch.ethz.inf.vs.android.nethz.capitalize;

import java.io.IOException;
import java.io.Serializable;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

@SuppressLint("UseSparseArrays")
/**
 * This class is handling the communication with the server
 * @author hong-an
 *
 */
public class MessageLogic extends MessageEventSource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -459244179641490462L;

	/**
	 * 
	 */
	Context appContext;
	
	/**
	 * Use this handler for outgoing traffic, aka requests to the server.
	 */
	private Handler requestHandler;
	
	/**
	 * Use this handler for incoming traffic, aka responses from the server.
	 */
	private Handler receiveHandler;

	/**
	 * This object handles the UDP communication between the client and the chat server
	 */
	UDPCommunicator comm;
	
	/**
	 * This logger should always called when an incoming or outgoing message is ready to be
	 * displayed in the view.
	 */
	Logger log;
	
	private boolean listening;

	/**
	 * Constructor
	 * @param context The calling activity
	 */
	public MessageLogic(Context context) {
		appContext = context;
		this.initLogger();
		listening = true;
		comm = new UDPCommunicator();
		comm.setupConnection();
		new Receiver().execute(new String[]{});
	}

	/**
	 * Initialization of the logger
	 */
	public void initLogger() {
		this.log = new Logger(appContext);
	}
	
	public void sendMessage(String text){
		comm.sendRequest(text);
	}
	
	private class Receiver extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String msg;
			ChatEvent event;
			while(listening) {
				try {
					msg = comm.receiveAnswer();
					event = new ChatEvent(this, Utils.MessageEventType.MESSAGE_RECEIVED , msg, null);
					event.dispatchEvent();
				} catch (IOException e) {
				}
			}
			return "finished";
		}
		
	}
}
