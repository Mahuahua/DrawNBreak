package Draw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Zhaoyang on 2016-02-29.
 */
public class DrawingBoard extends JPanel implements Observer {
    private Model model;


    DrawingBoard(Model model_) {
        model = model_;
        addMouseListener(new MouseAdapter() {
                             @Override
                             public void mousePressed(MouseEvent e) {
                                 model.startWithColorAndWidth();
                             }

                             @Override
                             public void mouseReleased(MouseEvent e) {
                                 model.finishCurStroke();
                             }
                         }

        );


        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {


                double xfactor = getWidth() / 700.0;
                double yfactor = getHeight() / 500.0;
                if (model.viewMode == true) {
                    xfactor = 1;
                    yfactor = 1;
                }
                model.curStrokeDraw((int) Math.round(e.getX() / xfactor), (int) Math.round(e.getY() / yfactor));
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Toolkit.getDefaultToolkit().sync();


        if (model.viewMode == true) {
            //full size
            setBackground(Color.DARK_GRAY);
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, 700, 500);


            for (int j = 0; j < model.jsValue / 1000; j++) {
                Model.STROKE s = model.getpastStrokes().get(j);
                g2.setColor(new Color(s.color));
                g2.setStroke(new BasicStroke(s.width));
                for (int i = 0; i < s.coorx.size() - 1; i++) {
                    if (checkInSide(s.coorx.get(i), s.coory.get(i)) && checkInSide(s.coorx.get(i + 1), s.coory.get(i + 1)))
                        g2.drawLine(s.coorx.get(i), s.coory.get(i), s.coorx.get(i + 1), s.coory.get(i + 1));
                }
            }

            if (model.jsValue % 1000 != 0) {
                Model.STROKE s = model.getpastStrokes().get(model.jsValue / 1000);
                g2.setColor(new Color(s.color));
                g2.setStroke(new BasicStroke(s.width));
                for (int i = 0; i < (int) (s.coorx.size() * (model.jsValue % 1000) / 1000.0) - 1; i++) {
                    if (checkInSide(s.coorx.get(i), s.coory.get(i)) && checkInSide(s.coorx.get(i + 1), s.coory.get(i + 1)))
                        g2.drawLine(s.coorx.get(i), s.coory.get(i), s.coorx.get(i + 1), s.coory.get(i + 1));
                }

            }

            if (model.getCurStroke().coorx.size() != 0) {
                g2.setColor(new Color(model.getCurStroke().color));
                g2.setStroke(new BasicStroke(model.getCurStroke().width));
                for (int i = 0; i < model.getCurStroke().coorx.size() - 1; i++) {
                    if (checkInSide(model.getCurStroke().coorx.get(i), model.getCurStroke().coory.get(i)) && checkInSide(model.getCurStroke().coorx.get(i + 1), model.getCurStroke().coory.get(i + 1)))
                        g2.drawLine(model.getCurStroke().coorx.get(i), model.getCurStroke().coory.get(i), model.getCurStroke().coorx.get(i + 1), model.getCurStroke().coory.get(i + 1));
                }
            }


        } else {
            //fit
            setBackground(Color.DARK_GRAY);
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());

            double xfactor = getWidth() / 700.0;
            double yfactor = getHeight() / 500.0;


            for (int j = 0; j < model.jsValue / 1000; j++) {
                Model.STROKE s = model.getpastStrokes().get(j);
                g2.setColor(new Color(s.color));
                g2.setStroke(new BasicStroke((int) Math.round(xfactor * yfactor * s.width)));
                for (int i = 0; i < s.coorx.size() - 1; i++) {
                    if (checkInSide(s.coorx.get(i), s.coory.get(i)) && checkInSide(s.coorx.get(i + 1), s.coory.get(i + 1)))
                        g2.drawLine((int) Math.round(xfactor * s.coorx.get(i)), (int) Math.round(yfactor * s.coory.get(i)), (int) Math.round(xfactor * s.coorx.get(i + 1)), (int) Math.round(yfactor * s.coory.get(i + 1)));
                }
            }

            if (model.jsValue % 1000 != 0) {
                Model.STROKE s = model.getpastStrokes().get(model.jsValue / 1000);
                g2.setColor(new Color(s.color));
                g2.setStroke(new BasicStroke((int) Math.round(xfactor * yfactor * s.width)));
                for (int i = 0; i < (int) (s.coorx.size() * (model.jsValue % 1000) / 1000.0) - 1; i++) {
                    if (checkInSide(s.coorx.get(i), s.coory.get(i)) && checkInSide(s.coorx.get(i + 1), s.coory.get(i + 1)))
                        g2.drawLine((int) Math.round(xfactor * s.coorx.get(i)), (int) Math.round(yfactor * s.coory.get(i)), (int) Math.round(xfactor * s.coorx.get(i + 1)), (int) Math.round(yfactor * s.coory.get(i + 1)));
                }

            }

            if (model.getCurStroke().coorx.size() != 0) {
                g2.setColor(new Color(model.getCurStroke().color));
                g2.setStroke(new BasicStroke((int) Math.round(xfactor * yfactor * model.getCurStroke().width)));
                for (int i = 0; i < model.getCurStroke().coorx.size() - 1; i++) {
                    if (checkInSide(model.getCurStroke().coorx.get(i), model.getCurStroke().coory.get(i)) && checkInSide(model.getCurStroke().coorx.get(i + 1), model.getCurStroke().coory.get(i + 1)))
                        g2.drawLine((int) Math.round(xfactor * model.getCurStroke().coorx.get(i)), (int) Math.round(yfactor * model.getCurStroke().coory.get(i)), (int) Math.round(xfactor * model.getCurStroke().coorx.get(i + 1)), (int) Math.round(yfactor * model.getCurStroke().coory.get(i + 1)));
                }
            }


        }


        model.image = new BufferedImage(700,500, BufferedImage.TYPE_INT_RGB);
        Graphics2D gg = model.image.createGraphics();
        gg.setColor(Color.WHITE);
        gg.fillRect(0, 0, 700, 500);
        double xfactor = getWidth() / 700.0;
        double yfactor = getHeight() / 500.0;


        for (int j = 0; j < model.jsValue / 1000; j++) {
            Model.STROKE s = model.getpastStrokes().get(j);
            gg.setColor(new Color(s.color));
            gg.setStroke(new BasicStroke((int) Math.round(xfactor * yfactor * s.width)));
            for (int i = 0; i < s.coorx.size() - 1; i++) {
                if (checkInSide(s.coorx.get(i), s.coory.get(i)) && checkInSide(s.coorx.get(i + 1), s.coory.get(i + 1)))
                    gg.drawLine((int) Math.round(xfactor * s.coorx.get(i)), (int) Math.round(yfactor * s.coory.get(i)), (int) Math.round(xfactor * s.coorx.get(i + 1)), (int) Math.round(yfactor * s.coory.get(i + 1)));
            }
        }

        if (model.jsValue % 1000 != 0) {
            Model.STROKE s = model.getpastStrokes().get(model.jsValue / 1000);
            gg.setColor(new Color(s.color));
            gg.setStroke(new BasicStroke((int) Math.round(xfactor * yfactor * s.width)));
            for (int i = 0; i < (int) (s.coorx.size() * (model.jsValue % 1000) / 1000.0) - 1; i++) {
                if (checkInSide(s.coorx.get(i), s.coory.get(i)) && checkInSide(s.coorx.get(i + 1), s.coory.get(i + 1)))
                    gg.drawLine((int) Math.round(xfactor * s.coorx.get(i)), (int) Math.round(yfactor * s.coory.get(i)), (int) Math.round(xfactor * s.coorx.get(i + 1)), (int) Math.round(yfactor * s.coory.get(i + 1)));
            }

        }

        if (model.getCurStroke().coorx.size() != 0) {
            gg.setColor(new Color(model.getCurStroke().color));
            gg.setStroke(new BasicStroke((int) Math.round(xfactor * yfactor * model.getCurStroke().width)));
            for (int i = 0; i < model.getCurStroke().coorx.size() - 1; i++) {
                if (checkInSide(model.getCurStroke().coorx.get(i), model.getCurStroke().coory.get(i)) && checkInSide(model.getCurStroke().coorx.get(i + 1), model.getCurStroke().coory.get(i + 1)))
                    gg.drawLine((int) Math.round(xfactor * model.getCurStroke().coorx.get(i)), (int) Math.round(yfactor * model.getCurStroke().coory.get(i)), (int) Math.round(xfactor * model.getCurStroke().coorx.get(i + 1)), (int) Math.round(yfactor * model.getCurStroke().coory.get(i + 1)));
            }
        }
    }

    public boolean checkInSide(int x, int y) {
        if (x < 0 || y < 0 || x > 700 || y > 500)
            return false;

        return true;
    }
}
