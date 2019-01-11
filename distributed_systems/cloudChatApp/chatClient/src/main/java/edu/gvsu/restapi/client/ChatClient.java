package edu.gvsu.restapi.client;

import edu.gvsu.restapi.client.*;

import java.util.Scanner;
import java.util.Vector;
import java.net.*;

public class ChatClient {

    private static PresenceService server;
    private static RegistrationInfo myInfo;
    private static NetworkMessager messager;

    private static final boolean AVAILABLE = true;
    private static final boolean BUSY = false;
    private static final String DEF_URI = "http://localhost:8080";

    public static void main(String[] args) {
        try {
            // Register remote compute engine
            String name = "PresenceService";
            String serverUri = getUri(args);
            server = (PresenceService) new PresenceServiceImpl();
            server.setServerUri(serverUri);

            String myHost;
            try {
                myHost = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                myHost = "localhost";
            }

            messager = new NetworkMessager();

            // Initialize self, register with server
            myInfo = new RegistrationInfo(args[0], myHost, messager.getListeningPort(), BUSY);

            if (!server.register(myInfo)) {
                System.out.println("User with name \"" + args[0] + "\" already exists. Exiting.");
            }
            else {
                // Start messaging thread, set self as available, and listen to input
                Thread messageThread = new Thread(messager);
                messageThread.start();
                myInfo.setStatus(AVAILABLE);
                server.updateRegistrationInfo(myInfo);
                sendBroadcast("[" + myInfo.getUserName() + " is online]");

                controlLoop();

                messager.close();
            }

        } catch (Exception e) {
            System.err.println("ChatClient exception:");
            e.printStackTrace();
        }
    }

    private static String getUri(String[] args) {
        String uri = DEF_URI;

        if (2 == args.length) {
            uri = args[1];
        }

        return uri;
    }

    private static void controlLoop() {
        Scanner reader = new Scanner(System.in);
        boolean more = true;
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
                    sendBroadcast("[" + myInfo.getUserName() + " is going offline]");
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

    private static void getFriends() {
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

    private static void setSelfBusy() {
        if (BUSY == myInfo.getStatus()) {
            System.out.println("You are already set to \"not available\"");
        }
        else {
            myInfo.setStatus(BUSY);
            server.updateRegistrationInfo(myInfo);
        }
    }

    private static void setSelfAvailable() {
        if (AVAILABLE == myInfo.getStatus()) {
            System.out.println("You are already set to \"available\"");
        }
        else {
            myInfo.setStatus(AVAILABLE);
            server.updateRegistrationInfo(myInfo);
        }
    }

    private static void prepareForExit() {
        server.unregister(myInfo.getUserName());
        System.out.println("Exiting.");
    }

    private static void sendSingleMessage(String nameAndMessage) {
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

    private static void sendBroadcast(String message) {
        Vector<RegistrationInfo> users = server.listRegisteredUsers();

        for (RegistrationInfo user : users) {
            if (!myInfo.getUserName().equals(user.getUserName()) &&
                AVAILABLE == user.getStatus())
            {
                sendMessageTo(user, message);
            }
        }
    }

    private static void sendMessageTo(RegistrationInfo user, String message) {
        String namedMessage = myInfo.getUserName() + " says: " + message;
        messager.send(user, namedMessage);
    }
}