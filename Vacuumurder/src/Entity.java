package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Entity {
    private int xPos; 
    private int yPos;
    private String imageString;
    private BufferedImage image;
    
    public Entity(int xPos, int yPos, String image) {
       this.xPos = xPos;
       this.yPos = yPos;
        this.imageString = image;
         try {
            if (this.image == null) {
                this.image = ImageIO.read(new File(this.imageString));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }  
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
    
    public String getImageString() {
        return this.imageString;
    }
    
    public boolean equals(Entity e) {
      return imageString.equals(e.getImageString());
    }
    
    public int getX() {
        return this.xPos;
    }
    
    public int getY() {
        return this.yPos;
    }
    
    public void setX(int x) {
         this.xPos = x;
    }
    
    public void setY(int y) {
         this.yPos = y;
    }
 
    public void move(int dx, int dy, Board b) {
        xPos += dx;
        yPos += dy;
        if (getX() < 0) {
            this.xPos = 0;
        }
        if (getY() < 0) {
            yPos = 0;
        }
         if (getX() > b.size - 1) {
            xPos = b.size - 1;
        }
        if (getY() > b.size - 1) {
            yPos = b.size - 1;
        }
        
    }

    public abstract void draw(Graphics gc, Board b);
    //to implement
}















