package schiffeversenken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPStreams implements Streams, Runnable{

    Socket server;
    Socket client;
    DataInputStream dis;
    DataOutputStream dos;


    private int port;

    public void setPort(int port){
        this.port=port;
    };

    public void host() throws IOException {
        ServerSocket serverSocket=new ServerSocket(port);
        server= serverSocket.accept();
        dos=new DataOutputStream(server.getOutputStream());
        dis=new DataInputStream(server.getInputStream());

    }
    public void join(String s, int i) throws IOException {
        Socket client= new Socket(s,i);
        dos= new DataOutputStream(client.getOutputStream());
        dis= new DataInputStream(client.getInputStream());
    }

    @Override
    public DataInputStream getdis() {
        return dis;
    }

    @Override
    public DataOutputStream getdos() {
        return dos;
    }

    @Override
    public void run() {
        try {
            host();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
