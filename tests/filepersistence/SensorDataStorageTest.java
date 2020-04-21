package filepersistence;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

class SensorDataStorageTest {

    SensorDataStorage storage;

    @BeforeEach
    void start() throws FileNotFoundException {
        storage = new SensorDataStorageImpl("initTest.txt");
    }


    @Test
    void saveData_success1() throws IOException {
        storage.saveData(1, new float[]{(float) 1.0, (float) 2.0});
        Assertions.assertNotEquals(0, new File("initTest.txt").length());
    }

    @Test
    void saveData_success2() throws IOException {
        storage.saveData(1, new float[]{(float) 1.0, (float) 2.0});
        List list = storage.read(0);

        Assertions.assertNotEquals(0, new File("initTest.txt").length());
        Assertions.assertEquals(list.get(0), (long) 1);
        Assertions.assertArrayEquals((float[]) list.get(1), new float[]{(float) 1.0, (float) 2.0});
    }

    @Test
    void saveData_MaxLong() throws IOException {
        storage.saveData(Long.MAX_VALUE, new float[]{(float) 1.0, (float) 2.0});

        Assertions.assertNotEquals(0, new File("initTest.txt").length());

        List list = storage.read(0);

        Assertions.assertEquals(list.get(0), Long.MAX_VALUE);
        Assertions.assertArrayEquals((float[]) list.get(1), new float[]{(float) 1.0, (float) 2.0});
    }

    @Test
    void saveData_MinLong() throws IOException {
        storage.saveData(Long.MIN_VALUE, new float[]{(float) 1.0, (float) 2.0});

        Assertions.assertNotEquals(0, new File("initTest.txt").length());

        List list = storage.read(0);

        Assertions.assertEquals(list.get(0), Long.MIN_VALUE);
        Assertions.assertArrayEquals((float[]) list.get(1), new float[]{(float) 1.0, (float) 2.0});
    }

    @Test
    void saveData_fail_stream_closed() throws IOException {

        storage=new SensorDataStorageImpl("readonly.txt");
        storage.close();
        File file = new File("readonly.txt");


        try {
            storage.saveData(1, new float[]{(0)});
            Assertions.fail();
        }
        catch (IOException ex){}
        finally {
            file.delete();
        }
    }

    @Test
    void constructor_FileNotFound_Exception_when_readonly() throws IOException {

        storage=new SensorDataStorageImpl("readonly.txt");
        storage.close();

        File file = new File("readonly.txt");
        file.setReadOnly();

        try {
            storage=new SensorDataStorageImpl("readonly.txt");
            Assertions.fail();
        }
        catch (FileNotFoundException ex){}
        finally {
            file.delete();
        }
    }

    @Test
    void read_success() throws IOException {
        storage.saveData(1, new float[]{(float) 1.1});
        storage.saveData(2, new float[]{(float) 2.2});

        List set1 = storage.read(0);
        Assertions.assertEquals(set1.get(0), (long) 1);
        Assertions.assertArrayEquals((float[]) set1.get(1), new float[]{(float) 1.1});


        List set2 = storage.read(1);
        Assertions.assertEquals(set2.get(0), (long) 2);
        Assertions.assertArrayEquals((float[]) set2.get(1), new float[]{(float) 2.2});

    }

    @Test
    void read_fail_deletedfile() throws IOException {
        storage = new SensorDataStorageImpl("deletedtext.txt");
        storage.saveData(1,new float[]{(1)});
        File file = new File("deletedtext.txt");
        storage.close();
        file.delete();
        try {
            storage.read(0);
            Assertions.fail();
        } catch (IOException e) {
        }
    }


    @Test
    void read_failNotExistingElement() throws IOException {
        try {
            storage.read(1);
            Assertions.fail();
        }
        catch (NoSuchElementException e){
        }
    }

    @Test
    void size_success() throws IOException {
        storage.saveData(1, new float[]{(float) 1.1});
        Assertions.assertEquals(1, storage.size());
    }

    @Test
    void size_success_multiple_elements() throws IOException {
        storage.saveData(1, new float[]{(float) 1.1});
        storage.saveData(1, new float[]{(float) 1.1});
        Assertions.assertEquals(2, storage.size());
    }

    @Test
    void size_success_no_elements() throws IOException {
        Assertions.assertEquals(0, storage.size());
    }


    @Test
    void isEmpty_returnsTrueSuccess() throws IOException {
        Assertions.assertTrue(storage.isEmpty());
    }

    @Test
    void isEmpty_returnsFalseSuccess() throws IOException {
        storage.saveData(1, new float[]{(float) 1.0});
        Assertions.assertFalse(storage.isEmpty());
    }

    @Test
    void clear_success() throws IOException {
        storage.saveData(1,new float[]{0});
        storage.clear();
        Assertions.assertTrue(storage.isEmpty());
    }

    @Test
    void close_success() throws IOException {
        storage.close();
        try {
            storage.saveData(1,new float[]{1});
            Assertions.fail();
        } catch (IOException e) {

        }
    }
}