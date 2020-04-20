package filepersistence;

import java.io.*;

public class registerData {


    private OutputStream os;
    private DataOutputStream dataOutputStream;

    public registerData(String filename) throws FileNotFoundException {
        this.os = new FileOutputStream(filename);
        this.dataOutputStream = new DataOutputStream(os);
    }

    public void registerSensor(String sensorname) throws IOException {
        dataOutputStream.writeUTF(sensorname);
    }

    public void registerValues(float[] values) {

        try {
            dataOutputStream.writeLong(System.currentTimeMillis());
            dataOutputStream.writeInt(values.length);

            for (float value : values) {
                dataOutputStream.writeFloat(value);
            }

        } catch (IOException ex) {
            ex.printStackTrace();


        }
    }
    public void close() throws IOException {
        os.close();
    }
}