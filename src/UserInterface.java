import schiffeversenken.austausch.SchiffeVersenkenEngine;
import schiffeversenken.austausch.SchiffeVersenkenSenden;
import schiffeversenken.austausch.SchiffeVersenkenStatus;
import schiffeversenken.protocolBinding.StreamBindingSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class UserInterface {

    SchiffeVersenkenEngine engine;


    public void setEngine(SchiffeVersenkenEngine engine) {
        this.engine = engine;
    }

    public void begin() throws IOException {

        /*
        SchiffeVersenkenSenden sender= new StreamBindingSender();
        SchiffeVersenkenEngine engine = new SchiffeVersenkenEngine();
*/


        BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));

        while(true){

            if(engine.getStatus()== SchiffeVersenkenStatus.VERSENKEN_SENDEN) {
                System.out.println("Sende Koordinaten:");
                String s = reader.readLine();
                String[] commands = s.split(" ");
                if (commands.length == 1
                        && commands[0].equals("gg")) {
                    engine.kapitulation();
                } else if (commands.length == 2) {
                    try {
                        int x = Integer.parseInt(commands[0]);
                        int y = Integer.parseInt(commands[1]);
                        engine.versenken(x, y);
                    } catch (NumberFormatException e) {
                        System.out.println("Ungültig");
                    }
                }
            }
            else if(engine.getStatus()==SchiffeVersenkenStatus.BESTAETIGEN_SENDEN) {
                System.out.println("Sende Bestaetigung...");
                engine.bestaetigen();
            }

            else if(engine.getStatus()==SchiffeVersenkenStatus.BEENDEN){
                System.exit(0);
            }
        }
    }

}
