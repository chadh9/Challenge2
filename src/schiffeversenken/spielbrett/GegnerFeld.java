package schiffeversenken.spielbrett;

import java.util.Arrays;

public class GegnerFeld extends EigenesFeld {

    public GegnerFeld() {
        initialize(getGroesse());
    }



    @Override
    public void initialize(int size) {
        setFeld(new FeldStatus[size][size]);
        for (FeldStatus[] reihe:getFeld()) {
            Arrays.fill(reihe,FeldStatus.UNBEKANNT);
        }
    }

    public void getroffen(boolean schiff, int x,int y){
        if (schiff){
            getFeld()[x][y]=FeldStatus.SCHIFF;
        }
        else getFeld()[x][y]=FeldStatus.WASSER;
    }


}
