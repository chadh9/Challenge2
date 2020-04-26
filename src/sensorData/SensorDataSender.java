package sensorData;

import transmission.DataConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SensorDataSender  {

    private final DataConnection connection;

    public SensorDataSender(DataConnection connection) {
        this.connection = connection;
    }

    public void sendData(String name, long time, float[] values) throws IOException {
        DataOutputStream dataOutputStream = connection.getDataOutputStream();
        dataOutputStream.writeUTF(name);
        dataOutputStream.writeLong(time);
        dataOutputStream.writeInt(values.length);
        for (float f:values) {
            dataOutputStream.writeFloat(f);
        }
    }
}
