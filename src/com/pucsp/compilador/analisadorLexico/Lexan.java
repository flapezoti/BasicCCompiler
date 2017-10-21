package com.pucsp.compilador.analisadorLexico;

import com.pucsp.compilador.tabelaSimboloTerminal.TST;
import com.pucsp.compilador.tabelaSimboloTerminal.TokensEspeciais;

public class Lexan {
    private TST tst;


    public Token montarToken(String cadeia, TokensEspeciais tipoToken) throws Exception {
        Token token = new Token();
        int indiceTST = tst.searchInsert(cadeia, 'C', Boolean.TRUE);

        if (indiceTST != -1){
            //Significa que o token é uma palavra reservada
            token.setConteudoNaTST(cadeia);

        } else {
            //Pode ser NUMBER FLOAT ALPHA ou IDENT
            indiceTST = tst.buscaSimboloEspecial(tipoToken.name());
            token.setConteudoNaTST(tipoToken.name());

            if ( tipoToken.equals(TokensEspeciais.NUMBER)){
                int valorInteiro = Integer.parseInt(cadeia);
                token.setValorNumericoInt(valorInteiro);
            } else if (tipoToken.equals(TokensEspeciais.FLOAT)){
                float valorFloat = Float.parseFloat(cadeia);
                token.setValorNumericoFloat(valorFloat);
            }
        }
        token.setItemLexico(cadeia);
        token.setIndiceNaTST(indiceTST);
    }
}
