package arkanoid;

import java.awt.*;

public class Ball {

    public static int standardBallRadius = 8;
    private Gameplay gameInstance;
    private Point possition;
    private int radius;
    private Dimension movement = new Dimension(10,10);

    public Ball(Gameplay game, int x, int y, int r){
        gameInstance = game;
        possition = new Point(x,y);
        radius = r;
    }

    public void setPossition(int x, int y){
        possition.x = x;
        possition.y = y;
    }
    public void setVector(int xMove,  int yMove){
        movement.width = xMove;
        movement.height = yMove;
    }

    public void tick(){
        if(possition.x - 2*radius <= 0 && movement.width < 0) movement.width = -movement.width;
        if(possition.x + 2*radius >= gameInstance.getGamefield().width && movement.width > 0) movement.width = -movement.width;
        if(possition.y - 2*radius <= 0 && movement.height < 0) movement.height = -movement.height;
        if(possition.y + 2*radius >= gameInstance.getGamefield().height && movement.height > 0) movement.height = -movement.height;

        if(gameInstance.getPlayersPad().collidesWith(new Rectangle(possition.x+radius,possition.y+radius,radius,radius))){
            movement.height = -movement.height;
        }

        possition.move(possition.x + movement.width, possition.y + movement.height);
    }

    public void render(Graphics g){

        g.setColor(new Color(0,0,0));
        g.fillOval(possition.x - radius, possition.y - radius, radius*2, radius*2);
    }


};
