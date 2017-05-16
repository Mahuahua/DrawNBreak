package Draw;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by Zhaoyang on 2/24/2016.
 */
public class PlayBack extends JPanel implements Observer {
    private Model model;
    JButton start, end, play, playback, breaking;
    JSlider js;
    Timer timer;
    Timer timerb;

    PlayBack(Model model_) {
        model = model_;
        setBackground(Color.GRAY);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        play = new JButton("Play");
        start = new JButton("Start");
        end = new JButton("End");
        playback = new JButton("Backwards");
        breaking = new JButton("BreakImage");
        js = new JSlider();
        js.setMinimum(0);
        js.setMaximum(0);
        js.setValue(0);
        js.setBackground(Color.GRAY);

        js.setMinorTickSpacing(1000);
        js.setPaintTicks(true);
        play.setEnabled(false);
        playback.setEnabled(false);

        js.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setJsValue(js.getValue());
            }
        });

        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                js.setValue(js.getMaximum());
            }
        });
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                js.setValue(0);
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.playing = true;
                model.playingback = false;
                timer = new Timer();
                timer.schedule(new update(), 0, 1);
            }
        });

        playback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.playingback = true;
                model.playing = false;
                timerb = new Timer();
                timerb.schedule(new updateb(), 0, 1);
            }
        });


        breaking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Break.DoNotBreak(model.image);
            }
        });


        add(breaking);
        add(play);
        add(playback);
        add(js);
        add(start);
        add(end);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (model.getpastStrokes().size() != 0 || model.getCurStroke().coorx.size() != 0) {
            play.setEnabled(true);
            playback.setEnabled(true);
            Main.setSaveBarEnable(true);
        } else {
            play.setEnabled(false);
            playback.setEnabled(false);
            Main.setSaveBarEnable(false);
        }
        if (js.getMaximum() != model.getpastStrokes().size() * 1000) {
            js.setMaximum(model.getpastStrokes().size() * 1000);
            js.setValue(js.getMaximum());
        } else
            js.setValue(model.jsValue);
        System.out.println("js " + js.getValue());
        repaint();
    }


    private class update extends TimerTask {
        @Override
        public void run() {
            if (model.playing) {
                if (model.jsValue >= js.getMaximum()) {
                    model.playing = false;
                } else
                    model.setJsValue(model.jsValue + 1);
            } else {
                timer.cancel();
            }
        }
    }

    private class updateb extends TimerTask {
        @Override
        public void run() {
            if (model.playingback) {
                if (model.jsValue <= 0) {
                    model.playingback = false;
                } else
                    model.setJsValue(model.jsValue - 1);
            } else {
                timerb.cancel();
            }
        }
    }
}