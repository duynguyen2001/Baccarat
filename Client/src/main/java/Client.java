import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;



public class Client extends Thread{

	
	Socket socketClient;
	ObjectOutputStream out;
	ObjectInputStream in;
	String IPPort;
	String IPAddress;
	private Consumer<Serializable> callback;
	
	Client(String IPAddress, String IPPort, Consumer<Serializable> call){
		this.IPAddress = IPAddress;
		this.IPPort = IPPort;
		callback = call;
	}
	public void run(){
		
		try {
			socketClient= new Socket(IPAddress, Integer.parseInt(IPPort));
		    out = new ObjectOutputStream(socketClient.getOutputStream());
		    in = new ObjectInputStream(socketClient.getInputStream());
		    socketClient.setTcpNoDelay(true);
		    callback.accept("Connected");
		}
		catch(Exception e) {
			System.out.println("No socket found, please input another port.");
			callback.accept("Not Connected");
			
		}
		
		while(true) {
			 
			try {
				String message = in.readObject().toString();
				callback.accept(message);
			}
			catch(Exception e) {
				
			}
		}
	
    }
	public void send(String data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String receive() {
		try {
			String s = (String) in.readObject();
			System.out.println("Read String: " + s);
			return s;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
