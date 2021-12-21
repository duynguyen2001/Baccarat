import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server{

	int count = 1;
	int port;
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	
	
	Server(Consumer<Serializable> call, int port){
		this.port = port;
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
			try(ServerSocket mysocket = new ServerSocket(port);){
		    System.out.println("Server is waiting for a client!");
		  
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);
				callback.accept("client has connected to server: " + "client #" + count);
				clients.add(c);
				c.start();
				
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
			
			BaccaratGame game;
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				game = new BaccaratGame();
				this.connection = s;
				this.count = count;	
			}
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
									
				 while(true) {
				        // first: recieve the bid
					 	// second: create the cards and see if there is a need for a third one
					 	// third: send the cards to the client
					 	// fourth: evaluate the result and send it to the client
					    try {
					    	String data = in.readObject().toString();
					    	String bet = "";
					    	bet = evaluateBid2(data);
					    	while(bet == "NewGame") {
					    		game = new BaccaratGame();
					    		data = in.readObject().toString();
					    		bet = evaluateBid2(data);
					    	}
					    	Double betValue = evaluateBid( bet, data);
		
					    	callback.accept("client: " + count + " Bet: " + bet + betValue);
					    	out.writeObject("R_");
					    	game.theDealer.generateDeck();
					    	game.playerHand = game.theDealer.dealHand();
					    	game.bankerHand = game.theDealer.dealHand();
					    	game.setCurrentBet(betValue, bet);
					    	
					    	// add a third card to the player
					    	Card drawnCard = null;
					    	if(game.logic.evaluatePlayerDraw(game.playerHand)) {
					    		drawnCard = game.theDealer.drawOne();
					    		game.playerHand.add(drawnCard);
					    	}
					    	if(game.logic.evaluateBankerDraw(game.bankerHand, drawnCard)) {
					    		Card temp = game.theDealer.drawOne();
					    		game.bankerHand.add(temp);
					    	}
					        
					    	// send the player cards
					    	for(int i = 0; i < game.playerHand.size(); i++) {
					    		String message = "P_";
					    		message+= game.playerHand.get(i).value + "_";
					    		message+= game.playerHand.get(i).suite;
					    		out.writeObject(message);
					    	}
					    	
					    	// send the banker cards
					    	for(int i = 0; i < game.bankerHand.size(); i++) {
					    		String message = "B_";
					    		message+= game.bankerHand.get(i).value + "_";
					    		message+= game.bankerHand.get(i).suite;
					    		out.writeObject(message);
					    	}
					    	
					    	// evaluate the result and send it
					    	Double tempValue = game.evaluateWinnings();
					    	if(tempValue < 0.0) {
					    		out.writeObject("L_" + String.valueOf(tempValue*(-1)));
					    	} else if (tempValue == 0.0) {
					    		out.writeObject("D_" + String.valueOf(tempValue));
					    	} else {
					    		out.writeObject("W_" + String.valueOf((tempValue)));
					    	}
					    	out.writeObject("C_" + String.valueOf(game.totalWinnings));
					    }
					    catch(Exception e) {
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
					    	e.printStackTrace();
					    	clients.remove(this);
					    	break;
					    }
					}
				}//end of run
			
			// evaluate the recieved data from the client
			Double evaluateBid(String bet, String data) {
				Double betValue = 0.0;
		    	String temp = "";
		    	if(data.charAt(0) == 'B') {
		    		bet = "on Banker with ";
		    		for(int i = 2; i < data.length(); i++) {
		    			temp += data.charAt(i);
		    		}
		    	} else if(data.charAt(0) == 'P') {
		    		bet = "on Player with ";
		    		for(int i = 2; i < data.length(); i++) {
		    			temp += data.charAt(i);
		    		}
		    	} else {
		    		bet = "on Tie with ";
		    		for(int i = 2; i < data.length(); i++) {
		    			temp += data.charAt(i);
		    		}
		    	}
		    	betValue = Double.parseDouble(temp);
				return betValue;
			}
			
			String evaluateBid2(String data) {
				String result = "";
				if(data.charAt(0) == 'B') {
					result = "Banker";
				} else if(data.charAt(0) == 'P') {
					result = "Player";
				} else if(data.charAt(0) == 'T'){
					result = "Tie";
				} else if(data.charAt(0) == 'N') {
					result = "NewGame";
				}
				return result;
			}
			
			
		}//end of client thread
}


	
	

	
