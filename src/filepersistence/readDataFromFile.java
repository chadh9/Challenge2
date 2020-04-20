package filepersistence;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class readDataFromFile {


    public void read(String filename) {
        try {
            InputStream is = new FileInputStream(filename);

            DataInputStream dataInputStream = new DataInputStream(is);
            System.out.println("Sensorname:");
            System.out.println(dataInputStream.readUTF());

            while (dataInputStream.available() > 0) {
                System.out.println("\n\nDate: \n" + dataInputStream.readLong());
                int setsize = dataInputStream.readInt();
                System.out.println("Measures: ");
                for (int i = 0; i < setsize; i++) {

                    System.out.println(dataInputStream.readFloat());
                }
            }

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
