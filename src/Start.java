import schiffeversenken.TCPStreams;
import schiffeversenken.austausch.SchiffeVersenkenEmpfangen;
import schiffeversenken.austausch.SchiffeVersenkenEngine;
import schiffeversenken.austausch.SchiffeVersenkenSenden;
import schiffeversenken.austausch.SchiffeVersenkenStatus;
import schiffeversenken.protocolBinding.StreamBindingReceiver;
import schiffeversenken.protocolBinding.StreamBindingSender;

import javax.sound.midi.Receiver;
import java.io.IOException;
import java.util.Scanner;

public class Start {



    public static void main(String[] args) throws IOException {

        int port = 22222;
        UserInterface ui = new UserInterface();
        TCPStreams tcpStreams = new TCPStreams();
        Scanner scanner=new Scanner(System.in);
        String cs=scanner.nextLine();
        if(cs.equals("host")){
            tcpStreams.setPort(port);
            tcpStreams.host();

        }
        else if(cs.equals("join")){
            tcpStreams.join("localhost",port);
        }
        else{
            System.out.println("ayy");
        }
        SchiffeVersenkenSenden sender= new StreamBindingSender(tcpStreams.getdos());
        SchiffeVersenkenEngine engine = new SchiffeVersenkenEngine(sender);
        StreamBindingReceiver receiver = new StreamBindingReceiver(tcpStreams.getdis(), engine);
        receiver.start();
        engine.getSpielerfeld().initialize(10);
        engine.getGegnerfeld().initialize(10);
        engine.wuerfeln();
        ui.setEngine(engine);
        ui.begin();

    }
}
