package schiffeversenken.austausch;

public interface SchiffeVersenkenSenden {

    void reihenfolgeWuerfeln(int i) throws StatusException;

    void sendeKoordinate(int x,int y) throws StatusException;
    void sendeKapitulation() throws StatusException;

    void sendeBestaetigen(int i) throws StatusException;

}
