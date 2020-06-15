package schiffeversenken.austausch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import schiffeversenken.spielbrett.EigenesFeld;
import schiffeversenken.spielbrett.FeldStatus;
import schiffeversenken.spielbrett.GegnerFeld;

import java.io.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SchiffeVersenkenEngineTest {

    SchiffeVersenkenEngine sender;
    DataInputStream dis;
    int size =10;

    @BeforeEach
    void init() throws FileNotFoundException {
        sender = new SchiffeVersenkenEngine();
        sender.setSpielerfeld(new EigenesFeld());
        sender.setGegnerfeld(new GegnerFeld());
        sender.setDos(new DataOutputStream(new FileOutputStream("test.txt")));
        dis = new DataInputStream(new FileInputStream("test.txt"));
    }

    @Test
    void reihenfolgeWuerfeln_nicht_spielstart() throws IOException {
        try {
            sender.reihenfolgeWuerfeln(1);
            fail();
        } catch (StatusException e) {
        }
    }

    @Test
    void reihenfolgeWuerfeln() throws IOException {
        sender.setStatus(SchiffeVersenkenStatus.SPIELSTART);
        sender.reihenfolgeWuerfeln(1);
        assertEquals(dis.readInt(), 1);
    }

    @Test
    void wuerfelEmpfangen_verloren() throws StatusException {
        sender.setStatus(SchiffeVersenkenStatus.SPIELSTART);
        sender.reihenfolgeWuerfeln(1);
        sender.wuerfelEmpfangen(2);
        assertEquals(sender.getStatus(), SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN);
    }

    @Test
    void wuerfelEmpfangen_sieg() throws StatusException {
        sender.setStatus(SchiffeVersenkenStatus.SPIELSTART);
        sender.reihenfolgeWuerfeln(1);
        sender.wuerfelEmpfangen(0);
        assertEquals(sender.getStatus(), SchiffeVersenkenStatus.VERSENKEN_SENDEN);
    }

    @Test
    void wuerfelEmpfangen_unentschieden() throws StatusException {
        sender.setStatus(SchiffeVersenkenStatus.SPIELSTART);
        sender.reihenfolgeWuerfeln(1);
        sender.wuerfelEmpfangen(1);
        assertEquals(sender.getStatus(), SchiffeVersenkenStatus.SPIELSTART);
    }

    @Test
    void empfangeKoordinate_treffer() throws StatusException {
        sender.setStatus(SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN);


        sender.getSpielerfeld().getFeld()[1][1] = FeldStatus.SCHLACHTSCHIFF;
        sender.empfangeKoordinate(1, 1);

        assertEquals(sender.getSpielerfeld().getFeld()[1][1], FeldStatus.VERSENKT);
        assertEquals(sender.getStatus(), SchiffeVersenkenStatus.BESTAETIGEN_SENDEN);

    }

    @Test
    void empfangeKoordinate_daneben() throws StatusException {
        sender.setStatus(SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN);

        sender.getSpielerfeld().getFeld()[1][1] = FeldStatus.SCHLACHTSCHIFF;
        sender.empfangeKoordinate(1, 2);

        assertEquals(sender.getStatus(), SchiffeVersenkenStatus.BESTAETIGEN_SENDEN);
    }

    @Test
    void empfangeKapitulation() throws StatusException {
        sender.setStatus(SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN);
        sender.empfangeKapitulation();
        assertEquals(sender.getStatus(), SchiffeVersenkenStatus.BEENDEN);
    }

    @Test
    void empfangeBestaetigen_treffer() throws StatusException {
       sender.setStatus(SchiffeVersenkenStatus.BESTAETIGEN_EMPFANGEN);
        sender.empfangeBestaetigen(0);
        assertEquals(sender.getStatus(),SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN);
    }
    @Test
    void empfangeBestaetigen_verfehlt() throws StatusException {
        sender.setStatus(SchiffeVersenkenStatus.BESTAETIGEN_EMPFANGEN);
        sender.empfangeBestaetigen(1);
        assertEquals(sender.getStatus(),SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN);
    }
    @Test
    void empfangeBestaetigen_versenkt() throws StatusException {
        sender.setStatus(SchiffeVersenkenStatus.BESTAETIGEN_EMPFANGEN);
        sender.empfangeBestaetigen(2);
        assertEquals(sender.getStatus(),SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN);
    }
    @Test
    void empfangeBestaetigen_versenkt_sieg() throws StatusException {
        sender.setStatus(SchiffeVersenkenStatus.BESTAETIGEN_EMPFANGEN);

        FeldStatus[][] feld = new FeldStatus[size][size];

        Arrays.fill(feld[0],FeldStatus.SCHIFF);
        Arrays.fill(feld[1],FeldStatus.SCHIFF);
        Arrays.fill(feld[2],FeldStatus.SCHIFF);

        sender.getGegnerfeld().setFeld(feld);

        sender.empfangeBestaetigen(2);

        assertEquals(sender.getStatus(),SchiffeVersenkenStatus.BEENDEN);
    }
    @Test
    void sendeKoordinate() throws IOException {
        sender.setStatus(SchiffeVersenkenStatus.VERSENKEN_SENDEN);
        sender.sendeKoordinate(1, 1);
        assertEquals(dis.readInt(), 1);
        assertEquals(dis.readInt(), 1);
    }

    @Test
    void sendeKapitulation() throws StatusException {
        sender.setStatus(SchiffeVersenkenStatus.VERSENKEN_SENDEN);
        sender.sendeKapitulation();
        assertEquals(sender.getStatus(), SchiffeVersenkenStatus.BEENDEN);

    }

    @Test
    void sendeBestaetigen() throws IOException {
        sender.setStatus(SchiffeVersenkenStatus.BESTAETIGEN_SENDEN);
        sender.sendeBestaetigen();
        assertEquals(sender.getStatus(), SchiffeVersenkenStatus.VERSENKEN_SENDEN);
    }
}