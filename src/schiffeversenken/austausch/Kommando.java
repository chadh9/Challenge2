package schiffeversenken.austausch;

public enum Kommando {
    REIHENFOLGEWUERFELN (0), KOORDINATE (1), KAPITULATION (2), BESTAETIGEN (3);

    private int value;

    Kommando(int i) {
        value=i;
    }

    public int getValue() {
        return value;
    }
}
