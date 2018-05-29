package arkanoid.controller.actions;

import arkanoid.GameInterface;
import arkanoid.Gameplay;

public class UpdateAction extends Action {

    private GameInterface gameInterface;

    /**
     * Performs logical and graphical update
     */

    public UpdateAction(final Gameplay gameplay, final GameInterface gameInterface) {
        this.gameplay = gameplay;
        this.gameInterface = gameInterface;
    }

    public void perform() {
        gameplay.update();
        gameInterface.repaint();
    }
}