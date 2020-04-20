package filepersistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface SensorDataStorage {

    void setFile(String fileName) throws FileNotFoundException;

    void saveData(long time, float[] values);

    List read(int index) throws IOException;

    int size() throws IOException;

    boolean isEmpty() throws IOException;


}
