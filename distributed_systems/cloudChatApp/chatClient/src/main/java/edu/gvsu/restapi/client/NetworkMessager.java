package edu.gvsu.restapi.client;

import edu.gvsu.restapi.client.RegistrationInfo;
import edu.gvsu.restapi.client.NetworkMessageReceive;
import edu.gvsu.restapi.client.NetworkMessageSend;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class NetworkMessager implements Runnable {

    private int port;
    private volatile boolean keepRunning;
    private Semaphore controlSem;
    private ServerSocket listenServer = null;

    private static final int SOCKET_TIMEOUT_MS = 500;

    public NetworkMessager() {
        keepRunning = true;
        controlSem = new Semaphore(1);
        try {
            listenServer = new ServerSocket(0);
        } catch (IOException e) {
            System.out.println("Error: Couldn't allocate local socket endpoint.");
            System.exit(-1);
        }
    }

    public Integer getListeningPort() {
        return listenServer.getLocalPort();
    }

    @Override
    public void run() {
        try {
            controlSem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        Socket clientSocket = null;

        try {
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