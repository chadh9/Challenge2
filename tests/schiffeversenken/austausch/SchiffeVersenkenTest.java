package schiffeversenken.austausch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SchiffeVersenkenTest {

    @Test
    void usageTest() throws IOException {
        Shortcut sender1= new Shortcut();
        Shortcut sender2= new Shortcut();

        SchiffeVersenkenEngine game1= new SchiffeVersenkenEngine(sender1);
        SchiffeVersenkenEngine game2= new SchiffeVersenkenEngine(sender2);

        sender1.setReceiver(game2);
        sender2.setReceiver(game1);

        game1.wuerfeln();
        game2.wuerfeln();


        System.out.println(game1.getStatus());
        System.out.println(game2.getStatus());

        Assertions.assertNotEquals(game1.getStatus(),game2.getStatus());

         Usage active = (game1.getStatus() == SchiffeVersenkenStatus.VERSENKEN_SENDEN)? game1 : game2;
         Usage passive = (game1.getStatus()== SchiffeVersenkenStatus.VERSENKEN_EMPFANGEN)? game1:game2;



         active.getSpielerfeld().initialize(10);
         active.getGegnerfeld().initialize(10);
         passive.getSpielerfeld().initialize(10);
         passive.getGegnerfeld().initialize(10);



         active.versenken(1,1);


        Assertions.assertSame(active.getStatus(), SchiffeVersenkenStatus.BESTAETIGEN_EMPFANGEN);


         passive.bestaetigen();

        Assertions.assertSame(passive.getStatus(), SchiffeVersenkenStatus.VERSENKEN_SENDEN);



        passive.versenken(1,1);
        active.bestaetigen();

        Assertions.assertSame(active.getStatus(), SchiffeVersenkenStatus.VERSENKEN_SENDEN);
        active.kapitulation();
        Assertions.assertSame(active.getStatus(), SchiffeVersenkenStatus.BEENDEN);
        Assertions.assertSame(passive.getStatus(), SchiffeVersenkenStatus.BEENDEN);
    }


}
