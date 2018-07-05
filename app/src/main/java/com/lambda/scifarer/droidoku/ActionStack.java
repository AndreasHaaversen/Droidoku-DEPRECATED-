package com.lambda.scifarer.droidoku;

import java.util.NoSuchElementException;
import java.util.Stack;

public class ActionStack {

    private Stack<Action> regretStack;
    private Stack<Action> redoStack;

    public ActionStack() {
        this.regretStack = new Stack<Action>();
        this.redoStack = new Stack<Action>();
    }

    public void addAction(int x, int y, int val, boolean created) {
        regretStack.push(new Action(x, y, val, created));
    }

    public String regretAction() {
        if (!regretStack.isEmpty()) {
            String out;
            Action lastAction = regretStack.pop();
            redoStack.push(lastAction);
            if (lastAction.getCreated()) {
                out = lastAction.getXPos() + "," + lastAction.getYPos() + ",0";
            } else {
                Action toBeDone = findLastAction(lastAction);
                out = (toBeDone.getXPos() +","+ toBeDone.getYPos() + "," + toBeDone.getVal());
            }
            return out;
        } else {
            throw new NoSuchElementException("RegretStack is empty!");
        }
    }

    public String redoAction() throws NoSuchElementException {
        if (!redoStack.isEmpty()) {
            Action lastAction = redoStack.pop();
            regretStack.push(lastAction);
            return ("" + lastAction.getXPos() + lastAction.getYPos() + lastAction.getVal());
        } else throw new NoSuchElementException("undoStack is empty!");
    }

    private Action findLastAction(Action action) {
        for (int i = 0; i < this.regretStack.size(); i++) {
            Action thisAction = regretStack.get(i);
            if ((thisAction.getXPos() == action.getXPos()) && thisAction.getYPos() == action.getYPos()) {
                return thisAction;
            }
        }
        return action;
    }
}
