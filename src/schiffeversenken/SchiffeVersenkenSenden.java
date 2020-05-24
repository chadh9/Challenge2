package schiffeversenken;

public interface SchiffeVersenkenSenden {
    void reihenfolgeWuerfeln(int i);

    void sendeKoordinate(int x,int y);
    void sendeKapitulation();

    void sendeBestaetigen(int i);

}
