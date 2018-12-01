package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import presence.PresenceService;
import presence.RegistrationInfo;
import java.util.Scanner;
import java.util.Vector;

public class ChatClient {

    /* Data */
    private static PresenceService server;
    private static RegistrationInfo myInfo;
    private static NetworkMessager messenger;

    private static final boolean AVAILABLE = true;
    private static final boolean BUSY = false;
    private static final String DEF_HOST = "localhost";
    private static final int DEF_PORT = 1099;

    /* Public methods */
    public static void main(String[] args) {

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // Register remote compute engine
            String name = "PresenceService";
            String serverHost = getHost(args);
            int serverPort = getPort(args);
            Registry registry = LocateRegistry.getRegistry(serverHost, serverPort);
            server = (PresenceService) registry.lookup(name);

            // Initialize self, register with server
            myInfo = new RegistrationInfo(args[0], "", 0, BUSY);

            if (!server.register(myInfo)) {
                System.out.println("User with name \"" + args[0] + "\" already exists. Exiting.");
            }
            else {
                // Update myInfo since the server assigns the client a port
                myInfo = server.lookup(myInfo.getUserName());

                // Start messaging thread, set self as available, and listen to input
                messenger = new NetworkMessager(myInfo.getUserName(), myInfo.getPort(), serverHost, serverPort + 1);
                Thread messageThread = new Thread(messenger);
                messageThread.start();
                myInfo.setStatus(AVAILABLE);
                server.updateRegistrationInfo(myInfo);

                controlLoop();

                messageThread.interrupt();
                messageThread.join();
                messenger.close();
            }

        } catch (Exception e) {
            System.err.println("ChatClient exception:");
            e.printStackTrace();
        }
    }

    /* Private methods */
    private static String getHost(String[] args) {
        String host = DEF_HOST;

        if (2 == args.length) {
            host = args[1].split(":")[0];
        }

        return host;
    }

    private static int getPort(String[] args) {
        int port = DEF_PORT;

        if (2 == args.length) {
            String[] hostPort = args[1].split(":");
            if (2 == hostPort.length) {
                port = Integer.parseInt(hostPort[1]);
            }
        }

        return port;
    }

    private static void controlLoop() throws RemoteException {
        Scanner reader = new Scanner(System.in);
        boolean more = true;
        sendBroadcast("[online]");
        printUsage();

        while (more) {
            System.out.println("\nEnter a command: ");
            String command = reader.nextLine();
            String commandBase = getFirstWord(command);
            String commandRemainder = getAfterFirstWord(command);
            switch (commandBase) {
                case "friends":
                    getFriends();
                    break;
                case "talk":
                    sendSingleMessage(commandRemainder);
                    break;
                case "broadcast":
                    sendBroadcast(commandRemainder);
                    break;
                case "busy":
                    setSelfBusy();
                    break;
                case "available":
                    setSelfAvailable();
                    break;
                case "exit":
                    sendBroadcast("[going offline]");
                    prepareForExit();
                    more = false;
                    break;
                default:
                    printUsage();
                    break;
            }
        }

        reader.close();
    }

    private static void printUsage() {
        System.out.println("Usage:\n"+
                           "\tEnter \"friends\" to list all registered users\n"+
                           "\tEnter \"talk {username} {message}\" to send a single message\n"+
                           "\tEnter \"broadcast {message}\" to broadcast a message\n"+
                           "\tEnter \"busy\" to set your status as \"not available\"\n"+
                           "\tEnter \"available\" to set your status as \"available\"\n"+
                           "\tEnter \"exit\" to unregister and quit\n");
    }

    private static String getFirstWord(String str) {
        str = removeLeadingSpaces(str);
        return str.split(" ")[0];
    }

    private static String getAfterFirstWord(String str) {
        String rv = "";

        str = removeLeadingSpaces(str);
        int indexOfFirstSpace = str.indexOf(" ");

        if (-1 != indexOfFirstSpace) {
            rv = removeLeadingSpaces(str.substring(indexOfFirstSpace));
        }

        return rv;
    }

    private static String removeLeadingSpaces(String str) {
        String rv = "";

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                rv = str.substring(i);
                break;
            }
        }

        return rv;
    }

    private static void getFriends() throws RemoteException {
        Vector<RegistrationInfo> users = server.listRegisteredUsers();

        System.out.println("Currently registered users:");

        for (RegistrationInfo user : users) {
            System.out.print("\t" + user.getUserName());
            if (myInfo.getUserName().equals(user.getUserName())) {
                System.out.print(" (you)");
            }
            System.out.println(": " + ((user.getStatus() == AVAILABLE ) ? 
                                       "available" : "not available") );
        }
    }

    private static void setSelfBusy() throws RemoteException {
        if (BUSY == myInfo.getStatus()) {
            System.out.println("You are already set to \"not available\"");
        }
        else {
            myInfo.setStatus(BUSY);
            server.updateRegistrationInfo(myInfo);
            messenger.broadcastUnsubscribe();
            System.out.println("You are now set to \"not available\"");
        }
    }

    private static void setSelfAvailable() throws RemoteException {
        if (AVAILABLE == myInfo.getStatus()) {
            System.out.println("You are already set to \"available\"");
        }
        else {
            myInfo.setStatus(AVAILABLE);
            server.updateRegistrationInfo(myInfo);
            messenger.broadcastSubscribe();
            System.out.println("You are now set to \"available\"");
        }
    }

    private static void prepareForExit() throws RemoteException {
        server.unregister(myInfo.getUserName());
        System.out.println("Exiting.");
    }

    private static void sendSingleMessage(String nameAndMessage) throws RemoteException {
        String username = getFirstWord(nameAndMessage);
        String message = getAfterFirstWord(nameAndMessage);

        if (myInfo.getUserName().equals(username)) {
            System.out.println("You cannot message yourself.");
        }
        else {
            RegistrationInfo user = server.lookup(username);

            if (null == user) {
                System.out.println("User named \"" + username + "\" does not exist.");
            }
            else {
                if (BUSY == user.getStatus()) {
                    System.out.println(user.getUserName() + " is not available.");
                }
                else {
                    sendMessageTo(user, message);
                }
            }
        }
    }

    private static void sendBroadcast(String message) throws RemoteException {
        String namedMessage = myInfo.getUserName() + ": " + message;
        server.broadcast(namedMessage);
    }

    private static void sendMessageTo(RegistrationInfo user, String message) {
        String namedMessage = myInfo.getUserName() + ": " + message;
        messenger.send(user, namedMessage);
    }
}