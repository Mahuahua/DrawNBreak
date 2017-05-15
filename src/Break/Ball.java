package Break;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * Created by Zhaoyang on 1/25/2016.
 */
public class Ball extends Rec{
    boolean badboy = false;
    double vx;
    double vy;
    double cx;
    double cy;
    public Ball(boolean badboy){
        try {
            if (badboy)
                img = ImageIO.read(new File("evil_ball.gif"));
            else
                img = ImageIO.read(new File("ball.gif"));
        }catch (Exception e){
        }
        length = img.getWidth(null);
        height = img.getHeight(null);
        this.badboy=badboy;
        reset();
    }
    public void reset(){
        vx=-Math.sqrt(0.5);
        vy=-Math.sqrt(0.5);
        cx = 525+(int)Math.round(Math.random()*30)-15;
        cy = 375+(int)Math.round(Math.random()*10)-5;
        x = (int)Math.round(cx);
        y = (int)Math.round(cy);
    }

    public void nextState(){
        cy = cy + vy;
        cx = cx + vx;
        x = (int)Math.round(cx);
        y = (int)Math.round(cy);
    }

    public void setV(double nx, double ny){
        vx=nx;
        vy=ny;
        vx = vx + (Math.random()/5.0)-1.0/10;
        vy = vy + (Math.random()/5.0)-1.0/10;
        if (vx > 1.4) vx =1.4;
        if (vy > 1.4) vy =1.4;
        if (vx < -1.4) vx =-1.4;
        if (vy < -1.4) vy =-1.4;
        if (vx > 0 && vx<0.3) vx =0.3;
        if (vx < 0 && vx>-0.3) vx =-0.3;
        if (vy > 0 && vy<0.3) vy =0.3;
        if (vy < 0 && vy>-0.3) vy =-0.3;
    }
}
