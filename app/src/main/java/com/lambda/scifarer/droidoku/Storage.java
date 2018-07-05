package com.lambda.scifarer.droidoku;


public interface Storage {

    public String readBoard(String fileName);

    public void writeBoard(String fileName);

    public String decode(String input);

    public String encode(Board input);

}
