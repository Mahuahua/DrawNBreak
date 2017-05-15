package Break;

/**
 * Created by Zhaoyang on 1/25/2016.
 */
public class Config {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int NUMPARTS = 128;// 16*8
    public static final int NUMPARTROW = 8;
    public static final int NUMPARTCOL = 16;
    public static final int PARTWIDTH = 60; //16*60 = 960
    public static final int PARTHEIGHT = 40; //8 * 40 = 320
    public static final int TOPPADDING = 40;
    public static final int SIDEPADDING = (WIDTH - PARTWIDTH * NUMPARTCOL)/2;
    public static double  FPS = 40;
    public static double SPEED = 400;
    public static final int paddleLine = 700;
    public static final int deadline = 750;
    public static final int maxX = 1175;

}
