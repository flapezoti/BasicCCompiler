package com.pucsp.compilador.analisadorLexico;

public class FilePositioner {
    private static FilePositioner instance;
    private int linePostition, charPosition, currentLexemBeginning;
    private SourceFileReader srf;

    private FilePositioner(int currentLine, int currentChar, int currentLexemBeginning, String sourceCodeFilePath){
        this.linePostition = currentLine;
        this.charPosition = currentChar;
        this.currentLexemBeginning = currentLexemBeginning;
        this.srf = new SourceFileReader(sourceCodeFilePath);
    }

    static public void create(int currentLine, int currentChar, int currentLexemBeginning, String sourceCodeFilePath){
        instance = new FilePositioner(currentLine, currentChar, currentLexemBeginning, sourceCodeFilePath);
    }

    static public FilePositioner getInstance(){
        return instance;
    }

    public int nextChar() {
        if(!srf.currentCharIsLast()){
            charPosition++;
        } else {
            charPosition = -1;
        }
        return charPosition;
    }

    public int nextLine() {
        if(!srf.currentLineIsLast()) {
            linePostition++;
            currentLexemBeginning = 0;
            srf.nextLine();
            if(srf.currentCharIsLast()){
                charPosition = -1;
            } else {
                charPosition = 0;
            }
        } else {
            linePostition = -1;
        }

        return linePostition;
    }

    public int getCharPosition() {
        return charPosition;
    }

    public int getLinePostition() {
        return linePostition;
    }

    public int getCurrentLexemBeginning() {
        return currentLexemBeginning;
    }

    public void moveToNextLexem() {
        currentLexemBeginning = charPosition;
    }

    public char getCurrentChar(){
        return srf.getChar();
    }

    public String getCurrentLine(){
        return srf.getCurrentLine();
    }

}
