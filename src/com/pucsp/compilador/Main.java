package com.pucsp.compilador;

import com.pucsp.compilador.tabelaSimboloTerminal.CommandLineHelper;
import com.pucsp.compilador.tabelaSimboloTerminal.FileReader;
import com.pucsp.compilador.tabelaSimboloTerminal.TST;

import java.util.List;

import java.util.Map;

public class Main {
    
    public static void main(String[] args) throws Exception {

        Map<String,String> cmdLineArgsMap = CommandLineHelper.getCommandLineArgsAsMap(args);

        String tstPath = cmdLineArgsMap.get("arquivoTstBinario");
        TST tst = new TST(tstPath);

        /*
        * 10. Incluir manualmente, os seguintes elementos na parte especial da TST:
          ID, NUMBER, FLOAT, ALPHA, EOF defina constantes para eles
        */

        final int ID_INDEX = tst.insereSimboloEspecial("ID");
        final int NUMBER_INDEX = tst.insereSimboloEspecial("NUMBER");
        final int FLOAT_INDEX = tst.insereSimboloEspecial("FLOAT");
        final int ALPHA_INDEX = tst.insereSimboloEspecial("ALPHA");
        final int EOF_INDEX = tst.insereSimboloEspecial("EOF");

    }
}
