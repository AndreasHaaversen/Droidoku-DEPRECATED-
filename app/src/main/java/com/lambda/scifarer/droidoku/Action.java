package com.lambda.scifarer.droidoku;

public class Action {

    private int xPos;
    private int yPos;
    private int val;
    private boolean created;

    Action(int x, int y, int val, boolean created) {
        this.xPos = x;
        this.yPos = y;
        this.val = val;
        this.created = created;
    }

    public int getXPos() {
        return this.xPos;
    }

    public int getYPos() {
        return this.yPos;
    }

    public int getVal() {
        return this.val;
    }

    public boolean getCreated() {
        return this.created;
    }

}
