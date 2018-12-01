package client;

import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class NetworkMessageSend implements Runnable {

    /* Data */
    private ZContext context;
    private String addr;
    private String message;

    /* Public methods */
    public NetworkMessageSend(ZContext context, String addr, String message) {
        super();
        this.context = context;
        this.addr = addr;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            ZMQ.Socket reqSocket = context.createSocket(ZMQ.REQ);
            reqSocket.connect(addr);
            reqSocket.send(message.getBytes(ZMQ.CHARSET), ZMQ.NOBLOCK);
            byte[] ack = reqSocket.recv(0);
            context.destroySocket(reqSocket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
