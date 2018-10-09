package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class NetworkMessageReceive implements Runnable {

    private Socket clientSocket;

    public NetworkMessageReceive(Socket clientSocket) {
        super();
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        String line;
        BufferedReader is;

        try {
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while(true) {
                line = is.readLine();
                if(line == null) {
                    break;
                }
                System.out.println(line);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
