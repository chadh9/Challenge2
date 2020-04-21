package filepersistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface SensorDataStorage {

    void setFile(String fileName);

    void saveData(long time, float[] values) throws IOException;

    List read(int index) throws IOException;

    int size() throws IOException;

    boolean isEmpty() throws IOException;

    void close() throws IOException;
}
