package schiffeversenken.protocolBinding;

import schiffeversenken.austausch.SchiffeVersenkenEngine;
import schiffeversenken.austausch.SchiffeVersenkenSenden;
import schiffeversenken.austausch.StatusException;

import java.io.DataOutputStream;
import java.io.IOException;

public class StreamBindingSender implements SchiffeVersenkenSenden {

    private DataOutputStream dos;
    private SchiffeVersenkenSenden sender;

    public StreamBindingSender(DataOutputStream dataOutputStream, SchiffeVersenkenSenden sender){
        dos=dataOutputStream;
        this.sender=sender;
    }


    @Override
    public void reihenfolgeWuerfeln(int random) throws IOException, StatusException {
        dos.writeInt(Kommando.REIHENFOLGEWUERFELN);
        sender.reihenfolgeWuerfeln(random);

    }

    @Override
    public void sendeKoordinate(int x, int y) throws IOException, StatusException {
        dos.writeInt(Kommando.KOORDINATE);
        sender.sendeKoordinate(x,y);
    }

    @Override
    public void sendeKapitulation() throws IOException, StatusException {
        dos.writeInt(Kommando.KAPITULATION);
        sender.sendeKapitulation();
    }

    @Override
    public void sendeBestaetigen() throws IOException, StatusException {
        dos.writeInt(Kommando.BESTAETIGEN);
        sender.sendeBestaetigen();
    }


}
