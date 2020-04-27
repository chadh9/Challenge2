package sensorData;


import filepersistence.SensorDataStorage;
import filepersistence.SensorDataStorageImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transmission.DataConnection;
import transmission.DataConnector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class SensorDataTransmissionTest {
    private static final int PORTNUMBER = 9876;

    private static final int TEST_INT = 42;

    @Test
    public void gutConnectionTest1() throws IOException {
        // open server side
        DataConnection serverSide = new DataConnector(PORTNUMBER);

        // open client side
        DataConnection clientSide = new DataConnector("localhost", PORTNUMBER);

        DataOutputStream dataOutputStream = clientSide.getDataOutputStream();
        dataOutputStream.writeInt(TEST_INT);

        DataInputStream dataInputStream = serverSide.getDataInputStream();
        int readValue = dataInputStream.readInt();

        Assertions.assertEquals(TEST_INT, readValue);
    }

    @Test
    public void gutTransmissionTest() throws IOException {
        // create example data set
        String sensorName = "MyGoodOldSensor.txt"; // does not change
        long timeStamp = System.currentTimeMillis();
        float[] valueSet = new float[3];
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;
        valueSet[2] = (float) 2.1;





        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        //                                              receiver side                                        //
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        // create storage
        // TODO: create object that implements SensorDataStorage

        SensorDataStorage dataStorage = new SensorDataStorageImpl(sensorName);

        // create connections

        DataConnection receiverConnection = new DataConnector(PORTNUMBER);

        // create receiver
        SensorDataReceiver sensorDataReceiver = new SensorDataReceiver(receiverConnection, dataStorage);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        //                                              sender side                                          //
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        // create connections
        DataConnection senderConnection = new DataConnector("localhost", PORTNUMBER);

        // create sender
        SensorDataSender sensorDataSender = new SensorDataSender(senderConnection);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        //                               execute communication and test                                      //
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        receiverConnection.connect();

        // send data with TCP
        sensorDataSender.sendData(sensorName, timeStamp, valueSet);

        // test if stored
        SensorDataStorage dataStorageReceived = sensorDataReceiver.getStorage();

        sensorDataReceiver.fetch();
        // TODO - get data and test

        // just dummy values


        List list = dataStorageReceived.read(0);

        Assertions.assertEquals(timeStamp, list.get(0));
        Assertions.assertArrayEquals(valueSet, (float[]) list.get(1));

        // TODO: test values*/
    }

}
