package com.pucsp.compilador.analisadorLexico;

public class Lexan {
    private static Lexan instance;
    String x;

    private Lexan(String x){
        x = x;
    }

    public void create(String x){
       this.instance = new Lexan(x);
    }

    static public Lexan getInstance(){
        return instance;
    }



}
