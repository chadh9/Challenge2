package schiffeversenken.austausch;

public interface SchiffeVersenkenEmpfangen {

    void wuerfelEmpfangen(int random) throws StatusException;

    void empfangeKoordinate(int x,int y) throws StatusException;
    void empfangeKapitulation() throws StatusException;

    void empfangeBestaetigen(int i) throws StatusException;

}
