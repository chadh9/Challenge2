package schiffeversenken.protocolBinding;

import schiffeversenken.austausch.SchiffeVersenkenSenden;
import schiffeversenken.austausch.StatusException;

import java.io.DataOutputStream;
import java.io.IOException;

public class StreamBindingSender implements SchiffeVersenkenSenden {

    private DataOutputStream dos;

    public StreamBindingSender(DataOutputStream dataOutputStream){
        dos=dataOutputStream;
    }

    @Override
    public void reihenfolgeWuerfeln(int random) throws IOException, StatusException {
        dos.writeInt(Kommando.REIHENFOLGEWUERFELN);
        dos.writeInt(random);
    }

    @Override
    public void sendeKoordinate(int x, int y) throws IOException, StatusException {
        dos.writeInt(Kommando.KOORDINATE);
        dos.writeInt(x);
        dos.writeInt(y);
    }

    @Override
    public void sendeKapitulation() throws IOException, StatusException {
        dos.writeInt(Kommando.KAPITULATION);
    }

    @Override
    public void sendeBestaetigen(int i) throws IOException, StatusException {
        dos.writeInt(Kommando.BESTAETIGEN);
    }


}
