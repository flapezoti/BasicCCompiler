package com.company;

public class TST {

    private final static int N = 127;
    private final static int FIRST_COLLISION_INDEX = N ;
    private final static int FIRST_SPECIAL_TOKEN_INDEX = 2*N ;

    private static int nextCollisionIndex;
    private static SimboloTerminal[] tst;

    public TST() {
       tst = new SimboloTerminal[2*N + 10];
       nextCollisionIndex = FIRST_COLLISION_INDEX;
       for( int i = 0; i < tst.length; i++){
           tst[i] = new SimboloTerminal();
       }

    }

    public int searchInsert(String simbolo, char mode, Boolean debug) throws Exception {
        int position;
        int key = hashFunction(simbolo);
        System.out.println("HASHKEY do simbolo " + key );
        int parent_key;
        Boolean found = false;

        do {
            System.out.println("Verificando posição: " + key );
            if (tst[key].getSimbolo() == simbolo) {
                found = true;
            }
            parent_key = key;
            key = tst[key].getChaveProxSimbolo();
            System.out.println("Próxima posição: " + key );

        } while ( key > 0 && !found);

        if ( found ) {
            position = parent_key;
        } else {
            if (mode == 'C'){
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
                System.out.println("INSERINDO NA POSIÇÃO" + position );
                tst[position].setSimbolo(simbolo);
                tst[position].setChaveProxSimbolo(-1);
                position = position;
                System.out.println("Simbolo" + tst[position].getSimbolo());
                System.out.println("Posição" + tst[position].getChaveProxSimbolo());
            } else {
                throw new Exception("Invalid mode");
            }
        }
        return position;
    };

    private int hashFunction(String keyword){
        int key;
        if (keyword.length() >=3) {
            key = keyword.substring(0, 3).hashCode() % N;
        } else {
            key = keyword.charAt(0) % N;
        }
        return key;
    }

}
