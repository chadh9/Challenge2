package schiffeversenken.protocolBinding;

import schiffeversenken.austausch.SchiffeVersenkenEmpfangen;
import schiffeversenken.austausch.StatusException;

import java.io.DataInputStream;
import java.io.IOException;

public class StreamBindingReceiver extends Thread {
    private DataInputStream dis;
    private SchiffeVersenkenEmpfangen receiver;

    public StreamBindingReceiver(DataInputStream dataInputStream, SchiffeVersenkenEmpfangen receiver){
        dis=dataInputStream;
        this.receiver=receiver;
    }


    public void wuerfelEmpfangen() throws IOException {
        int random = dis.readInt();
        receiver.wuerfelEmpfangen(random);
    }


    public void empfangeKoordinate() throws IOException{
        int x = dis.readInt();
        int y = dis.readInt();
        receiver.empfangeKoordinate(x,y);
    }


    public void empfangeKapitulation() throws StatusException {
        receiver.empfangeKapitulation();
    }


    public void empfangeBestaetigen() throws IOException {
        int i = dis.readInt();
        receiver.empfangeBestaetigen(i);
    }

    @Override
    public void run() {
        boolean again = true;
        while (again){
            try {
                int cmd= dis.readInt();
                switch (cmd){
                    case Kommando.REIHENFOLGEWUERFELN: wuerfelEmpfangen(); break;
                    case Kommando.KOORDINATE: empfangeKoordinate(); break;
                    case Kommando.KAPITULATION: empfangeKapitulation();break;
                    case Kommando.BESTAETIGEN: empfangeBestaetigen();break;
                    default: again = false;
                        System.out.println("Unknown cmd" + cmd);
                }

            } catch (StatusException e){
                System.out.println("STATUSEXCEPTION;"+e.getLocalizedMessage());
                again=false;
            } catch (IOException e){
                System.out.println("IOEXCEPTION;"+e.getLocalizedMessage());
                again=false;
            }
        }
    }
}
