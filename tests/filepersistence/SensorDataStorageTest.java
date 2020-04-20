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

class SensorDataStorageTest {

    SensorDataStorage storage;

    @BeforeEach
    void start() throws FileNotFoundException {
        storage = new SensorDataStorageImpl("initTest.txt");
    }


    @Test
    void setFile_success() throws FileNotFoundException {
        storage.setFile("test.txt");
        File file = new File("test.txt");

        Assertions.assertTrue(file.exists());
    }

    @Test
    void setFile_success2() throws IOException {
        storage.setFile("test.txt");
        File file = new File("test.txt");
        storage.saveData(1, new float[]{1});
        List list = storage.read(0);
        Assertions.assertTrue(file.exists());
        Assertions.assertEquals((long) 1, list.get(0));
    }

    @Test
    void setFile_fail_readonlyfile() throws IOException {
        storage.setFile("readonlyfile.txt");
        File file = new File("readonlyfile.txt");
        file.setReadOnly();

        try {
            storage.setFile("readonlyfile.txt");
            Assertions.fail();
        } catch (FileNotFoundException e) {
            //test success
        }
        file.delete();
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
    void read_success() throws IOException {
        storage.saveData(1, new float[]{(float) 1.1});
        storage.saveData(1, new float[]{(float) 1.1});

        List list = storage.read(1);
        Assertions.assertEquals(list.get(0), (long) 1);
        Assertions.assertArrayEquals((float[]) list.get(1), new float[]{(float) 1.1});
    }

    @Test
    void read_fail_deletedfile() throws IOException {
        storage.setFile("deletedtext.txt");
        storage.saveData(1,new float[]{(1)});
        File file = new File("deletedtext.txt");
        storage.close();


        try {
            List list = storage.read(0);
            Assertions.fail();
        } catch (IOException e) {
            file.delete();
        }
    }

    @Test
    void size_success() throws IOException {
        storage.saveData(1, new float[]{(float) 1.1});
        Assertions.assertEquals(1, storage.size());
    }

    @Test
    void size_success2() throws IOException {
        storage.saveData(1, new float[]{(float) 1.1});
        storage.saveData(1, new float[]{(float) 1.1});
        Assertions.assertEquals(2, storage.size());
    }

    @Test
    void size_success3() throws IOException {
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
    void saveData_fail__close_success() throws IOException {
        storage.close();
        try {
            storage.saveData(1,new float[]{});
            Assertions.fail();
        }
        catch (IOException e){
        }
    }
}