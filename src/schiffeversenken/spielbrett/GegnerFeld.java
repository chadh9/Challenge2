package schiffeversenken.spielbrett;

import java.util.Arrays;

public class GegnerFeld extends EigenesFeld {

    public GegnerFeld() {
        initialize(getGroesse());
    }
    private int availableships=30;


    @Override
    public void initialize(int size) {
        setFeld(new FeldStatus[size][size]);
        for (FeldStatus[] reihe:getFeld()) {
            Arrays.fill(reihe,FeldStatus.UNBEKANNT);
        }
    }

    @Override
    public int remaining() {
        int count=0;
        for (int i = 0; i < getGroesse(); i++) {
            for (int j = 0; j < getGroesse(); j++) {
                if (getFeld()[i][j]==FeldStatus.SCHIFF)
                    count++;
            }
        }
        return count-availableships;
    }

    public void getroffen(boolean schiff, int x, int y){
        if (schiff){
            getFeld()[x][y]=FeldStatus.SCHIFF;
        }
        else getFeld()[x][y]=FeldStatus.WASSER;
    }


}
