package com.lambda.scifarer.droidoku;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Sudoku {
//test
    Board board;
    private ActionStack stack;
    private SudokuStorage storage;
    private List<String> boards = Arrays.asList("....");

    public Sudoku(int size) {
        Random random = new Random();
        String inputString = boards.get(random.nextInt(boards.size()));
        Stack<String> inputStack = toStack(inputString);
        this.board = new Board(size, size, inputStack);
        this.stack = new ActionStack();
        this.storage = new SudokuStorage(this);
    }

    private Stack<String> toStack(String input) {
        if (input == null) {
            throw new IllegalArgumentException("String cannot be null");
        } else if (input.length() == 0) {
            throw new IllegalArgumentException("String cannot be empty");
        } else {
            List<String> toRun;
            toRun = Arrays.asList(input.split(""));
            Stack<String> output = new Stack<String>();
            for (int i = (toRun.size() - 1); i > -1; i--) {
                output.push(toRun.get(i));
            }
            return output;
        }
    }

    private void getInput(String input) {
        if (!input.endsWith("a") && (input.length() < 5 || input.length() > 8)) {
            throw new IllegalArgumentException("Input is either too short or too long");
        }
        String[] components = input.split(",");
        try {
            int x = Integer.valueOf(components[0]) - 1;
            int y = Integer.valueOf(components[1]) - 1;
            int val = Integer.valueOf(components[2]);
            boolean created = (this.board.getPos(y, x).getIntValue() == 0);
            this.board.getPos(y, x).setValue(val);
            this.board.updateBoard();
            if (components.length == 3 && this.board.getPos(y, x).getEditable()) {
                this.stack.addAction(x + 1, y + 1, val, created);
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Input must be valid numbers");
        }
    }

    public void undo() {
        String action = stack.regretAction() + "a";
        getInput(action);
    }

    public void redo() {
        String action = stack.redoAction() + "a";
        getInput(action);
    }

    public void load(String fileName) {
        if (fileName.endsWith(".txt")) {
            String in = this.storage.readBoard(fileName);
            Stack<String> inputStack = toStack(in);
            this.board = new Board(this.board.getRows(), this.board.getColumns(), inputStack);
            this.board.updateBoard();
            this.stack = new ActionStack();
            System.out.println("Successfully loaded!");
        } else {
            throw new IllegalArgumentException("Illegal file extension");
        }
    }

    public void save(String fileName) {
        if (fileName.endsWith(".txt")) {
            this.storage.writeBoard(fileName);
        } else {
            throw new IllegalArgumentException("Illegal file extension");
        }
    }

    private String addRowString() {
        String out = "  +---------------";
        for (int z = 0; z < this.board.getRightRoot(); z++) {
            out += "+---------------";
        }
        out += "+\n";
        return out;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        if (this.board.isFinished()) {
            out.append("Spillet er over, du vant!\n");
        }
        // rad
        for (int i = 0; i < this.board.getRows(); i++) {
            if (i % this.board.getDownRoot() == 0) {
                out.append(addRowString());
            }
            if (i < 9) {
                out.append(String.valueOf(i + 1) + " |");
            } else {
                out.append(String.valueOf(i + 1) + "|");
            }
            // kolonne
            for (int j = 0; j < this.board.getColumns(); j++) {
                out.append(this.board.getPos(i, j).getValue());
                if (j > 0 && (j + 1) % this.board.getRightRoot() == 0)
                    out.append(" | ");
            }
            out.append("\n");
        }
        out.append(addRowString());
        for (int i = 0; i < this.board.getColumns(); i++) {
            out.append( "     " + (i + 1));
        }
        return out.toString();
    }
}
