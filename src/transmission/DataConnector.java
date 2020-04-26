package transmission;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DataConnector implements DataConnection, Runnable{

    private ServerSocket serverSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    /**
     * Create client side - open connection to address / port
     *
     * @param address
     */
    public DataConnector(String address, int port) throws IOException {
        Socket socket = new Socket(address, port);
        dataOutputStream= new DataOutputStream(socket.getOutputStream());

    }

    /**
     * Create server side - open port on this port and wait for one client
     *
     * @param port
     */
    public DataConnector(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }

    @Override
    public DataInputStream getDataInputStream() throws IOException {
        return dataInputStream;

    }

    @Override
    public DataOutputStream getDataOutputStream() throws IOException {
        return dataOutputStream;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            dataInputStream= new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

