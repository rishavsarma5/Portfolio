import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// to represent a Card in a standard deck of 52 cards
class Card {
  int rank;
  String suit;
  WorldImage cardImage;

  Card(int rank, String suit) {
    this.rank = rank;
    this.suit = suit;
    this.cardImage = new RectangleImage(35, 68, "outline", Color.black);
  }

  // EFFECT: sets this.cardImage to a drawn WorldImage representing the Card with its rank and suit
  public void drawACard() {
    TextImage rankText = new TextImage("" + this.rank + " " + this.suit, 15, this.suitColor());
    this.cardImage = new OverlayImage(rankText, this.cardImage);
  }

  // returns correct color for the suit (black for clubs and spades, red for hearts and diamonds)
  public Color suitColor() {
    if (this.suit.equals("♣") || this.suit.equals("♠")) {
      return Color.black;
    }
    else {
      return Color.red;
    }
  }

  // EFFECT: sets this.cardImage to a drawn WorldImage representing a matched card out of play
  public void removeACard() {
    this.cardImage = new RectangleImage(35, 68, "solid", Color.white);
  }

  // does this Card's rank and suit color equal the given Card's rank and suit color? 
  // (are they a match?)
  public boolean cardEquality(Card aCard) {
    if (this.suit.equals("♣") || this.suit.equals("♠")) {
      return this.rank == aCard.rank && (aCard.suit.equals("♣") || aCard.suit.equals("♠"));
    }
    else {
      return this.rank == aCard.rank && (aCard.suit.equals("♦") || aCard.suit.equals("♥"));
    }
  }

  // EFFECT: sets this.cardImage to face-down state
  public void faceDown() {
    this.cardImage = new RectangleImage(35, 68, "outline", Color.black);
  }

  // EFFECT: draws a faceUp (flipped) card as faceDown
  public void flipDown() {
    if (this.cardImage.equals(new RectangleImage(35, 68, "outline", Color.black)) ||
        this.cardImage.equals(new RectangleImage(35, 68, "solid", Color.white))) {
      // does nothing
    }
    else {
      this.faceDown();
    }
  }

  // EFFECT: draws a faceDown card as faceUp (flipped)
  public void flipUp() {
    if (this.cardImage.equals(new RectangleImage(35, 68, "outline", Color.black))) {
      this.drawACard();
    }
  }

  // is this card currently faceUp (flipped)?
  public boolean isFaceUp() {
    return !(this.cardImage.equals(new RectangleImage(35, 68, "solid", Color.white)) ||
        this.cardImage.equals(new RectangleImage(35, 68, "outline", Color.black)));
  }
}

// to represent the game Concentration
class Concentration extends World {
  ArrayList<Card> cards;
  int score;
  int minutes;
  int seconds;
  int stepsLeft;

  // the constructor called to randomize the deck of cards at beginning of game
  Concentration() {
    this.cards = new Util(new ArrayList<Card>(52)).initializeDeck();
    this.score = 26;
    this.minutes = 0;
    this.seconds = 0;
    this.stepsLeft = 150;
  }

  // constructor for Concentration in the middle of the game
  Concentration(ArrayList<Card> cards) {
    this.cards = cards;
    this.score = 26;
    this.minutes = 0;
    this.seconds = 0;
    this.stepsLeft = 150;
  }

  // constructor for Concentration to test Random using seed
  Concentration(Random seed) {
    this.cards = new Util(new ArrayList<Card>(52), seed).initializeDeck();
  }

  // EFFECT: resets the game when "r" is pressed
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      Concentration reset = new Concentration();
      this.cards = reset.cards;
    }
  }

  // EFFECT: helps for testing onKeyEvent
  public void onKeyEventTester(String key, Random seed) {
    if (key.equals("r")) {
      Concentration reset = new Concentration(seed);
      this.cards = reset.cards;
    }
  }

  // creates the score as an WorldImage to be drawn in the WorldScene
  public WorldImage scoreImage() {
    TextImage scoreText = new TextImage("" + "Score: " + this.score, 25, Color.red);
    return scoreText;
  }

  // creates the timer as an WorldImage to be drawn in the WorldScene
  public WorldImage timerImage() {
    if (this.seconds > 9 && this.minutes > 0) {
      TextImage timerText = new TextImage("" + "Time Elapsed: " + this.minutes 
          + ":" + this.seconds, 25, Color.black);
      return timerText;
    }
    else if (this.seconds < 10 && this.minutes > 0) {
      TextImage timerText = new TextImage("" + "Time Elapsed: " + this.minutes 
          + ":0" + this.seconds, 25, Color.black);
      return timerText;
    }
    else { 
      TextImage timerText = new TextImage("" + "Time Elapsed: " + "" 
          + this.seconds, 25, Color.black);
      return timerText;
    }
  }

  // creates the steps left as an WorldImage to be drawn in the WorldScene
  public WorldImage stepImage() {
    return new TextImage("Steps Left: " + "" + this.stepsLeft, 25, Color.blue);
  }

  // renders a WorldScene with current deck of Cards
  public WorldScene makeScene() {
    WorldScene currScene = new WorldScene(560, 400);
    this.renderBG(currScene);
    currScene.placeImageXY(this.scoreImage(), 80, 350);
    currScene.placeImageXY(this.timerImage(), 440, 350);
    currScene.placeImageXY(this.stepImage(), 245, 350);

    return currScene;
  }

  // EFFECT: determines if the given posn where the mouse clicked is on a Card in the scene
  // and flips the card to be faceUp if it is not already
  // does not do anything if Card is matched or already faceUp
  public void findCoord(Posn pos) {
    boolean active = true;
    int index = 0;
    int xacc = 0;
    int yacc = 0;

    while (active) {
      for (int i = 0; i < 13; i += 1) {
        xacc += 40;
        yacc = 0;
        for (int j = 0; j < 4; j += 1) {
          yacc += 70;

          if ((Math.abs(xacc - pos.x) < 17) && (Math.abs(yacc - pos.y) < 35)) {
            this.cards.get(index).flipUp();
            active = false;
          }
          else {
            index += 1;

            if (index == 52) {
              active = false;
            }
          }
        }
      }
    }
  }

  // EFFECT: contains all commands for mouse clicks
  public void onMousePressed(Posn pos) {
    if (new Util(this.cards).oneOrZero()) {
      this.findCoord(pos);
    }
    else {
      this.matchOrFlip();
      this.stepsLeft--;
    }
  }

  // draws the winning message if the score is 0, meaning the player has found all matches
  // also draws the losing message if the stepLeft is 0 before the score is 0
  public WorldScene lastScene(String msg) {
    if (msg.equals("You Win!")) {
      WorldScene currScene = new WorldScene(560, 400);
      WorldImage winnerText = new TextImage(msg, 50, FontStyle.BOLD, Color.GREEN);
      currScene.placeImageXY(winnerText, 280, 200);
      return currScene;
    }
    else  {
      WorldScene currScene = new WorldScene(560, 400);
      WorldImage loserText = new TextImage(msg, 50, FontStyle.BOLD, Color.RED);
      currScene.placeImageXY(loserText, 280, 200);
      return currScene;
    }
  }

  // renders the WorldScene with updated deck of Cards
  void renderBG(WorldScene scene) {
    int xacc = 0;
    int yacc = 0;
    int ind = 0;

    if (this.score == 0) {
      this.endOfWorld("You Win!");
    }
    else if (this.stepsLeft == 0) {
      this.endOfWorld("You Lose");
    }
    else {
      for (int i = 0; i < 13; i += 1) {
        xacc += 40;
        yacc = 0;
        for (int j = 0; j < 4; j += 1) {
          yacc += 70;
          scene.placeImageXY(cards.get(ind).cardImage, xacc, yacc);
          ind += 1;
        }
      }
    }
  }

  // flips all cards still in play faceDown
  void flipAllDown() {
    int ind = 0;

    for (int j = 0; j < 52; j += 1) {
      this.cards.get(ind).flipDown();
      ind += 1;
    }
  }

  // returns list of cards of all faceUp (flipped) cards in deck in the scene
  // also throws error if size of filtered list is greater than 2
  ArrayList<Card> filterFaceUp() {
    ArrayList<Card> filtered = new ArrayList<Card>(2);

    for (int i = 0; i < this.cards.size(); i += 1) {
      if (this.cards.get(i).isFaceUp()) {
        filtered.add(this.cards.get(i));

        if (filtered.size() > 2) {
          throw new RuntimeException("Too many cards are face up");
        }
      }
    }
    return filtered;
  }

  // EFFECT: determines if the two faceUp cards in the list are matches or not
  //         - draws cards as matched if they are and decreases score by 1
  //         - draws each card as faceDown if they are not matches
  void matchOrFlip() {
    if (this.filterFaceUp().get(0).cardEquality(this.filterFaceUp().get(1))) {
      this.filterFaceUp().get(1).removeACard();
      this.filterFaceUp().get(0).removeACard();
      this.score -= 1;
    }
    else {
      this.flipAllDown();
    }
  }

  // EFFECT: adds 1 to time every tick (1 second). Resets seconds at 60, and adds to minutes
  public void onTick() {
    if (this.seconds == 59) {
      this.minutes++;
      this.seconds = 0;
    }
    else {
      this.seconds++;
    }
  }
}

// to represent the utility methods for ArrayList
class Util {
  ArrayList<Card> cards;
  ArrayList<String> suits;
  ArrayList<Card> template;

  // creates standard deck of cards
  Util(ArrayList<Card> cards) {
    this.cards = cards;
    this.suits = new ArrayList<String>(Arrays.asList("♣", "♠", "♦", "♥"));
    this.template = new ArrayList<Card>(52);

    for (int i = 0; i < 4; i += 1) {
      for (int j = 1; j < 14; j += 1) {
        Card temp = new Card(j, suits.get(i));
        template.add(temp);
      }
    }
    this.seed = new Random();
  }

  // created for testing with Random with set seed
  Random seed = new Random();

  // constructor for testing Random Generation with given seed
  Util(ArrayList<Card> cards, Random seed) {
    this.cards = cards;
    this.suits = new ArrayList<String>(Arrays.asList("♣", "♠", "♦", "♥"));
    this.template = new ArrayList<Card>(52);

    for (int i = 0; i < 4; i += 1) {
      for (int j = 1; j < 14; j += 1) {
        Card temp = new Card(j, suits.get(i));
        template.add(temp);
      }
    }
    this.seed = seed;
  }

  // A random number generator that returns an int between 0 and i exclusive
  public int encodingInt(int i) {
    Random rand = seed;
    int randInt = rand.nextInt(i);
    return randInt;
  }

  // returns a randomly ordered deck of cards to be rendered at start of the game
  ArrayList<Card> initializeDeck() {
    if (this.template.size() <= 0) {
      return this.cards;
    }
    else {
      int i = this.encodingInt(this.template.size());
      Card tempCard = this.template.remove(i);
      this.cards.add(tempCard);
      return this.initializeDeck();
    }
  }

  // are there less than 2 cards in the scene that are faceUp (flipped)?
  public boolean oneOrZero() {
    int acc = 0;
    for (int i = 0; i < this.cards.size(); i += 1) {
      if (this.cards.get(i).isFaceUp()) {
        acc += 1;
      }
    }
    return acc < 2;
  }
}

// examples for Concentration
class ExamplesConcentration {
  Card AceOfSpades;
  Card c1;
  Card c2;
  Card c3;
  ArrayList<Card> starter;
  ArrayList<Card> starter2;
  Util randTest;
  Util randTest2;
  Util utilTest;
  Concentration empty;
  WorldScene emptyScene;
  Concentration randTester;
  Concentration randTester2;

  // EFFECT: sets the data to its initial conditions
  void initData() {
    this.starter = new ArrayList<Card>(52);
    this.AceOfSpades = new Card(1, "♠");
    this.c1 = new Card(1, "♦");
    this.c2 = new Card(5, "♣");
    this.c3 = new Card(10, "♣");
    starter.add(AceOfSpades);
    this.starter2 = new Util(new ArrayList<Card>(52)).initializeDeck();
    this.randTest2 = new Util(new ArrayList<Card>(52), new Random(21));
    this.randTest = new Util(new ArrayList<Card>(52), new Random(8));
    this.utilTest = new Util(new ArrayList<Card>(52));
    this.empty = new Concentration(this.starter);
    this.emptyScene = new WorldScene(560, 350);
    this.randTester = new Concentration(new Random(8));
    this.randTester2 = new Concentration(new Random(21));
  }

  // EFFECT: creates Concentration example when two cards are flipped and some are matched
  void initData2() {
    this.initData();
    ArrayList<Card> mutableFull = new ArrayList<Card>(52);
    for (int i = 0; i < 52; i += 25) {
      Card mutable = this.starter2.get(i);
      mutable.drawACard();
      mutableFull.add(mutable);  
    }

    for (int i = 0; i < 52; i += 7) {
      Card mutable = this.starter2.get(i);
      mutable.removeACard();
      mutableFull.add(mutable);  
    }
    this.starter.clear();
    this.starter.addAll(mutableFull);
  }

  // EFFECT: creates Concentration example when all cards are matched
  void initData3() {
    this.initData();
    ArrayList<Card> mutableFull = new ArrayList<Card>(52);
    for (int i = 0; i < 52; i += 1) {
      Card mutable = this.starter2.get(i);
      mutable.removeACard();
      mutableFull.add(mutable);  
    }
    this.starter.clear();
    this.starter.addAll(mutableFull);
  }

  // EFFECT: creates Concentration example when all cards are flipped up
  void initData4() {
    this.initData();
    ArrayList<Card> mutableFull = new ArrayList<Card>(52);
    for (int i = 0; i < 52; i += 1) {
      Card mutable = this.starter2.get(i);
      mutable.drawACard();
      mutableFull.add(mutable);  
    }
    this.starter.clear();
    this.starter.addAll(mutableFull);
  }

  // tests for Card constructor
  void testCardConstructor(Tester t) {
    this.initData();
    t.checkExpect(this.AceOfSpades.cardImage, new RectangleImage(35, 68, "outline", Color.black));
    t.checkExpect(new Card(5, "♠").cardImage, new RectangleImage(35, 68, "outline", Color.black));
  }

  // tests for drawACard
  void testDrawACard(Tester t) {
    this.initData();
    this.AceOfSpades.drawACard();
    Card testietest = new Card(5, "♠");
    TextImage AceText = new TextImage("" + this.AceOfSpades.rank + " "
        + this.AceOfSpades.suit, 15, this.AceOfSpades.suitColor());
    TextImage Text5 = new TextImage("" + 5 + " " + "♠", 15, Color.black);
    WorldImage outline = new RectangleImage(35, 68, "outline", Color.black);
    testietest.drawACard();
    t.checkExpect(this.AceOfSpades.cardImage,
        new OverlayImage(AceText, outline));
    t.checkExpect(testietest.cardImage, new OverlayImage(Text5, outline));
  }

  // tests for suitColor
  void testSuitColor(Tester t) {
    Card red = new Card(5, "♦");
    Card red2 = new Card(5, "♥");
    Card black = new Card(5, "♣");
    Card black2 = new Card(5, "♠");
    t.checkExpect(red.suitColor(), Color.red);
    t.checkExpect(red2.suitColor(), Color.red);
    t.checkExpect(black.suitColor(), Color.black);
    t.checkExpect(black2.suitColor(), Color.black);
  }

  // tests for removeACard
  void testRemoveACard(Tester t) {
    this.initData();
    this.AceOfSpades.removeACard();
    Card testing123 = new Card(11, "♦");
    testing123.removeACard();
    WorldImage blank = new RectangleImage(35, 68, "solid", Color.white);
    t.checkExpect(testing123.cardImage, blank);
    t.checkExpect(this.AceOfSpades.cardImage, blank);
  }

  // tests for cardEquality
  void testCardEquality(Tester t) {
    this.initData();
    t.checkExpect(this.AceOfSpades.cardEquality(this.c1), false);
    t.checkExpect(this.c2.cardEquality(this.c3), false);
    Card a1 = new Card(1, "♣");
    t.checkExpect(this.AceOfSpades.cardEquality(a1), true);
  }

  // tests for faceDown
  void testFaceDown(Tester t) {
    this.initData();
    this.AceOfSpades.drawACard();
    t.checkExpect(this.AceOfSpades.cardImage, new OverlayImage(
        new TextImage("1 ♠", 15, Color.black), new RectangleImage(35, 68, "outline", Color.black)));
    this.AceOfSpades.faceDown();
    t.checkExpect(this.AceOfSpades.cardImage, new RectangleImage(35, 68, "outline", Color.black));

    this.c1.drawACard();
    t.checkExpect(this.c1.cardImage, new OverlayImage(
        new TextImage("1 ♦", 15, Color.red), new RectangleImage(35, 68, "outline", Color.black)));
    this.c1.faceDown();
    t.checkExpect(this.c1.cardImage, new RectangleImage(35, 68, "outline", Color.black));
  }

  // tests for flipDown
  void testFlipDown(Tester t) {
    this.initData();
    this.AceOfSpades.drawACard();
    t.checkExpect(this.AceOfSpades.cardImage, new OverlayImage(
        new TextImage("1 ♠", 15, Color.black), new RectangleImage(35, 68, "outline", Color.black)));
    this.AceOfSpades.flipDown();
    t.checkExpect(this.AceOfSpades.cardImage, new RectangleImage(35, 68, "outline", Color.black));

    this.c2.removeACard();
    t.checkExpect(this.c2.cardImage, new RectangleImage(35, 68, "solid", Color.white));
    this.c2.flipDown();
    t.checkExpect(this.c2.cardImage, new RectangleImage(35, 68, "solid", Color.white));

    t.checkExpect(this.c3.cardImage, new RectangleImage(35, 68, "outline", Color.black));
    this.c3.flipDown();
    t.checkExpect(this.c3.cardImage, new RectangleImage(35, 68, "outline", Color.black));
  }

  // tests for flipUp
  void testFlipUp(Tester t) {
    this.initData();
    t.checkExpect(this.AceOfSpades.cardImage, new RectangleImage(35, 68, "outline", Color.black));
    this.AceOfSpades.flipUp();
    t.checkExpect(this.AceOfSpades.cardImage, new OverlayImage(
        new TextImage("1 ♠", 15, Color.black), new RectangleImage(35, 68, "outline", Color.black)));

    this.c2.removeACard();
    t.checkExpect(this.c2.cardImage, new RectangleImage(35, 68, "solid", Color.white));
    this.c2.flipUp();
    t.checkExpect(this.c2.cardImage, new RectangleImage(35, 68, "solid", Color.white));

    this.c1.drawACard();
    t.checkExpect(this.c1.cardImage, new OverlayImage(
        new TextImage("1 ♦", 15, Color.red), new RectangleImage(35, 68, "outline", Color.black)));
    this.c1.flipUp();
    t.checkExpect(this.c1.cardImage, new OverlayImage(
        new TextImage("1 ♦", 15, Color.red), new RectangleImage(35, 68, "outline", Color.black)));
  }

  // tests for isFaceUp
  void testIsFaceUp(Tester t) {
    this.initData();
    t.checkExpect(this.AceOfSpades.isFaceUp(), false);
    this.AceOfSpades.drawACard();
    t.checkExpect(this.AceOfSpades.isFaceUp(), true);

    t.checkExpect(this.c3.isFaceUp(), false);
    this.c3.flipDown();
    t.checkExpect(this.c3.isFaceUp(), false);
  }

  // tests for Concentration constructors
  void testConcConstructor(Tester t) {
    Concentration tl = new Concentration(new Random(8));
    Concentration t2 = new Concentration(new Random(21));
    ArrayList<Card> ans1 = new ArrayList<Card>(Arrays.asList(
        new Card(2, "♥"), new Card(3, "♥"), new Card(5, "♦"), new Card(3, "♦"), new Card(13, "♦"),
        new Card(9, "♦"), new Card(6, "♦"), new Card(1, "♦"), new Card(6, "♣"), new Card(12, "♠"),
        new Card(8, "♥"), new Card(10, "♣"), new Card(13, "♠"), new Card(10, "♦"),
        new Card(12, "♥"), new Card(9, "♠"), new Card(8, "♣"), new Card(6, "♥"), new Card(4, "♦"),
        new Card(10, "♠"), new Card(12, "♣"), new Card(5, "♣"), new Card(1, "♠"), new Card(11, "♣"),
        new Card(2, "♦"), new Card(10, "♥"), new Card(4, "♣"), new Card(8, "♦"), new Card(11, "♦"),
        new Card(4, "♠"), new Card(13, "♣"), new Card(8, "♠"), new Card(7, "♦"), new Card(2, "♠"),
        new Card(12, "♦"), new Card(7, "♠"), new Card(3, "♣"), new Card(2, "♣"), new Card(11, "♠"),
        new Card(7, "♣"), new Card(1, "♥"), new Card(6, "♠"), new Card(11, "♥"), new Card(4, "♥"),
        new Card(9, "♥"), new Card(9, "♣"), new Card(7, "♥"), new Card(5, "♠"), new Card(1, "♣"),
        new Card(5, "♥"), new Card(3, "♠"), new Card(13, "♥")));
    ArrayList<Card> ans2 = new ArrayList<Card>(Arrays.asList(
        new Card(10, "♠"), new Card(6, "♠"), new Card(9, "♠"), new Card(1, "♥"), new Card(9, "♣"),
        new Card(12, "♠"), new Card(12, "♥"), new Card(7, "♠"), new Card(2, "♣"), new Card(9, "♥"),
        new Card(1, "♠"), new Card(5, "♦"), new Card(3, "♣"), new Card(5, "♠"),
        new Card(5, "♣"), new Card(3, "♥"), new Card(8, "♣"), new Card(9, "♦"), new Card(6, "♥"),
        new Card(3, "♠"), new Card(10, "♥"), new Card(10,  "♦"), new Card(4, "♦"),
        new Card(12, "♣"), new Card(13, "♥"), new Card(8, "♥"), new Card(10, "♣"),
        new Card(6, "♣"), new Card(3, "♦"), new Card(11, "♦"), new Card(2, "♠"), new Card(12, "♦"),
        new Card(7, "♥"), new Card(5, "♥"), new Card(11, "♣"), new Card(4, "♠"), new Card(13, "♦"),
        new Card(7, "♣"), new Card(1, "♣"), new Card(13, "♠"), new Card(11, "♥"), new Card(7, "♦"),
        new Card(8, "♠"), new Card(4, "♥"), new Card(2, "♥"), new Card(8, "♦"), new Card(6, "♦"),
        new Card(13, "♣"), new Card(1, "♦"), new Card(4, "♣"), new Card(2, "♦"), new Card(11,"♠")));
    t.checkExpect(tl.cards, ans1);
    t.checkExpect(t2.cards, ans2);
  }

  // tests for onKeyEvent
  void testOnKeyEvent(Tester t) {
    this.initData();
    t.checkExpect(this.randTester.cards, this.randTest.initializeDeck());
    this.randTester.onKeyEventTester("r", new Random(21));
    t.checkExpect(this.randTester.cards, new ArrayList<Card>(Arrays.asList(
        new Card(10, "♠"), new Card(6, "♠"), new Card(9, "♠"), new Card(1, "♥"), new Card(9, "♣"),
        new Card(12, "♠"), new Card(12, "♥"), new Card(7, "♠"), new Card(2, "♣"), new Card(9, "♥"),
        new Card(1, "♠"), new Card(5, "♦"), new Card(3, "♣"), new Card(5, "♠"),
        new Card(5, "♣"), new Card(3, "♥"), new Card(8, "♣"), new Card(9, "♦"), new Card(6, "♥"),
        new Card(3, "♠"), new Card(10, "♥"), new Card(10,  "♦"), new Card(4, "♦"),
        new Card(12, "♣"), new Card(13, "♥"), new Card(8, "♥"), new Card(10, "♣"),
        new Card(6, "♣"), new Card(3, "♦"), new Card(11, "♦"), new Card(2, "♠"), new Card(12, "♦"),
        new Card(7, "♥"), new Card(5, "♥"), new Card(11, "♣"), new Card(4, "♠"), new Card(13, "♦"),
        new Card(7, "♣"), new Card(1, "♣"), new Card(13, "♠"), new Card(11, "♥"), new Card(7, "♦"),
        new Card(8, "♠"), new Card(4, "♥"), new Card(2, "♥"), new Card(8, "♦"), new Card(6, "♦"),
        new Card(13, "♣"), new Card(1, "♦"), new Card(4, "♣"), new Card(2, "♦"),
        new Card(11,"♠"))));

    t.checkExpect(this.randTester2.cards, this.randTest2.initializeDeck());
    this.randTester2.onKeyEventTester("g", new Random(21));
    t.checkExpect(this.randTester2.cards, this.randTest2.initializeDeck());
  }

  // tests for scoreImage
  void testScoreImage(Tester t) {
    Concentration cons = new Concentration();
    t.checkExpect(cons.scoreImage(), new TextImage("Score: 26", 25, Color.red));
    Concentration tl = new Concentration(new Random(8));
    tl.score = 20;
    t.checkExpect(tl.scoreImage(), new TextImage("Score: 20", 25, Color.red));  
  }

  // tests for timerImage
  void testTimerImage(Tester t) {
    Concentration cons = new Concentration();
    WorldImage first =  new TextImage("Time Elapsed: 0", 25, Color.black);
    WorldImage sec =  new TextImage("Time Elapsed: 2:30", 25, Color.black);
    WorldImage thir = new TextImage("Time Elapsed: 30", 25, Color.black);
    t.checkExpect(cons.timerImage(), first);
    cons.seconds = 30;
    t.checkExpect(cons.timerImage(), thir);
    cons.minutes = 2;
    t.checkExpect(cons.timerImage(), sec);
  }

  // tests for stepImage
  void testStepImage(Tester t) {
    Concentration conc = new Concentration();
    WorldImage test = new TextImage("Steps Left: 150", 25, Color.blue);
    t.checkExpect(conc.stepImage(), test);
    conc.stepsLeft = 45;
    WorldImage test2 = new TextImage("Steps Left: 45", 25, Color.blue);
    t.checkExpect(conc.stepImage(), test2);
  }

  // tests for makeScene
  void testMakeScene(Tester t) {
    this.initData2();
    Concentration tester = new Concentration(this.starter2);
    WorldScene testScene = new WorldScene(560, 400);
    int xacc = 0;
    int yacc = 0;
    int ind = 0;

    for (int i = 0; i < 13; i += 1) {
      xacc += 40;
      yacc = 0;
      for (int j = 0; j < 4; j += 1) {
        yacc += 70;
        testScene.placeImageXY(this.starter2.get(ind).cardImage, xacc, yacc);
        ind += 1;
      }
    }
    testScene.placeImageXY(tester.scoreImage(), 80, 350);
    testScene.placeImageXY(tester.timerImage(), 440, 350);
    testScene.placeImageXY(tester.stepImage(), 245, 350);
    t.checkExpect(tester.makeScene(), testScene);
  }

  // tests for makeScene
  void testMakeScene2(Tester t) {
    this.initData4();
    Concentration tester = new Concentration(this.starter2);
    WorldScene testScene = new WorldScene(560, 400);
    int xacc = 0;
    int yacc = 0;
    int ind = 0;

    for (int i = 0; i < 13; i += 1) {
      xacc += 40;
      yacc = 0;
      for (int j = 0; j < 4; j += 1) {
        yacc += 70;
        testScene.placeImageXY(this.starter2.get(ind).cardImage, xacc, yacc);
        ind += 1;
      }
    }
    testScene.placeImageXY(tester.scoreImage(), 80, 350);
    testScene.placeImageXY(tester.timerImage(), 440, 350);
    testScene.placeImageXY(tester.stepImage(), 245, 350);
    t.checkExpect(tester.makeScene(), testScene);
  }

  // tests for findCoord
  void testfindCoord(Tester t) {
    Concentration world = new Concentration(new Random(8));
    Posn miss = new Posn(0, 0);
    Posn hit = new Posn(25, 200);
    world.findCoord(miss);

    t.checkExpect(world.cards, new Concentration(new Random(8)).cards);
    world.findCoord(hit);
    Concentration worldTester =  new Concentration(new Random(8));
    worldTester.cards.get(2).drawACard();

    t.checkExpect(world.cards, worldTester.cards);
    world.findCoord(new Posn(25, 60));
    worldTester.cards.get(0).drawACard();
    t.checkExpect(world.cards, worldTester.cards);
  }

  // tests for onMousePressed
  void testonMousePressed(Tester t) {
    Concentration world = new Concentration(new Random(8));
    Concentration worldTester = new Concentration(new Random(8));
    Posn miss = new Posn(0, 0);
    Posn hit = new Posn(25, 200);

    t.checkExpect(world.cards, worldTester.cards);
    world.onMousePressed(miss);
    t.checkExpect(world.cards, worldTester.cards);
    world.onMousePressed(hit);
    worldTester.cards.get(2).drawACard();
    t.checkExpect(world.cards, worldTester.cards);
    world.onMousePressed(new Posn(25, 60));
    worldTester.cards.get(0).drawACard();
    t.checkExpect(world.cards, worldTester.cards);
    world.onMousePressed(miss);
    worldTester.cards.get(0).removeACard();
    worldTester.cards.get(2).removeACard();
  }

  // tests for renderBG
  void testRenderInitialWorld(Tester t) {
    Concentration test = new Concentration();
    WorldScene testScene = new WorldScene(650, 500);
    test.renderBG(testScene);
    WorldScene answerScene = new WorldScene(650, 500);
    int xacc = 0;
    int yacc = 0;

    for (int i = 0; i < 13; i += 1) {
      xacc += 40;
      yacc = 0;
      for (int j = 0; j < 4; j += 1) {
        yacc += 70;
        answerScene.placeImageXY(new RectangleImage(35, 68, "outline", Color.black), xacc, yacc);
      }
    }
    t.checkExpect(testScene, answerScene);
  }

  // tests for renderBG
  void testRenderallFlippedWorld(Tester t) {
    this.initData4();
    Concentration test = new Concentration(starter2);
    WorldScene testScene = new WorldScene(650, 400);
    test.renderBG(testScene);
    WorldScene answerScene = new WorldScene(650, 400);
    int xacc = 0;
    int yacc = 0;
    int ind = 0;

    for (int i = 0; i < 13; i += 1) {
      xacc += 40;
      yacc = 0;
      for (int j = 0; j < 4; j += 1) {
        yacc += 70;
        answerScene.placeImageXY(starter2.get(ind).cardImage, xacc, yacc);
        ind += 1;
      }
    }
    t.checkExpect(testScene, answerScene);
  }

  // tests for lastScene
  void testLastScene(Tester t) {
    Concentration Conc = new Concentration();
    WorldScene winnerScene = new WorldScene(560, 400);
    WorldScene loserScene = new WorldScene(560, 400);
    WorldImage winnerText = new TextImage("You Win!", 50, FontStyle.BOLD, Color.GREEN);
    WorldImage loserText = new TextImage("You Lose!", 50, FontStyle.BOLD, Color.RED);
    winnerScene.placeImageXY(winnerText, 280, 200);
    loserScene.placeImageXY(loserText, 280, 200);
    t.checkExpect(Conc.lastScene("You Win!"), winnerScene);
    t.checkExpect(Conc.lastScene("You Lose!"), loserScene);
  }

  // tests for flipAllDown
  void testflipAllDown(Tester t) {
    this.initData4();
    Concentration conc = new Concentration(starter);
    t.checkExpect(conc.cards, starter);
    conc.flipAllDown();
    t.checkExpect(conc.cards, starter2);
    this.initData3();
    Concentration conc2 = new Concentration(starter);
    conc2.flipAllDown();
    t.checkExpect(conc2.cards, starter);
  }

  // tests for onTick
  void testOnTick(Tester t) {
    Concentration test = new Concentration();

    t.checkExpect(test.seconds, 0);
    test.onTick();
    t.checkExpect(test.seconds, 1);
    test.seconds = 59;
    test.onTick();
    t.checkExpect(test.minutes, 1);
    t.checkExpect(test.seconds, 0);
  }

  // tests for filterFaceUp
  void testFilterFaceUp(Tester t) {
    Card H5 = new Card(5, "H");
    Card HB5 = new Card(5, "D");
    Card H7 = new Card(7, "H");
    Card H13 = new Card(13, "H");
    Card H2 = new Card(2, "H");
    ArrayList<Card> test = new ArrayList<Card>(Arrays.asList(H5, HB5, H7, H13, H2));
    Concentration tester = new Concentration(test);
    t.checkExpect(tester.filterFaceUp(), new ArrayList<Card>(2));
    H5.flipUp();
    t.checkExpect(tester.filterFaceUp(), new ArrayList<Card>(Arrays.asList(H5)));
    HB5.flipUp();
    t.checkExpect(tester.filterFaceUp(), new ArrayList<Card>(Arrays.asList(H5, HB5)));
  }

  // tests for matchOrFlip
  void testmatchOrFlip(Tester t) {
    Concentration test1 = new Concentration(new Random(8));
    test1.cards.get(0).drawACard();
    test1.cards.get(1).drawACard();
    test1.matchOrFlip();
    t.checkExpect(test1.cards, new Concentration(new Random(8)).cards);
    test1.cards.get(0).drawACard();
    test1.cards.get(24).drawACard();
    test1.matchOrFlip();
    Concentration test2 = new Concentration(new Random(8));
    test2.cards.get(0).removeACard();
    test2.cards.get(24).removeACard();
    t.checkExpect(test1.cards, test2.cards);
  }

  // tests for Util constructor
  void testUtilConstructor(Tester t) {
    this.initData();
    t.checkExpect(this.utilTest.template, new ArrayList<Card>(Arrays.asList(
        new Card(1, "♣"), new Card(2, "♣"), new Card(3, "♣"), new Card(4, "♣"), new Card(5, "♣"),
        new Card(6, "♣"), new Card(7, "♣"), new Card(8, "♣"), new Card(9, "♣"), new Card(10, "♣"),
        new Card(11, "♣"), new Card(12, "♣"), new Card(13, "♣"), new Card(1, "♠"), new Card(2, "♠"),
        new Card(3, "♠"), new Card(4, "♠"), new Card(5, "♠"), new Card(6, "♠"), new Card(7, "♠"),
        new Card(8, "♠"), new Card(9, "♠"), new Card(10, "♠"),  new Card(11, "♠"),
        new Card(12, "♠"), new Card(13, "♠"), new Card(1, "♦"), new Card(2, "♦"), new Card(3, "♦"),
        new Card(4, "♦"), new Card(5, "♦"), new Card(6, "♦"), new Card(7, "♦"), new Card(8, "♦"),
        new Card(9, "♦"), new Card(10, "♦"), new Card(11, "♦"), new Card(12, "♦"),
        new Card(13, "♦"), new Card(1, "♥"), new Card(2, "♥"), new Card(3, "♥"), new Card(4, "♥"),
        new Card(5, "♥"),new Card(6, "♥"), new Card(7, "♥"), new Card(8, "♥"), new Card(9, "♥"),
        new Card(10, "♥"), new Card(11, "♥"), new Card(12, "♥"), new Card(13, "♥"))));
  }

  // tests for encodingInt
  void testEncodingInt(Tester t) {
    this.initData();
    t.checkExpect(this.randTest.encodingInt(51), 49);
    t.checkExpect(this.randTest.encodingInt(33), 7);
    t.checkExpect(this.randTest.encodingInt(1), 0);
  }

  // tests for initializeDeck
  void testInitializeDeck(Tester t) {
    this.initData();
    Util t2 = new Util(new ArrayList<Card>(52), new Random(8));
    t.checkExpect(this.randTest.initializeDeck(), new ArrayList<Card>(Arrays.asList(
        new Card(2, "♥"), new Card(3, "♥"), new Card(5, "♦"), new Card(3, "♦"), new Card(13, "♦"),
        new Card(9, "♦"), new Card(6, "♦"), new Card(1, "♦"), new Card(6, "♣"), new Card(12, "♠"),
        new Card(8, "♥"), new Card(10, "♣"), new Card(13, "♠"), new Card(10, "♦"),
        new Card(12, "♥"), new Card(9, "♠"), new Card(8, "♣"), new Card(6, "♥"), new Card(4, "♦"),
        new Card(10, "♠"), new Card(12, "♣"), new Card(5, "♣"), new Card(1, "♠"), new Card(11, "♣"),
        new Card(2, "♦"), new Card(10, "♥"), new Card(4, "♣"), new Card(8, "♦"), new Card(11, "♦"),
        new Card(4, "♠"), new Card(13, "♣"), new Card(8, "♠"), new Card(7, "♦"), new Card(2, "♠"),
        new Card(12, "♦"), new Card(7, "♠"), new Card(3, "♣"), new Card(2, "♣"), new Card(11, "♠"),
        new Card(7, "♣"), new Card(1, "♥"), new Card(6, "♠"), new Card(11, "♥"), new Card(4, "♥"),
        new Card(9, "♥"), new Card(9, "♣"), new Card(7, "♥"), new Card(5, "♠"), new Card(1, "♣"),
        new Card(5, "♥"), new Card(3, "♠"), new Card(13, "♥"))));
    ArrayList<Card> ans2 = new ArrayList<Card>(Arrays.asList(
        new Card(2, "♥"), new Card(3, "♥"), new Card(5, "♦"), new Card(3, "♦"), new Card(13, "♦"),
        new Card(9, "♦"), new Card(6, "♦"), new Card(1, "♦"), new Card(6, "♣"), new Card(12, "♠"),
        new Card(8, "♥"), new Card(10, "♣"), new Card(13, "♠"), new Card(10, "♦"),
        new Card(12, "♥"), new Card(9, "♠"), new Card(8, "♣"), new Card(6, "♥"), new Card(4, "♦"),
        new Card(10, "♠"), new Card(12, "♣"), new Card(5, "♣"), new Card(1, "♠"), new Card(11, "♣"),
        new Card(2, "♦"), new Card(10, "♥"), new Card(4, "♣"), new Card(8, "♦"), new Card(11, "♦"),
        new Card(4, "♠"), new Card(13, "♣"), new Card(8, "♠"), new Card(7, "♦"), new Card(2, "♠"),
        new Card(12, "♦"), new Card(7, "♠"), new Card(3, "♣"), new Card(2, "♣"), new Card(11, "♠"),
        new Card(7, "♣"), new Card(1, "♥"), new Card(6, "♠"), new Card(11, "♥"), new Card(4, "♥"),
        new Card(9, "♥"), new Card(9, "♣"), new Card(7, "♥"), new Card(5, "♠"), new Card(1, "♣"),
        new Card(5, "♥"), new Card(3, "♠"), new Card(13, "♥")));
    t.checkExpect(t2.initializeDeck(), ans2);
  }

  // tests for oneOrZero
  void testOneOrZero(Tester t) {
    Concentration tester = new Concentration();
    t.checkExpect(new Util(tester.cards).oneOrZero(), true);
    this.initData2();
    t.checkExpect(new Util(starter2).oneOrZero(), false);
  }

  // tests for bigBang (start of game when all cards flipped)
  // RUN TO PLAY ACTUAL GAME!!!!!!!!!
  // SEE ABOVE! THIS IS THE GAME STARTED CORRECTLY
  void testBigBang(Tester t) {
    this.initData2();
    Concentration world = new Concentration();
    int worldWidth = 560;
    int worldHeight = 400;
    double tickRate = 1.0;
    world.bigBang(worldWidth, worldHeight, tickRate);
  }

  // tests for bigBang (mid game when two cards flipped and cards have been matched)
  // Actual display commented out for gameplay. remove slashes on line 957
  // to see gamestate.
  // this gamestate would never be rendered in course of a real game
  void testBigBang2(Tester t) {
    this.initData2();
    Concentration world = new Concentration(starter2);
    int worldWidth = 560;
    int worldHeight = 400;
    double tickRate = 1.0;
    // world.bigBang(worldWidth, worldHeight, tickRate);
  }

  // tests for bigBang (end game when all cards have been matched)
  // Actual display commented out for gameplay. remove slashes on line 970
  // to see gamestate.
  // this gamestate would never be rendered in course of a real game
  void testBigBang3(Tester t) {
    this.initData3();
    Concentration world = new Concentration(starter);
    int worldWidth = 560;
    int worldHeight = 400;
    double tickRate = 1.0;
    // world.bigBang(worldWidth, worldHeight, tickRate);
  }
}