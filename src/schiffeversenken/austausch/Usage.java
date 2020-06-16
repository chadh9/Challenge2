package schiffeversenken.austausch;

import schiffeversenken.spielbrett.Feld;

import java.io.IOException;

public interface Usage {

    public void wuerfeln() throws StatusException, IOException;
    public void versenken(int x ,int y) throws IOException;
    public SchiffeVersenkenStatus getStatus();
    public void kapitulation() throws IOException;
    public void bestaetigen() throws IOException;



    public Feld getSpielerfeld();

    public Feld getGegnerfeld();
}
