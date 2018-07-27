package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Wall extends Entity implements Comparable<Wall> {
     private String imageString; 
     private String allignment;
     private BufferedImage image;
     private boolean highlight;
     //private TreeSet<Wall> neighbors = new TreeSet<Wall>();
     private ArrayList<Wall> neighbors = new ArrayList<Wall>();
    
    public Wall(int x, int y, String image, String allignment) {
        super(x, y, image);
        this.allignment = allignment;
        this.imageString = image;
         this.highlight = false;
         try {
            if (this.image == null) {
                this.image = ImageIO.read(new File(this.imageString));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }  
    }
    
     public Wall(int x, int y, String image, String allignment, Board b) {
        super(x, y, image);
        this.allignment = allignment;
        this.imageString = image;
         this.highlight = false;
         for (Wall w : b.getWalls()) {
             if (this.overlaps(w)) {
                 neighbors.add(w);
             }
         }
         try {
            if (this.image == null) {
                this.image = ImageIO.read(new File(this.imageString));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }  
    }
    
     public Wall(int x, int y, String image, String allignment, boolean h) {
        super(x, y, image);
        this.allignment = allignment;
        this.imageString = image;
        this.highlight = h;
         try {
            if (this.image == null) {
                this.image = ImageIO.read(new File(this.imageString));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }  
    }
    
    public boolean isHL() {
        return this.highlight;
    }
    
    public String getAllignment() {
        return allignment;
    }
    
    public void changeAllignment(String al) {
        this.allignment = al;
    }
    
    
    public boolean overlaps(Wall w) {
        if (this.allignment.equals(w.getAllignment())) {      
        if (this.getX() == w.getX() && this.getY() == w.getY())
            return true;
            }
        if (this.allignment.equals("Horizontal")) {
            if (this.getX() == w.getX() && this.getY() == w.getY()) {
                 return true;
            } else {
             return false;
            }
        } else if (this.allignment.equals("Vertical")) {
            if (this.getY() == w.getY() && this.getX() == w.getX()) {
                return true;
            } else {
             return false;
            }
        }
        //for our purposes, things with different allignments do not need
        //to be considered so to be safe, we'll say they overlap.
       //  neighbors.add(w);
          return true;
    }
    
    
    
    @Override
    public int compareTo(Wall w) {
        if (this.overlaps(w)) {
            return 0;
        } else if (this.getX() > w.getX() && this.getY() > w.getY()) {
            return 1;
        } else {
            return - 1;
        }
    }
    
    
    
    
    @Override
     public void draw(Graphics gc, Board b) {
         int length = 1;
         if (isHL() == true) {
             length = 2;
         }
         if (allignment.equals("Horizontal")) {
             gc.drawImage(this.image,
                          this.getX() * Board.BOARD_WIDTH / b.size,
                          this.getY() *  Board.BOARD_HEIGHT / b.size + (7 * Board.BOARD_WIDTH) / (8 * b.size),
                          length * Board.BOARD_WIDTH / b.size,
                          Board.BOARD_HEIGHT / (4 * b.size),
                          null);
         } else if (allignment.equals("Vertical")) {
         gc.drawImage(this.image,
                      this.getX() * Board.BOARD_WIDTH / b.size +  (7 * Board.BOARD_WIDTH) / (8 * b.size),
                      this.getY() *  Board.BOARD_HEIGHT / b.size,
                      Board.BOARD_WIDTH / (4 * b.size),
                      length * Board.BOARD_HEIGHT / b.size,
                      null);
         }
     }
}