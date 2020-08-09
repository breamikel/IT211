import java.io.IOException;

public class cardGame {
    public static cardDeck deck;

    public static void main(String[] args) throws IOException {
        deck = new cardDeck();
        deck.shuffle();
        new cardGameGUI();
    }
}
