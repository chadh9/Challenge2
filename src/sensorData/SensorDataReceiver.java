package sensorData;

import filepersistence.SensorDataStorage;
import filepersistence.SensorDataStorageImpl;
import transmission.DataConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class SensorDataReceiver implements Runnable {
    private final DataConnection connection;
    private final SensorDataStorage storage;
    //private final StreamMachine storage;

    public SensorDataReceiver(DataConnection connection, SensorDataStorage storage) {
        this.connection = connection;
        this.storage = storage;
    }

    SensorDataStorage getStorage() {
        return storage;
    }

    private void write() {
        try {
            DataInputStream dataInputStream = connection.getDataInputStream();

                while (dataInputStream.available() > 0) {

                    String name = dataInputStream.readUTF();
                    long date = dataInputStream.readLong();
                    int count = dataInputStream.readInt();
                    float[] values = new float[count];

                    for (int i = 0; i < count; i++) {
                        values[i] = dataInputStream.readFloat();
                    }
                    storage.saveData(date, values);
                    System.out.println("success");
                }


        } catch (IOException e) {


        }
    }

    public void run() {
        write();
    }
}
