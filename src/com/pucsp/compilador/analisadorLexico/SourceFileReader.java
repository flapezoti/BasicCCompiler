package com.pucsp.compilador.analisadorLexico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SourceFileReader {

    private String fileUrl;
    private Iterator<String> lines;
    private String currentLine;

    public SourceFileReader(String fileUrl) {
        this.fileUrl = fileUrl;
        abrirArquivoFonte(fileUrl);
        nextLine();
    }

    public String getCurrentLine(){ return currentLine;}

    public char getChar() throws StringIndexOutOfBoundsException {

        return currentLine.charAt(FilePositioner.getInstance().getCharPosition());
    }

    public void nextLine() throws NoSuchElementException {
        currentLine = lines.next();

    }

    public boolean currentCharIsLast() {
        if(FilePositioner.getInstance().getCharPosition() > currentLine.length() - 2 ){
            System.out.println("fim da linha");
            return true;
        } else {
            return false;
        }
    }

    public boolean currentLineIsLast() {
        return !lines.hasNext();
    }

    private void abrirArquivoFonte(String sourceCodeFilePath) {
        try{
            BufferedReader sourceCodeFile = new BufferedReader(new FileReader(sourceCodeFilePath));
            lines = sourceCodeFile.lines().iterator();
        }catch(IOException e){
            System.err.println("Problema ao abrir o arquivo " + sourceCodeFilePath);
            System.exit(-1);
        }
    }

}
