package ch.ethz.inf.vs.android.aenz.capitalize;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import ch.ethz.inf.vs.android.aenz.capitalize.MessageEventSource.ChatEvent;
import ch.ethz.inf.vs.android.nethz.chat.R;

/**
 * This function represents the main activity for launching the app.
 * 
 * @author hong-an
 *
 */
public class MainActivity extends ListActivity implements MessageEventListener {
	/**
	 * This object handles the logic for sending and receiving messages
	 * to / from the server
	 */
	private MessageLogic logic;
	/**
	 * This lists contains the DisplayMessage to be used for the UI
	 */
	ArrayList<DisplayMessage> displayMessages;
	/**
	 * The adapter for displaying the DisplayMessage in a view
	 */
	DisplayMessageAdapter adapter;

	// TODO Add some more views to control the sending of the messages
	private final String TAG = "MainActivity";
	private EditText myText;
	private ListView messageView;
	private Logger log;

	
	@Override
	/**
	 * This function is called to create the app. Think about 
	 * what is necessary for the initialization.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_main);
		//this.callbackHandler.getLooper().p
		this.logic = new MessageLogic(this);
		logic.addMessageEventListener(this);
		
		myText = (EditText) findViewById(R.id.text);
		
		displayMessages = new ArrayList<DisplayMessage>();
		adapter = new DisplayMessageAdapter(this, displayMessages);
		messageView = (ListView) findViewById(android.R.id.list);
		messageView.setAdapter(adapter);
		log = new Logger(this);
		
	}
	
	@Override
	public void onStop() {
		logic.close();
		super.onStop();
	}

	@Override
	public Handler getCallbackHandler() {
		return callbackHandler;
	}

	/**
	 * This function handles pressing on the back button.
	 * This should be a clean exit. Think about what should be closed.
	 */
	public void onBackPressed() {
		super.onBackPressed();
		logic.close();
	}
	
	
	public void sendMessage(View v) throws IOException {
		String text;
		DisplayMessage bubble;
		if(!(text = myText.getText().toString()).equals("")) {
			Log.d(TAG, "sendMessage triggered with: " + text);
			myText.setText("");
			bubble = new DisplayMessage(text, Utils.USER, true);
			logic.sendMessage(text);
			displayMessages.add(bubble);
			log.logReadyMsg(bubble, false);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onReceiveMessage(ChatEvent e) {
		DisplayMessage bubble;
		if(e.getType() == Utils.MessageEventType.MESSAGE_RECEIVED) {
			bubble = new DisplayMessage(e.message, "Server", false);
			displayMessages.add(bubble);
			log.logReadyMsg(bubble, true);
			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					adapter.notifyDataSetChanged();
				}	
			});
		}
		
	}
}
