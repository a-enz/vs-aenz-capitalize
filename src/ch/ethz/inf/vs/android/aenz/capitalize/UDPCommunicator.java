package ch.ethz.inf.vs.android.aenz.capitalize;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.json.JSONObject;

import android.util.Log;

/**
 * This class should be used to interface with the server
 * using UDP
 * @author hong-an
 *
 */
public class UDPCommunicator {
	// TODO: Add the necessary objects
	private DatagramSocket socket;
	private final String TAG = "UDPCommunicator";
	/**
	 * Constructor
	 */
	public UDPCommunicator() {
		setupConnection();
	}

	/**
	 * This function should be used to setup the "connection" to the server
	 * Not crucial in task 1, but in task 2, the port should be bound.
	 * @return
	 */
	public boolean setupConnection() {
		// TODO Setup the connection with the server and make sure to bind the
		// socket
		try {
			socket = new DatagramSocket();	//Bind is already done here
			//TODO set buffersizes etc...
			socket.setSoTimeout(Utils.RESPONSE_TIMEOUT);
			socket.setReceiveBufferSize(Utils.RECEIVE_BUFFER_SIZE);
			socket.connect(InetAddress.getByName(Utils.SERVER_ADDRESS), Utils.SERVER_PORT);
			Log.d(TAG, "Socket bound: " + socket.isBound());
			Log.d(TAG, "Socket connected:" + socket.isConnected());
		} catch (SocketException e) {
			e.printStackTrace();
			Log.d(TAG, "Socket messed up");
			return false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Log.d(TAG, "Host not found");
			return false;
		}
		return true;
	}

	/**
	 * This function should be used to send a request to the server
	 * @param request The request in JSON format
	 */
	public void sendRequest(JSONObject request) {
		// TODO Implement sending the JSONObject to the server
	}
	
	/**
	 * Hand-made send function, because dat above ain't no good for task 1
	 * @param text
	 * @throws IOException 
	 */
	public void sendRequest(String text) throws IOException {
		byte[] msg = text.getBytes(Charset.defaultCharset());
		int length = msg.length;
		socket.send(new DatagramPacket(msg, length));
		Log.d(TAG, "Msg sent: " + text);
	}
	
	public String receiveAnswer() throws IOException {
		DatagramPacket res = new DatagramPacket(new byte[Utils.RECEIVE_BUFFER_SIZE], Utils.RECEIVE_BUFFER_SIZE);
		Log.d(TAG, "receiving...");
		socket.receive(res);
		return new String(res.getData(), Charset.defaultCharset());
	}

	public void close() {
		socket.close();
	}
	
	
}
