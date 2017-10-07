package com.company;

import java.util.List;

import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

        TST tst = new TST();
        Map<String,String> cmdLineArgsMap = CommandLineHelper.getCommandLineArgsAsMap(args);

        String pathSimbolos = cmdLineArgsMap.get("arquivoSimbolos");
        List<String> simbolosSimbolos = FileReader.fileToString(pathSimbolos);
        for(String x : simbolosSimbolos){
            tst.searchInsert(x, 'I', true);
        }


        tst.gravaTSTBinario(cmdLineArgsMap.get("arquivoTST"));

        tst.leTSTBinario(cmdLineArgsMap.get("arquivoTST"));

        String pathTeste = cmdLineArgsMap.get("arquivoTeste");
        List<String> simbolosTeste = FileReader.fileToString(pathTeste);
        for(String x : simbolosTeste){
            tst.searchInsert(x, 'C', true);
        }

        tst.gravaTSTTexto(cmdLineArgsMap.get("arquivoLog"));
    }
}
