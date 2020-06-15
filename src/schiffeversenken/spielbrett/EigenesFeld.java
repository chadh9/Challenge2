package schiffeversenken.spielbrett;

import java.util.Arrays;

public class EigenesFeld implements Feld{

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
                    case SCHLACHTSCHIFF:
                        System.out.print("S  ");
                        break;
                    case KREUZER_I:
                    case KREUZER_II:
                        System.out.print("C  ");
                        break;
                    case ZERSTOERER_I:
                    case ZERSTOERER_II:
                    case ZERSTOERER_III:
                        System.out.print("D  ");
                        break;
                    case UBOOT_I:
                    case UBOOT_II:
                    case UBOOT_III:
                    case UBOOT_IV:
                        System.out.print("U  ");
                        break;
                    case SCHIFF: break;
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
                if (feld[i][j]!=FeldStatus.WASSER||
                        feld[i][j]!=FeldStatus.VERSENKT)
                    count++;
                }
            }
        return count;
    }

    public int remaining(FeldStatus feldStatus){
        int count=0;
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                if (feld[i][j]!=feldStatus)
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

}
