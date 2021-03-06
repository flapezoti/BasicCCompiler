package com.pucsp.compilador;

import com.pucsp.compilador.analisadorLexico.FilePositioner;
import com.pucsp.compilador.analisadorLexico.Lexan;
import com.pucsp.compilador.analisadorLexico.SourceFileReader;
import com.pucsp.compilador.analisadorLexico.Token;
import com.pucsp.compilador.tabelaSimboloTerminal.CommandLineHelper;
import com.pucsp.compilador.tabelaSimboloTerminal.TST;
import com.pucsp.compilador.tabelaSimboloTerminal.TokensEspeciais;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileReader;

import java.util.Map;

public class Main {

    private static TST tst;
    private static OutputStream exitFile;

    public static void main(String[] args) throws Exception {

        Map<String,String> cmdLineArgsMap = CommandLineHelper.getCommandLineArgsAsMap(args);

        String tstFilePath = cmdLineArgsMap.get("arquivoTstBinario");
        String sourceCodeFilePath =  cmdLineArgsMap.get("arquivoFonte");
        String exitFilePath =  cmdLineArgsMap.get("arquivoSaida");

        FilePositioner.create(0, 0, 0, sourceCodeFilePath);

        tst = new TST(tstFilePath);

        inserirSimbolosEspeciais();
        abrirArquivoSaida(exitFilePath);

        Lexan lexan = new Lexan(tst, exitFile);
        while(FilePositioner.getInstance().getLinePostition() != -1){
            while(FilePositioner.getInstance().getCharPosition() != -1){

                Token token = lexan.aef();
                FilePositioner.getInstance().nextChar();
            }
            FilePositioner.getInstance().nextLine();
        }

    }

    private static void abrirArquivoSaida(String exitFilePath) {
        if (exitFilePath != null){
            try{
                exitFile = new FileOutputStream(exitFilePath);
            }catch(IOException e){
                System.err.println("Problema ao criar o arquivo de saída");
                System.exit(-1);
            }
        }

    }

    private static void inserirSimbolosEspeciais(){

        /*
        * 10. Incluir manualmente, os seguintes elementos na parte especial da TST:
          ID, NUMBER, FLOAT, ALPHA, EOF defina constantes para eles
        */

        tst.insereSimboloEspecial(TokensEspeciais.IDENT.name());
        tst.insereSimboloEspecial(TokensEspeciais.ALPHA.name());
        tst.insereSimboloEspecial(TokensEspeciais.FLOAT.name());
        tst.insereSimboloEspecial(TokensEspeciais.NUMBER.name());
        tst.insereSimboloEspecial(TokensEspeciais.EOF.name());
    }
}
