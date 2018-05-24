package arkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Gameplay extends JPanel {

    private Dimension gameField = new Dimension(getWidth()*3/4,getHeight());
    private PlayersPad playersPad;
    private Ball ball;
    private Obstacle[][] obstacleMatrix;
    private boolean isReleased = false;

    public Gameplay(JFrame container){

        container.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!isReleased){
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) release();
                }
                else{
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) playersPad.move(10);
                if(e.getKeyCode() == KeyEvent.VK_LEFT) playersPad.move(-10);
                repaint();
                }
            }
        });

        playersPad = new PlayersPad(this,(int) ((gameField.getWidth()-PlayersPad.standardPlayersPadWidth)/2),(int)(gameField.getHeight()-PlayersPad.standardPlayersPadHeight) ,PlayersPad.standardPlayersPadWidth,PlayersPad.standardPlayersPadHeight);
        ball = new Ball(this,(int) ((gameField.getWidth()-Ball.standardBallRadius)/2) ,(int)(gameField.getHeight()-PlayersPad.standardPlayersPadHeight - 2*(Ball.standardBallRadius)) ,Ball.standardBallRadius);
    }

    private void release(){
        gameThread.start();
    }

    private Thread gameThread = new Thread(new Runnable() {
        @Override
        public void run() {
            isReleased = true;
            ball.setVector(10,-10);
            while(isReleased)
            {
                ball.tick();
                repaint();

                try{
                    Thread.sleep(50);

                }
                catch (Exception e){}
            }

        }
    });


    public void setPlayersPad(PlayersPad pad){
        this.playersPad = pad;
    }

    public PlayersPad getPlayersPad(){
        return this.playersPad;
    }

    public Dimension getGamefield(){
        return this.gameField;
    }

    public void setSize(Dimension size){
        super.setSize(size);
        if(!isReleased) {
            gameField = new Dimension(size.width * 3 / 4, size.height);
            ball.setPossition(((int) ((gameField.getWidth() - Ball.standardBallRadius) / 2)), (int) (gameField.getHeight() - PlayersPad.standardPlayersPadHeight - (Ball.standardBallRadius)) - 10);
            playersPad.setX((int) ((gameField.getWidth() - PlayersPad.standardPlayersPadWidth) / 2));
            playersPad.setY((int) (gameField.getHeight() - PlayersPad.standardPlayersPadHeight));
        }
    }

    public void paint(Graphics g){
        super.paint(g);
        g.setColor(new Color(0,0,255));
        g.fillRect(0,0,gameField.width,gameField.height);
        g.setColor(new Color(0,0,0));
        g.drawRect(0,0,gameField.width,gameField.height);
        g.setColor(new Color(255,0,0));
        g.fillRect(gameField.width,0,getWidth()-gameField.width,gameField.height);
        g.setColor(new Color(0,0,0));
        g.drawRect(gameField.width,0,getWidth()-gameField.width,gameField.height);


        playersPad.render(g);
        ball.render(g);
    }
};
