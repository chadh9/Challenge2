package schiffeversenken.austausch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schiffeversenken.protocolBinding.Kommando;
import schiffeversenken.protocolBinding.StreamBindingReceiver;
import schiffeversenken.protocolBinding.StreamBindingSender;
import schiffeversenken.spielbrett.EigenesFeld;
import schiffeversenken.spielbrett.GegnerFeld;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class Integration {
    StreamBindingSender streamBindingSender;
    StreamBindingReceiver streamBindingReceiver;
    SchiffeVersenkenEngine schiffeVersenkenEngine;
    DataOutputStream dos;
    DataOutputStream enemydos;
    DataInputStream dis;
    Thread t;
    /*
    @BeforeEach
    void init() throws FileNotFoundException {
        schiffeVersenkenEngine = new SchiffeVersenkenEngine();
        schiffeVersenkenEngine.setSpielerfeld(new EigenesFeld());
        schiffeVersenkenEngine.setGegnerfeld(new GegnerFeld());

        schiffeVersenkenEngine.getSpielerfeld().initialize(10);
        schiffeVersenkenEngine.getGegnerfeld().initialize(10);

        dos=new DataOutputStream(new FileOutputStream("test2.txt"));

        enemydos = new DataOutputStream(new FileOutputStream("test3.txt"));

        dis=new DataInputStream(new FileInputStream("test3.txt"));
        schiffeVersenkenEngine.setDos(dos);

        streamBindingSender= new StreamBindingSender(dos,schiffeVersenkenEngine);
        streamBindingReceiver=new StreamBindingReceiver(dis,schiffeVersenkenEngine);
        t=new Thread(streamBindingReceiver);
        t.start();
    }



    @Test
    void sendData() throws IOException {


        streamBindingSender.reihenfolgeWuerfeln(2);


        enemydos.writeInt(Kommando.REIHENFOLGEWUERFELN);
        enemydos.writeInt(1);


        enemydos.writeInt(Kommando.KOORDINATE);
        enemydos.writeInt(2);
        enemydos.writeInt(3);


        //Assertions.assertEquals(schiffeVersenkenEngine.getStatus(),SchiffeVersenkenStatus.VERSENKEN_SENDEN);




        enemydos.close();

        Assertions.assertEquals(schiffeVersenkenEngine.getStatus(),SchiffeVersenkenStatus.BESTAETIGEN_EMPFANGEN);


    }
*/

}
