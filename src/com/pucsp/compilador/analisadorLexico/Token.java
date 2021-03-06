package com.pucsp.compilador.analisadorLexico;

import java.io.IOException;
import java.io.OutputStream;

public class Token {
    private int indiceNaTST = -1;
    /* o significado do lexema representado pelo índice da TST
    correspondente ao item identificado. Este valor indica se o lexema é um identificador,
    palavra reservada, número, operador ou constante, etc.*/

    private String itemLexico; //cadeia de caracteres referente ao token
    private int valorNumericoInt;
    private float valorNumericoFloat;
    private String conteudoNaTST;
    /*O conteúdo armazenado na TST referente ao índice retornado. Não tem utilidade
    para fins de compilador. Apenas para verificação do funcionamento do Lexan.*/

    public int getIndiceNaTST() {
        return indiceNaTST;
    }

    public void setIndiceNaTST(int indiceNaTST) {
        this.indiceNaTST = indiceNaTST;
    }

    public String getItemLexico() {
        return itemLexico;
    }

    public void setItemLexico(String itemLexico) {
        this.itemLexico = itemLexico;
    }

    public int getValorNumericoInt() {
        return valorNumericoInt;
    }

    public void setValorNumericoInt(int valorNumericoInt) {
        this.valorNumericoInt = valorNumericoInt;
    }

    public float getValorNumericoFloat() {
        return valorNumericoFloat;
    }

    public void setValorNumericoFloat(float valorNumericoFloat) {
        this.valorNumericoFloat = valorNumericoFloat;
    }

    public String getConteudoNaTST() {
        return conteudoNaTST;
    }

    public void setConteudoNaTST(String conteudoNaTST) {
        this.conteudoNaTST = conteudoNaTST;
    }

    public void imprimirToken(OutputStream arquivoSaida) throws IOException {
        arquivoSaida.write(this.toString().getBytes());
        arquivoSaida.write("\n".getBytes());
        arquivoSaida.flush();
    }

    public String toString(){
        return "indice na TST: " + this.getIndiceNaTST() + "\n cadeia: " + this.getItemLexico() + "\n conteudo na TST: " + this.getConteudoNaTST();

    }
}
