import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class cardGUI implements ItemListener, ActionListener {
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


  public cardGUI() throws IOException {
    
    // Frame creation
    window = new JFrame("Card Game");

    // gameContainer creation
    gameContainer = new JPanel(new GridLayout(2, 3, 8, 8));

    // game info for player
    gameInfo = new JTextArea("Correct Guesses: " + wins + "\nTotal Attempts: " + count + "\nCards Left: " + cardsLeft);
    gameInfo.setEditable(false);

    // dropdown lists creation
    suits = new JComboBox<>(card.Suit.values());
    ranks = new JComboBox<>(card.Rank.values());  

    // dropdown listeners
    suits.addItemListener(this);
    ranks.addItemListener(this);

    //set variables to not be null
    suitGuess = suits.getSelectedItem().toString();
    rankGuess = ranks.getSelectedItem().toString();
    combinedGuess = rankGuess + " of " + suitGuess;

    // Instructions for game
    dropDownText = new JTextArea("Guess the next card from the suit and rank selection");
    dropDownText.setEditable(false);

    //Used to keep dropdowns together in the overall window grid
    dropDownContainer = new JPanel(new GridLayout(3, 6));
    dropDownContainer.add(ranks);
    dropDownContainer.add(new JLabel("of", JLabel.CENTER));
    dropDownContainer.add(suits);

    // set card pictures
    cardBack = new ImageIcon(ImageIO.read(new File("cards/b.gif")));
    cardImg = new ImageIcon();
    imgHolder = new JLabel(cardImg);
    
    // made button to guess
    buttonGo = new JButton("Click me to guess");
    buttonGo.addActionListener(this);

    // default closing operation and addition of gameContainer to window
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.add(gameContainer);

    //make game container grid and add components
    gameContainer.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
    gameContainer.add(new JLabel(cardBack));
    gameContainer.add(imgHolder);
    gameContainer.add(dropDownText);
    gameContainer.add(gameInfo);
    gameContainer.add(buttonGo);
    gameContainer.add(dropDownContainer);

    //change window size to components and set it to visible
    window.pack();
    window.setVisible(true);
  }

  // make sure all cards work
  private void printEntireDeck() {
    int n = 0;
    for (card c : deckOfCards.deck) {
      cardImg = new ImageIcon(deckOfCards.deck[n].getCardImage());
      gameContainer.add(new JLabel(cardImg));
      deckOfCards.deck[n] = c;
      n++;
    }
  }

  //method called when button is pressed
  @Override
  public void actionPerformed(ActionEvent e) {
    currentCard = cardGame.deck.deal();

    if (currentCard == null)
      return;
    //set image to current card
    if (cardsLeft > 0)
      cardImg.setImage(currentCard.getCardImage());

    //this was for testing
    System.out.println(currentCard.toString());
    System.out.println(combinedGuess);

    //checks the dropdowns to the currentcard
    if (combinedGuess.equalsIgnoreCase(currentCard.toString()))
      wins++;

    // refresh the label to change the image
    imgHolder.repaint();

    cardsLeft--;
    count++;
    gameInfo.setText("Correct Guesses: " + wins + "\nTotal Attempts: " + count + "\nCards Left: " + cardsLeft);
  }

  //called when a dropdown is changed
  @Override
  public void itemStateChanged(ItemEvent e) {
    Object source = e.getItemSelectable();
    //change if dropdown is used
    if(source == suits)
      suitGuess = suits.getSelectedItem().toString();
    //change if dropdown is used
    if(source == ranks)
      rankGuess = ranks.getSelectedItem().toString();
    //make combinedguess equal to new variables
    combinedGuess = rankGuess + " of " + suitGuess;
  }
}