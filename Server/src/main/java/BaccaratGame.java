import java.util.ArrayList;

public class BaccaratGame {
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;
	Double currentBet;
	Double totalWinnings;
	String betSide;
	BaccaratGameLogic logic;
	
	// default constructor
	public BaccaratGame() {
		playerHand = new ArrayList<Card>();
		bankerHand = new ArrayList<Card>();
		theDealer = new BaccaratDealer();
		currentBet = 0.0;
		totalWinnings = 0.0;
		logic = new BaccaratGameLogic();
		betSide = "";
	}
	
	// sets the values for the bet and the betSide
	public void setCurrentBet(Double bet, String betSide) {
		currentBet = bet;
		this.betSide = betSide;
	}
	
	// returns a value of the user winning
	public Double evaluateWinnings() {
		// I need to check what the user has entered
		if(logic.whoWon(playerHand, bankerHand) == "Player" && betSide == "Player") {
			totalWinnings += currentBet;
			return currentBet;
		}
		if(logic.whoWon(playerHand, bankerHand) == "Banker" && betSide == "Banker") {
			totalWinnings += currentBet *.95;
			return currentBet*.95;
		} else if(betSide == "Tie" && logic.whoWon(playerHand, bankerHand) == "Tie"){
			totalWinnings += currentBet * 7;
			return currentBet * 7;
		} else if(logic.whoWon(playerHand, bankerHand) == "Tie") {
			return 0.0;
		}
		totalWinnings -= currentBet;
		return 0-currentBet;
	}
}