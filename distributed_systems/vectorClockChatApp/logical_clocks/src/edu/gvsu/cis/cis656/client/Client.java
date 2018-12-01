package edu.gvsu.cis.cis656.client;

import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import edu.gvsu.cis.cis656.clock.VectorClock;
import edu.gvsu.cis.cis656.queue.PriorityQueue;
import edu.gvsu.cis.cis656.message.Message;
import edu.gvsu.cis.cis656.message.MessageTypes;
import edu.gvsu.cis.cis656.message.MessageComparator;

public class Client {

	/* Data */
    private static final Integer SERVER_PORT = 8000;

    private InetAddress m_serverIpAddr;
    private String m_username;
	private VectorClock m_clock;
	private Integer m_pid;
    private DatagramSocket m_socket = null;
	private Thread m_listeningThread;
    private Integer m_orderTag = 0;

    /* Public methods */
	public static void main(String[] args) {

	    String username = "";
	    String serverHost = "localhost";

	    if (args.length == 1) {
            username = args[0];
        }
        else if (args.length == 2) {
            username = args[0];
            serverHost = args[1];
        }
        else {
            System.out.println("Not enough args! Usage: <launch> <name> [<server-host>]");
            System.exit(1);
        }

        Client client = new Client(username, serverHost);
        client.run();
        client.close();
	}

	/* Private methods */

    private Client(String username, String serverHost) {

        m_username = username;
        m_pid = 0;

        /* Initialize own clock */
        m_clock = new VectorClock();
        m_clock.addProcess(0, 0);

        /* Get server IP address */
        try {
            m_serverIpAddr = InetAddress.getByName(serverHost);
        }
        catch (UnknownHostException uhe) {
            System.out.println(uhe.getMessage());
            System.exit(1);
        }

        /* Create datagram socket */
        try {
            m_socket = new DatagramSocket();
        }
        catch (SocketException se) {
            System.out.println(se.getMessage());
            System.exit(1);
        }

        /* Register self with server */
        Message register = new Message(MessageTypes.REGISTER, m_username, m_pid, m_clock, "Please register me");
        Message.sendMessage(register, m_socket, m_serverIpAddr, SERVER_PORT);

        /* Wait for ACK */
        Message ack = Message.receiveMessage(m_socket);
        if (MessageTypes.ACK == ack.type) {
            m_pid = ack.pid;
            m_clock.addProcess(ack.pid, 0);
        }
        else {
            System.out.println("Error: " + ack.message);
            System.exit(1);
        }
        System.out.println("You are registered as '" + m_username + "'.");

        /* Create listening thread */
        m_listeningThread = new Thread(new ListeningThread(m_socket));
    }

    private void run() {

        /* Kick off listening thread */
        m_listeningThread.start();

        /* Prompt user for input */
        Scanner reader = new Scanner(System.in);
        boolean more = true;

        /*  Send greeting */
        sendMessage("[" + m_username + " has logged on]");

        System.out.println("\nType to talk. Type ':exit' to exit.\n");

        while (more) {
            String firstWord = reader.next();
            String text = m_username + ": " + firstWord + reader.nextLine();

            if (firstWord.equals(":exit")) {
                more = false;
            }
            else {
                sendMessage(text);
            }
        }
    }

    private void close() {

        /*  Send farewell */
        sendMessage("[" + m_username + " has logged off]");

        /* Close listening thread */
        m_listeningThread.interrupt();

        /* Unregister self with server */
        /* NOTE: the server doesn't seem to have unregistering capabilities */
        Message register = new Message(MessageTypes.REGISTER, m_username, m_pid, m_clock, "Please unregister me");
        Message.sendMessage(register, m_socket, m_serverIpAddr, SERVER_PORT);

        /* Join listening thread */
        try {
            m_listeningThread.join();
        } catch (Exception e) {
            System.err.println("Client exception:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void sendMessage(String str) {
        m_clock.tick(m_pid);
        Message msg = new Message(MessageTypes.CHAT_MSG, m_username, m_pid, m_clock, str);
        Message.sendMessage(msg, m_socket, m_serverIpAddr, SERVER_PORT);
    }

    /* Private classes */
    private class ListeningThread implements Runnable {

        /* Data */
        private DatagramSocket m_recvSocket = null;
        private PriorityQueue<Message> priorityQueue = null;

        /* Public methods */
        private ListeningThread(DatagramSocket socket) {
            m_recvSocket = socket;
            priorityQueue = new PriorityQueue<Message>(new MessageComparator());
        }

        @Override
        public void run() {
            boolean more = true;
            while (more) {
                Message newMessage = Message.receiveMessage(m_recvSocket);

                if (Thread.interrupted()) {
                    more = false;
                } else if (newMessage.type == MessageTypes.ERROR) {
                    System.out.println("Error: " + newMessage.message);
                } else if (newMessage.type == MessageTypes.CHAT_MSG) {
                    newMessage.tag = ++m_orderTag;
                    priorityQueue.add(newMessage);
                    Message topMessage = priorityQueue.peek();
                    while (null != topMessage) {
                        Integer pid = topMessage.pid;
                        VectorClock adjusted = new VectorClock();
                        adjusted.setClock(m_clock);
                        adjusted.tick(pid);
                        //System.out.println("++ My clock: " + m_clock.toString());
                        //System.out.println("+++ Message clock: " + topMessage.ts.toString());
                        if (topMessage.ts.getTime(pid) == (m_clock.getTime(pid) + 1)
                                && topMessage.ts.happenedBefore(adjusted)) {

                            //System.out.println("++++ PASS");
                            m_clock.update(topMessage.ts);
                            System.out.println(topMessage.tag + ":" + topMessage.message);
                            priorityQueue.remove(topMessage);
                            topMessage = priorityQueue.peek();
                        } else {
                            //System.out.println("---- FAIL");
                            topMessage = null;
                        }
                    }
                } else {
                    /* throwaway */
                }
            }
        }

        /* Private methods */
    }
}
