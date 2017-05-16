package Break;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.TimerTask;
import java.util.Timer;
import java.awt.Toolkit;

/**
 * Created by Zhaoyang on 1/25/2016.
 */
public class gamePanel extends JPanel {
    Paddle paddle;
    Brick bricks[];
    Ball ball;
    Ball badboy;
    BufferedImage bufferedImage;
    public int gameState = 0;//state 0 ,1 ,2 ,3,4 -> pre,in, in with hazard, post-win, post-lose
    //6 pause
    int lastState = 0;
    int score = 0;
    int life = 3;


    double xsizefactor = 1;
    double ysizefactor = 1;
    Image WelcomeBG, GameBG, LossBG, WinBG;
    double speedfactor = 0;


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // System.out.println(gameState);
        if (gameState == 1 || gameState == 2) {
            drawObjects(g2);
        } else if (gameState == 0) {
            startScreen(g2);
        } else if (gameState == 3) {
            winScreen(g2);
            timer.cancel();
        } else if (gameState == 4) {
            lossScreen((g2));
            timer.cancel();
        }


        Toolkit.getDefaultToolkit().sync();
    }

    private void startScreen(Graphics2D g2) {
        g2.drawImage(WelcomeBG, 0, 0, (int) Math.round(1200 * xsizefactor), (int) Math.round(800 * ysizefactor), null);
        Font font = new Font("Raanana", Font.BOLD, (int) Math.round(20 * Math.min(xsizefactor, ysizefactor)));
        g2.setFont(font);
        g2.setColor(new Color(120, 0, 120));
        g2.drawString("Welcome! Press any key to start the game", (int) Math.round(200 * xsizefactor), (int) Math.round(300 * ysizefactor));
        g2.drawString("Press left/right arrow to move the paddle left/right, move mouse over also works, P for pause", (int) Math.round(200 * xsizefactor), (int) Math.round(340 * ysizefactor));
        g2.drawString("Remember to keep the silver ball up by bouncing it with the paddle", (int) Math.round(200 * xsizefactor), (int) Math.round(380 * ysizefactor));
        g2.drawString("Destroy all parts of image using the ball", (int) Math.round(200 * xsizefactor), (int) Math.round(420 * ysizefactor));
        g2.drawString("When 5 of the parts are gone, the sanity ball appears ", (int) Math.round(200 * xsizefactor), (int) Math.round(460 * ysizefactor));
        g2.drawString("Do not let it touch the paddle or it will break!", (int) Math.round(200 * xsizefactor), (int) Math.round(500 * ysizefactor));
    }

    private void lossScreen(Graphics2D g2) {

        g2.drawImage(LossBG, 0, 0, (int) Math.round(1200 * xsizefactor), (int) Math.round(800 * ysizefactor), null);
        Font font = new Font("Jokerman", Font.BOLD | Font.ITALIC, (int) Math.round(40 * Math.min(xsizefactor, ysizefactor)));
        g2.setFont(font);
        g2.setColor(new Color(255, 126, 10));
        g2.drawString("You lost! Press any key to re-start the game", (int) Math.round(200 * xsizefactor), (int) Math.round(400 * ysizefactor));
    }

    private void winScreen(Graphics2D g2) {

        g2.drawImage(WinBG, 0, 0, (int) Math.round(1200 * xsizefactor), (int) Math.round(800 * ysizefactor), null);
        Font font = new Font("Jokerman", Font.BOLD | Font.ITALIC, (int) Math.round(40 * Math.min(xsizefactor, ysizefactor)));
        g2.setFont(font);
        g2.setColor(new Color(0, 120, 200));
        g2.drawString("You win! Press any key to re-start the game", (int) Math.round(200 * xsizefactor), (int) Math.round(400 * ysizefactor));
    }

    private void drawObjects(Graphics2D g2) {
        g2.drawImage(GameBG, 0, 0, (int) Math.round(1200 * xsizefactor), (int) Math.round(800 * ysizefactor), null);
        for (int i = 0; i < Config.NUMPARTS; i++) {
            if (bricks[i].solid) {
                g2.drawImage(bricks[i].img, (int) Math.round(bricks[i].x * xsizefactor), (int) Math.round(bricks[i].y * ysizefactor),
                        (int) Math.round(bricks[i].length * xsizefactor), (int) Math.round(bricks[i].height * ysizefactor), this);
            }
        }
        g2.drawImage(paddle.img, (int) Math.round(paddle.x * xsizefactor), (int) Math.round(paddle.y * ysizefactor),
                (int) Math.round(paddle.length * xsizefactor), (int) Math.round(paddle.height * ysizefactor), this);
        g2.drawImage(ball.img, (int) Math.round(ball.x * xsizefactor), (int) Math.round(ball.y * ysizefactor),
                (int) Math.round(ball.length * xsizefactor), (int) Math.round(ball.height * ysizefactor), this);


        if (gameState == 2) {
            g2.drawImage(badboy.img, (int) Math.round(badboy.x * xsizefactor), (int) Math.round(badboy.y * ysizefactor),
                    (int) Math.round(badboy.length * xsizefactor), (int) Math.round(badboy.height * ysizefactor), this);
        }
        Font font = new Font("Raanana", Font.BOLD, (int) Math.round(20 * Math.min(xsizefactor, ysizefactor)));
        g2.setFont(font);
        g2.setColor(new Color(80, 200, 180));
        g2.drawString("Current Score :" + score, (int) Math.round(10 * xsizefactor), (int) Math.round(20 * ysizefactor));
        g2.drawString("Current FPS :" + Config.FPS, (int) Math.round(10 * xsizefactor), (int) Math.round(40 * ysizefactor));
        g2.drawString("Current Lives :" + life, (int) Math.round(10 * xsizefactor), (int) Math.round(60 * ysizefactor));
    }


    public class MA extends MouseAdapter {
        public void mouseMoved(MouseEvent e) {
            paddle.x = (int) Math.round((e.getX()) / xsizefactor) - paddle.length / 2;
            if (paddle.x < 0) paddle.x = 0;
            if (paddle.x + paddle.length > Config.maxX) paddle.x = Config.maxX - paddle.length - 1;
        }

    }

    public class KA extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (gameState == 0 || (gameState == 4 || gameState == 3)) {
                init(bufferedImage);
                timer = new Timer();
                timer.schedule(new update(), 0, Math.round(1000 / Config.FPS));
            } else if (e.getKeyChar() == 'p') {
                if (gameState != 6) {
                    lastState = gameState;
                    gameState = 6;
                    new update();
                    timer.cancel();
                } else {
                    gameState = lastState;
                    timer = new Timer();
                    timer.schedule(new update(), 0, Math.round(1000 / Config.FPS));

                }
            } else {
                paddle.keyPressed(e);
            }
        }


    }

    public class CL extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            updateSize();
        }
    }

    public void updateSize() {
        ysizefactor = this.getHeight() / 1.0 / Config.HEIGHT;
        xsizefactor = this.getWidth() / 1.0 / Config.WIDTH;
    }

    Timer timer;

    public gamePanel(double fps, double speed, BufferedImage image) {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new KA());
        this.addMouseMotionListener(new MA());
        this.addComponentListener(new CL());
        Config.FPS = fps;
        Config.SPEED = speed;
        try {
            WelcomeBG = ImageIO.read(new File("welcome.jpg"));
            GameBG = ImageIO.read(new File("game.jpg"));
            LossBG = ImageIO.read(new File("loss.jpg"));
            WinBG = ImageIO.read(new File("win.jpg"));
        } catch (Exception e) {
            System.out.println("imagebackgroundfilereaderrOR");
        }
        init(image);
        gameState = 0;
    }

    public void init(BufferedImage image) {
        gameState = 1;
        life = 3;
        score = 0;
        ball = new Ball(false);//a good ball
        paddle = new Paddle();
        bricks = new Brick[Config.NUMPARTS];
        badboy = new Ball(true);//a evil ball
        int c = 0;
        BufferedImage inputImage = image;
        if (image == null) {
            try {
                inputImage = ImageIO.read(new File("breakMe.jpg"));
            } catch (Exception e) {
            }
        }

        Image img = inputImage.getScaledInstance(Config.PARTWIDTH * Config.NUMPARTCOL, Config.PARTHEIGHT * Config.NUMPARTROW, Image.SCALE_DEFAULT);
        bufferedImage = new BufferedImage(Config.PARTWIDTH * Config.NUMPARTCOL, Config.PARTHEIGHT * Config.NUMPARTROW, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(img, 0, 0, null);
        for (int i = 0; i < Config.NUMPARTROW; i++) {
            for (int j = 0; j < Config.NUMPARTCOL; j++) {
                BufferedImage buffImage = bufferedImage.getSubimage(
                        j * Config.PARTWIDTH,
                        i * Config.PARTHEIGHT,
                        Config.PARTWIDTH-1,
                        Config.PARTHEIGHT-1);
                bricks[c] = new Brick(j * Config.PARTWIDTH + Config.SIDEPADDING, i * Config.PARTHEIGHT + Config.TOPPADDING, buffImage);
                c++;
            }
        }
    }

    private class update extends TimerTask {
        @Override
        public void run() {
            if (gameState == 1 || gameState == 2) {
                speedfactor = speedfactor + Config.SPEED / Config.FPS;
                while (speedfactor > 1) {
                    speedfactor--;
                    ball.nextState();
                    if (gameState == 2) {
                        badboy.nextState();
                    }
                    reflect();
                }
                paddle.move();
            }
            repaint();
        }
    }

    private int findSmall(double a, double b, double c, double d) {
        if (a < b && a < c && a < d) return 1;
        if (b < c && b < d) return 2;
        if (c < d) return 3;
        return 4;
    }

    private int findSmall6(double x, double y, double a, double b, double c, double d) {
        if (x < y && x < a && x < b && x < c && x < d) {
            return 1;
        }
        if (y < a && y < b && y < c && y < d) {
            return 2;
        }
        return 2 + findSmall(a, b, c, d);

    }

    private void reflect() {


        int x1 = ball.x;
        int x2 = ball.x + ball.length;
        int y1 = ball.y;
        int y2 = ball.y + ball.height;

        //check death
        if (y1 > Config.deadline) {
            //death
            life--;
            score = score - 500;
            if (life <= 0) {
                gameState = 4;
            }
            ball.reset();
        }


        //check boarder
        if (x2 >= Config.maxX) {
            ball.setV(-ball.vx, ball.vy);
            score = score + 10;
        }
        if (y1 <= 0) {
            ball.setV(ball.vx, -ball.vy);
            score = score + 10;
        }
        if (x1 <= 0) {
            ball.setV(-ball.vx, ball.vy);
            score = score + 10;
        }

        //check paddle
        if (paddle.breaks) {
            if (new Rectangle(paddle.x, paddle.y, paddle.length, paddle.height).intersects(new Rectangle(ball.x, ball.y, ball.length, ball.height))) {
                //check not all in bad x- coordinates
                //45 is how large each broken part its
                double v3 = 1200; //set it to huge
                double v4 = 1200;
                double v5 = 1200;
                double v6 = 1200;
                if (ball.x <= paddle.x + 45) {
                    v3 = (Math.abs(ball.x + ball.length / 2 - paddle.x)) / 1.0 / 45;
                    v4 = (Math.abs(ball.x + ball.length / 2 - paddle.x - 45)) / 1.0 / 45;
                } else {
                    v5 = (Math.abs(ball.x + ball.length / 2 - paddle.x - paddle.length)) / 1.0 / 45;
                    v6 = (Math.abs(ball.x + ball.length / 2 - paddle.x - paddle.length - 45)) / 1.0 / 45;
                }
                int position = findSmall6(Math.abs(paddle.y - y2) / 1.0 / paddle.height, Math.abs(paddle.y + paddle.height - y1) / 1.0 / paddle.height, v3, v4, v5, v6);
                switch (position) {
                    case 1:
                        //ball to top
                        if ((ball.x > paddle.x + 45 && ball.x + ball.length < paddle.x + paddle.length - 45) == false) {
                            //no in middle
                            ball.y = paddle.y - ball.height;
                            ball.setV(ball.vx, -ball.vy);
                        }
                        break;
                    case 2:
                        //ball to bot
                        if ((ball.x > paddle.x + 45 && ball.x + ball.length < paddle.x + paddle.length - 45) == false) {
                            //no in middle
                            ball.y = paddle.y + paddle.height;
                            ball.setV(ball.vx, -ball.vy);
                        }
                        break;
                    case 3:   //ball to the left of broken left
                        ball.x = paddle.x - ball.length;
                        ball.setV(-ball.vx, ball.vy);
                        break;
                    case 4:    //ball to the right of broken right
                        ball.x = paddle.x + 45;
                        ball.setV(-ball.vx, ball.vy);
                        break;
                    case 5:    //ball to left of broken right
                        ball.x = paddle.x + paddle.length - 45 - ball.length;
                        ball.setV(-ball.vx, ball.vy);
                        break;
                    case 6:     //ball to right of broken right
                        ball.x = paddle.x + paddle.length;
                        ball.setV(-ball.vx, ball.vy);
                        break;
                    default:
                }
            }
        } else {
            if (new Rectangle(paddle.x, paddle.y, paddle.length, paddle.height).intersects(new Rectangle(ball.x, ball.y, ball.length, ball.height))) {    // if it is inside(sudden move paddle)
                //find the closest contact point and telepot ball to it.
                int position = findSmall(Math.abs(paddle.y - y2) / 1.0 / paddle.height, Math.abs(paddle.y + paddle.height - y1) / 1.0 / paddle.height, Math.abs(paddle.x - x2) / 1.0 / paddle.length, Math.abs(paddle.x + paddle.length - x1) / 1.0 / paddle.length);
                switch (position) {
                    case 1:
                        //ball to top
                        ball.y = paddle.y - ball.height;
                        ball.setV(ball.vx, -ball.vy);
                        break;
                    case 2:
                        //ball to bot
                        ball.y = paddle.y + paddle.height + 1;
                        ball.setV(ball.vx, -ball.vy);
                        break;
                    case 3: //ball to the left
                        ball.x = paddle.x - ball.length;
                        ball.setV(-ball.vx, ball.vy);
                        break;
                    case 4: //ball to the right
                        ball.x = paddle.x + paddle.length;
                        ball.setV(-ball.vx, ball.vy);
                        break;
                    default:
                }
            }
        }

        //check bricks
        for (int i = 0; i < Config.NUMPARTS; i++) {
            if (bricks[i].solid && new Rectangle(bricks[i].x, bricks[i].y, bricks[i].length, bricks[i].height).intersects(new Rectangle(ball.x, ball.y, ball.length, ball.height))) {
                if (Math.abs(bricks[i].x - x2) <= 1) {
                    //ball to the left
                    ball.setV(-ball.vx, ball.vy);
                    score = score + 10;
                }
                if (Math.abs(bricks[i].y - y2) <= 1) {
                    //ball to top
                    ball.setV(ball.vx, -ball.vy);
                    score = score + 10;
                }
                if (Math.abs(bricks[i].x + bricks[i].length - x1) <= 1) {
                    //ball to the right
                    ball.setV(-ball.vx, ball.vy);
                    score = score + 10;
                }
                if (Math.abs(bricks[i].y + bricks[i].height - y1) <= 1) {
                    //ball to bot
                    ball.setV(ball.vx, -ball.vy);
                    score = score + 10;
                }
                bricks[i].solid = false;
                score = score + 100;
            }
        }

        //check all killed
        int check = 0;
        for (int i = 0; i < Config.NUMPARTS; i++) {
            if (bricks[i].getSolid()) {
                check++;
            }
        }
        if (check <= Config.NUMPARTS - 5 && gameState == 1) {
            JOptionPane.showMessageDialog(this,
                    "Sanity thoughts are preventing the masterpiece being destroyed.",
                    "Sanity check",
                    JOptionPane.WARNING_MESSAGE);
            enablebadboy();
        }
        if (check == 0) {
            gameState = 3;
        }


        if (gameState == 2) {


            int badboyx1 = badboy.x;
            int badboyx2 = badboy.x + badboy.length;
            int badboyy1 = badboy.y;
            int badboyy2 = badboy.y + badboy.height;

            //bad boy does not destroy bricks but paddle
            if (paddle.breaks) {
                if (new Rectangle(paddle.x, paddle.y, paddle.length, paddle.height).intersects(new Rectangle(badboy.x, badboy.y, badboy.length, badboy.height))) {
                    //check not all in bad x- coordinates
                    //45 is how large each broken part its
                    double v3 = 1200; //set it to huge
                    double v4 = 1200;
                    double v5 = 1200;
                    double v6 = 1200;
                    if (badboy.x <= paddle.x + 45) {
                        v3 = (Math.abs(badboy.x + badboy.length / 2 - paddle.x)) / 1.0 / 45;
                        v4 = (Math.abs(badboy.x + badboy.length / 2 - paddle.x - 45)) / 1.0 / 45;
                    } else {
                        v5 = (Math.abs(badboy.x + badboy.length / 2 - paddle.x - paddle.length)) / 1.0 / 45;
                        v6 = (Math.abs(badboy.x + badboy.length / 2 - paddle.x - paddle.length - 45)) / 1.0 / 45;
                    }
                    int position = findSmall6(Math.abs(paddle.y - badboyy2) / 1.0 / paddle.height, Math.abs(paddle.y + paddle.height - badboyy1) / 1.0 / paddle.height, v3, v4, v5, v6);
                    switch (position) {
                        case 1:
                            //badboy to top
                            if ((badboy.x > paddle.x + 45 && badboy.x + badboy.length < paddle.x + paddle.length - 45) == false) {
                                //no in middle
                                badboy.y = paddle.y - badboy.height;
                                badboy.setV(badboy.vx, -badboy.vy);
                            }
                            break;
                        case 2:
                            //badboy to bot
                            if ((badboy.x > paddle.x + 45 && badboy.x + badboy.length < paddle.x + paddle.length - 45) == false) {
                                //no in middle
                                badboy.y = paddle.y + paddle.height;
                                badboy.setV(badboy.vx, -badboy.vy);
                            }
                            break;
                        case 3:   //badboy to the left of broken left
                            badboy.x = paddle.x - badboy.length;
                            badboy.setV(-badboy.vx, badboy.vy);
                            break;
                        case 4:    //badboy to the right of broken right
                            badboy.x = paddle.x + 45;
                            badboy.setV(-badboy.vx, badboy.vy);
                            break;
                        case 5:    //badboy to left of broken right
                            badboy.x = paddle.x + paddle.length - 45 - badboy.length;
                            badboy.setV(-badboy.vx, badboy.vy);
                            break;
                        case 6:     //badboy to right of broken right
                            badboy.x = paddle.x + paddle.length;
                            badboy.setV(-badboy.vx, badboy.vy);
                            break;
                        default:
                    }
                }
            } else {
                if (new Rectangle(paddle.x, paddle.y, paddle.length, paddle.height).intersects(new Rectangle(badboy.x, badboy.y, badboy.length, badboy.height))) {    // if it is inside(sudden move paddle)
                    //find the closest contact point and telepot badboy to it.
                    int position = findSmall(Math.abs(paddle.y - badboyy2) / 1.0 / paddle.height, Math.abs(paddle.y + paddle.height - badboyy1) / 1.0 / paddle.height, Math.abs(paddle.x - badboyx2) / 1.0 / paddle.length, Math.abs(paddle.x + paddle.length - badboyx1) / 1.0 / paddle.length);
                    switch (position) {
                        case 1:
                            //badboy to top
                            badboy.y = paddle.y - badboy.height;
                            badboy.setV(badboy.vx, -badboy.vy);
                            break;
                        case 2:
                            //badboy to bot
                            badboy.y = paddle.y + paddle.height + 1;
                            badboy.setV(badboy.vx, -badboy.vy);
                            break;
                        case 3: //badboy to the left
                            badboy.x = paddle.x - badboy.length;
                            badboy.setV(-badboy.vx, badboy.vy);
                            break;
                        case 4: //badboy to the right
                            badboy.x = paddle.x + paddle.length;
                            badboy.setV(-badboy.vx, badboy.vy);
                            break;
                        default:
                    }

                    paddle.setToBreak();
                }
            }
            //check boarder, do not go over bottom
            if (badboyx2 >= Config.maxX) {
                badboy.setV(-badboy.vx, badboy.vy);
            }
            if (badboyy1 <= 0) {
                badboy.setV(badboy.vx, -badboy.vy);
            }
            if (badboyx1 <= 0) {
                badboy.setV(-badboy.vx, badboy.vy);
            }
            if (badboyy2 >= Config.deadline) {
                badboy.setV(badboy.vx, -badboy.vy);
            }


            for (int i = 0; i < Config.NUMPARTS; i++) {
                if (bricks[i].solid && new Rectangle(bricks[i].x, bricks[i].y, bricks[i].length, bricks[i].height).intersects(new Rectangle(badboy.x, badboy.y, badboy.length, badboy.height))) {
                    if (Math.abs(bricks[i].x - badboyx2) <= 1) {
                        //ball to the left
                        badboy.setV(-badboy.vx, badboy.vy);
                    }
                    if (Math.abs(bricks[i].y - badboyy2) <= 1) {
                        //ball to top
                        badboy.setV(badboy.vx, -badboy.vy);
                    }
                    if (Math.abs(bricks[i].x + bricks[i].length - badboyx1) <= 1) {
                        //ball to the right
                        badboy.setV(-badboy.vx, badboy.vy);
                    }
                    if (Math.abs(bricks[i].y + bricks[i].height - badboyy1) <= 1) {
                        //ball to bot
                        badboy.setV(badboy.vx, -badboy.vy);
                    }
                }
            }
        }
    }


    public void enablebadboy() {
        gameState = 2;
    }
}
