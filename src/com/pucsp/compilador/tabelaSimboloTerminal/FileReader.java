package com.pucsp.compilador.tabelaSimboloTerminal;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileReader {


    public static List<String> fileToString(String path) {

        List<String> simbolos = new ArrayList<String>();

        try (Stream<String> stream = Files.lines(Paths.get(path))) {

            for(Object obj : stream.toArray()){
                String symbol = (String) obj;
                simbolos.add(symbol);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return simbolos;
    }

}
