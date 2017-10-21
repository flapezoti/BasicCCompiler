package com.pucsp.compilador.analisadorLexico;

public class FilePositioner {
    private static FilePositioner instance;
    private int line, currentChar, lexem;

    private FilePositioner(int line, int currentChar, int lexem){
        this.line = line;
        this.currentChar = currentChar;
        this.lexem = lexem;
    }

    public void create(){
       this.instance = new FilePositioner(0, 0,0);
    }

    public void create(int line, int currentChar, int lexem){
        this.instance = new FilePositioner(line, currentChar, lexem);
    }

    static public FilePositioner getInstance(){
        return instance;
    }

    public int nextPosition() {
        currentChar = currentChar++;
        return currentChar;
    }

    public int nextLine() {
        line = line++;
        currentChar = 0;
        lexem = 0;
        return line;
    }

    public int getCurrentChar() {
        return currentChar;
    }

    public int getLine() {
        return line;
    }

    public void markLex() {
        lexem = currentChar++;
    }





}
