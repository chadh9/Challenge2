package filepersistence;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class SensorDataStorageImpl implements SensorDataStorage {

    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private InputStream inputStream;
    private DataInputStream dataInputStream;


    public SensorDataStorageImpl(String s) throws FileNotFoundException {
        outputStream = new FileOutputStream(s);
        inputStream = new FileInputStream(s);
    }

    @Override
    public void setFile(String fileName) throws FileNotFoundException {
        outputStream = new FileOutputStream(fileName);
        inputStream = new FileInputStream(fileName);
    }

    @Override
    public void saveData(long time, float[] values) {


        dataOutputStream = new DataOutputStream(outputStream);

        try {
            dataOutputStream.writeLong(time);
            dataOutputStream.writeInt(values.length);
            for (float value : values) {
                dataOutputStream.writeFloat(value);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public List read(int index) throws IOException {

        long time = 0;
        float[] values;

        dataInputStream = new DataInputStream(inputStream);


        time = dataInputStream.readLong();
        int count = dataInputStream.readInt();
        values = new float[count];
        for (int j = 0; j < count; j++) {
            values[j] = dataInputStream.readFloat();
        }

        return Arrays.asList(time, values);


    }

    @Override
    public int size() throws IOException {
        int i = 0;
        dataInputStream = new DataInputStream(inputStream);
        while (dataInputStream.available() > 0) {
            dataInputStream.readLong();
            int count = dataInputStream.readInt();
            for (int j = 0; j < count; j++) {
                dataInputStream.readFloat();
            }
            i++;
        }
        return i;
    }

    @Override
    public boolean isEmpty() throws IOException {
        dataInputStream = new DataInputStream(inputStream);
        if (dataInputStream.available() > 0) {
            return false;
        } else return true;
    }
}
