package schiffeversenken.spielbrett;

import java.util.Arrays;

public class EigenesFeld{

    private int groesse = 10;
    private FeldStatus[][] feld;

    public EigenesFeld() {
        initialize(groesse);
    }



    public void initialize(int size) {
        feld = new FeldStatus[size][size];
        for (FeldStatus[] reihe:feld) {
            Arrays.fill(reihe,FeldStatus.WASSER);
        }
    }



    public void show(){
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                switch (feld[i][j]) {
                    case WASSER:
                        System.out.print("W  ");
                        break;
                    case SCHIFF:
                        System.out.print("S  ");
                        break;
                    case UNBEKANNT:
                        System.out.print("?  ");
                }
            }
            System.out.println("");
        }
    }


    public int remaining(){
        int count=0;
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                if (feld[i][j]==FeldStatus.SCHIFF);
                    count++;
                }
            }
        return count;
    }

    public FeldStatus[][] getFeld() {
        return feld;
    }

    public int getGroesse() {
        return groesse;
    }

    public void setFeld(FeldStatus[][] feld) {
        this.feld = feld;
    }

    public void getroffen(int x, int y){
        if (feld[x][y]==FeldStatus.SCHIFF){
            feld[x][y]=FeldStatus.WASSER;
        }
    }
}
