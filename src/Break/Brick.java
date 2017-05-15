package Break;

import java.awt.image.BufferedImage;

/**
 * Created by Zhaoyang on 1/25/2016.
 */
public class Brick extends Rec {
    boolean solid;
    public Brick(int x, int y, BufferedImage image){
        this.img = image;
        length = img.getWidth(null);
        height = img.getHeight(null);
        this.solid = true;
        this.x=x;
        this.y=y;
    }

    public void hit(){
        solid=false;
    }
    public boolean getSolid(){
        return solid;
    }
}
