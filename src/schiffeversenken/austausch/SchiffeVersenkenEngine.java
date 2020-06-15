package schiffeversenken.austausch;

import schiffeversenken.TCPStreams;
import schiffeversenken.protocolBinding.Kommando;
import schiffeversenken.spielbrett.Feld;
import schiffeversenken.spielbrett.FeldStatus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class SchiffeVersenkenEngine implements SchiffeVersenkenEmpfangen, SchiffeVersenkenSenden {

    private SchiffeVersenkenStatus status;
    private DataOutputStream dos;
    private int randomInt;
    private Feld spielerfeld;
    private Feld gegnerfeld;
    private int x;
    private int y;

    public SchiffeVersenkenStatus getStatus() {
        return status;
    }

    public Feld getSpielerfeld() {
        return spielerfeld;
    }

    public Feld getGegnerfeld() {
        return gegnerfeld;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public void setSpielerfeld(Feld spielerfeld) {
        this.spielerfeld = spielerfeld;
    }

    public void setGegnerfeld(Feld gegnerfeld) {
        this.gegnerfeld = gegnerfeld;
    }

    public void setStatus(SchiffeVersenkenStatus status){
        this.status=status;
    }


    @Override
    public void reihenfolgeWuerfeln(int i) throws StatusException {

        if (status != SchiffeVersenkenStatus.SPIELSTART) {
            throw new StatusException();
        }

        try {

            randomInt=i;
            dos.writeInt(randomInt);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void wuerfelEmpfangen(int random) throws StatusException {
        if (status != SchiffeVersenkenStatus.SPIELSTART) {
            throw new StatusException();
        }

        if (random>randomInt){
            status=SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN;

        }
        else if (random<randomInt) {
            status = SchiffeVersenkenStatus.VERSENKEN_SENDEN;
        }

    }

    @Override
    public void empfangeKoordinate(int x, int y) throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN) {
            throw new StatusException();
        }
        System.out.println("Gegner schießt auf: " +x+" "+y);

        this.x=x;
        this.y=y;

        if(spielerfeld.getFeld()[x][y]!=FeldStatus.WASSER
        ){
            spielerfeld.getFeld()[x][y]=FeldStatus.VERSENKT;
            System.out.println("Gegnerischer Treffer!");
        }

        else System.out.println("Kein Treffer!");

        status=SchiffeVersenkenStatus.BESTAETIGEN_SENDEN;

    }

    @Override
    public void empfangeKapitulation() throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN) {
            throw new StatusException();
        }

        System.out.println("Gegner kapituliert... Gewonnen!");
        status=SchiffeVersenkenStatus.BEENDEN;
    }


    @Override
    public void empfangeBestaetigen(int i) throws StatusException {
        if (status != SchiffeVersenkenStatus.BESTAETIGEN_EMPFANGEN) {
            throw new StatusException();
        }
        switch (i){
            case 0:
                System.out.println("Treffer!");
                gegnerfeld.getFeld()[x][y]=FeldStatus.SCHIFF;

                status=SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN;

            case 1:
                System.out.println("Verfehlt!");
                gegnerfeld.getFeld()[x][y]=FeldStatus.WASSER;
                status=SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN;

            case 2:
                System.out.println("Versenkt!");

                if(gegnerfeld.remaining()==0) {
                    gegnerfeld.getFeld()[x][y]=FeldStatus.SCHIFF;
                    status=SchiffeVersenkenStatus.BEENDEN;
                    System.out.println("Alle versenkt...Gewonnen!");
                }
                else status=SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN;

        }
    }


    @Override
    public void sendeKoordinate(int x, int y) throws IOException {
        if (status != SchiffeVersenkenStatus.VERSENKEN_SENDEN) {
            throw new StatusException();
        }
        this.x=x;
        this.y=y;

        dos.writeInt(x);
        dos.writeInt(y);

    }

    @Override
    public void sendeKapitulation() throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN_SENDEN) {
            throw new StatusException();
        }
        System.out.println("Kapituliere... Verloren!");
        status=SchiffeVersenkenStatus.BEENDEN;
    }

    @Override
    public void sendeBestaetigen() throws IOException {
        if (status != SchiffeVersenkenStatus.BESTAETIGEN_SENDEN) {
            throw new StatusException();
        }

        if(spielerfeld.getFeld()[x][y]!=FeldStatus.WASSER||
                spielerfeld.getFeld()[x][y]!=FeldStatus.VERSENKT) {
            FeldStatus feldStatus=spielerfeld.getFeld()[x][y];

            spielerfeld.getFeld()[x][y]=FeldStatus.VERSENKT;

            if(spielerfeld.remaining(feldStatus)==0) dos.writeInt(2);
            else dos.writeInt(0);
        }
        else dos.writeInt(1);

        status=SchiffeVersenkenStatus.VERSENKEN_SENDEN;
    }


}
