import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BaccaratDealer {
	ArrayList<Card> deck = new ArrayList<Card>();
	
	// generated a deck with cards
	public void generateDeck() {
		for(int i = 1; i < 14; i++) {
			for(int k = 0; k < 4; k++) {	
				Card myCard;
				if(k==0) myCard = new Card("C", i);
				else if (k==1) myCard = new Card("D", i);
				else if (k==2) myCard = new Card("H", i);
				else myCard = new Card("S", i);
				deck.add(myCard);
			}
		}
	}
	
	// returns a list of cards with two random cards
	// and it removes the rwo random cards off of
	// the deck
	public ArrayList<Card> dealHand() {
		ArrayList<Card> myArray = new ArrayList<Card>();
		int firstNumber, secondNumber;
		Random ran = new Random();
		firstNumber = ran.nextInt(deck.size());
		Card firstCard = new Card(deck.get(firstNumber).suite, deck.get(firstNumber).value);
		myArray.add(firstCard);
		deck.remove(firstNumber);
		secondNumber = ran.nextInt(deck.size());
		Card secondCard = new Card(deck.get(secondNumber).suite, deck.get(secondNumber).value);
		myArray.add(secondCard);
		deck.remove(secondNumber);
		return myArray;
	}
	
	// return a randomly drawn card
	// and removes it off of the deck
	public Card drawOne() {
		int firstCard = ThreadLocalRandom.current().nextInt(0, deck.size());
		Card card = new Card(deck.get(firstCard).suite, deck.get(firstCard).value);
		deck.remove(firstCard);
		return card;
	}
	
	// shuffles the order of the deck
	public void shuffleDeck() {
		generateDeck();
	    Random r = ThreadLocalRandom.current();
	    for (int i = 51; i > 0; i--)
	    {
	      int index = r.nextInt(i + 1);
	      // Simple swap
	      Card a = deck.get(index);
	      deck.set(index, deck.get(i));
	      deck.set(i, a);
	    }
	}
}
