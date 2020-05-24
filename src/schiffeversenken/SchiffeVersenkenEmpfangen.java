package schiffeversenken;

public interface SchiffeVersenkenEmpfangen {
    void wuerfelEmpfangen() throws StatusException;

    void empfangeKoordinate();
    void empfangeKapitulation();

    void empfangeBestaetigen();

}
