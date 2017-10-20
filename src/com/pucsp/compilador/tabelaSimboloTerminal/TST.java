package com.pucsp.compilador.tabelaSimboloTerminal;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TST implements Serializable{

    private final static int N = 127;
    private final static int FIRST_COLLISION_INDEX = N ;
    private final static int FIRST_SPECIAL_TOKEN_INDEX = 2*N ;
    private final String[] tokens_especiais = {"IDENT", "NUMBER", "FLOAT", "ALFA", "FOF"};

    private static int nextCollisionIndex;
    private static int nextSpecialTokenIndex;
    public static SimboloTerminal[] tst;

    public TST() {

       this.initializeSimbolosTerminais();

       for( String token : tokens_especiais){
            tst[nextSpecialTokenIndex].setSimbolo(token);
            tst[nextSpecialTokenIndex].setChaveProxSimbolo(-1);
            nextSpecialTokenIndex++;
       }

    }

    public TST(String tstFilePath) throws IOException, ClassNotFoundException {
        initializeSimbolosTerminais();
        leTSTBinario(tstFilePath);
    }

    private void initializeSimbolosTerminais(){
        this.tst = new SimboloTerminal[2*N + 10];
        this.nextCollisionIndex = FIRST_COLLISION_INDEX;
        this.nextSpecialTokenIndex = FIRST_SPECIAL_TOKEN_INDEX;
        for( int i = 0; i < this.tst.length; i++){
            this.tst[i] = new SimboloTerminal();
        }
    }

    public int searchInsert(String simbolo, char mode, Boolean debug) throws Exception {
        int position;
        int key = hashFunction(simbolo);
        int parent_key;
        Boolean found = false;

        do {
            if (tst[key].getSimbolo().equals(simbolo)) {
                found = true;
            }
            parent_key = key;
            key = tst[key].getChaveProxSimbolo();
        } while ( key > 0 && !found);

        if ( found ) {
            position = parent_key;
        } else {
            if (mode == 'C') {
                position = -1;
            } else if (mode == 'I') {
                if (tst[parent_key].getChaveProxSimbolo() == -2) {
                    position = parent_key;
                    tst[parent_key].setChaveProxSimbolo(-1);

                } else {
                    position = nextCollisionIndex;
                    tst[parent_key].setChaveProxSimbolo(position);
                    nextCollisionIndex++;
                }
                tst[position].setSimbolo(simbolo);
                tst[position].setChaveProxSimbolo(-1);
            } else {
                throw new Exception("Modo invalido");
            }
        }

        if(debug){
            if( found ) {
                System.out.println("Simbolo " + simbolo + " já existe na posição " + position);
            } else {
                if (mode == 'C') {
                    System.out.println("Simbolo " + simbolo + " não encontrado");
                } else if (mode == 'I') {
                    System.out.println("Simbolo " + simbolo + " inserido na posição " + position);
                }
            }
        }
        return position;
    };

    private int hashFunction(String value){
        int key;
        if (value.length() >=3) {
            key = value.substring(0, 3).hashCode() % N;
        } else {
            key = value.charAt(0) % N;
        }
        return key;
    }

    public void gravaTSTBinario(String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(tst);
        oos.flush();
        oos.close();
    }

    public void gravaTSTTexto(String path) throws IOException {
        FileOutputStream fis = new FileOutputStream(path);
        fis.write(("Tabela de simbolos terminais \n").getBytes());
        for ( int i = 0; i < tst.length; i ++ ) {
            fis.write((" Posição " + i ).getBytes());
            fis.write((" Simbolo: " + tst[i].getSimbolo()).getBytes() );
            fis.write((" Colisão: " + tst[i].getChaveProxSimbolo() + "\n").getBytes() );
        }
        fis.close();
    }

    public void leTSTBinario(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        this.tst = (SimboloTerminal[])ois.readObject();
        ois.close();
    }



}
