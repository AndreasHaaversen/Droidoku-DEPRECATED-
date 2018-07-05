package com.lambda.scifarer.droidoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Board {

    private int rows, columns, rightRoot, downRoot;

    private List<List<Pos>> board = new ArrayList<List<Pos>>();

    public Board(int rows, int columns, Stack<String> input) {
        if(rows != columns) {
            throw new IllegalArgumentException("Illegal board dimmensions");
        }
        this.rows = rows;
        this.columns = columns;
        List<Integer> roots = primeFactors(rows);
        this.rightRoot = roots.get(0);
        this.downRoot = roots.get(1);
        if (rows * columns != input.size()) {
            throw new IllegalArgumentException("Stack is of insufficient length");
        }
        for (int i = 0; i < rows; i++) {
            board.add(new ArrayList<Pos>());
            for (int j = 0; j < columns; j++) {
                if (input.peek().equals("0")) {
                    board.get(i).add(new Pos(Integer.valueOf(input.pop())));
                } else if (input.peek().endsWith("E")) {
                    board.get(i).add(new Pos(Integer.valueOf(input.pop().substring(0, 1)), true));
                } else if (input.peek().equals(".")) {
                    input.pop();
                    board.get(i).add(new Pos(0));
                } else {
                    board.get(i).add(new Pos(Integer.valueOf(input.pop()), false));
                }
            }
        }
    }

    private boolean checkValid(int y, int x, int val) {
        return checkRow(x, y, val) && checkColumn(x, y, val) && checkBox(x, y, val);
    }

    private boolean checkRow(int x, int y, int val) {
        for (int i = 0; i < this.getColumns(); i++) {
            if (i != x)
                if (getPos(y, i).getIntValue() == val)
                    return false;
        }
        return true;
    }

    private boolean checkColumn(int x, int y, int val) {
        for (int i = 0; i < this.getRows(); i++) {
            if (i != y)
                if (getPos(i, x).getIntValue() == val)
                    return false;
        }
        return true;
    }

    private boolean checkBox(int x, int y, int val) {
        // find first the next multiple of three
        int nextX = 1;
        int nextY = 1;
        if (x % rightRoot == 0)
            nextX = x + rightRoot;
        else
            for (int i = x; nextX % rightRoot != 0; i++) {
                nextX = i;
            }
        int xRangeStart = nextX - rightRoot;
        // same, for y
        if (y % downRoot == 0)
            nextY = y + downRoot;
        else
            for (int i = y; nextY % downRoot != 0; i++) {
                nextY = i;
            }
        int yRangeStart = nextY - downRoot;
        for (int i = yRangeStart; i < nextY; i++) {
            for (int j = xRangeStart; j < nextX; j++) {
                if (!(i == y && j == x))
                    if (this.getPos(i, j).getIntValue() == val)
                        return false;
            }
        }
        return true;
    }

    public boolean isFinished() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (!this.getPos(i, j).getValid())
                    return false;
            }
        }
        return true;
    }

    void updateBoard() {
        for (int i = 0; i < this.columns; i++) {
            for (int j = 0; j < this.rows; j++) {
                if (this.getPos(i, j).getEditable() && this.getPos(i, j).getIntValue() != 0) {
                    boolean validity = this.checkValid(i, j, this.getPos(i, j).getIntValue());
                    this.getPos(i, j).updateValidity(validity);
                }
            }
        }
    }

    Pos getPos(int row, int column) {
        return board.get(row).get(column);
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public List<List<Pos>> getBoard() {
        return this.board;
    }

    public int getRightRoot() {
        return this.rightRoot;
    }

    public int getDownRoot() {
        return this.downRoot;
    }

    private static List<Integer> primeFactors(int number) {
        int n = number;
        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if(factors.size() == 1) {
            factors.add(1);
        }
        return factors;
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < (this.rows); i++) {
            for (int j = 0; j < (this.columns); j++) {
                out += getPos(i, j).getValue();
            }
            out += "\n";
        }
        return out;
    }
}
