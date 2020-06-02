package schiffeversenken.spielbrett;

public enum FeldStatus {
    UNBEKANNT(0), WASSER(1), SCHIFF(2);

    private int value;

    FeldStatus(int i) {
        value=i;
    }

    public int getValue() {
        return value;
    }
}
