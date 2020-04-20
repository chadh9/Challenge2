package filepersistence;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WriteAndReadDataSet {

    public static void main(String[] args) throws IOException {
        // three example data sets
        String sensorName = "MyGoodOldSensor"; // does not change

        long timeStamps[] = new long[3];
        timeStamps[0] = System.currentTimeMillis();
        timeStamps[1] = timeStamps[0] + 1; // milli sec later
        timeStamps[2] = timeStamps[1] + 1000; // second later

        float[][] values = new float[3][];
        // 1st measure .. just one value
        float[] valueSet = new float[1];
        values[0] = valueSet;
        valueSet[0] = (float) 1.5; // example value 1.5 degrees

        // 2nd measure .. just three values
        valueSet = new float[3];
        values[1] = valueSet;
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;
        valueSet[2] = (float) 2.1;

        // 3rd measure .. two values
        valueSet = new float[2];
        values[2] = valueSet;
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;



        //////////////////////////////////////////////////////////////////////////////////////////////////////////////



        String filename = "valuesets.txt";


        registerData registerData = new registerData(filename);


        registerData.registerSensor(sensorName);
        registerData.registerValues(values[0]);
        registerData.registerValues(values[1]);
        registerData.registerValues(values[2]);

        registerData.registerValues(new float[]{(float)1.2, (float) 1.1,(float) 1.1,(float) 1.1,(float) 1.1,(float) 1.1,(float) 1.1});

        registerData.close();


        readDataFromFile readDataFromFile = new readDataFromFile();
        readDataFromFile.read(filename);




    }
}
