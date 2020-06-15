package schiffeversenken.spielbrett;

public interface Feld {
    void initialize(int size);
    void show();
    int remaining();
    int remaining(FeldStatus feldStatus);
    FeldStatus[][] getFeld();
    void setFeld(FeldStatus[][] feld);

    }


