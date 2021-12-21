import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyTest {

	BaccaratGame game;
	Card nine;
	Card jack;
	Card five;
	@BeforeEach
	void setUp() {
		game = new BaccaratGame();
		nine = new Card("H", 9);
		jack = new Card("H", 11);
		five = new Card("H", 5);
	}
	
	@Test
	void test1() {
		game.setCurrentBet(50.0, "Player");
		assertEquals(game.currentBet, 50);
	}
	
	@Test
	void test2() {
		game.setCurrentBet(50.0, "Player");
		assertEquals(game.betSide, "Player");
	}
	
	@Test
	void test3() {
		assertEquals(game.currentBet, 0);
	}
	
	@Test
	void test4() {
		assertEquals(game.betSide, "");
	}
	
	@Test
	void test5() {
		game.setCurrentBet(50.0, "Player");
		game.bankerHand.add(nine);
		game.bankerHand.add(jack);
		game.playerHand.add(jack);
		game.playerHand.add(jack);
		assertEquals(-50.0, game.evaluateWinnings());
	}
	
	@Test
	void test6() {
		game.setCurrentBet(50.0, "Player");
		game.bankerHand.add(nine);
		game.bankerHand.add(jack);
		game.playerHand.add(jack);
		game.playerHand.add(jack);
		assertEquals(-50.0, game.evaluateWinnings());
	}
	
	@Test
	void test7() {
		game.setCurrentBet(50.0, "Banker");
		game.bankerHand.add(nine);
		game.bankerHand.add(jack);
		game.playerHand.add(jack);
		game.playerHand.add(jack);
		assertEquals(47.5, game.evaluateWinnings());
	}
	
	@Test
	void test8() {
		game.setCurrentBet(50.0, "Tie");
		game.bankerHand.add(nine);
		game.bankerHand.add(jack);
		game.playerHand.add(nine);
		game.playerHand.add(jack);
		assertEquals(350.0, game.evaluateWinnings());
	}
	
	@Test
	void test9() {
		game.setCurrentBet(50.0, "Banker");
		game.bankerHand.add(nine);
		game.bankerHand.add(jack);
		game.playerHand.add(jack);
		game.playerHand.add(jack);
		game.evaluateWinnings();
		assertEquals(50*.95, game.totalWinnings);
	}

	@Test
	void test10() {
		game.setCurrentBet(50.0, "Player");
		game.bankerHand.add(nine);
		game.bankerHand.add(jack);
		game.playerHand.add(jack);
		game.playerHand.add(jack);
		game.evaluateWinnings();
		assertEquals(-50, game.totalWinnings);
	}
	
	@Test
	void test11() {
		game.setCurrentBet(50.0, "Player");
		game.theDealer.generateDeck();
		game.bankerHand.clear();
		ArrayList<Card> temp = game.theDealer.dealHand();
		for(int i = 0; i < temp.size(); i++) {
			game.bankerHand.add(temp.get(i));
		}
		game.bankerHand = game.theDealer.dealHand();
		game.playerHand.add(jack);
		game.playerHand.add(jack);
		if(game.logic.evaluatePlayerDraw(game.playerHand)) {
			Card drawnCard = game.theDealer.drawOne();
    		game.playerHand.add(drawnCard);
		}
		game.evaluateWinnings();
		assertEquals(3, game.playerHand.size());
	}
	
	@Test
	void test12() {
		game.setCurrentBet(50.0, "Player");
		game.theDealer.generateDeck();
		game.bankerHand.add(jack);
		game.bankerHand.add(jack);
		game.playerHand = game.theDealer.dealHand();
		if(game.logic.evaluateBankerDraw(game.bankerHand, null)) {
			Card drawnCard = game.theDealer.drawOne();
    		game.bankerHand.add(drawnCard);
		}
		game.evaluateWinnings();
		assertEquals(3, game.bankerHand.size());
	}
	
	@Test
	void test13() {
		game.setCurrentBet(50.0, "Player");
		game.theDealer.generateDeck();
		game.bankerHand.clear();
		ArrayList<Card> temp = game.theDealer.dealHand();
		for(int i = 0; i < temp.size(); i++) {
			game.bankerHand.add(temp.get(i));
		}
		game.bankerHand = game.theDealer.dealHand();
		game.playerHand.add(nine);
		game.playerHand.add(jack);
		if(game.logic.evaluatePlayerDraw(game.playerHand)) {
			Card drawnCard = game.theDealer.drawOne();
    		game.playerHand.add(drawnCard);
		}
		game.evaluateWinnings();
		assertEquals(2, game.playerHand.size());
	}
	
	@Test
	void test14() {
		game.setCurrentBet(50.0, "Player");
		game.theDealer.generateDeck();
		game.bankerHand.add(nine);
		game.bankerHand.add(jack);
		game.playerHand = game.theDealer.dealHand();
		if(game.logic.evaluateBankerDraw(game.bankerHand, null)) {
			Card drawnCard = game.theDealer.drawOne();
    		game.bankerHand.add(drawnCard);
		}
		game.evaluateWinnings();
		assertEquals(2, game.bankerHand.size());
	}
	
	@Test
	void test15() {
		game.setCurrentBet(50.0, "Player");
		game.theDealer.generateDeck();
		game.bankerHand.add(five);
		game.bankerHand.add(jack);
		game.playerHand = game.theDealer.dealHand();
		if(game.logic.evaluateBankerDraw(game.bankerHand, null)) {
			Card drawnCard = game.theDealer.drawOne();
    		game.bankerHand.add(drawnCard);
		}
		game.evaluateWinnings();
		assertEquals(2, game.bankerHand.size());
	}
	
	@Test
	void test16() {
		game.setCurrentBet(50.0, "Player");
		game.theDealer.generateDeck();
		game.bankerHand.add(five);
		game.bankerHand.add(jack);
		game.playerHand = game.theDealer.dealHand();
		if(game.logic.evaluateBankerDraw(game.bankerHand, jack)) {
			Card drawnCard = game.theDealer.drawOne();
    		game.bankerHand.add(drawnCard);
		}
		game.evaluateWinnings();
		assertEquals(3, game.bankerHand.size());
	}
	
	@Test
	void test17() {
		game.bankerHand.add(five);
		game.bankerHand.add(jack);
		assertEquals(5, game.logic.handTotal(game.bankerHand));
	}
	
	@Test
	void test18() {
		game.bankerHand.add(five);
		game.bankerHand.add(five);
		assertEquals(0, game.logic.handTotal(game.bankerHand));
	}
	
	@Test
	void test19() {
		game.bankerHand.add(nine);
		game.bankerHand.add(nine);
		assertEquals(8, game.logic.handTotal(game.bankerHand));
	}
	
	@Test
	void test20() {
		game.theDealer.generateDeck();
		assertEquals(52, game.theDealer.deck.size());
	}
}
