package Draw;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Zhaoyang on 2/24/2016.
 */
@XmlRootElement
public class Model extends Observable implements Serializable {

    @XmlRootElement
    public static class STROKE implements Serializable {
        @XmlElementWrapper(name = "Points")
        @XmlElement(name = "Pointx")
        ArrayList<Integer> coorx = new ArrayList<Integer>();
        @XmlElement(name = "Pointy")
        ArrayList<Integer> coory = new ArrayList<Integer>();
        @XmlElement(name = "Color")
        int color = Color.black.getRGB();
        @XmlElement(name = "width")
        int width = 1;

        public void STROKE() {
            coorx = new ArrayList<Integer>();
            coory = new ArrayList<Integer>();
        }

        public void addCoor(int x, int y) {
            coorx.add(x);
            coory.add(y);
        }

        public void setColor(Color c) {
            color = c.getRGB();
        }

        public void setWidth(int c) {
            width = c;
        }

        public STROKE newCopy() {
            STROKE temp = new STROKE();
            temp.coorx = this.coorx;
            temp.coory = this.coory;
            temp.color = this.color;
            temp.width = this.width;
            return temp;
        }


    }

    @XmlElementWrapper(name = "Strokes")
    @XmlElement(name = "Stroke")
    public ArrayList<STROKE> paststrokes = new ArrayList<STROKE>();
    public int jsValue = 0;
    public STROKE curStroke = new STROKE();
    ;
    public int curColor = Color.black.getRGB();
    public BufferedImage image = null;
    public int curWidth = 1;
    public boolean viewMode = true; //false for fit, true for full-size
    public boolean playing = false;
    public boolean playingback = false;

    Model() {
        setChanged();
        notifyObservers();
    }

    public void setJsValue(int v) {
        jsValue = v;
        System.out.println("Update1 to " + jsValue);
        setChanged();
        notifyObservers();
    }

    public Color getcurColor() {
        return new Color(curColor);
    }

    public void setPaststrokes(ArrayList<STROKE> sb) {
        paststrokes = sb;
    }

    public void setCurrentColor(Color c) {
        curColor = c.getRGB();
        setChanged();
        notifyObservers();
    }


    public void setCurWidth(int w) {
        curWidth = w;
        setChanged();
        notifyObservers();
    }

    public int getCurrentWidth() {
        return curWidth;
    }

    public void setToFit() {
        if (viewMode == false) return;
        viewMode = false;
        Main.removeScroll();
        setChanged();
        notifyObservers();
    }

    public void setToFullSize() {
        if (viewMode == true) return;
        viewMode = true;
        Main.addScroll();
        setChanged();
        notifyObservers();
    }

    public void startWithColorAndWidth() {
        if (jsValue % 1000 != 0) {
            ArrayList<Integer> tmpx = paststrokes.get(jsValue / 1000).coorx;
            ArrayList<Integer> tmpy = paststrokes.get(jsValue / 1000).coory;
            int fullsize = tmpx.size();
            while (tmpx.size() / 1.0 / fullsize >= (jsValue % 1000) / 1000.0)
                tmpx.remove(tmpx.size() - 1);
            tmpy.remove(tmpy.size() - 1);

            jsValue = jsValue + 1000 - jsValue % 1000;
        }
        playing = false;
        playingback = false;
        curStroke.setColor(new Color(curColor));
        curStroke.setWidth(curWidth);
        setChanged();
        notifyObservers();
    }

    public void curStrokeDraw(int x, int y) {
        curStroke.addCoor(x, y);
        setChanged();
        this.notifyObservers();
    }

    public void finishCurStroke() {
        jsValue = jsValue + 1000;
        System.out.println("Update to " + jsValue);
        while (paststrokes.size() > jsValue / 1000 - 1) {
            paststrokes.remove(jsValue / 1000 - 1);
        }
        paststrokes.add(curStroke.newCopy());
        curStroke = new STROKE();
        setChanged();
        notifyObservers();
    }

    public ArrayList<STROKE> getpastStrokes() {
        return paststrokes;
    }

    public STROKE getCurStroke() {
        return curStroke;
    }

    public void forceChange() {
        setChanged();
        notifyObservers();
    }

    public boolean getviewMode() {
        return viewMode;
    }


}

