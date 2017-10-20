package com.pucsp.compilador;

import com.pucsp.compilador.tabelaSimboloTerminal.CommandLineHelper;
import com.pucsp.compilador.tabelaSimboloTerminal.FileReader;
import com.pucsp.compilador.tabelaSimboloTerminal.TST;

import java.util.List;

import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

        TST tst = new TST();
        Map<String,String> cmdLineArgsMap = CommandLineHelper.getCommandLineArgsAsMap(args);

        String pathSimbolos = cmdLineArgsMap.get("listaSimbolosTerminais");
        List<String> simbolosSimbolos = FileReader.fileToString(pathSimbolos);
        for(String x : simbolosSimbolos){
            tst.searchInsert(x, 'I', true);
        }

        tst.gravaTSTBinario(cmdLineArgsMap.get("tstArquivoBinario"));
        tst.gravaTSTTexto(cmdLineArgsMap.get("tstArquivoTexto"));
    }
}
