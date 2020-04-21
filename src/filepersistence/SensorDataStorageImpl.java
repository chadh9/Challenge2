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

    private String fileName;

    public SensorDataStorageImpl(String fileName) throws FileNotFoundException {
        outputStream = new FileOutputStream(fileName);
        this.fileName = fileName;
    }

    @Override
    public void setFile(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void saveData(long time, float[] values) throws IOException {

        dataOutputStream = new DataOutputStream(outputStream);

        dataOutputStream.writeLong(time);
        dataOutputStream.writeInt(values.length);
        for (float value : values) {
            dataOutputStream.writeFloat(value);
        }


    }


    @Override
    public List read(int index) throws IOException {

        long time;
        float[] values;

        inputStream = new FileInputStream(fileName);
        dataInputStream = new DataInputStream(inputStream);

        int counter = 0;


        while (dataInputStream.available() > 0) {

            time = dataInputStream.readLong();
            int count = dataInputStream.readInt();
            values = new float[count];

            for (int j = 0; j < count; j++) {
                values[j] = dataInputStream.readFloat();
            }


            if (index == counter) {
                inputStream.close();
                return Arrays.asList(time, values);
            }

            counter++;

        }
        inputStream.close();
        throw new NoSuchElementException();

    }

    @Override
    public int size() throws IOException {
        int i = 0;

        inputStream=new FileInputStream(fileName);
        dataInputStream = new DataInputStream(inputStream);
        while (dataInputStream.available() > 0) {
            dataInputStream.readLong();
            int count = dataInputStream.readInt();
            for (int j = 0; j < count; j++) {
                dataInputStream.readFloat();
            }
            i++;
        }
        inputStream.close();
        return i;
    }

    @Override
    public boolean isEmpty() throws IOException {
        inputStream= new FileInputStream(fileName);
        dataInputStream = new DataInputStream(inputStream);
        if (dataInputStream.available() > 0) {
            return false;
        } else return true;
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }
}
