package schiffeversenken.austausch;

import schiffeversenken.TCPStreams;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class SchiffeVersenkenEngine implements SchiffeVersenkenEmpfangen, SchiffeVersenkenSenden {

    private SchiffeVersenkenStatus status;
    private TCPStreams tcpStreams;

    private int randomInt;


    @Override
    public void reihenfolgeWuerfeln(int i) throws StatusException {
        if (status != SchiffeVersenkenStatus.SPIELSTART) {
            throw new StatusException();
        }

        try {
            DataOutputStream dos=tcpStreams.getdos();
            dos.writeInt(Kommando.REIHENFOLGEWUERFELN.getValue());
            dos.writeInt(new Random().nextInt());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void wuerfelEmpfangen() throws StatusException {
        if (status != SchiffeVersenkenStatus.SPIELSTART) {
            throw new StatusException();


        }
        try {
            DataInputStream dis=tcpStreams.getdis();
            if(dis.readInt() == Kommando.REIHENFOLGEWUERFELN.getValue()){
                int gegnerWert=dis.readInt();
                if (gegnerWert>randomInt){
                    /*
                    STATECHANGE: EMPFANGE KOORDINATE
                      */
                }
                else if (gegnerWert<randomInt){
                    /*
                    STATECHANGE: SENDE KOORDINATE
                     */
                }
                else; //KEIN STATECHANGE: WÜRFELSENDEN

            };



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void empfangeKoordinate() throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN) {
            throw new StatusException();
        }
    }

    @Override
    public void empfangeKapitulation() throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN) {
            throw new StatusException();
        }
    }

    @Override
    public void empfangeBestaetigen() throws StatusException {
        if (status != SchiffeVersenkenStatus.BESTAETIGEN) {
            throw new StatusException();
        }
    }


    @Override
    public void sendeKoordinate(int x, int y) throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN) {
            throw new StatusException();
        }
    }

    @Override
    public void sendeKapitulation() throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN) {
            throw new StatusException();
        }
    }

    @Override
    public void sendeBestaetigen(int i) throws StatusException {
        if (status != SchiffeVersenkenStatus.BESTAETIGEN) {
            throw new StatusException();
        }
    }

    public void ende() throws StatusException {
        if (status != SchiffeVersenkenStatus.BEENDEN) {
            throw new StatusException();
        }
    }

}
