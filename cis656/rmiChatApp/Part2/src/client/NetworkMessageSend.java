package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkMessageSend implements Runnable {

    private Socket clientSocket;
    private String message;

    public NetworkMessageSend(Socket clientSocket, String message) {
        super();
        this.clientSocket = clientSocket;
        this.message = message;
    }

    @Override
    public void run() {
        String line;
        PrintStream os;

        try {
            os = new PrintStream(clientSocket.getOutputStream());
            os.println(message);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
