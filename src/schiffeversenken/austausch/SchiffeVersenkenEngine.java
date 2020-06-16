package schiffeversenken.austausch;

import schiffeversenken.spielbrett.EigenesFeld;
import schiffeversenken.spielbrett.Feld;
import schiffeversenken.spielbrett.FeldStatus;
import schiffeversenken.spielbrett.GegnerFeld;

import java.io.IOException;
import java.util.Random;

public class SchiffeVersenkenEngine implements SchiffeVersenkenEmpfangen, Usage {

    private SchiffeVersenkenStatus status;

    private int sentrandom = 0;
    private int spielerRandom;
    private int gegnerRandom;

    private Feld spielerfeld;
    private Feld gegnerfeld;
    private int x;
    private int y;
    private SchiffeVersenkenSenden sender;

    public SchiffeVersenkenEngine(SchiffeVersenkenSenden sender) {
        this.sender = sender;
        status = SchiffeVersenkenStatus.SPIELSTART;

        spielerfeld=new EigenesFeld();
        gegnerfeld=new GegnerFeld();
    }

    @Override
    public SchiffeVersenkenStatus getStatus() {
        return status;
    }

    public Feld getSpielerfeld() {
        return spielerfeld;
    }

    public Feld getGegnerfeld() {
        return gegnerfeld;
    }

    public void setSpielerfeld(Feld spielerfeld) {
        this.spielerfeld = spielerfeld;
    }

    public void setGegnerfeld(Feld gegnerfeld) {
        this.gegnerfeld = gegnerfeld;
    }

    public void setStatus(SchiffeVersenkenStatus status) {
        this.status = status;
    }

    private void decide() {
        System.out.println("int " + spielerRandom + " " + gegnerRandom);
        if (gegnerRandom > spielerRandom) {
            status = SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN;
        } else if (gegnerRandom < spielerRandom) {
            status = SchiffeVersenkenStatus.VERSENKEN_SENDEN;
        }
    }

    @Override
    public void wuerfelEmpfangen(int random) throws StatusException {
        if (status != SchiffeVersenkenStatus.SPIELSTART) {
            throw new StatusException();
        }

        gegnerRandom = random;

        if (sentrandom==1) {
            decide();
        }
        else sentrandom=2;
    }

    @Override
    public void empfangeKoordinate(int x, int y) throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN) {
            throw new StatusException();
        }
        System.out.println("Gegner schießt auf: " + x + " " + y);

        this.x = x;
        this.y = y;

        if (spielerfeld.getFeld()[x][y] != FeldStatus.WASSER
        ) {
            spielerfeld.getFeld()[x][y] = FeldStatus.VERSENKT;
            System.out.println("Gegnerischer Treffer!");
        } else System.out.println("Kein Treffer!");

        status = SchiffeVersenkenStatus.BESTAETIGEN_SENDEN;

    }

    @Override
    public void empfangeKapitulation() throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN) {
            throw new StatusException();
        }

        System.out.println("Gegner kapituliert... Gewonnen!");
        status = SchiffeVersenkenStatus.BEENDEN;
    }


    @Override
    public void empfangeBestaetigen(int i) throws StatusException {
        if (status != SchiffeVersenkenStatus.BESTAETIGEN_EMPFANGEN) {
            throw new StatusException();
        }
        switch (i) {
            case 0:
                System.out.println("Treffer!");
                gegnerfeld.getFeld()[x][y] = FeldStatus.SCHIFF;

                status = SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN;

            case 1:
                System.out.println("Verfehlt!");
                gegnerfeld.getFeld()[x][y] = FeldStatus.WASSER;
                status = SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN;

            case 2:
                System.out.println("Versenkt!");

                if (gegnerfeld.remaining() == 0) {
                    gegnerfeld.getFeld()[x][y] = FeldStatus.SCHIFF;
                    status = SchiffeVersenkenStatus.BEENDEN;
                    System.out.println("Alle versenkt...Gewonnen!");
                } else status = SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN;

        }
    }


    @Override
    public void wuerfeln() throws StatusException, IOException {
        if (status != SchiffeVersenkenStatus.SPIELSTART) {
            throw new StatusException();
        }


        Random random = new Random();

        spielerRandom = random.nextInt();

        sender.reihenfolgeWuerfeln(spielerRandom);

        if(sentrandom==2)
            decide();
        else sentrandom = 1;


    }

    @Override
    public void versenken(int x, int y) throws IOException {
        if (status != SchiffeVersenkenStatus.VERSENKEN_SENDEN) {
            throw new StatusException();
        }
        sender.sendeKoordinate(x, y);
        status=SchiffeVersenkenStatus.BESTAETIGEN_EMPFANGEN;
    }

    @Override
    public void kapitulation() throws IOException {
        if (status != SchiffeVersenkenStatus.VERSENKEN_SENDEN) {
            throw new StatusException();
        }
        System.out.println("Kapituliere... Verloren!");
        sender.sendeKapitulation();
        status = SchiffeVersenkenStatus.BEENDEN;
    }

    @Override
    public void bestaetigen() throws IOException {
        if (status != SchiffeVersenkenStatus.BESTAETIGEN_SENDEN) {
            throw new StatusException();
        }

        if (spielerfeld.getFeld()[x][y] == FeldStatus.WASSER ||
                spielerfeld.getFeld()[x][y] == FeldStatus.VERSENKT) {
            FeldStatus feldStatus = spielerfeld.getFeld()[x][y];

            spielerfeld.getFeld()[x][y] = FeldStatus.VERSENKT;

            if (spielerfeld.remaining(feldStatus) == 0) sender.sendeBestaetigen(2);
            else sender.sendeBestaetigen(0);
        } else sender.sendeBestaetigen(1);

        status = SchiffeVersenkenStatus.VERSENKEN_SENDEN;
    }
}
