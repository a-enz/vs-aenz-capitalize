package ch.ethz.inf.vs.android.aenz.capitalize;

import java.util.EventListener;

import ch.ethz.inf.vs.android.aenz.capitalize.MessageEventSource.ChatEvent;

import android.os.Handler;
import android.os.Message;

/**
 * This class provides a wrapper for events to be triggered
 * by the message logic.
 * @author hong-an
 *
 */

public interface MessageEventListener extends EventListener {
	/**
	 * Handler for the events stemming from the message
	 * logic.
	 */
	final Handler callbackHandler = new Handler();
//	final Handler callbackHandler = new Handler() {
//		
//		@Override
//		public void handleMessage(Message msg) {
//			msg.getData().g
//		}
//	};
	
	/**
	 * Function that returns the callback handler
	 * @return
	 */
	public Handler getCallbackHandler();
	
	// TODO Add all necessary event triggers
	
	public abstract void onReceiveMessage(ChatEvent e);
	
}
