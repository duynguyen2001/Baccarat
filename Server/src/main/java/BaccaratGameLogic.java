import java.util.ArrayList;

public class BaccaratGameLogic {
	
	// returns a string of the winner side
	public static String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		int total1 = handTotal(hand1);
		int total2 = handTotal(hand2);
		if(total1 == total2)
			return "Tie";
		if(total1 > total2)
			return "Player";
		return "Banker";
	}
	
	// returns the total value of the hand
	public static int handTotal(ArrayList<Card> hand) {
		int handValue = 0;
		for(int i = 0; i < hand.size(); i++) {
			int card = hand.get(i).value;
			if(card == 10 || card == 11 || card == 12 || card == 13) card = 0;
			handValue += card;
		}
		while(handValue >= 10) {
			String temp = String.valueOf(handValue);
			handValue = Integer.valueOf(temp.substring(1));
		}
		return handValue;
	}
	
	// returns wether the banker should or shouldn't withdraw another card
	public static boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		if(hand.size() == 2) {
			if(handTotal(hand) >= 7) {
				return false;
			} else if (handTotal(hand) < 3) {
				return true;
			} else if(handTotal(hand) > 6 && playerCard == null){
				return true;
			} else if(playerCard == null) {
				return false;
			} else if(handTotal(hand) > 4 && (playerCard.value == 0 || playerCard.value > 10)) {
				return true;
			}else if(handTotal(hand) > 4 && playerCard.value == 1) {
				return true;
			} else if(handTotal(hand) > 5 && playerCard.value == 2) {
				return true;
			}else if(handTotal(hand) > 5 && playerCard.value == 3) {
				return true;
			}else if(handTotal(hand) > 6 && playerCard.value == 4) {
				return true;
			}else if(handTotal(hand) > 6 && playerCard.value == 5) {
				return true;
			}else if(handTotal(hand) > 7 && playerCard.value == 6) {
				return true;
			}else if(handTotal(hand) > 7 && playerCard.value == 7) {
				return true;
			} else if(handTotal(hand) > 3 && playerCard.value == 8) {
				return true;
			} else if(handTotal(hand) > 4 && playerCard.value == 9) {
				return true;
			}
		}
		return false;
	}
	
	// returns wether the player should withdraw another card
	public static boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		if(hand.size() == 2) {
			if(handTotal(hand) < 6) {
				return true;
			}
		}
		return false;
	}
}
