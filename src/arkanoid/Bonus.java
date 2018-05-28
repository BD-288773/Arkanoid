package arkanoid;

import java.util.Random;
import java.awt.*;

public class Bonus {

    private static int standardWidth = 20;
    private static int standardHeight = 20;
    private Rectangle hitBox;
    private int type;

    public Bonus(int x, int y){
        hitBox = new Rectangle(x,y,standardWidth,standardHeight);
        Random rand = new Random();
        type = rand.nextInt(7);
    }

    public int getType(){
        return this.type;
    }

    public void tick(){
        hitBox.y+=10;
    }

    public int getY(){
        return hitBox.y;
    }

    public Rectangle getHitBox(){
        return hitBox;
    }

    public void render(Graphics g){

        if(type == 0)
            g.setColor(new Color(205,50,50));
        else
        {
            if(type == 1)
                g.setColor(new Color(50,205,50));
            else
            {
                if(type == 2)
                    g.setColor(new Color(70,105,70));
                else
                {
                    if(type == 3)
                        g.setColor(new Color(123,123,13));
                    else
                    {
                        if(type == 4)
                            g.setColor(new Color(213,231,12));
                        else
                        {
                            if(type == 5)
                                g.setColor(new Color(12,243,123));
                            else g.setColor(new Color(170,170,170));

                        }
                    }
                }
            }
        }

        g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
        g.setColor(new Color(0,0,0));
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }
};
