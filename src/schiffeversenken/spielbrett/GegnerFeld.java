package schiffeversenken.spielbrett;

import java.util.Arrays;

public class GegnerFeld extends EigenesFeld {
    private int groesse = 10;
    private FeldStatus[][] feld;

    public GegnerFeld() {
        initialize(groesse);
    }


    @Override
    public void remaining() {

    }

    @Override
    public void initialize(int size) {
        feld = new FeldStatus[size][size];
        for (FeldStatus[] reihe:feld) {
            Arrays.fill(reihe,FeldStatus.UNKBEKANNT);
        }
    }


}
