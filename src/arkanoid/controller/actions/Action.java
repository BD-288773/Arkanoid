package arkanoid.controller.actions;

import arkanoid.Gameplay;

public abstract class Action {
    protected Gameplay gameplay;
    public abstract void perform();
}
