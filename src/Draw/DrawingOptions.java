package Draw;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Zhaoyang on 2/24/2016.
 */
public class DrawingOptions extends JPanel implements Observer {
    private Model model;
    JLabel jl;
    JButton red, blue, green, yellow, black, choose;
    JButton width1, width2, width3, width4, width5, width6, width7;
    JPanel CurrentPanel;

    DrawingOptions(Model model_) {
        model = model_;
        setBackground(Color.GRAY);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel colorPanel = new JPanel();
        JPanel widthPanel = new JPanel();
        CurrentPanel = new JPanel();

        TitledBorder colorBorder = BorderFactory.createTitledBorder("Color");
        colorBorder.setTitleJustification(TitledBorder.LEFT);
        TitledBorder widthBorder = BorderFactory.createTitledBorder("Width");
        widthBorder.setTitleJustification(TitledBorder.LEFT);
        TitledBorder CurrentBorder = BorderFactory.createTitledBorder("Current");
        CurrentBorder.setTitleJustification(TitledBorder.LEFT);

        colorPanel.setBorder(colorBorder);
        widthPanel.setBorder(widthBorder);
        CurrentPanel.setBorder(CurrentBorder);


        this.add(colorPanel);
        this.add(widthPanel);
        this.add(CurrentPanel);

        colorPanel.setLayout(new GridLayout(3, 2));
        red = new JButton();
        red.setBackground(Color.RED);
        blue = new JButton();
        blue.setBackground(Color.BLUE);
        yellow = new JButton();
        yellow.setBackground(Color.YELLOW);
        green = new JButton();
        green.setBackground(Color.GREEN);
        black = new JButton();
        black.setBackground(Color.black);
        choose = new JButton();
        red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurrentColor(Color.RED);
            }
        });
        blue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurrentColor(Color.BLUE);
            }
        });
        yellow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurrentColor(Color.YELLOW);
            }
        });
        green.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurrentColor(Color.GREEN);
            }
        });
        black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurrentColor(Color.BLACK);
            }
        });

        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(choose, "Choose Color", model.getcurColor());
                if (newColor != null)
                    model.setCurrentColor(newColor);
            }
        });

        colorPanel.add(red);
        colorPanel.add(blue);
        colorPanel.add(green);
        colorPanel.add(yellow);
        colorPanel.add(black);
        colorPanel.add(choose);


        widthPanel.setLayout(new GridLayout(7, 1));
        width1 = new JButton();
        width2 = new JButton();
        width3 = new JButton();
        width4 = new JButton();
        width5 = new JButton();
        width6 = new JButton();
        width7 = new JButton();

        width1.setBackground(Color.WHITE);
        width2.setBackground(Color.WHITE);
        width3.setBackground(Color.WHITE);
        width4.setBackground(Color.WHITE);
        width5.setBackground(Color.WHITE);
        width6.setBackground(Color.WHITE);
        width7.setBackground(Color.WHITE);

        Graphics2D gg2;
        BufferedImage b1 = new BufferedImage(200, 10, BufferedImage.TYPE_INT_ARGB);
        gg2 = b1.createGraphics();
        gg2.setColor(Color.BLACK);
        gg2.setStroke(new BasicStroke(1));
        gg2.drawLine(40, 4, 160, 4);
        width1.setIcon(new ImageIcon(b1));

        BufferedImage b2 = new BufferedImage(200, 10, BufferedImage.TYPE_INT_ARGB);
        gg2 = b2.createGraphics();
        gg2.setColor(Color.BLACK);
        gg2.setStroke(new BasicStroke(2));
        gg2.drawLine(40, 4, 160, 4);
        width2.setIcon(new ImageIcon(b2));

        BufferedImage b3 = new BufferedImage(200, 10, BufferedImage.TYPE_INT_ARGB);
        gg2 = b3.createGraphics();
        gg2.setColor(Color.BLACK);
        gg2.setStroke(new BasicStroke(3));
        gg2.drawLine(40, 4, 160, 4);
        width3.setIcon(new ImageIcon(b3));


        BufferedImage b4 = new BufferedImage(200, 10, BufferedImage.TYPE_INT_ARGB);
        gg2 = b4.createGraphics();
        gg2.setColor(Color.BLACK);
        gg2.setStroke(new BasicStroke(4));
        gg2.drawLine(40, 4, 160, 4);
        width4.setIcon(new ImageIcon(b4));

        BufferedImage b5 = new BufferedImage(200, 10, BufferedImage.TYPE_INT_ARGB);
        gg2 = b5.createGraphics();
        gg2.setColor(Color.BLACK);
        gg2.setStroke(new BasicStroke(5));
        gg2.drawLine(40, 4, 160, 4);
        width5.setIcon(new ImageIcon(b5));

        BufferedImage b6 = new BufferedImage(200, 10, BufferedImage.TYPE_INT_ARGB);
        gg2 = b6.createGraphics();
        gg2.setColor(Color.BLACK);
        gg2.setStroke(new BasicStroke(6));
        gg2.drawLine(40, 4, 160, 4);
        width6.setIcon(new ImageIcon(b6));

        BufferedImage b7 = new BufferedImage(200, 10, BufferedImage.TYPE_INT_ARGB);
        gg2 = b7.createGraphics();
        gg2.setColor(Color.BLACK);
        gg2.setStroke(new BasicStroke(7));
        gg2.drawLine(40, 4, 160, 4);
        width7.setIcon(new ImageIcon(b7));


        width1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurWidth(1);
            }
        });

        width2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurWidth(2);
            }
        });
        width3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurWidth(3);
            }
        });

        width4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurWidth(4);
            }
        });
        width5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurWidth(5);
            }
        });
        width6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurWidth(6);
            }
        });
        width7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurWidth(7);
            }
        });
        widthPanel.add(width1);
        widthPanel.add(width2);
        widthPanel.add(width3);
        widthPanel.add(width4);
        widthPanel.add(width5);
        widthPanel.add(width6);
        widthPanel.add(width7);

        jl = new JLabel();
        CurrentPanel.setLayout(new GridLayout(1, 1));
        CurrentPanel.add(jl);

    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Toolkit.getDefaultToolkit().sync();

        Color c = model.getcurColor();
        int w = model.getCurrentWidth();


        BufferedImage bi = new BufferedImage(200, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg2 = bi.createGraphics();
        gg2.setColor(c);
        gg2.setStroke(new BasicStroke(w));
        gg2.drawLine(10, 4, 80, 4);
        jl.setIcon(new ImageIcon(bi));


    }
}