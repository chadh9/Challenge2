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


}
