package arkanoid;

import java.awt.*;

public class PlayersPad {

    public static int standardPlayersPadWidth = 100;
    public static int standardPlayersPadHeight = 10;
    private Rectangle hitBox;
    private Gameplay gameInstance;

    /**
     * Player constructor
     * @param game
     * @param x
     * @param y
     * @param width
     * @param height
     */
    PlayersPad(Gameplay game, int x, int y, int width, int height){
        hitBox = new Rectangle(x,y,width,height);
        gameInstance = game;
    }

    public int getX(){
        return hitBox.x;
    }

    public int getY(){
        return hitBox.y;
    }

    public int getWidth(){
        return hitBox.width;
    }

    public int getHeight(){
        return hitBox.height;
    }

    public Rectangle getHitBox() { return hitBox; }

    public void  setWidth(int width) { hitBox.width = width; }

    public void render(Graphics g){

        g.setColor(new Color(200,20,220));
        g.fillRect(hitBox.x,hitBox.y,hitBox.width,hitBox.height);

    }

    public void move(int speed)
    {
        hitBox.x += speed;
        if(hitBox.x <= 0) hitBox.x = 0;
        if(hitBox.x >= gameInstance.getGamefield().width - hitBox.width) hitBox.x = gameInstance.getGamefield().width - hitBox.width;
    }

    public void setX(int x) {
        hitBox.x=x;
    }

    public void setY(int y) {
        hitBox.y=y;
    }
};
