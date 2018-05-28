package arkanoid;

import java.awt.*;
import java.util.Random;

public class Ball {

    public static int standardBallRadius = 8;
    private Gameplay gameInstance;
    private Point possition;
    private int radius;
    private Dimension movement = new Dimension(10,10);
    private Random rand;

    public Ball(Gameplay game, int x, int y, int r){
        gameInstance = game;
        possition = new Point(x,y);
        radius = r;
        rand = new Random();
    }

    public void setPossition(int x, int y){
        possition.x = x;
        possition.y = y;
    }
    public void setMovement(int xMove,  int yMove){
        movement.width = xMove;
        movement.height = yMove;
    }

    public Dimension getMovement(){ return movement; }

    public void tick(){
        if(possition.x - 2*radius <= 0 && movement.width < 0) movement.width = -movement.width;
        if(possition.x + 2*radius >= gameInstance.getGamefield().width && movement.width > 0) movement.width = -movement.width;
        if(possition.y - 2*radius <= 0 && movement.height < 0) movement.height = -movement.height;
        if(possition.y + 2*radius >= gameInstance.getGamefield().height && movement.height > 0) gameInstance.loseBall();

        PlayersPad pad = gameInstance.getPlayersPad();

        /*if(possition.x >= pad.getX() && possition.x <= pad.getX() + pad.getWidth() || possition.x + 2 * radius >= pad.getX() && possition.x + 2 * radius <= pad.getX() + pad.getWidth())
            movement.height = -movement.height;
        else if(possition.y >= pad.getY() && possition.y <= pad.getY() + pad.getHeight() || possition.y + 2 * radius >= pad.getY() && possition.y + 2 * radius >= pad.getY() + pad.getHeight())
            movement.width = -movement.width;
        */
        if(possition.x + 2*radius >= gameInstance.getPlayersPad().getX() && possition.y + 2*radius >= gameInstance.getGamefield().height - gameInstance.getPlayersPad().getHeight() && possition.x <= gameInstance.getPlayersPad().getX() +gameInstance.getPlayersPad().getWidth()) movement.height = -movement.height;


        Rectangle hBox[][] = gameInstance.getObstacle().getHitBox();
        for(int i=0; i<hBox.length; i++)
        {
            for(int j=0;j<hBox[0].length;j++)
            {
                if(hBox[i][j].intersects(new Rectangle(possition.x, possition.y, 2*radius, 2*radius)))
                {
                    if(gameInstance.getObstacle().getMap()[i][j]>0) {
                        gameInstance.getObstacle().crack(i, j);

                        int r = rand.nextInt(20);
                        if(r == 7)
                            gameInstance.getBonuses().add(new Bonus(possition.x, possition.y));

                        if(gameInstance.isTranscendent() == false) {
                            if (possition.y >= hBox[i][j].y && possition.y <= hBox[i][j].y + hBox[i][j].height || possition.y + 2 * radius >= hBox[i][j].y && possition.y + 2 * radius >= hBox[i][j].y + hBox[i][j].height)
                                movement.width = -movement.width;
                            else if (possition.x >= hBox[i][j].x && possition.x <= hBox[i][j].x + hBox[i][j].width || possition.x + 2 * radius >= hBox[i][j].x && possition.x + 2 * radius <= hBox[i][j].x + hBox[i][j].width)
                                movement.height = -movement.height;
                        }
                    }
                }
            }
        }

        if(gameInstance.getObstacle().getTotalBricks() == 0)
            gameInstance.setIsWon(true);

        possition.move(possition.x + movement.width, possition.y + movement.height);
    }

    public void render(Graphics g){

        g.setColor(new Color(0,0,0));
        g.fillOval(possition.x - radius, possition.y - radius, radius*2, radius*2);
    }


};
