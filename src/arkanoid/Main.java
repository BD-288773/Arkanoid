package arkanoid;

import javax.swing.*;

public class Main {

    public static JFrame frame;
    public static Gameplay game;
    public static void main(String[] args){
        frame = new JFrame("Arkanoid");
        frame.setSize(800,700);
        frame.setLocationRelativeTo(null);

        game = new Gameplay(frame);
        game.setSize(frame.getSize());
        frame.add(game);


        frame.setVisible(true);
    }
};
