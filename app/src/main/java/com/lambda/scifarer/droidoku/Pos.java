package com.lambda.scifarer.droidoku;

public class Pos {

    private int value;
    private boolean editable;
    private boolean valid;

    public Pos(int value, boolean editable) {
        this.editable = editable;
        this.value = value;
    }

    public Pos(int value) {
        this.editable = true;
        this.value = value;
    }

    public void updateValidity(boolean valid) {
        if (this.editable)
            this.valid = valid;
    }

    public void setupValue(int value) {
        this.value = value;
        this.editable = false;
    }

    public void setValue(int newValue) {
        if (this.editable) {
            this.value = newValue;
        } else
            System.out.println("That position is set by the program!");
    }

    public int getIntValue() {
        return this.value;
    }

    public boolean getValid() {
        return this.valid;
    }

    public boolean getEditable() {
        return this.editable;
    }

    public String getValue() {
        if (this.value == 0)
            return "  .  ";
        else if (!this.editable)
            return " (" + this.value + ") ";
        else if (!this.valid)
            return "  " + this.value + "* ";
        else
            return "  " + this.value + "  ";
    }

}
