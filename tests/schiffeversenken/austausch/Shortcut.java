package schiffeversenken.austausch;

import schiffeversenken.protocolBinding.StreamBindingSender;

import java.io.IOException;

public class Shortcut implements SchiffeVersenkenSenden {

    private final SchiffeVersenkenEmpfangen receiver;

    public Shortcut(SchiffeVersenkenEmpfangen receiver){
        this.receiver=receiver;
    }

    @Override
    public void reihenfolgeWuerfeln(int i) throws IOException {
        receiver.wuerfelEmpfangen(i);
    }

    @Override
    public void sendeKoordinate(int x, int y) throws IOException {
        receiver.empfangeKoordinate(x,y);
    }

    @Override
    public void sendeKapitulation() throws IOException {
        receiver.empfangeKapitulation();
    }

    @Override
    public void sendeBestaetigen(int i) throws IOException {
        receiver.empfangeKapitulation();
    }
}
