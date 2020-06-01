package schiffeversenken.austausch;

public enum SchiffeVersenkenKommando {
    REIHENFOLGEWUERFELN (0), KOORDINATE (1), KAPITULATION (2), BESTAETIGEN (3);

    private int value;

    SchiffeVersenkenKommando(int i) {
        value=i;
    }

    public int getValue() {
        return value;
    }
}
