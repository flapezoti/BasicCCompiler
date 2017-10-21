package com.pucsp.compilador.analisadorLexico;

import com.pucsp.compilador.tabelaSimboloTerminal.TST;
import com.pucsp.compilador.tabelaSimboloTerminal.TokensEspeciais;

import java.io.OutputStream;

public class Lexan {
    private TST tst;
    private OutputStream arquivoSaida;

    public Lexan(TST tst, OutputStream arquivoSaida){
        this.tst = tst;
        this.arquivoSaida = arquivoSaida;
    }

    public Token aef() throws Exception {
        Token token = new Token();
        boolean aceite = false;
        int estado_corrente = 0;
        char prox_caracter;

        prox_caracter = FilePositioner.getInstance().getCurrentChar();

        while(FilePositioner.getInstance().getCharPosition() != -1 && !aceite){
            switch(estado_corrente){
                case 0:
                    if(Character.isWhitespace(prox_caracter)){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 0;
                    }else if (prox_caracter == '#'){
                        estado_corrente = 1;
                    }else if (Character.isLetter(prox_caracter) || prox_caracter == '_'){
                        estado_corrente = 2;
                    }else if (prox_caracter == '('){
                        estado_corrente = 3;
                    }else if (prox_caracter == ')'){
                        estado_corrente = 4;
                    }else if (Character.isDigit(prox_caracter) || prox_caracter == '.'){
                        estado_corrente = 5;
                    }else if (prox_caracter == '<'){
                        estado_corrente = 7;
                    }else if (prox_caracter == '>'){
                        estado_corrente = 10;
                    }else if (prox_caracter == '{'){
                        estado_corrente = 13;
                    }else if (prox_caracter == '}'){
                        estado_corrente = 14;
                    }else if (prox_caracter == ';'){
                        estado_corrente = 15;
                    }else if (prox_caracter == '+'){
                        estado_corrente = 16;
                    }else if (prox_caracter == '='){
                        estado_corrente = 17;
                    }else if (prox_caracter == '/'){
                        estado_corrente = 18;
                    }else if(prox_caracter == '\''){
                        estado_corrente = 22;
                    }else if(prox_caracter == '\"'){
                        estado_corrente = 24;
                    }else {
                        StringBuilder msgErro = new StringBuilder("Erro [Simbolo nao permitido] na linha [" + FilePositioner.getInstance().getLinePostition() + "] Coluna ["+ FilePositioner.getInstance().getCharPosition()+"]\n ");
                        msgErro.append(FilePositioner.getInstance().getCurrentLine()).append("\n");
                        for(int i = 0; i <= FilePositioner.getInstance().getCharPosition(); i++){
                            msgErro.append(" ");
                        }
                        msgErro.append("^\n");
                        System.out.println( msgErro);
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 0;
                    }
                    break;
                case 1: //#
                    if (prox_caracter == '#'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 1;
                    }else if (Character.isWhitespace(prox_caracter)){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 1;
                    }else if(Character.isLetter(prox_caracter) || prox_caracter == '_'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 1;
                    }else{
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 0;
                    }

                    break;
                case 2: //letras
                    if (Character.isLetterOrDigit(prox_caracter)){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 2;
                    }else{
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        estado_corrente = 0;
                    }
                    break;
                case 3: //(
                    if (prox_caracter == '('){
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 3;
                    }else if (prox_caracter == ')'){
                        estado_corrente = 4;
                    }else{
                        estado_corrente = 0;
                    }
                    break;
                case 4: //)
                    if (prox_caracter == ')'){
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                    }
                    estado_corrente = 0;
                    break;
                case 5: //0-9 
                    if (Character.isDigit(prox_caracter)){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 5;
                    }else if (prox_caracter == '.'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 6;
                    }else{
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.NUMBER);
                        estado_corrente = 0;
                    }
                    break;
                case 6: //. iniciado em 0-9
                    if (Character.isDigit(prox_caracter)){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 6;

                    }else{
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.NUMBER);
                        estado_corrente = 0;
                    }
                    break;
                case 7: //< 
                    if(prox_caracter == '<'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 8;
                    }
                    break;
                case 8: //<= ou < 
                    if(prox_caracter == '='){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.RESERVED);
                        estado_corrente = 0;
                    }else if(prox_caracter == '<'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 9;
                    }else{
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        estado_corrente = 0;
                    }
                    break;
                case 9: //<< ou <<=
                    if (prox_caracter == '='){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                    }
                    aceite = true;
                    token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                    estado_corrente = 0;
                case 10: //> 
                    if(prox_caracter == '>'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 11;
                    }
                    break;
                case 11: //>= ou >
                    if(prox_caracter == '='){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        estado_corrente = 0;
                    }else if(prox_caracter == '>'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 12;
                    }else{
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        estado_corrente = 0;
                    }
                    break;
                case 12: //>> ou >>=
                    if (prox_caracter == '='){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        estado_corrente = 0;
                    }else{
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        estado_corrente = 0;
                    }
                    break;
                case 13: //{
                    if (prox_caracter == '{'){
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                    }
                    estado_corrente = 0;
                    break;
                case 14: //}
                    if (prox_caracter == '}'){
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                    }
                    estado_corrente = 0;
                    break;
                case 15: //;
                    if (prox_caracter == ';'){
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                    }
                    estado_corrente = 0;
                    break;
                case 16: //+
                    if (prox_caracter == '+'){
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                    }
                    estado_corrente = 0;
                    break;
                case 17: //=
                    if (prox_caracter == '='){
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                    }
                    estado_corrente = 0;
                    break;
                case 18: // /
                    if (prox_caracter == '/'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 19;
                    }
                    break;
                case 19: // / ou /*
                    if (prox_caracter == '*'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 20;
                    }else if(prox_caracter == '/'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 20;
                    }else if(prox_caracter == '='){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        estado_corrente = 0;
                    }else{ // /
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.IDENT);
                        estado_corrente = 0;
                    }
                    break;
                case 20: // /*****
                    if (prox_caracter == '*'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 21;
                    }else{
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 20;
                    }
                    break;
                case 21: // */
                    if (prox_caracter == '/'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 0;
                    }else if(prox_caracter == '*'){
                        estado_corrente = 20;
                    }else{
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 20;
                    }
                    break;
                case 22:
                    if (prox_caracter == '\''){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 23;
                    }
                    break;
                case 23:
                    if (prox_caracter == '\''){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.ALPHA);
                        estado_corrente = 0;
                    }else if(prox_caracter == ';'){
                        StringBuilder msgErro = new StringBuilder("Erro [Aspas simples desbalanceado]na linha [" + FilePositioner.getInstance().getLinePostition() + "] Coluna ["+ FilePositioner.getInstance().getCharPosition()+"]\n ");
                        msgErro.append(FilePositioner.getInstance().getCurrentLine()).append("\n");
                        for(int i = 0; i <= FilePositioner.getInstance().getCharPosition(); i++){
                            msgErro.append(" ");
                        }
                        msgErro.append("^\n");
                        System.out.println( msgErro);

                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 0;
                    }else{
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 23;
                    }
                    break;
                case 24:
                    if (prox_caracter == '\"'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 25;
                    }
                    break;
                case 25:
                    if (prox_caracter == '\"'){
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        aceite = true;
                        token = montarToken(montarCadeia(), TokensEspeciais.ALPHA);
                        estado_corrente = 0;
                    }else if(prox_caracter == ';'){
                        StringBuilder msgErro = new StringBuilder("Erro [Aspas duplas desbalanceado]na linha [" + FilePositioner.getInstance().getLinePostition() + "] Coluna ["+ FilePositioner.getInstance().getCharPosition()+"]\n ");
                        msgErro.append(FilePositioner.getInstance().getCurrentLine()).append("\n");
                        for(int i = 0; i <= FilePositioner.getInstance().getCharPosition(); i++){
                            msgErro.append(" ");
                        }
                        msgErro.append("^\n");
                        System.out.println( msgErro);
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 0;
                    }else{
                        FilePositioner.getInstance().nextChar();
                        FilePositioner.getInstance().moveToNextLexem();
                        estado_corrente = 25;
                    }
                    break;
            }
            prox_caracter = FilePositioner.getInstance().getCurrentChar();
        }



        return token;
    }


    public Token montarToken(String cadeia, TokensEspeciais tipoToken) throws Exception {
        System.out.println(cadeia);
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
        token.imprimirToken(arquivoSaida);

        return token;
    }


    private String montarCadeia(){
        int posicaoInicialCadeia = FilePositioner.getInstance().getCharPosition();
        int posicaoFinalCadeia = FilePositioner.getInstance().getCurrentLexemBeginning();
        String cadeia = null;
        if (posicaoInicialCadeia == posicaoFinalCadeia){
            cadeia = ""+FilePositioner.getInstance().getCurrentChar();
        }else{
            cadeia = FilePositioner.getInstance().getCurrentLine().substring(posicaoInicialCadeia, posicaoFinalCadeia);
        }
        return cadeia;
    }
}
