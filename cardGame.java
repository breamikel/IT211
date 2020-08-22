import java.io.IOException;

public class cardGame {
	public static deckOfCards deck;

	public static void main(String[] args) throws IOException {
		deck = new deckOfCards();
		deck.shuffle();
		new cardGUI();
	}
}

