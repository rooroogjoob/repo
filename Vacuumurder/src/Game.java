package src; /**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */

public class Game implements Runnable {
    public static JLabel modeLabel = new JLabel();
    public static JLabel numWalls1 = new JLabel();
    public static JLabel numWalls2 = new JLabel();
    public static String player1Name;
    public static String player2Name;
    public static String vMessageP1;
    public static String vMessageP2;
    
    public void run() {
        final JFrame frame = new JFrame("Vacuumurder");
        frame.setSize(400, 400);
        Board board = new Board(9);
        frame.add(board);
        modeLabel.setText(board.getMode());
        frame.add(modeLabel, BorderLayout.SOUTH);
        JButton walls = new JButton("Toggle Walls");
        walls.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (board.getMode().equals("PLAYER1_TURN") || 
                    board.getMode().equals("PLAYER1_MOVE")) {
                    board.erasePaths(board.getPlayer1());
                    board.setMode(Mode.PLAYER1_WALL);
                    Entity newHL = new Wall(0, 0, "files/highlight.png", "Vertical", true);
                    board.setHighlight(newHL);
                    board.requestFocusInWindow();
                    board.repaint();
            } else if (board.getMode().equals("PLAYER1_WALL")) {
                    board.setMode(Mode.PLAYER1_TURN); 
                    Entity newHL = new Dog(board.size / 2, board.size / 2, "files/highlight.png");
                    board.setHighlight(newHL);
                    board.requestFocusInWindow();
                    board.repaint();
               } else if (board.getMode().equals("PLAYER2_TURN") ||
                    board.getMode().equals("PLAYER2_MOVE") ) {
                    board.erasePaths(board.getPlayer2());
                    board.setMode(Mode.PLAYER2_WALL);
                    Entity newHL = new Wall(0, 0, "files/highlight.png", "Vertical", true);
                    board.setHighlight(newHL);
                    board.requestFocusInWindow();
                    board.repaint();
               } else if (board.getMode().equals("PLAYER2_WALL")) {
                    board.setMode(Mode.PLAYER2_TURN); 
                    Entity newHL = new Dog(board.size / 2, board.size / 2, "files/highlight.png");
                    board.setHighlight(newHL);
                    board.requestFocusInWindow();
                    board.repaint();
               }
            }
        });
        JOptionPane howTo = new JOptionPane();
        JPanel menuBar = new JPanel(new GridLayout(6, 1));
        JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                howTo.showMessageDialog(instructions, "How To Play:\n\nWelcome to " + 
       "Vacuumurder! The Quoridor and Minotaurus inspired strategy match!" +
       "\nYou and a friend will each take the roles of dogs (doggos, puppers, " +
       "pupperinos,\nif you desire).\n\nYour goal is to reach the other end of the board " +
       "where your opponent starts.\nMaybe there's peanut butter there, I don't know. " +
       "It's not my job to know your\nmotivations for doing so.\n\nBut watch out! The human " +
       "(hooman?) is cleaning the house and is terrorizing you\nwith the vacuum! Don't let " +
       "that noise monster get to you or you'll be scared back\nto the start!\n" +
       "\nYou may also choose to place pink doggie gates to block your opponent's path! " +
       "\n\nPlease be a good boy and don't trap anything with your gates. No one likes\n" +
       "timeouts. If your opponent decides traps you, the vacuum, or blocks you from\n" + 
       "reaching the other side entirely, feel free to press the 'SUBMIT TO VACUUM' button\n" +
       "to veto such despicable behavior and reset the match." +
       "\n\nThe CONTROLS are simple, the ARROW KEYS move the red cursor, and ENTER" +
       "\nselects your dog to move them." +
       "\n\nNOTE: You may MOVE or place a WALL but not both during a given turn. And BE CAREFUL\n" +
       "You only have a limited number of walls. If you\nrun out, the advantage goes to your " +
       "opponent.\n\nBORK BORK, my friends, BORK BORK.",
       "How To Play:",
       JOptionPane.INFORMATION_MESSAGE, new ImageIcon(""));
            }
        });
        menuBar.add(instructions);
        frame.add(menuBar, BorderLayout.WEST);
        menuBar.add(walls);
        menuBar.add(numWalls1);
        menuBar.add(numWalls2);
        JButton reset = new JButton("SUBMIT TO VACUUM");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        menuBar.add(reset);
        JRadioButton easy = new JRadioButton("Roomba");
        JRadioButton medium = new JRadioButton("Dyson");
        JRadioButton hard = new JRadioButton("Shop-Vac");
        easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setDifficulty(1);
                board.requestFocusInWindow();
            }
        });
        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setDifficulty(2);
                 board.requestFocusInWindow();
            }
        });
        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.setDifficulty(3);
                 board.requestFocusInWindow();
            }
        });
        JOptionPane whoAreYouP1 = new JOptionPane(); 
        JOptionPane victoryScreechP1 = new JOptionPane();
        JOptionPane whoAreYouP2 = new JOptionPane();
        JOptionPane victoryScreechP2 = new JOptionPane();
        player1Name = (String) whoAreYouP1.showInputDialog(whoAreYouP1, "Who Are You, Player 1?",
                                           "Butt-Sniff Greetings", 
                                           JOptionPane.PLAIN_MESSAGE, null, null, null);
        vMessageP1 = (String) victoryScreechP1.showInputDialog(victoryScreechP1,
                                          "What do you want your victory message to be, Player 1?",
                                          "Victory Screech", 
                                          JOptionPane.PLAIN_MESSAGE, null, null, null);
        player2Name = (String) whoAreYouP2.showInputDialog(whoAreYouP2, "Who Are You, Player 2?",
                                          "Butt-Sniff Greetings", 
                                          JOptionPane.PLAIN_MESSAGE, null, null, null);
        vMessageP2 = (String) victoryScreechP2.showInputDialog(victoryScreechP2,
                                          "What do you want your victory message to be, Player 2?",
                                          "Victory Screech", 
                                          JOptionPane.PLAIN_MESSAGE, null, null, null);
        numWalls1.setText("<html>" + player1Name + " has:<br>" + board.getP1Walls() + 
                           " walls left</html>");
        numWalls2.setText("<html>" + player2Name + " has:<br>" + board.getP2Walls() + 
                           " walls left</html>");
        ButtonGroup difficulty = new ButtonGroup();
        JPanel difficultyPanel = new JPanel(new GridLayout(2, 1));
        JPanel difficultyChoice = new JPanel();
        difficulty.add(easy);
        difficulty.add(medium);
        difficulty.add(hard); 
        easy.setSelected(true);
        difficultyPanel.add(new JLabel("Difficulty:"));
        difficultyPanel.add(difficultyChoice);
        difficultyChoice.add(easy);
        difficultyChoice.add(medium);
        difficultyChoice.add(hard); 
        menuBar.add(difficultyPanel); 
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        board.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}