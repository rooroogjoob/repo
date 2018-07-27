package src;

import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel {
  private int difficulty = 1;
  public int size;
  private boolean Player1Win = false;
  private boolean Player2Win = false;
  private int Player1NumWalls = 10;
  private int Player2NumWalls = 10;
  private Mode mode = Mode.PLAYER1_TURN;
  private int MinotaurTimer;
  private Entity Player1;
  private Entity Player2;
  private Entity Minotaur;
  private Entity HIGHLIGHT;
  public static final int BOARD_WIDTH = 400;
  public static final int BOARD_HEIGHT = 400;
  private Entity[][] boardPieces = new Entity[size][size];
  private Entity[][][] wallsToDraw = new Entity[2][size][size];
  private Set<Wall> walls = new TreeSet<Wall>();
  //private boolean[][] seen = new boolean[size][size];
  //private boolean[][] correct = new boolean[size][size];

  public int getWLeft(Entity player) {
    if (player.equals(Player1)) {
      return Player1NumWalls;
    } else if (player.equals(Player2)) {
      return Player2NumWalls;
    }
    return -1;
  }

  public Set<Wall> getWalls() {
      return walls;
  }  
    
    
  public void setDifficulty(int n) {
    this.difficulty = n;
  }

  public void lessWalls(Entity player, int n) {
    if (player.equals(Player1)) {
      Player1NumWalls -= n;
      Game.numWalls1.setText(
          "<html>" + Game.player1Name + " has:<br>" + Player1NumWalls + " walls left</html>");
    } else if (player.equals(Player2)) {
      Player2NumWalls -= n;
      Game.numWalls2.setText(
          "<html>" + Game.player2Name + " has:<br>" + Player2NumWalls + " walls left</html>");
    }
  }

  public int getP1Walls() {
    return this.Player1NumWalls;
  }

  public int getP2Walls() {
    return this.Player2NumWalls;
  }

  public boolean gameWon() {
    if (Player1Win || Player2Win) {
      return true;
    }
    return false;
  }

  public boolean getWinState1() {
    return Player1Win;
  }

  public boolean getWinState2() {
    return Player2Win;
  }

  public void checkPlayer1Win() {
    if (Player1.getX() == size - 1) {
      Player1Win = true;
    }
  }

  public void checkPlayer2Win() {
    if (Player2.getX() == 0) {
      Player2Win = true;
    }
  }

  public Entity getPlayer1() {
    return Player1;
  }

  public Entity getPlayer2() {
    return Player2;
  }

  public void setHighlight(Entity e) {
    HIGHLIGHT = e;
  }

  public String getMode() {
    return this.mode.name();
  }

  public void setMode(Mode m) {
    this.mode = m;
    Game.modeLabel.setText(getMode());
  }

  public boolean handledTurn(KeyEvent e, Entity player, Board b) {
    // Keys being pressed
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      HIGHLIGHT.move(-1, 0, b);
      repaint();
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      HIGHLIGHT.move(1, 0, b);
      repaint();
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      HIGHLIGHT.move(0, 1, b);
      repaint();
    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
      HIGHLIGHT.move(0, -1, b);
      repaint();
    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (HIGHLIGHT.getX() == player.getX() && HIGHLIGHT.getY() == player.getY()) {
        paintPossiblePaths(e, player);
        return true;
      }
    }
    return false;
  }

  public void paintPossiblePaths(KeyEvent e, Entity player) {
    int inx = player.getX();
    int iny = player.getY();
    // Paints Possible Moves
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if (inx + i > -1 && iny + j > -1 && inx + i < size && iny + j < size) {
          if (boardPieces[inx + i][iny + j] == null) {
            if (i == 0 && j == 1 && wallsToDraw[0][inx][iny] == null) {
              boardPieces[inx + i][iny + j] = new Dog(inx + i, iny + j, "files/highlight.png");
            }
            if (i == 0 && j == -1 && wallsToDraw[0][inx][iny - 1] == null) {
              boardPieces[inx + i][iny + j] = new Dog(inx + i, iny + j, "files/highlight.png");
            }
            if (j == 0 && i == 1 && wallsToDraw[1][inx][iny] == null) {
              boardPieces[inx + i][iny + j] = new Dog(inx + i, iny + j, "files/highlight.png");
            }
            if (j == 0 && i == -1 && wallsToDraw[1][inx - 1][iny] == null) {
              boardPieces[inx + i][iny + j] = new Dog(inx + i, iny + j, "files/highlight.png");
            }
          }
        }
      }
    }
  }

  public void handleMove(KeyEvent e, Entity player, Mode prev, Mode next, Board b) {
    int inx = player.getX();
    int iny = player.getY();
    if (e.getKeyCode() == KeyEvent.VK_LEFT && boardPieces[inx - 1][iny].equals(HIGHLIGHT)) {
      if (player.equals(Player2)) {
        HIGHLIGHT.move(-1, 0, b);
      }
      boardPieces[inx][iny] = null;
      player.move(-1, 0, b);
      boardPieces[inx - 1][iny] = player;
      setMode(next);
      repaint();
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && boardPieces[inx + 1][iny].equals(HIGHLIGHT)) {
      if (player.equals(Player2)) {
        HIGHLIGHT.move(1, 0, b);
      }
      boardPieces[inx][iny] = null;
      player.move(1, 0, b);
      boardPieces[inx + 1][iny] = player;
      setMode(next);
      repaint();
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN && boardPieces[inx][iny + 1].equals(HIGHLIGHT)) {
      if (player.equals(Player2)) {
        HIGHLIGHT.move(0, 1, b);
      }
      boardPieces[inx][iny] = null;
      player.move(0, 1, b);
      boardPieces[inx][iny + 1] = player;
      setMode(next);
      repaint();
    } else if (e.getKeyCode() == KeyEvent.VK_UP && boardPieces[inx][iny - 1].equals(HIGHLIGHT)) {
      if (player.equals(Player2)) {
        HIGHLIGHT.move(0, -1, b);
      }
      boardPieces[inx][iny] = null;
      player.move(0, -1, b);
      boardPieces[inx][iny - 1] = player;
      setMode(next);
      repaint();
    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      boardPieces[inx][iny] = player;
      repaint();
    }
  }

  public void erasePaths(Entity player) {
    // Erase Potential Paths For Player
    int inx = player.getX();
    int iny = player.getY();
    for (int i = -2; i < 3; i++) {
      for (int j = -2; j < 3; j++) {
        if (inx + i > -1 && iny + j > -1 && inx + i < size && iny + j < size) {
          if (boardPieces[inx + i][iny + j] != null
              && boardPieces[inx + i][iny + j] != Player1
              && boardPieces[inx + i][iny + j] != Player2
              && boardPieces[inx + i][iny + j] != Minotaur) {
            boardPieces[inx + i][iny + j] = null;
          }
        }
      }
    }
    repaint();
  }

  public void wallTurn(Entity player, Mode next, KeyEvent e, Board b) {
    int inx = HIGHLIGHT.getX();
    int iny = HIGHLIGHT.getY();
    String allignment = ((Wall) HIGHLIGHT).getAllignment();
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      HIGHLIGHT.move(-1, 0, b);
      repaint();
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      if (allignment.equals("Horizontal") && inx < size - 2) {
        HIGHLIGHT.move(1, 0, b);
        repaint();
      } else if (inx < size - 2) {
        HIGHLIGHT.move(1, 0, b);
        repaint();
      }
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      if (allignment.equals("Vertical") && iny < size - 1) {
        HIGHLIGHT = new Wall(inx, iny, "files/highlight.png", "Horizontal", true);
        repaint();
      }
      if (allignment.equals("Horizontal") && iny < size - 2) {
        if (inx > size - 2) {
          HIGHLIGHT = new Wall(inx - 1, iny + 1, "files/highlight.png", "Vertical", true);
          repaint();
        } else {
          HIGHLIGHT = new Wall(inx, iny + 1, "files/highlight.png", "Vertical", true);
          repaint();
        }
      }
    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
      if (allignment.equals("Vertical") && iny < size && iny > 0) {
        HIGHLIGHT = new Wall(inx, iny - 1, "files/highlight.png", "Horizontal", true);
        repaint();
      }
      if (allignment.equals("Horizontal")) {
        if (inx > size - 2) {
          HIGHLIGHT = new Wall(inx - 1, iny, "files/highlight.png", "Vertical", true);
          repaint();
        } else {
          HIGHLIGHT = new Wall(inx, iny, "files/highlight.png", "Vertical", true);
          repaint();
        }
      }

    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (getWLeft(player) > 0) {
        if (walls.add((Wall) HIGHLIGHT)) {
          if (allignment.equals("Vertical")) {
            wallsToDraw[1][inx][iny] = new Wall(inx, iny, "files/wall.png", "Vertical");
            wallsToDraw[1][inx][iny + 1] = new Wall(inx, iny + 1, "files/wall.png", "Vertical");
          } else if (allignment.equals("Horizontal")) {
            wallsToDraw[0][inx][iny] = new Wall(inx, iny, "files/wall.png", "Horizontal");
            wallsToDraw[0][inx + 1][iny] = new Wall(inx + 1, iny, "files/wall.png", "Horizontal");
          }
          lessWalls(player, 1);
          setMode(next);
          HIGHLIGHT = new Dog(size / 2, size / 2, "files/highlight.png");
        }
      }
    }
    repaint();
  }

  public boolean findSpace(int x, int y, boolean[][] seen, boolean[][] correct) {
      
        if (x == Minotaur.getX() && y == Minotaur.getY()) {
                  return true;
              }
              
              if (seen[x][y] == true) {
                  return false;
              }
              
              if (wallsToDraw[1][x - 1][y] == null && Minotaur.getX() > 0) {
                  if (findSpace(x - 1, y, seen, correct)) {
                      correct[x][y] = true;
                      return true;
                  }
              }
      
      if (wallsToDraw[1][x][y] == null && Minotaur.getX() < size - 1) {
                  if (findSpace(x - 1, y, seen, correct)) {
                      correct[x][y] = true;
                      return true;
                  }
              }
      
              if (wallsToDraw[0][x][y - 1] == null && Minotaur.getY() > 0) {
                  if (findSpace(x, y - 1, seen, correct)) {
                      correct[x][y] = true;
                      return true;
                  }
              }
      if (wallsToDraw[0][x][y] == null && Minotaur.getY() < size - 1) {
                  if (findSpace(x, y + 1, seen, correct)) {
                      correct[x][y] = true;
                      return true;
                  }
              }
      
      return false;
      
  }  
    
    
    
  public void moveMinotaur(int numSpaces, Board b) {
    //   System.out.println("NS: " + numSpaces);
     MinotaurTimer = 0;
    Timer timer = new Timer(450, null);
    timer.setRepeats(true);
    timer.setCoalesce(false);
    timer.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (MinotaurTimer == numSpaces - 1) {
              timer.stop();
              setMode(Mode.PLAYER1_TURN);
            }
            int n = (int) Math.floor(4 * Math.random());
            int inx = Minotaur.getX();
            int iny = Minotaur.getY();
            if (n == 0 && wallsToDraw[1][inx - 1][iny] == null && inx > 0) {
              boardPieces[inx][iny] = null;
              Minotaur.move(-1, 0, b);
              boardPieces[inx - 1][iny] = Minotaur;
              repaint();
            } else if (n == 1 && wallsToDraw[1][inx][iny] == null && inx < b.size - 1) {
              boardPieces[inx][iny] = null;
              Minotaur.move(1, 0, b);
              boardPieces[inx + 1][iny] = Minotaur;
              repaint();
            } else if (n == 2 && wallsToDraw[0][inx][iny] == null && iny < b.size - 1) {
              boardPieces[inx][iny] = null;
              Minotaur.move(0, 1, b);
              boardPieces[inx][iny + 1] = Minotaur;
              repaint();
            } else if (n == 3 && wallsToDraw[0][inx][iny - 1] == null && iny > 0) {
              boardPieces[inx][iny] = null;
              Minotaur.move(0, -1, b);
              boardPieces[inx][iny - 1] = Minotaur;
              repaint();
            } else {
              MinotaurTimer--;
            }
            int newX = Minotaur.getX();
            int newY = Minotaur.getY();
            if (newX == Player1.getX() && newY == Player1.getY()) {
              boardPieces[newX][newY] = Minotaur;
              boardPieces[0][size / 2] = Player1;
              Player1.setX(0);
              Player1.setY(size / 2);
              repaint();
            }
            if (newX == Player2.getX() && iny == Player2.getY()) {
              boardPieces[newX][newY] = Minotaur;
              boardPieces[size - 1][size / 2] = Player2;
              Player2.setX(size - 1);
              Player2.setY(size / 2);
              repaint();
            } 
            MinotaurTimer++;
            repaint();
            //     System.out.println("n = " + n);
            //  System.out.println(MinotaurTimer);
          }
        });
    timer.start();
  }


  public Board(int size) {
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    setFocusable(true);
    this.size = size;
    Board b = this;
    addKeyListener(
        new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            // HANDLING PLAYER1's TURN
            if (mode.equals(Mode.PLAYER1_TURN)) {
              if (handledTurn(e, Player1, b)) {
                setMode(Mode.PLAYER1_MOVE);
                repaint();
              }
            }
            // Handling PLAYER1's MOVE
            if (mode.equals(Mode.PLAYER1_MOVE)) {
              handleMove(e, Player1, Mode.PLAYER1_TURN, Mode.PLAYER2_TURN, b);
              checkPlayer1Win();
              if (gameWon()) {
                Player1Win = false;
                (new JOptionPane())
                    .showMessageDialog(
                        (JPanel) b,
                        "Woof Woof " + Game.vMessageP1 + " Woof Woof!",
                        "Congratulations " + Game.player1Name + "!",
                        JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon("files/dog1.png"));
                reset();
              }
            }
            // HANDLING PLAYER2's TURN
            if (mode.equals(Mode.PLAYER2_TURN)) {
              erasePaths(Player1);
              if (handledTurn(e, Player2, b)) {
                setMode(Mode.PLAYER2_MOVE);
                repaint();
              }
            }
            // Handling PLAYER2's MOVE
            if (mode.equals(Mode.PLAYER2_MOVE)) {
              handleMove(e, Player2, Mode.PLAYER2_TURN, Mode.MINOTAUR_TURN, b);
              checkPlayer2Win();
              if (gameWon()) {
                (new JOptionPane())
                    .showMessageDialog(
                        (JPanel) b,
                        "Woof Woof " + Game.vMessageP2 + " Woof Woof!",
                        "Congratulations " + Game.player2Name + "!",
                        JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon("files/dog2.png"));
                reset();
                Player2Win = false;
                reset();
              }
            }

            if (mode.equals(Mode.PLAYER1_WALL)) {
              wallTurn(Player1, Mode.PLAYER2_TURN, e, b);
              repaint();
            }

            if (mode.equals(Mode.PLAYER2_WALL)) {
              wallTurn(Player2, Mode.MINOTAUR_TURN, e, b);
              repaint();
            }

            // Handling MINOTAUR's TURN
            if (mode.equals(Mode.MINOTAUR_TURN)) {
              erasePaths(Player2);
              // Randomly Move Minotaur
            //  MinotaurTimer = 0;
              
              moveMinotaur(size * difficulty, b);
              setMode(Mode.MINOTAUR_RAMPAGE);
            }
          }
        });
  }

  public void reset() {
    Player1 = new Dog(0, size / 2, "files/dog1.png");
    Player2 = new Dog(size - 1, size / 2, "files/dog2.png");
    Minotaur = new Dog(size / 2, size / 2, "files/vacuum.png");
    HIGHLIGHT = new Dog(size / 2, size / 2, "files/highlight.png");
    Player1NumWalls = 10;
    Player2NumWalls = 10;
    boardPieces = new Entity[size][size];
    wallsToDraw = new Entity[2][size][size];
    walls = new TreeSet<Wall>();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        boardPieces[i][j] = null;
        wallsToDraw[0][i][j] = null;
        wallsToDraw[1][i][j] = null;
      }
    }
    Player1NumWalls = 10;
    Player2NumWalls = 10;
    Game.numWalls1.setText(
        "<html>" + Game.player1Name + " has:<br>" + Player1NumWalls + " walls left</html>");
    Game.numWalls2.setText(
        "<html>" + Game.player2Name + " has:<br>" + Player2NumWalls + " walls left</html>");
    boardPieces[0][size / 2] = Player1;
    boardPieces[size - 1][size / 2] = Player2;
    boardPieces[size / 2][size / 2] = Minotaur;
    setMode(Mode.PLAYER1_TURN);
    requestFocusInWindow();
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        g.drawRect(
            i * BOARD_WIDTH / size,
            j * BOARD_HEIGHT / size,
            BOARD_WIDTH / size,
            BOARD_HEIGHT / size);
      }
    }

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (j != size - 1 && wallsToDraw[0][i][j] != null) {
          wallsToDraw[0][i][j].draw(g, this);
        }
        if (i != size - 1 && wallsToDraw[1][i][j] != null) {
          wallsToDraw[1][i][j].draw(g, this);
        }
        if (boardPieces[i][j] != null) {
          boardPieces[i][j].draw(g, this);
        }
      }
    }
    HIGHLIGHT.draw(g, this);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
  }
}
