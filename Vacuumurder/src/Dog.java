import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Dog extends Entity {
    
     public Dog(int x, int y, String image) {
        super(x, y, image);
    }

    @Override
     public void draw(Graphics gc, Board b) {
         gc.drawImage(getImage(),
                      this.getX() * Board.BOARD_WIDTH / b.size, 
                      this.getY() *  Board.BOARD_HEIGHT / b.size,
                      Board.BOARD_WIDTH / b.size, Board.BOARD_HEIGHT / b.size,
                      null);
     }
}








































