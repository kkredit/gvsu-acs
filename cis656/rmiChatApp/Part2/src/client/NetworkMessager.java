package client;

import presence.RegistrationInfo;
import client.NetworkMessageReceive;
import client.NetworkMessageSend;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class NetworkMessager implements Runnable {

    private int port;
    private String serverHost;
    private volatile boolean keepRunning;
    private Semaphore controlSem;

    private static final int SOCKET_TIMEOUT_MS = 500;

    public NetworkMessager(int port, String serverHost) {
        this.port = port;
        this.serverHost = serverHost;
        keepRunning = true;
        controlSem = new Semaphore(1);
    }

    @Override
    public void run() {
        try {
            controlSem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        ServerSocket listenServer = null;
        Socket clientSocket = null;

        try {
            listenServer = new ServerSocket(port);
            listenServer.setSoTimeout(SOCKET_TIMEOUT_MS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(true) {
            try {
                clientSocket = listenServer.accept();
                Thread thread = new Thread(new NetworkMessageReceive(clientSocket));
                thread.start();
            } catch (SocketTimeoutException e) {
                if (!keepRunning) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        controlSem.release();
    }

    public void send(RegistrationInfo user, String message) {
        Socket clientSocket = null;

        try {
            String host = user.getHost();
            if (host.equals("127.0.0.1")) {
                host = serverHost;
            }
            clientSocket = new Socket(host, user.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Thread thread = new Thread(new NetworkMessageSend(clientSocket, message));
        thread.start();
    }

    public void close() {
        keepRunning = false;
        try {
            controlSem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}