package com.pucsp.compilador.analisadorLexico;

public class FilePositioner {
    private static FilePositioner instance;
    private int currentLine, currentChar, currentLexemBeginning;

    private FilePositioner(int currentLine, int currentChar, int currentLexemBeginning){
        this.currentLine = currentLine;
        this.currentChar = currentChar;
        this.currentLexemBeginning = currentLexemBeginning;
    }

    static public void create(){
       instance = new FilePositioner(0, 0,0);
    }

    static public void create(int currentLine, int currentChar, int currentLexemBeginning){
        instance = new FilePositioner(currentLine, currentChar, currentLexemBeginning);
    }

    static public FilePositioner getInstance(){
        return instance;
    }

    public int nextChar() {
        currentChar++;
        return currentChar;
    }

    public int nextLine() {
        currentLine++;
        currentChar = 0;
        currentLexemBeginning = 0;
        return currentLine;
    }

    public int getCurrentChar() {
        return currentChar;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public int getCurrentLexemBeginning() {
        return currentLexemBeginning;
    }

    public void moveToNextLexem() {
        currentLexemBeginning = currentChar + 1;
    }

}
