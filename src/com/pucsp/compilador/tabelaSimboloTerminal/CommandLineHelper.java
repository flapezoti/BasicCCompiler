package com.pucsp.compilador.tabelaSimboloTerminal;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Parser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.ParseException;

public class CommandLineHelper {

    public static Map<String,String> getCommandLineArgsAsMap(String[] args){
        final Options options
                = new Options();

        if (args == null) {
            throw new IllegalArgumentException("Command Line args can't be null");
        }

        final String arquivoSimbolosArg = "arquivoSimbolos";
        final Option arquivoSimbolosOption = new Option( arquivoSimbolosArg, true, "Caminho do arquivo com os símbolos terminais da linguagem");
        arquivoSimbolosOption.setRequired( true );
        options.addOption(arquivoSimbolosOption);

        final String arquivoTSTArg = "arquivoTST";
        final Option arquivoTSTOption = new Option( arquivoTSTArg, true, "Caminho para arquivo binário de saída da tabela de simbolos terminais");
        arquivoTSTOption.setRequired( true );
        options.addOption(arquivoTSTOption);

        final String arquivoLogArg = "arquivoLog";
        final Option arquivoLogOption = new Option( arquivoLogArg, true, "Caminho para arquivo texto de saída dos logs da aplicação");
        arquivoLogOption.setRequired( true );
        options.addOption(arquivoLogOption);

        final String arquivoTesteArg = "arquivoTeste";
        final Option arquivoTesteOption = new Option( arquivoTesteArg, true, "Caminho para o arquivo texto de teste");
        arquivoTesteOption.setRequired( true );
        options.addOption(arquivoTesteOption);


        final Parser parser = new GnuParser();
        final CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);
        } catch ( ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        if (!commandLine.getArgList().isEmpty()) {
            throw new IllegalArgumentException("There were non-recognized options/arguments: " + commandLine.getArgList());
        }

        Map<String,String> cmdArgsMap = new HashMap<String, String>();
        cmdArgsMap.put(arquivoSimbolosArg, commandLine.getOptionValue(arquivoSimbolosArg));
        cmdArgsMap.put(arquivoLogArg, commandLine.getOptionValue(arquivoLogArg));
        cmdArgsMap.put(arquivoTesteArg, commandLine.getOptionValue(arquivoTesteArg));
        cmdArgsMap.put(arquivoTSTArg, commandLine.getOptionValue(arquivoTSTArg));

        return cmdArgsMap;

    }
}
