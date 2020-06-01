package schiffeversenken.austausch;

public interface SchiffeVersenkenEmpfangen {

    void wuerfelEmpfangen() throws StatusException;

    void empfangeKoordinate() throws StatusException;
    void empfangeKapitulation() throws StatusException;

    void empfangeBestaetigen() throws StatusException;

}
