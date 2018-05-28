package arkanoid;

import java.awt.*;
import java.util.Random;

public class Obstacle {
    private Rectangle hitBox[][];
    private int map[][];
    private Gameplay gameInstance;
    private int brickWidth;
    private int brickHeight;
    private int totalBricks;

    public Obstacle(Gameplay game, int row, int col){
        gameInstance = game;
        map =  new int[row][col];
        hitBox = new Rectangle[row][col];
        Random rand = new Random();
        brickWidth = (gameInstance.getGamefield().width - 120) / col;
        brickHeight = ((gameInstance.getGamefield().height /3) - 50) / row;
        totalBricks = 0;
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++)
            {
                map[i][j] = rand.nextInt(6);
                totalBricks+=map[i][j];
                hitBox[i][j] = new Rectangle(60+j*brickWidth,50+i*brickHeight, brickWidth, brickHeight);
            }
        }

    }

    public Rectangle[][] getHitBox(){
        return hitBox;
    }

    public int[][] getMap(){
        return map;
    }

    public void crack(int i, int j){
        map[i][j]--;
        totalBricks--;
    }

    public int getTotalBricks(){
        return totalBricks;
    }


    public void render(Graphics g){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++) {
                if (map[i][j] > 0) {


                    if (map[i][j] == 1) g.setColor(new Color(0, 255, 0));
                    else {
                        if (map[i][j] == 2) g.setColor(new Color(0, 255, 255));
                        else {
                            if (map[i][j] == 3) g.setColor(new Color(255, 255, 0));
                            else {
                                if (map[i][j] == 4) g.setColor(new Color(120, 0, 255));
                                else g.setColor(new Color(123, 123, 0));
                            }
                        }
                    }

                    g.fillRect(hitBox[i][j].x, hitBox[i][j].y, hitBox[i][j].width, hitBox[i][j].height);
                    g.setColor(new Color(0, 0, 0));
                    g.drawRect(hitBox[i][j].x, hitBox[i][j].y, hitBox[i][j].width, hitBox[i][j].height);
                }
            }
        }
    }
};
