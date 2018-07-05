package com.lambda.scifarer.droidoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class SudokuStorage implements Storage {

    private final String EDITABLE = "E;";
    private final String ROOT = "";

    private Sudoku owner;

    public SudokuStorage(Sudoku sudoku) {
        this.owner = sudoku;
    }

    @Override
    public String readBoard(String fileName) {
        String out = "";
        Scanner in = null;
        try {
            in = new Scanner(new File(ROOT + fileName));
            while (in.hasNext()) {
                out += in.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error, file " + fileName + " was not found");
            System.exit(1);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        out = this.decode(out);
        return out;
    }

    @Override
    public void writeBoard(String fileName) {
        String toWrite = this.encode(this.owner.board);
        PrintWriter outfile = null;
        //File file = new File(ROOT + fileName);
        try {
            outfile = new PrintWriter(ROOT + fileName);
            outfile.write(toWrite);
            System.out.println("Successfully saved!");
        } catch (FileNotFoundException e) {
            System.err.println("Error, file " + fileName + " was not found");
            System.exit(1);
        } finally {
            if (outfile != null) {
                outfile.close();
            }
        }
    }

    @Override
    public String decode(String input) {
        String out = "";
        String[] StringList = input.split(";");
        for (String e : StringList) {
            out += e;
        }
        return out;
    }

    @Override
    public String encode(Board input) {
        String out = "";
        for (List<Pos> row : input.getBoard()) {
            for (Pos pos : row) {
                if (pos.getEditable()) {
                    out += pos.getIntValue() + EDITABLE;
                } else if (pos.getIntValue() == 0) {
                    out += ".;";
                } else {
                    out += pos.getIntValue() + ";";
                }
            }
            out += "\n";
        }
        return out;
    }
}
