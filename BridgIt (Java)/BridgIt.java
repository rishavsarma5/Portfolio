import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// to represent the game board
class GameBoard extends World {
  int size;
  ArrayList<Cell> board;

  GameBoard(int size) {
    if (size >= 3 && size % 2 != 0) {
      this.size = size;
    }
    else {
      throw new IllegalArgumentException("Board Game parameters are invalid");
    }
    this.board = this.playerAdder(size);
    this.initializeNeighbors();
  }

  // mutates even Cells in game board (ArrayList<Cell>) to be either Player1 or Player2
  ArrayList<Cell> playerAdder(int size) {
    ArrayList<Cell> board = this.blankBoard(size);
    int p1Index = 0;
    int p2Index = 0;

    for (int i = 0; i < size; i++) {
      if (i % 2 == 0) {
        for (int j = 1; j < size; j += 2) {
          board.set(j + p1Index, new Player1());
        }
      }
      else {
        for (int l = 0; l < size;  l += 2) {
          board.set(l + p2Index, new Player2());
        }
      }
      p1Index += size;
      p2Index += size;
    }
    return board;
  }

  // creates an ArrayList<Cell> (game board) of Neutral of length size * size
  ArrayList<Cell> blankBoard(int size) {
    ArrayList<Cell> blank = new ArrayList<Cell>(size * size);
    for (int i = 0; i < size * size; i++) {
      blank.add(new Neutral());
    }
    return blank;
  }

  // EFFECT: links the neighbors of all Cells in game board (ArrayList<Cell>)
  void initializeNeighbors() {
    int placeAcc = 1;

    for (int i = 0; i < size * size; i++) {
      if (placeAcc == 1) {
        // do nothing
      }
      else if (i % size == 0) {
        this.setLeft(i);
      }
      else if (placeAcc <= size) {
        this.setTop(i);
      }
      else {
        this.setLeft(i);
        this.setTop(i);
      }
      placeAcc += 1;
    }
  }

  // EFFECT: links this Cell's top neighbor to Cell above in board
  //         also sets the Cell above in board's bottom neighbor to this Cell
  public void setTop(int index) {
    Cell object = this.board.get(index);
    Cell previous = this.board.get(index - 1);
    object.top = previous;
    previous.bottom = object;
  }

  // EFFECT: links this Cell's left neighbor to the given Cell
  //         also sets the Cell to the left in board's right neighbor to this Cell
  public void setLeft(int index) {
    Cell object = this.board.get(index);
    Cell previous = this.board.get(index - size);
    object.left = previous;
    previous.right = object;
  }

  // EFFECT: resets the game board if "r" is pressed
  public void onKeyEvent(String aKey) {
    if (aKey.equals("r")) {
      this.board = new GameBoard(size).board;
    }  
  }

  // EFFECT: handles mouse clicks on cells on and off game board
  public void onMousePressed(Posn posn) {
    boolean player = this.playerTurn();
    if (this.onBoard(posn)) {
      this.cellFlipper(posn, player);
      this.findPath(posn, player);
    }
  }

  // determines if a click is within the constraints of the game board
  boolean onBoard(Posn pos) {
    int x = pos.x;
    int y = pos.y;

    return x > 75 && x < this.size * 50 - 25
        && y > 75 && y < this.size * 50 - 25;
  }

  // EFFECT: flips clicked Cell and fixes neighbor links
  void cellFlipper(Posn click, boolean player) {
    int index = this.findIndex(click);
    Cell replacement = this.board.get(index).cellFlipperHelp(player);
    this.board.set(index, replacement);
    this.initializeNeighbors();
  }

  // determines if it is Player 1's turn (true) or Player 2's turn (false)
  boolean playerTurn() {
    int acc = 0;

    for (int i = 0; i < this.size * this.size; i += 1) {
      acc += this.board.get(i).turnCounter();  
    }
    return acc < 0;
  }

  // EFFECT: determines if either Player1 has linked cells from top/bottom or Player2 has 
  // from left/right
  public void findPath(Posn posn, boolean player) {
    int index = this.findIndex(posn);
    PathUtils util = new PathUtils(this.board, index, this.size);

    if (util.path()) {
      if (player) {
        this.endOfWorld("Player 2 has won!");
      }
      else {
        this.endOfWorld("Player 1 has won!");
      }
    }
  }

  // displays which player won as a text on screen
  public WorldScene lastScene(String msg) {
    int dimension = size * 50 + 50;

    if (msg.equals("Player 1 has won!")) {
      WorldScene scene = new WorldScene(dimension, dimension);
      WorldImage player1 = new TextImage(msg, 25, FontStyle.BOLD, Color.BLACK);
      scene.placeImageXY(player1, dimension / 2, dimension / 2);
      return scene;
    }
    else {
      WorldScene scene = new WorldScene(dimension, dimension);
      WorldImage player2 = new TextImage(msg, 25, FontStyle.BOLD, Color.BLACK);
      scene.placeImageXY(player2, dimension / 2, dimension / 2);
      return scene;
    }
  }

  // finds the index of the Cell that has been clicked
  int findIndex(Posn click) {
    int col = this.findColRow(click.x);
    int row = this.findColRow(click.y);
    return (size * (col - 1)) + row + size;
  }

  // returns column or row of Cell that was clicked
  int findColRow(int coord) {
    int row = 0;

    while (coord > 75) {
      row += 1;
      coord -= 50;
    }
    return row;
  }

  // draws the updated game board
  public WorldScene makeScene() {
    WorldScene currScene = new WorldScene(size * 50 + 50, size * 50 + 50);
    this.renderBG(currScene);
    return currScene;
  }

  // EFFECT: updates WorldScene to reflect changes to ArrayList<Cell> (game board)
  void renderBG(WorldScene scene) {
    int xacc = 0;
    int yacc = 0;
    int ind = 0;

    for (int i = 0; i < this.size; i += 1) {
      xacc += 50;
      yacc = 0;

      for (int j = 0; j < this.size; j += 1) {
        yacc += 50;
        scene.placeImageXY(board.get(ind).cellImage, xacc, yacc);
        ind += 1;
      }
    }
  }
}

// to represent a utility class for determining Cell links (if a player won the game)
class PathUtils {
  Cell start;
  ArrayList<Cell> board;
  int initialIndex;
  ArrayList<Integer> accumulator;
  int size;

  PathUtils(ArrayList<Cell> board, int initialIndex, int size) {
    this.start = board.get(initialIndex);
    this.board = board;
    this.initialIndex = initialIndex;
    this.accumulator = new ArrayList<Integer>(size * size);
    this.accumulator.add(initialIndex);
    this.size = size;
  }

  // do the cells either link from top/bottom (Player1 won) or left/right (Player2 won)?
  boolean path() {
    int index = initialIndex;
    PathUtils proxy = new PathUtils(this.board, index, this.size);

    if (start.cellImage.equals(new RectangleImage(50, 50, "solid", Color.red))) {
      return this.pathHelp("left", index) && proxy.pathHelp("right", index);
    }
    else {
      return this.pathHelp("bottom", index) && proxy.pathHelp("top", index);
    }
  }

  // helps determine if an edge Cell is linked to the clicked Cell
  boolean pathHelp(String destination, int index) {
    Cell curr = this.board.get(index);
    if (this.atDest(curr, destination)) {
      return true;
    }
    else {
      return (this.linkable(board.get(index), "right")
          && this.linkCell(curr.right, index + size, destination)) 
          || (this.linkable(board.get(index), "left")
              && this.linkCell(curr.left, index - size, destination)) 
          || (this.linkable(board.get(index), "top")
              && this.linkCell(curr.top, index - 1, destination)) 
          || (this.linkable(board.get(index), "bottom")
              && this.linkCell(curr.bottom, index + 1, destination));
    }
  }

  // does the Cell have a neighbor that is null, meaning it is an edge Cell?
  boolean atDest(Cell curr, String dest) {
    if (dest.equals("right")) {
      return curr.right == null;
    }
    else if (dest.equals("left")) {
      return curr.left == null;
    }
    else if (dest.equals("top")) {
      return curr.top == null;
    }
    else {
      return curr.bottom == null;
    }
  }

  // determines if neighbor cells are valid to be linked to current cell (same player Cell)
  boolean linkable(Cell a, String dir) {
    if (dir.equals("right")) {
      return a.right != null && a.imageLink(a.right);
    }
    else if (dir.equals("left")) {
      return a.left != null && a.imageLink(a.left);
    }
    else if (dir.equals("top")) {
      return a.top != null && a.imageLink(a.top);
    }
    else {
      return a.bottom != null && a.imageLink(a.bottom);
    }
  }

  // returns false if this cell has already been checked to be linked
  boolean linkCell(Cell curr, int index, String dest) {
    if (accumulator.contains(index)) {
      return false;
    }
    else {
      accumulator.add(index);
      return this.pathHelp(dest, index);
    }
  }
}

// to represent a Cell on the game board
abstract class Cell {
  Cell top;
  Cell bottom;
  Cell left;
  Cell right;
  WorldImage cellImage;

  Cell() {
    this.top = null;
    this.bottom = null;
    this.left = null;
    this.right = null;
  }

  // if neutral, creates a new activated cell for a Player based on boolean
  // if a Player cell, does nothing
  public Cell cellFlipperHelp(boolean player) {
    return this;
  }

  // returns 0 for neutral, 1 for Player 1, -1 for Player 2
  public int turnCounter() {
    return 0;
  }

  // does this Cell's image equal the given Cell's image?
  boolean imageLink(Cell next) {
    return this.cellImage.equals(next.cellImage);  
  }
}

// to represent a Cell that belongs to Player1
class Player1 extends Cell {
  Player1() {
    this.cellImage = new RectangleImage(50, 50, "solid", Color.red);
  }

  // returns 0 for neutral, 1 for Player 1, -1 for Player 2
  public int turnCounter() {
    return 1;
  }
}

// to represent a Cell that belongs to Player2
class Player2 extends Cell {
  Player2() {
    this.cellImage = new RectangleImage(50, 50, "solid", Color.blue);
  }

  // returns 0 for neutral, 1 for Player 1, -1 for Player 2
  public int turnCounter() {
    return -1;
  }
}

// to represent a Cell that is Neutral
class Neutral extends Cell {
  Neutral() {
    this.cellImage = new RectangleImage(50, 50, "solid", Color.gray);
  }

  // creates a new activated cell based on boolean, and sets neighbors
  public Cell cellFlipperHelp(boolean player) {
    if (player) {
      Cell p1 = new Player1();
      p1.top = this.top;
      p1.left = this.left;
      p1.right = this.right;
      p1.bottom = this.bottom;
      return p1;
    }
    else {
      Cell p2 = new Player2();
      p2.top = this.top;
      p2.left = this.left;
      p2.right = this.right;
      p2.bottom = this.bottom;
      return p2;
    }
  }
}

// examples for BridgIt
class ExamplesGameBoard {
  GameBoard starter;
  GameBoard board3;
  GameBoard playBoard;
  GameBoard board5;
  Cell p1;
  Cell p2;
  Cell neu;
  PathUtils util5top;
  PathUtils util5mid;

  // EFFECT: sets the data to its initial conditions
  void initData() {
    this.starter = new GameBoard(11);
    this.board3 = new GameBoard(3);
    this.board5 = new GameBoard(5);
    this.playBoard = new GameBoard(3);
    this.p1 = new Player1();
    this.p2 = new Player2();
    this.neu = new Neutral();
    this.util5top = new PathUtils(this.board5.board, 20, 5);
    this.util5mid = new PathUtils(this.board5.board, 12, 5);
  }

  // EFFECT: creates BridgIt example when players have clicked some Neutral Cells
  void initData2() {
    this.initData();
    for (int i = 12; i < this.starter.board.size(); i += 72) {
      this.starter.board.set(i, new Player1());
    }
    for (int j = 0; j < this.starter.board.size(); j++) {
      if (j == 14 || j == 16 || j == 38) {
        this.starter.board.set(j, new Player2());
      }
    }
  }

  // EFFECT: creates BridgIt example when Player 1 wins
  void initData3() {
    this.initData();
    for (int i = 0; i < this.starter.board.size(); i++) {
      if (i == 12 || i == 34 || i == 56 || i == 68 || i == 80 || i == 102) {
        this.starter.board.set(i, new Player1());
      }
    }
    for (int j = 0; j < this.starter.board.size(); j++) {
      if (j == 24 || j == 36 || j == 38 || j == 40 || j == 62 || j == 64) {
        this.starter.board.set(j, new Player2());
      }
    }
  }

  // tests for GameBoard constructors
  void testGameBoardContructor(Tester t) {
    this.initData();
    t.checkConstructorException(
        new IllegalArgumentException("Board Game parameters are invalid"),
        "GameBoard", 2);
    GameBoard tester = new GameBoard(3);
    t.checkExpect(tester.board.get(0).bottom, tester.board.get(1));
  }

  // tests for playerAdder
  void testPlayerAdder(Tester t) {
    this.initData();
    ArrayList<Cell> blank = new ArrayList<Cell>(9);
    for (int i = 0; i < 9; i++) {
      blank.add(new Neutral());
    }
    int p1Index = 0;
    int p2Index = 0;
    for (int i = 0; i < 3; i++) {
      if (i % 2 == 0) {
        for (int j = 1; j < 3; j += 2) {
          blank.set(j + p1Index, new Player1());
        }
      }
      else {
        for (int l = 0; l < 3;  l += 2) {
          blank.set(l + p2Index, new Player2());
        }
      }
      p1Index += 3;
      p2Index += 3;
    }
    t.checkExpect(this.board3.playerAdder(3), blank);

    this.playBoard.board.clear();
    for (int i = 0; i < 9; i++) {
      this.playBoard.board.add(new Player2());
    }
    t.checkExpect(this.playBoard.playerAdder(3), blank);
  }

  // tests for blankBoard
  void testBlankBoard(Tester t) {
    this.initData();
    ArrayList<Cell> neutralBoard = new ArrayList<Cell>(9);
    for (int i = 0; i < 9; i++) {
      neutralBoard.add(new Neutral());
    }
    this.board3.board.clear();
    t.checkExpect(this.board3.blankBoard(3), neutralBoard);
    t.checkExpect(this.playBoard.blankBoard(3), neutralBoard);
  }

  // tests for initializeNeighbors
  void testInitializeNeighbors(Tester t) {
    this.initData();
    GameBoard tester = new GameBoard(11);
    tester.board.clear();
    tester.board = tester.playerAdder(11);
    Cell cell0 = tester.board.get(0);
    Cell cell1 = tester.board.get(1);
    Cell cell11 = tester.board.get(11);
    Cell cell12 = tester.board.get(12);
    Cell cell110 = tester.board.get(110);
    Cell cell120 = tester.board.get(120);

    t.checkExpect(cell0.bottom, null);
    t.checkExpect(cell0.left, null);
    t.checkExpect(cell0.right, null);
    t.checkExpect(cell0.top, null);

    t.checkExpect(cell1.bottom, null);
    t.checkExpect(cell1.left, null);
    t.checkExpect(cell1.right, null);
    t.checkExpect(cell1.top, null);

    t.checkExpect(cell11.bottom, null);
    t.checkExpect(cell11.left, null);
    t.checkExpect(cell11.right, null);
    t.checkExpect(cell11.top, null);

    t.checkExpect(cell12.bottom, null);
    t.checkExpect(cell12.left, null);
    t.checkExpect(cell12.right, null);
    t.checkExpect(cell12.top, null);

    t.checkExpect(cell110.bottom, null);
    t.checkExpect(cell110.left, null);
    t.checkExpect(cell110.right, null);
    t.checkExpect(cell110.top, null);

    t.checkExpect(cell120.bottom, null);
    t.checkExpect(cell120.left, null);
    t.checkExpect(cell120.right, null);
    t.checkExpect(cell120.top, null);

    tester.initializeNeighbors();

    t.checkExpect(cell0.bottom, cell1);
    t.checkExpect(cell0.left, null);
    t.checkExpect(cell0.right, cell11);
    t.checkExpect(cell0.top, null);

    t.checkExpect(cell1.bottom, tester.board.get(2));
    t.checkExpect(cell1.left, null);
    t.checkExpect(cell1.right, tester.board.get(12));
    t.checkExpect(cell1.top, cell0);

    t.checkExpect(cell11.bottom, cell12);
    t.checkExpect(cell11.left, cell0);
    t.checkExpect(cell11.right, tester.board.get(22));
    t.checkExpect(cell11.top, null);

    t.checkExpect(cell12.bottom, tester.board.get(13));
    t.checkExpect(cell12.left, cell1);
    t.checkExpect(cell12.right, tester.board.get(23));
    t.checkExpect(cell12.top, cell11);

    t.checkExpect(cell110.bottom, tester.board.get(111));
    t.checkExpect(cell110.left, tester.board.get(99));
    t.checkExpect(cell110.right, null);
    t.checkExpect(cell110.top, null);

    t.checkExpect(cell120.bottom, null);
    t.checkExpect(cell120.left, tester.board.get(109));
    t.checkExpect(cell120.right, null);
    t.checkExpect(cell120.top, tester.board.get(119));
  }

  // tests for setTop
  void testSetTop(Tester t) {
    this.initData();
    this.board5.board.clear();
    for (int i = 0; i < 24; i++) {
      this.board5.board.add(new Neutral());
    }
    Cell testCell = this.board5.board.get(6);

    t.checkExpect(testCell.top, null);
    t.checkExpect(this.board5.board.get(5).bottom, null);
    this.board5.setTop(6);
    t.checkExpect(testCell.top, this.board5.board.get(5));
    t.checkExpect(this.board5.board.get(5).bottom, testCell);

    t.checkExpect(this.board5.board.get(1).top, null);
    t.checkExpect(this.board5.board.get(0).bottom, null);
    this.board5.setTop(1);
    t.checkExpect(this.board5.board.get(1).top, this.board5.board.get(0));
    t.checkExpect(this.board5.board.get(0).bottom, this.board5.board.get(1));
  }

  // tests for setLeft
  void testSetLeft(Tester t) {
    this.initData();
    this.board5.board.clear();
    for (int i = 0; i < 24; i++) {
      this.board5.board.add(new Neutral());
    }
    Cell testCell = this.board5.board.get(6);

    t.checkExpect(testCell.left, null);
    t.checkExpect(this.board5.board.get(1).right, null);
    this.board5.setLeft(6);
    t.checkExpect(testCell.left, this.board5.board.get(1));
    t.checkExpect(this.board5.board.get(1).right, testCell);

    t.checkExpect(this.board5.board.get(18).left, null);
    t.checkExpect(this.board5.board.get(13).right, null);
    this.board5.setLeft(18);
    t.checkExpect(this.board5.board.get(18).left, this.board5.board.get(13));
    t.checkExpect(this.board5.board.get(13).right, this.board5.board.get(18));
  }

  // tests for onKeyEvent
  void testOnKeyEvent(Tester t) {
    this.initData2();
    GameBoard newBoard5 = new GameBoard(5);
    t.checkExpect(this.board5.board, newBoard5.board);
    this.board5.onKeyEvent("g");
    t.checkExpect(this.board5.board, this.board5.board);

    t.checkExpect(this.starter.board, this.starter.board);
    this.starter.onKeyEvent("r");
    GameBoard starterBoard = new GameBoard(11);
    t.checkExpect(this.starter.board, starterBoard.board);
  }

  // tests for onMousePressed
  void testOnMousePressed(Tester t) {
    this.initData();
    Posn edge = new Posn(26, 45);
    Posn out = new Posn(12, 92);
    Posn pos = new Posn(77, 81);
    Posn initialBoard = new Posn(181, 131);
    GameBoard newBoard3 = new GameBoard(3);
    GameBoard nb5 = new GameBoard(5);
    t.checkExpect(this.board3.board, newBoard3.board);
    this.board3.onMousePressed(edge);
    t.checkExpect(this.board3.board, newBoard3.board);
    this.board3.onMousePressed(out);
    t.checkExpect(this.board3.board, newBoard3.board);
    this.board3.onMousePressed(pos);
    newBoard3.cellFlipper(pos, false);
    t.checkExpect(this.board3.board, newBoard3.board);
    this.board5.onMousePressed(initialBoard);
    t.checkExpect(this.board5.board, nb5.board);
  }

  // tests for onBoard
  void testOnBoard(Tester t) {
    this.initData();
    Posn pos = new Posn(102, 115);
    Posn pos2 = new Posn(650, 320);
    Posn pos3 = new Posn(545, 550);

    t.checkExpect(this.board5.onBoard(pos), true);
    t.checkExpect(this.board5.onBoard(pos2), false);
    t.checkExpect(this.board5.onBoard(pos3), false);
  }

  // tests for cellFlipper
  void testCellFlipper(Tester t) {
    this.initData();
    Posn pos = new Posn(110, 110);
    Posn pos2 = new Posn(105, 102);
    Cell newCell = new Player2();
    newCell.top = this.board3.board.get(3);
    newCell.bottom = this.board3.board.get(5);
    newCell.left = this.board3.board.get(1);
    newCell.right = this.board3.board.get(7);

    t.checkExpect(this.board3.board.get(4), this.board3.board.get(4));
    t.checkExpect(this.board3.board.get(1).right, this.board3.board.get(4));
    this.board3.cellFlipper(pos, false);
    t.checkExpect(this.board3.board.get(4), newCell);
    t.checkExpect(this.board3.board.get(1).right, newCell);

    this.board3.cellFlipper(pos2, true);
    t.checkExpect(this.board3.board.get(4), newCell);
    t.checkExpect(this.board3.board.get(1).right, newCell);
  }

  // tests for playerTurn
  void testPlayerTurn(Tester t) {
    this.initData();
    Posn pos = new Posn(135, 140);
    Posn pos2 = new Posn(80, 190);
    Posn out = new Posn(35, 36);
    t.checkExpect(this.board5.playerTurn(), false);
    this.board5.onMousePressed(pos);
    t.checkExpect(this.board5.playerTurn(), true);
    this.board5.onMousePressed(pos2);
    t.checkExpect(this.board5.playerTurn(), false);
    this.board5.onMousePressed(out);
    t.checkExpect(this.board5.playerTurn(), false);
  }

  // tests for findPath
  void testFindPath(Tester t) {
    GameBoard world = new GameBoard(3);
    world.findPath(new Posn(85, 85), false);
    t.checkExpect(world, new GameBoard(3));
    world.cellFlipper(new Posn(85, 85), false);
    world.findPath(new Posn(85, 85), false);
    GameBoard ender = new GameBoard(3);
    ender.cellFlipper(new Posn(85, 85), false);
    ender.endOfWorld("Player 1 has won!");
    t.checkExpect(world, ender);
  }

  // tests for lastScene
  void testLastScene(Tester t) {
    GameBoard board = new GameBoard(5);
    WorldScene player1Scene = new WorldScene(300, 300);
    WorldScene player2Scene = new WorldScene(300, 300);
    WorldImage player1Text = new TextImage("Player 1 has won!", 25, FontStyle.BOLD, Color.BLACK);
    WorldImage player2Text = new TextImage("Player 2 has won!", 25, FontStyle.BOLD, Color.BLACK);
    player1Scene.placeImageXY(player1Text, 150, 150);
    player2Scene.placeImageXY(player2Text, 150, 150);
    t.checkExpect(board.lastScene("Player 1 has won!"), player1Scene);
    t.checkExpect(board.lastScene("Player 2 has won!"), player2Scene);
  }

  // tests for findIndex
  void testFindIndex(Tester t) {
    this.initData();
    Posn pos = new Posn(102, 115);
    Posn pos2 = new Posn(473, 320);
    t.checkExpect(this.board5.findIndex(pos), 6);
    t.checkExpect(this.starter.findIndex(pos2), 93);
  }

  // tests for findColRow
  void testFindColRow(Tester t) {
    this.initData();
    Posn pos = new Posn(102, 115);
    Posn pos2 = new Posn(473, 320);
    t.checkExpect(this.board5.findColRow(pos.x), 1);
    t.checkExpect(this.board5.findColRow(pos.y), 1);
    t.checkExpect(this.starter.findColRow(pos2.x), 8);
    t.checkExpect(this.starter.findColRow(pos2.y), 5);
  }

  // tests for makeScene
  void testMakeScene(Tester t) {
    WorldScene testScene = new WorldScene(300, 300);
    GameBoard tester = new GameBoard(5);
    int xacc = 0;
    int yacc = 0;
    int ind = 0;

    for (int i = 0; i < tester.size; i += 1) {
      xacc += 50;
      yacc = 0;

      for (int j = 0; j < tester.size; j += 1) {
        yacc += 50;
        testScene.placeImageXY(tester.board.get(ind).cellImage, xacc, yacc);
        ind += 1;
      }
    }
    t.checkExpect(tester.makeScene(), testScene);
  }

  // tests for makeScene
  void testMakeScene2(Tester t) {
    this.initData2();
    WorldScene testScene = new WorldScene(600, 600);
    int xacc = 0;
    int yacc = 0;
    int ind = 0;

    for (int i = 0; i < this.starter.size; i += 1) {
      xacc += 50;
      yacc = 0;

      for (int j = 0; j < this.starter.size; j += 1) {
        yacc += 50;
        testScene.placeImageXY(this.starter.board.get(ind).cellImage, xacc, yacc);
        ind += 1;
      }
    }
    t.checkExpect(this.starter.makeScene(), testScene);
  }

  // tests for renderBG (5 x 5 grid)
  void testRenderBG(Tester t) {
    WorldScene scene = new WorldScene(300, 300);
    WorldScene scene2 = new WorldScene(300, 300);
    WorldImage neut = new RectangleImage(50, 50, "solid", Color.gray);
    WorldImage p1 = new RectangleImage(50, 50, "solid", Color.red);
    WorldImage p2 = new RectangleImage(50, 50, "solid", Color.blue);
    scene.placeImageXY(neut, 50, 50);
    scene.placeImageXY(p2, 100, 50);
    scene.placeImageXY(neut, 150, 50);
    scene.placeImageXY(p2, 200, 50);
    scene.placeImageXY(neut, 250, 50);

    scene.placeImageXY(p1, 50, 100);
    scene.placeImageXY(neut, 100, 100);
    scene.placeImageXY(p1, 150, 100);
    scene.placeImageXY(neut, 200, 100);
    scene.placeImageXY(p1, 250, 100);

    scene.placeImageXY(neut, 50, 150);
    scene.placeImageXY(p2, 100, 150);
    scene.placeImageXY(neut, 150, 150);
    scene.placeImageXY(p2, 200, 150);
    scene.placeImageXY(neut, 250, 150);

    scene.placeImageXY(p1, 50, 200);
    scene.placeImageXY(neut, 100, 200);
    scene.placeImageXY(p1, 150, 200);
    scene.placeImageXY(neut, 200, 200);
    scene.placeImageXY(p1, 250, 200);

    scene.placeImageXY(neut, 50, 250);
    scene.placeImageXY(p2, 100, 250);
    scene.placeImageXY(neut, 150, 250);
    scene.placeImageXY(p2, 200, 250);
    scene.placeImageXY(neut, 250, 250);

    GameBoard test = new GameBoard(5);
    test.renderBG(scene2);
    t.checkExpect(scene, scene2);
  }

  // tests for renderBG (3 x 3 grid)
  void testRenderBG2(Tester t) {
    WorldScene scene = new WorldScene(200, 200);
    WorldScene scene2 = new WorldScene(200, 200);
    WorldImage neut = new RectangleImage(50, 50, "solid", Color.gray);
    WorldImage p1 = new RectangleImage(50, 50, "solid", Color.red);
    WorldImage p2 = new RectangleImage(50, 50, "solid", Color.blue);
    scene.placeImageXY(neut, 50, 50);
    scene.placeImageXY(p2, 100, 50);
    scene.placeImageXY(neut, 150, 50);

    scene.placeImageXY(p1, 50, 100);
    scene.placeImageXY(neut, 100, 100);
    scene.placeImageXY(p1, 150, 100);

    scene.placeImageXY(neut, 50, 150);
    scene.placeImageXY(p2, 100, 150);
    scene.placeImageXY(neut, 150, 150);

    GameBoard tester = new GameBoard(3);
    tester.renderBG(scene2);
    t.checkExpect(scene, scene2);
  }

  // tests for path
  void testPath(Tester t) {
    this.initData();
    PathUtils util6 = new PathUtils(this.board5.board, 6, 5);
    PathUtils util8 = new PathUtils(this.board5.board, 8, 5);
    Posn pos = new Posn(80, 83);
    Posn pos2 = new Posn(135, 128);
    Posn pos3 = new Posn(89, 191);
    t.checkExpect(util6.path(), false);
    this.board5.onMousePressed(pos);
    t.checkExpect(util6.path(), false);
    this.board5.onMousePressed(pos2);
    t.checkExpect(this.util5mid.path(), false);
    this.board5.onMousePressed(pos3);
    t.checkExpect(util8.path(), true);
  }

  // tests for pathHelp
  void testpathHelp(Tester t) {
    GameBoard world = new GameBoard(3);
    PathUtils aPath = new PathUtils(world.board, 2, 3);
    t.checkExpect(aPath.pathHelp("top", 2), false);
    t.checkExpect(aPath.pathHelp("top", 3), true);
    t.checkExpect(aPath.pathHelp("bottom", 3), false);
    world.cellFlipper(new Posn(85, 85), false);
    aPath.accumulator = new ArrayList<Integer>();
    aPath.accumulator.add(2);
    t.checkExpect(aPath.pathHelp("bottom", 3), true);
  }

  // tests for atDest
  void testAtDest(Tester t) {
    this.initData();
    t.checkExpect(this.util5top.atDest(this.board5.board.get(20), "top"), true);
    t.checkExpect(this.util5top.atDest(this.board5.board.get(20), "right"), true);
    t.checkExpect(this.util5top.atDest(this.board5.board.get(20), "left"), false);
    t.checkExpect(this.util5top.atDest(this.board5.board.get(20), "bottom"), false);
    t.checkExpect(this.util5mid.atDest(this.board5.board.get(12), "top"), false);
    t.checkExpect(this.util5mid.atDest(this.board5.board.get(12), "right"), false);
    t.checkExpect(this.util5mid.atDest(this.board5.board.get(12), "left"), false);
    t.checkExpect(this.util5mid.atDest(this.board5.board.get(12), "bottom"), false);
  }

  // tests for linkable
  void testLinkable(Tester t) {
    this.initData();
    Posn pos = new Posn(135, 128);
    Posn pos2 = new Posn(188, 76);
    PathUtils util15 = new PathUtils(this.board5.board, 15, 5);
    t.checkExpect(this.util5mid.linkable(this.board5.board.get(12), "top"), false);
    t.checkExpect(this.util5mid.linkable(this.board5.board.get(12), "right"), false);
    t.checkExpect(this.util5mid.linkable(this.board5.board.get(12), "bottom"), false);
    t.checkExpect(this.util5mid.linkable(this.board5.board.get(12), "left"), false);
    this.board5.onMousePressed(pos);
    t.checkExpect(this.util5mid.linkable(this.board5.board.get(12), "top"), false);
    t.checkExpect(this.util5mid.linkable(this.board5.board.get(12), "right"), true);
    t.checkExpect(this.util5mid.linkable(this.board5.board.get(12), "bottom"), false);
    t.checkExpect(this.util5mid.linkable(this.board5.board.get(12), "left"), true);
    this.board5.onKeyEvent("r");
    t.checkExpect(util15.linkable(this.board5.board.get(15), "top"), false);
    t.checkExpect(util15.linkable(this.board5.board.get(15), "right"), false);
    t.checkExpect(util15.linkable(this.board5.board.get(15), "bottom"), false);
    t.checkExpect(util15.linkable(this.board5.board.get(15), "left"), false);
    this.board5.onMousePressed(pos2);
    t.checkExpect(util15.linkable(this.board5.board.get(15), "top"), false);
    t.checkExpect(util15.linkable(this.board5.board.get(15), "right"), false);
    t.checkExpect(util15.linkable(this.board5.board.get(15), "bottom"), true);
    t.checkExpect(util15.linkable(this.board5.board.get(15), "left"), false);
  }

  // tests for linkCell
  void testLinkCell(Tester t) {
    GameBoard world = new GameBoard(3);
    PathUtils aPath = new PathUtils(world.board, 2, 3);
    world.cellFlipper(new Posn(85, 85), false);
    t.checkExpect(aPath.linkCell(world.board.get(4), 4, "bottom"), true);
    t.checkExpect(aPath.linkCell(world.board.get(4), 4, "bottom"), false);
  }

  // tests for cellFlipperHelp
  void testCellFlipperHelp(Tester t) {
    this.initData();
    Cell c12 = new Player2();
    Cell c18 = new Player1();
    c12.top = this.board5.board.get(12).top;
    c12.bottom = this.board5.board.get(12).bottom;
    c12.left = this.board5.board.get(12).left;
    c12.right = this.board5.board.get(12).right;
    c18.top = this.board5.board.get(18).top;
    c18.bottom = this.board5.board.get(18).bottom;
    c18.left = this.board5.board.get(18).left;
    c18.right = this.board5.board.get(18).right;
    t.checkExpect(this.board5.board.get(12).cellFlipperHelp(false), c12);
    t.checkExpect(this.board5.board.get(11).cellFlipperHelp(true), this.board5.board.get(11));
    t.checkExpect(this.board5.board.get(5).cellFlipperHelp(true), this.board5.board.get(5));
    t.checkExpect(this.board5.board.get(18).cellFlipperHelp(true), c18);
  }

  // tests for turnCounter
  void testTurnCounter(Tester t) {
    this.initData();
    t.checkExpect(this.p1.turnCounter(), 1);
    t.checkExpect(this.p2.turnCounter(), -1);
    t.checkExpect(this.neu.turnCounter(), 0);
  }

  // tests for imageLink
  void testImageLink(Tester t) {
    this.initData();
    t.checkExpect(this.p1.imageLink(this.p2), false);
    t.checkExpect(this.p2.imageLink(this.p1), false);
    t.checkExpect(this.neu.imageLink(this.neu), true);
  }

  /*
  // tests for bigBang (start of game)
  void testBigBang(Tester t) {
    this.initData();
    int worldWidth = 600;
    int worldHeight = 600;
    double tickRate = 1.00;
    starter.bigBang(worldWidth, worldHeight, tickRate);
  }

  // tests for bigBang (mid-game)
  void testBigBang2(Tester t) {
    this.initData2();
    int worldWidth = 600;
    int worldHeight = 600;
    double tickRate = 1.00;
    starter.bigBang(worldWidth, worldHeight, tickRate);
  }

  // tests for bigBang (end-game = Player 1 wins)
  void testBigBang3(Tester t) {
    this.initData3();
    int worldWidth = 600;
    int worldHeight = 600;
    double tickRate = 1.00;
    starter.bigBang(worldWidth, worldHeight, tickRate);
  }
   */

  // tests for bigBang (11 x 11)
  void testBigBang4(Tester t) {
    GameBoard world = new GameBoard(11);
    int worldWidth = 600;
    int worldHeight = 600;
    double tickRate = 1.00;
    world.bigBang(worldWidth, worldHeight, tickRate);
  }
}