import java.awt.BorderLayout;
import java.io.IOException;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class cardGameGUI implements ItemListener, ActionListener {
    private static cardDeck deck;
    private ImageIcon cardBack;
    private ImageIcon cardImg;
    private JTextArea dropDownText;
    private JFrame window;
    private JPanel gameContainer;
    private JPanel dropDownContainer;
    private JTextArea gameInfo;
    private JComboBox suits;
    private JComboBox ranks;
    private JButton buttonGo;
    private String suitGuess;
    private String rankGuess;
    private String combinedGuess;
    private JLabel imgHolder;
    private card currentCard;

    public int wins = 0;
    public int count = 0;
    public int cardsLeft = 52;


    public cardGameGUI() throws IOException {

        //frame
        window = new JFrame("Card Game");
        
        //gameContainer
        gameContainer = new JPanel(new GridLayout(3, 3, 10, 10));

        // game info
        gameInfo = new JTextArea("Correct Guesses: " + wins + "\nTotal Attempts: " + count + "\nCards Left: " + cardsLeft);
        gameInfo.setEditable(false);

        // dropdown list
        suits = new JComboBox<>(card.Suit.values());
        ranks = new JComboBox<>(card.Rank.values());

        // dropdown listeners
        suits.addItemListener(this);
        ranks.addItemListener(this);


        suitGuess = suits.getSelectedItem().toString();
        rankGuess = ranks.getSelectedItem().toString();
        combinedGuess = rankGuess + " of " + suitGuess;

        // Game instructions
        dropDownText = new JTextArea("Guess the next card");
        dropDownText.setEditable(false);

        //keeps dropdowns together
        dropDownContainer = new JPanel(new GridLayout(1, 3));
        dropDownContainer.add(ranks);
        dropDownContainer.add(new JLabel("of", JLabel.CENTER));
        dropDownContainer.add(suits);

        // set card img
        cardBack = new ImageIcon(ImageIO.read(new File("cards/b.gif")));
        cardImg = new ImageIcon();
        imgHolder = new JLabel(cardImg);

        // create button to guess
        buttonGo = new JButton("Click me to guess!");
        buttonGo.addActionListener(this);

        // default closing operation and addition of gameContainer to window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(gameContainer);

        //setting up whole game container as a grid and add components
        gameContainer.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        gameContainer.add(new JLabel(cardBack));
        gameContainer.add(imgHolder);
        gameContainer.add(dropDownText);
        gameContainer.add(gameInfo);
        gameContainer.add(buttonGo);
        gameContainer.add(dropDownContainer);

        //changes window size to components and sets to visible
        window.pack();
        window.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        currentCard = cardGameGUI.deck.deal();

        if (currentCard == null)
            return;
        //sets the image to current card
        if (cardsLeft > 0)
            cardImg.setImage(currentCard.getCardImage());


        if (combinedGuess.equalsIgnoreCase(currentCard.toString()))
            wins++;


        imgHolder.repaint();

        cardsLeft--;
        count++;
        gameInfo.setText("Correct Guesses: " + wins + "\nTotal Attempts: " + count + "\nCards Left: " + cardsLeft);
    }

    //called when dropdown is changed
    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        //change if dropdown is used
        if(source == suits)
            suitGuess = suits.getSelectedItem().toString();
        //change if dropdown is used
        if(source == ranks)
            rankGuess = ranks.getSelectedItem().toString();
        //makes combinedguess equal to new variables
        combinedGuess = rankGuess + " of " + suitGuess;
    }
}
