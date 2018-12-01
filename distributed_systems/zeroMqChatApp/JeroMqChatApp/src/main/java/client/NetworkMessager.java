package client;

import presence.RegistrationInfo;
import client.NetworkMessageSend;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class NetworkMessager implements Runnable {

    /* Data */
    private String myName;
    private int localPort;
    private String serverHost;
    private int serverPubPort;
    private volatile boolean broadcastSubscribed;
    private ZContext context;

    private static final int SOCKET_TIMEOUT_MS = 50;
    private static final byte[] ACK = "ack".getBytes(ZMQ.CHARSET);
    private static final byte[] B_TOPIC = ZMQ.SUBSCRIPTION_ALL;

    /* Public methods */
    public NetworkMessager(String myName, int localPort, String serverHost, int serverPubPort) {
        this.myName = myName;
        this.localPort = localPort;
        this.serverHost = serverHost;
        this.serverPubPort = serverPubPort;
        broadcastSubscribed = true;
        context = new ZContext();
    }

    @Override
    public void run() {
        // Create REP socket for direct messages
        ZMQ.Socket repSocket = context.createSocket(ZMQ.REP);
        repSocket.bind("tcp://*:" + Integer.toString(localPort));

        // Create SUB socket for broadcasts
        ZMQ.Socket subSocket = context.createSocket(ZMQ.SUB);
        subSocket.connect("tcp://" + serverHost + ":" + Integer.toString(serverPubPort));
        subSocket.subscribe(B_TOPIC);

        // Create Polling object to listen for both at once
        ZMQ.Poller poller = new ZMQ.Poller(2);
        poller.register(repSocket, ZMQ.Poller.POLLIN);
        poller.register(subSocket, ZMQ.Poller.POLLIN);

        // Listen for messages while periodically checking for interruptions
        while(!Thread.interrupted()) {
            int rv = poller.poll(SOCKET_TIMEOUT_MS);

            if (0 < rv) {
                if (poller.pollin(0)) {
                    byte[] msg = repSocket.recv();
                    repSocket.send(ACK, ZMQ.NOBLOCK);
                    String parsedMsg = new String(msg, ZMQ.CHARSET);
                    System.out.println(parsedMsg);
                }
                else {
                    byte[] msg = subSocket.recv();
                    String parsedMsg = new String(msg, ZMQ.CHARSET);
                    if (broadcastSubscribed
                        && !parsedMsg.startsWith(myName + ":")) {
                        System.out.println(parsedMsg);
                    }
                }
            }
        }

        // Cleanup
        context.destroySocket(repSocket);
        context.destroySocket(subSocket);
    }

    public void send(RegistrationInfo user, String message) {
        NetworkMessageSend sender = new NetworkMessageSend(context, getAddrFromUser(user), message);
        Thread thread = new Thread(sender);
        thread.start();
    }

    public void close() {
        context.destroy();
    }

    public void broadcastSubscribe() {
        broadcastSubscribed = true;
    }

    public void broadcastUnsubscribe() {
        broadcastSubscribed = false;
    }

    /* Private methods */
    private String getAddrFromUser(RegistrationInfo user) {
        String host = user.getHost();
        if (host.equals("127.0.0.1")) {
            host = serverHost;
        }
        return "tcp://" + host + ":" + Integer.toString(user.getPort());
    }
}