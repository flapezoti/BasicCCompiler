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

    public char nextChar() throws StringIndexOutOfBoundsException {
        FilePositioner.getInstance().nextChar();
        return currentLine.charAt(FilePositioner.getInstance().getCurrentChar());
    }

    public void nextLine() throws NoSuchElementException {
        FilePositioner.getInstance().nextLine();
        currentLine = lines.next();
    }

    public boolean currentCharIsLast() {
        if(FilePositioner.getInstance().getCurrentChar() >= currentLine.length() - 2 ){
            return true;
        } else {
            return false;
        }
    }

    public boolean currentLineIsLast() {
        return lines.hasNext();
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
