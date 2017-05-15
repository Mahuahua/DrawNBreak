package Break;

import java.awt.*;

/**
 * Created by Zhaoyang on 1/25/2016.
 */
public class Rec  {
    int x,y,length,height;
    Image img;
    Rectangle getRec() {
        return new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
    }

}
