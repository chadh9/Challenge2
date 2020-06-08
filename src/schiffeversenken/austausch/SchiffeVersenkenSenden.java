package schiffeversenken.austausch;

import java.io.IOException;

public interface SchiffeVersenkenSenden {

    void reihenfolgeWuerfeln(int i) throws IOException;

    void sendeKoordinate(int x,int y) throws IOException;
    void sendeKapitulation() throws IOException;

    void sendeBestaetigen(int i) throws IOException;

}
