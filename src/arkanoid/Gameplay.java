package arkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Gameplay extends JPanel {

    private Dimension gameField = new Dimension(getWidth()*3/4,getHeight());
    private PlayersPad playersPad;
    private Ball ball;
    private Obstacle obstacle;
    private List<Bonus> bonuses;
    private int lives;
    private int speed;
    private static int standardSpeed = 25;
    private boolean isInProgress = false;
    private boolean isLost = false;
    private boolean isWon = false;
    private boolean isLonger = false;
    private boolean isShorter = false;
    private boolean isPadSlower = false;
    private boolean isPadFaster = false;
    private boolean isBallSlower = false;
    private boolean isBallFaster = false;
    private boolean isTranscendent = false;

    public Gameplay(JFrame container){

        speed = standardSpeed;
        container.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!isInProgress){
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) release();
                }
                else{
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) playersPad.move(speed);
                if(e.getKeyCode() == KeyEvent.VK_LEFT) playersPad.move(-speed);
                repaint();
                }
            }
        });

        playersPad = new PlayersPad(this,(int) ((gameField.getWidth()-PlayersPad.standardPlayersPadWidth)/2),(int)(gameField.getHeight()-PlayersPad.standardPlayersPadHeight) ,PlayersPad.standardPlayersPadWidth,PlayersPad.standardPlayersPadHeight);
        ball = new Ball(this,(int) ((gameField.getWidth()-Ball.standardBallRadius)/2) ,(int)(gameField.getHeight()-PlayersPad.standardPlayersPadHeight - 2*(Ball.standardBallRadius) - 10) ,Ball.standardBallRadius);
        lives = 3;
        bonuses = new ArrayList<>();
    }

    private void release(){
        if(!isInProgress) gameThread.start();
    }

    private Thread gameThread = new Thread(new Runnable() {
        @Override
        public void run() {
            isInProgress = true;
            ball.setMovement(10,-10);
            long lastTime = System.nanoTime();
            final double amountOfTicks = 30.0;
            double ns = 1000000000 / amountOfTicks;
            double delta = 0;
            double lengthTimer = 0;
            double padSpeedTimer = 0;
            double ballSpeedTimer = 0;
            double transcendecyTimer = 0;
            while(isInProgress)
            {
                if(lives == 0)
                {
                    isLost = true;
                    break;
                }
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;

                if(lengthTimer < 8000000000.0 && (isLonger == true || isShorter == true))
                    lengthTimer += (now - lastTime);
                if(lengthTimer >= 8000000000.0) {
                    lengthTimer = 0;
                    isLonger = false;
                    isShorter = false;
                    playersPad.setWidth(playersPad.standardPlayersPadWidth);
                }

                if(padSpeedTimer < 800000000.0 && (isPadFaster == true || isPadSlower == true))
                    lengthTimer += (now-lastTime);
                if(padSpeedTimer >= 8000000000.0 ){
                    padSpeedTimer = 0;
                    isPadSlower = false;
                    isPadFaster = false;
                    speed = standardSpeed;
                }

                if(ballSpeedTimer < 8000000000.0 && (isBallFaster == true || isPadSlower == true))
                    ballSpeedTimer += (now-lastTime);
                if(ballSpeedTimer >= 8000000000.0){
                    ballSpeedTimer = 0;
                    isBallFaster = false;
                    isBallSlower = false;
                    if(ball.getMovement().getWidth() > 0)
                        if(ball.getMovement().getHeight() > 0)
                            ball.setMovement(10,10);
                        else
                            ball.setMovement(10,-10);
                    else
                        if(ball.getMovement().getHeight() > 0)
                            ball.setMovement(-10,10);
                        else
                            ball.setMovement(-10,-10);

                }

                if(transcendecyTimer < 8000000000.0 && isTranscendent == true)
                    transcendecyTimer += (now - lastTime);
                if(transcendecyTimer >= 8000000000.0){
                    transcendecyTimer =0;
                    isTranscendent = false;
                }



                if (delta >= 1) {
                    ball.tick();
                    for(Bonus b : bonuses){
                        if(b.getHitBox().intersects(playersPad.getHitBox()))
                        {
                            int type = b.getType();
                            bonuses.remove(b);
                            if(type == 0)                            {
                                isLonger = true;
                                lengthTimer = 0;
                                playersPad.setWidth(playersPad.standardPlayersPadWidth*4/3);
                            }
                            if(type == 1)                            {
                                isShorter = true;
                                lengthTimer = 0;
                                playersPad.setWidth(playersPad.standardPlayersPadWidth*3/4);

                            }
                            if(type == 2)                            {
                                isPadFaster = true;
                                isPadSlower = false;
                                padSpeedTimer = 0;
                                speed = standardSpeed + 5;
                            }
                            if(type == 3)                            {
                                isPadSlower = true;
                                isPadFaster = false;
                                padSpeedTimer = 0;
                                speed = standardSpeed - 5;

                            }
                            if(type == 4)                            {
                                isBallFaster = true;
                                isBallSlower = false;
                                ballSpeedTimer = 0;
                                if(ball.getMovement().getWidth() > 0)
                                    if(ball.getMovement().getHeight() > 0)
                                        ball.setMovement(15,15);
                                    else
                                        ball.setMovement(15,-15);
                                else
                                    if(ball.getMovement().getHeight() > 0)
                                        ball.setMovement(-15,15);
                                    else
                                        ball.setMovement(-15,-15);

                            }
                            if(type == 5)                            {
                                isBallSlower = true;
                                isBallFaster = false;
                                ballSpeedTimer = 0;
                                if(ball.getMovement().getWidth() > 0)
                                    if(ball.getMovement().getHeight() > 0)
                                        ball.setMovement(5,5);
                                    else
                                        ball.setMovement(5,-5);
                                else
                                    if(ball.getMovement().getHeight() > 0)
                                        ball.setMovement(-5,5);
                                    else
                                        ball.setMovement(-5,-5);
                            }
                            if(type == 6)                            {
                                isTranscendent = true;
                                transcendecyTimer = 0;
                            }


                        }
                        b.tick();
                        if(b.getY()>gameField.height)
                            bonuses.remove(b);
                    }
                    delta--;

                }
                repaint();
                if(isWon == true) break;

            }
            repaint();

        }
    });

    public void loseBall(){
        ball.setPossition(((int) ((gameField.getWidth() - Ball.standardBallRadius) / 2)), (int) (gameField.getHeight() - PlayersPad.standardPlayersPadHeight - (Ball.standardBallRadius)) - 10);
        playersPad.setX((int) ((gameField.getWidth() - PlayersPad.standardPlayersPadWidth) / 2));
        playersPad.setY((int) (gameField.getHeight() - PlayersPad.standardPlayersPadHeight));
        lives --;
        repaint();
    }

    public void setPlayersPad(PlayersPad pad){
        this.playersPad = pad;
    }

    public PlayersPad getPlayersPad(){
        return this.playersPad;
    }

    public Dimension getGamefield(){
        return this.gameField;
    }

    public Obstacle getObstacle() { return  this.obstacle; }

    public List<Bonus> getBonuses() { return this.bonuses; }

    public void setIsWon(boolean b){ isWon = b; }

    public boolean isTranscendent() { return isTranscendent; }

    public void setSize(Dimension size){
        super.setSize(size);
        if(!isInProgress) {
            gameField = new Dimension(size.width * 3 / 4, size.height*95/100);
            obstacle = new Obstacle(this, 7,15);
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

        if(isLost == true){
            g.setFont(new Font("arial",Font.BOLD, 50));
            g.drawString("YOU LOST",gameField.width/2 - 120,gameField.height/2);
        }
        if(isWon == true){
            g.setFont(new Font("arial",Font.BOLD, 50));
            g.drawString("YOU WON",gameField.width/2 - 100,gameField.height/2);
        }

        obstacle.render(g);
        playersPad.render(g);
        ball.render(g);

        for(Bonus b : bonuses){
            b.render(g);
        }
    }
};
