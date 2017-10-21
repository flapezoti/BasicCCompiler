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

    /*
    * 4.  Adotar três parâmetros para o programa principal
          a) Arquivo de entrada contendo a cadeia de entrada com a sequência de tokens.
          b) Arquivo de saída com mensagens e outras informações.
          c) Arquivo binário para obter a TST.
    * */

    public static Map<String,String> getCommandLineArgsAsMap(String[] args){
        final Options options
                = new Options();

        if (args == null) {
            throw new IllegalArgumentException("Command Line args can't be null");
        }

        final String arquivoFonte = "arquivoFonte";
        final Option arquivoFonteOption = new Option( arquivoFonte, true, "Caminho do arquivo com a sequência de caracteres a serem analisados");
        arquivoFonteOption.setRequired( true );
        options.addOption(arquivoFonteOption);

        final String arquivoSaida = "arquivoSaida";
        final Option arquivoSaidaOption = new Option( arquivoSaida, true, "Caminho para arquivo de saída com erros e mensagens");
        arquivoSaidaOption.setRequired( true );
        options.addOption(arquivoSaidaOption);

        final String arquivoTstBinario = "arquivoTstBinario";
        final Option arquivoTstBinarioOption = new Option( arquivoTstBinario, true, "Caminho para arquivo texto de saída dos logs da aplicação");
        arquivoTstBinarioOption.setRequired( true );
        options.addOption(arquivoTstBinarioOption);


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
        cmdArgsMap.put(arquivoFonte, commandLine.getOptionValue(arquivoFonte));
        cmdArgsMap.put(arquivoTstBinario, commandLine.getOptionValue(arquivoTstBinario));
        cmdArgsMap.put(arquivoSaida, commandLine.getOptionValue(arquivoSaida));

        return cmdArgsMap;

    }
}
