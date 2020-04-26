package sensorData;


import filepersistence.SensorDataStorage;
import filepersistence.SensorDataStorageImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transmission.DataConnection;
import transmission.DataConnector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class SensorDataTransmissionTest {
    private static final int PORTNUMBER = 9876;

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

        Runnable receiverConnection = new DataConnector(PORTNUMBER);
        new Thread(receiverConnection).start();


        // create receiver
        SensorDataReceiver sensorDataReceiver = new SensorDataReceiver((DataConnection) receiverConnection, dataStorage);

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

        new Thread(sensorDataReceiver).start();


        // send data with TCP
        sensorDataSender.sendData(sensorName, timeStamp, valueSet);
        sensorDataSender.sendData(sensorName, timeStamp, valueSet);

        // test if stored
        SensorDataStorage dataStorageReceived = sensorDataReceiver.getStorage();

        // TODO - get data and test

        // just dummy values


        List list = dataStorageReceived.read(0);
    //    List list1 = dataStorageReceived.read(1);

        Assertions.assertEquals(timeStamp, list.get(0));
        Assertions.assertArrayEquals(valueSet, (float[]) list.get(1));
      //  Assertions.assertEquals((long)1, list1.get(0));
        //Assertions.assertArrayEquals(new float[]{1}, (float[]) list1.get(1));

        // TODO: test values*/
    }

}
