package com.company;

import java.io.Serializable;

public class SimboloTerminal implements Serializable {

    private String simbolo;
    private int chaveProxSimbolo;

    public SimboloTerminal(){
        this.simbolo = "\\0";
        this.chaveProxSimbolo = -2;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public int getChaveProxSimbolo() {
        return chaveProxSimbolo;
    }

    public void setChaveProxSimbolo(int chaveProxSimbolo) {
        this.chaveProxSimbolo = chaveProxSimbolo;
    }
}
