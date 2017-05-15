package Break;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Created by Zhaoyang on 1/25/2016.
 */
public class Paddle extends Rec{
    boolean breaks = false;
    int nextmove;
    Image img;
    public Paddle(){
        reset();
    }


    public void setToBreak(){
        breaks = true;
        try {
            img = ImageIO.read(new File("paddle_break.gif"));
        }catch (Exception e){
        }
    }
    public void reset(){
        try {
            img = ImageIO.read(new File("paddle.jpg"));
        }catch (Exception e){
        }
        length = img.getWidth(null);
        height = img.getHeight(null);
        x = 400;
        y = Config.paddleLine;
    }

    public void move(){
        x= x+ nextmove;
        if (x < 0)  x = 0;
        if (x > Config.WIDTH - length) x = Config.WIDTH - length;
        nextmove= 0;
    }



    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT:
                nextmove= -50;break;
            case KeyEvent.VK_RIGHT:
                nextmove = 50; break;
            default:
                break;
        }

    }

    }










