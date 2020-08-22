import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class deckOfCards {
	public static card[] deck;
	public static int currentCard; // index of the next card to be dealt

	public deckOfCards() throws IOException {
		deck = new card[52];
		int i = 0;
		//for loop through 4 suits
		for (card.Suit s : card.Suit.values()){
			// for loop through 13 ranks
			for (card.Rank r : card.Rank.values()){
				deck[i] = new card (r.getRank(),
					s.getSuit(),
					r.getRankValue(),
					ImageIO.read(new File("cards/" + r.getImgAbbr() + s.getImgAbbr() + ".gif")));                   
				i++;
			}
		}
		currentCard = 0;
	}

	public void shuffle() {
		//create randomizer variable
		Random rand = new Random();
		card temp;

		//loop through deck
		for(int i = 0; i < deck.length; i++) {
			//set variable equal to a random number
			int num = rand.nextInt(deck.length - 1);
			
			temp = deck[i];
			deck[i] = deck[num];
			deck[num] = temp;
		}
		currentCard = 0;
	}

	//returns current card and increments for the next time this is run
	public static card deal(){
		if (currentCard < deck.length) {
			//card Return Value
			card cRValue = deck[currentCard];
			currentCard++;
			return cRValue;
		} else{
			System.out.println("Out of cards error");
			return null;
		}
	}
}