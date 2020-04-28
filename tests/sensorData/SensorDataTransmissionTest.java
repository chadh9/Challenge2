package sensorData;


import filepersistence.SensorDataStorage;
import filepersistence.SensorDataStorageImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import transmission.DataConnection;
import transmission.DataConnector;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;

public class SensorDataTransmissionTest {
    private static final int PORTNUMBER = 9876;

    @Test
    public void gutTransmissionTest() throws IOException, InterruptedException {
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

        DataConnector receiverConnection = new DataConnector(PORTNUMBER);

        new Thread(receiverConnection).start();

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

        Thread t = new Thread(sensorDataReceiver);
        t.start();


        // send data with TCP
        sensorDataSender.sendData(sensorName, timeStamp, valueSet);
        sensorDataSender.sendData(sensorName, 1, new float[]{1});


        // test if stored
        SensorDataStorage dataStorageReceived = sensorDataReceiver.getStorage();


        t.join();

        List list1 = dataStorageReceived.read(0);
        List list2 = dataStorageReceived.read(1);


        Assertions.assertEquals(timeStamp, list1.get(0));
        Assertions.assertArrayEquals(valueSet, (float[]) list1.get(1));
        Assertions.assertEquals((long) 1, list2.get(0));
        Assertions.assertArrayEquals(new float[]{1}, (float[]) list2.get(1));

    }

    @Test
    public void test2() throws IOException {
        SensorDataStorage sensorDataStorage = new SensorDataStorageImpl("MyGoodOldSensor.txt");
        List list = sensorDataStorage.read(0);

        float[] valueSet = new float[3];
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;
        valueSet[2] = (float) 2.1;

        Assertions.assertArrayEquals(valueSet, (float[]) list.get(1));
    }

    private static final int TEST_INT = 42;

    @Test
    public void gutConnectionTest1() throws IOException {
        // open server side
        DataConnection serverSide = new DataConnector(PORTNUMBER);


        // open client side
        DataConnection clientSide = new DataConnector("localhost", PORTNUMBER);


        Thread t = new Thread((Runnable) serverSide);
        t.start();
        DataOutputStream dataOutputStream = clientSide.getDataOutputStream();
        dataOutputStream.writeInt(TEST_INT);

        DataInputStream dataInputStream = serverSide.getDataInputStream();
        int readValue = dataInputStream.readInt();

        Assertions.assertEquals(TEST_INT, readValue);
    }

    @Test
    public void EOFifnotjoined() throws IOException, InterruptedException {
        String sensorName = "MyGoodOldSensor.txt"; // does not change
        long timeStamp = System.currentTimeMillis();
        float[] valueSet = new float[3];
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;
        valueSet[2] = (float) 2.1;


        SensorDataStorage dataStorage = new SensorDataStorageImpl(sensorName);
        DataConnector receiverConnection = new DataConnector(PORTNUMBER);
        new Thread(receiverConnection).start();
        SensorDataReceiver sensorDataReceiver = new SensorDataReceiver(receiverConnection, dataStorage);

        DataConnection senderConnection = new DataConnector("localhost", PORTNUMBER);
        SensorDataSender sensorDataSender = new SensorDataSender(senderConnection);

        Thread t = new Thread(sensorDataReceiver);
        t.start();


        sensorDataSender.sendData(sensorName, timeStamp, valueSet);


        // test if stored
        SensorDataStorage dataStorageReceived = sensorDataReceiver.getStorage();

        try {
            List list1 = dataStorageReceived.read(0);
            Assertions.fail();
        } catch (EOFException e) {

        }
        catch (NoSuchElementException e){

        }


    }


}
