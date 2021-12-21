

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ClientController implements Initializable {
	@FXML
	private BorderPane root;
	@FXML
	private BorderPane root2;
	@FXML
	private BorderPane root3;
	@FXML
	private HBox bankerCards;
	@FXML
	private HBox playerCards;
	@FXML
	private ListView<String> roundResultListView;
	@FXML
	private ListView<String> currentWinningListView;
	@FXML
	private TextField IPPort;
	@FXML
	private Label currentMoney;
	@FXML
	private TextField IPAddress;
	@FXML
	private TextField amountBet;
	@FXML
	private Label chooseBet;
	@FXML
	private Label playStat;
	private static Client clientConnection;
	private static ClientController myctr;
	private int clientNum;
	private static String playingStatus = "";
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		myctr = this;
	}
	public void setController(ClientController ctr) {
		myctr = ctr;
	}
	// play new game from scene 1 
	public void newGame1Method(ActionEvent e) throws IOException {
		clientConnection = new Client(IPAddress.getText(), IPPort.getText(), data->{
			Platform.runLater(()->
				{
					String dataString = data.toString();
					System.out.println("runned");
					System.out.println(dataString);
					if(dataString.equals("Connected")) {
						setController(this.renderClientGUI());
					} else if (dataString.equals("Not Connected")){
						this.alertMethod();
					}
//						myctr.addItemToRoundResult(dataString);
//						myctr.addItemToCurrentWinning(dataString);
//						myctr.addCardsToBanker(dataString);
//						myctr.addCardsToPlayer(dataString);
						//
						if (dataString.indexOf("_") == -1) return;
						Vector<String> inputOption = commandProcessingClass.processingSystemCommand(dataString);
						switch(inputOption.elementAt(0)) {
							case "B":
								myctr.addCardsToBanker(inputOption.elementAt(1), inputOption.elementAt(2));
								break;
							case "P":
								myctr.addCardsToPlayer(inputOption.elementAt(1), inputOption.elementAt(2));
								break;
							case "R":
								myctr.clearCards();
								break;
							case "W":
								myctr.addItemToCurrentWinning("Client win : $" + inputOption.elementAt(1));
								break;
							case "L":
								myctr.addItemToCurrentWinning("Client lose : $" + inputOption.elementAt(1));
								break;
							case "C":
								if (Double.parseDouble(inputOption.elementAt(1)) > 0.0) {
									playingStatus = " You won $" + inputOption.elementAt(1);
								} else if (Double.parseDouble(inputOption.elementAt(1)) < 0.0) {
									playingStatus = " You lose $" + inputOption.elementAt(1);
								} else {
									playingStatus = " You tied $" + inputOption.elementAt(1);
								}
								myctr.addItemToCurrentWinning("Current Money : $" + inputOption.elementAt(1));
								myctr.updateCurrentMoney(inputOption.elementAt(1));
								break;
							case "D":
								myctr.addItemToCurrentWinning("Client draw : $" + inputOption.elementAt(1));
								break;
								
							default:
								break;
						}
				});
			});
		// start the client	
		clientConnection.start();
	}
	// play new game from scene 3
	public void newGame2Method(ActionEvent e) throws IOException {
		//get instance of the loader class
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ClientFXML2.fxml"));
        Parent root2 = loader.load(); //load view into parent
        ClientController myctr = loader.getController();//get controller created by FXMLLoader
        setController(myctr);
        //setting the old scene with the new scene
        root3.getScene().setRoot(root2);//update scene graph
        
        
	}
	public void setPlayStat(String s) {
		playStat.setText(s);
	}
	// if winning or tie move to scene 3
	public void winningMethod(ActionEvent e){
		//get instance of the loader class
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ClientFXML3.fxml"));
        Parent root3;
		try {
			root3 = loader.load();
			ClientController myctr = loader.getController();//get controller created by FXMLLoader
	        setController(myctr);
	        //setting the old scene with the new scene
	        root2.getScene().setRoot(root3);//update scene graph
	        myctr.setPlayStat(playingStatus);
	        clientConnection.send("N_");
		} catch (IOException e1) {
			e1.printStackTrace();
		} //load view into parent
        
        
	}
	public void updateCurrentMoney(String money) {
		currentMoney.setText("$" + money);
	}
	public void addItemToRoundResult (String s) {
		if(roundResultListView != null)
			roundResultListView.getItems().add(s);
	}
	public void addItemToCurrentWinning (String s) {
		if(currentWinningListView != null)
			currentWinningListView.getItems().add(s);
	}
	
	public ImageView addCard(String nameCard) {
		try {
			String pathToImage = getClass().getResource("/images/Cards/" + nameCard).toString();
			Image image = new Image(pathToImage);  
			ImageView imageView = new ImageView(image);
			bankerCards.getChildren().add(imageView);
			return imageView;
		} catch (Exception e) {
			System.out.println("No image of " + nameCard + " found");
			return null;
		}
	}
	public void addCardsToBanker (String value, String suite) {
		String nameCard = commandProcessingClass.getNameCard(value, suite);
		System.out.println(nameCard);
		ImageView imageView = addCard(nameCard);
		bankerCards.getChildren().add(imageView);
		addItemToRoundResult("Banker Card: " + value + " of " + commandProcessingClass.getSuite(suite));
	}
	public void addCardsToPlayer (String value, String suite) {
		String nameCard = commandProcessingClass.getNameCard(value, suite);
		System.out.println(nameCard);
		ImageView imageView = addCard(nameCard);
		playerCards.getChildren().add(imageView);
		addItemToRoundResult("Player Card: " + value + " of " + commandProcessingClass.getSuite(suite));
	}
	// changing the port
	public void changePortMethod(ActionEvent e) throws IOException {
		clientConnection.socketClient.close();
		//get instance of the loader class
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ClientFXML.fxml"));
        Parent root = loader.load(); //load view into parent
        ClientController myctr = loader.getController();//get controller created by FXMLLoader
        setController(myctr);
        //setting the old scene with the new scene
        root3.getScene().setRoot(root);//update scene graph
        
	}
	
	// Exit the game
	public void exitMethod(ActionEvent e) throws IOException {
		clientConnection.socketClient.close();
		javafx.application.Platform.exit();
	}
	
	// betting for player or banker
	public void betPlayerMethod() {
		try {
			double amountBetPlayer = Double.parseDouble(amountBet.getText());
			if (amountBetPlayer <= 0) {
				alertNoAmount();
			}else {
				addItemToRoundResult("Client Bet on Player with : " + amountBetPlayer);
				chooseBet.setText("PLAYER");
				clientConnection.send("P_" + amountBet.getText());
			}
		} catch (Exception e1) {
			alertNoAmount();
		}
		amountBet.clear();
	}
	
	// betting for player or banker
	public void betBankerMethod() {
			
			double amountBetPlayer = Double.parseDouble(amountBet.getText());
			if (amountBetPlayer <= 0) {
				alertNoAmount();
			}else {
				addItemToRoundResult("Client Bet on Banker with : " + amountBetPlayer);
				chooseBet.setText("BANKER");
				clientConnection.send("B_" + amountBet.getText());
			}
			amountBet.clear();
		}
	
	// betting for player or banker
	public void betTieMethod() {
			double amountBetPlayer = Double.parseDouble(amountBet.getText());
			if (amountBetPlayer <= 0) {
				alertNoAmount();
			}else {
				addItemToRoundResult("Client Bet on Tie with : $" + amountBetPlayer);
				chooseBet.setText("TIE");
				clientConnection.send("T_" + amountBet.getText());
			}
			amountBet.clear();
			}

	public void clearCards() {
		playerCards.getChildren().clear();
		bankerCards.getChildren().clear();
		addItemToRoundResult("Card cleared");
	}
	//function to render client GUI
	public ClientController renderClientGUI() {
		
		//get instance of the loader class
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ClientFXML2.fxml"));
        Parent root2;
		try {
			root2 = loader.load();//load view into parent
			
	        ClientController myctr = loader.getController();//get controller created by FXMLLoader
	        //myctr.getRoundResultListView().getItems().add(roundResultListView.getItems().get(0));
	        
	        //setting the old scene with the new scene
	        root.getScene().setRoot(root2);//update scene graph
	        return myctr;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return this.myctr;
	}
	public void alertMethod() {
		Alert a = new Alert(AlertType.ERROR, "No server found, please try again");
		a.show();
	}
	public void alertNoAmount() {
		Alert a = new Alert(AlertType.WARNING, "The amount of money is not sufficient");
		a.show();
	}
	
}
