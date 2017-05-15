package Break;

import javax.swing.*;
import java.awt.Dimension;
/**
 * Created by Zhaoyang on 1/25/2016.
 */
public class DoNotBreak extends JFrame {

    public DoNotBreak(double FPS,double speed) {
        add(new gamePanel(FPS,speed));
        setTitle("Break Image. Not Break.Paddle!");
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Config.WIDTH, Config.HEIGHT);
        setVisible(true);
    }
    public static void main(String[] args) {

        double FPS = Config.FPS;
        double speed = Config.SPEED;
        if (args.length>0 && Integer.parseInt(args[0])>2){
            FPS = Double.parseDouble(args[0]);
            if (Integer.parseInt(args[0])>3)
            speed =Double.parseDouble(args[1]);

        }
       DoNotBreak game = new DoNotBreak(FPS,speed);
    }
}