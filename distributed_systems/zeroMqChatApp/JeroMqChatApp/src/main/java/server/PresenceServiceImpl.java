package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import presence.PresenceService;
import presence.RegistrationInfo;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.Vector;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class PresenceServiceImpl implements PresenceService {

    private static Vector<RegistrationInfo> registeredUsers;
    private static int broadcastPubPort;
    private static int portAssignCounter;
    private static ZMQ.Socket pubSocket;

    private static final int DEFAULT_PORT = 1099;

    public PresenceServiceImpl() {
        super();
        registeredUsers = new Vector<RegistrationInfo>();
    }

    public static void main(String[] args) {
        ZContext context = new ZContext();
        pubSocket = context.createSocket(ZMQ.PUB);

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
		    // Have the server start the RMI Registry
            int port = (args.length > 0) ? Integer.parseInt(args[0]) : DEFAULT_PORT;
            LocateRegistry.createRegistry(port);
            broadcastPubPort = port + 1;
            portAssignCounter = port + 2;
            pubSocket.bind("tcp://*:" + Integer.toString(broadcastPubPort));

            // Create the presence service object and bind in RMI
            String name = "PresenceService";
            PresenceService server = new PresenceServiceImpl();
            PresenceService stub =
                    (PresenceService) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            registry.rebind(name, stub);
            System.out.println("PresenceServiceImpl bound");
			Object o = new Object();
			synchronized (o) {
				o.wait();
			}
        } catch (Exception e) {
            System.err.println("PresenceServiceImpl exception:");
            e.printStackTrace();
        }

        context.destroySocket(pubSocket);
        context.destroy();
    }

    public boolean register(RegistrationInfo reg) throws RemoteException {
        boolean rv = false;

        if (null == lookup(reg.getUserName())) {
            try {
                reg.setHost(RemoteServer.getClientHost());
            } catch (ServerNotActiveException e) {
                e.printStackTrace();
            }
            reg.setPort(portAssignCounter++);
            registeredUsers.addElement(reg);
            rv = true;
        }

        return rv;
    }

    public boolean updateRegistrationInfo(RegistrationInfo reg) throws RemoteException {
        boolean rv = false;

        RegistrationInfo user = lookup(reg.getUserName());
        if (null != user) {
            try {
                reg.setHost(RemoteServer.getClientHost());
            } catch (ServerNotActiveException e) {
                e.printStackTrace();
            }
            user.setPort(reg.getPort());
            user.setStatus(reg.getStatus());
            rv = true;
        }

        return rv;
    }

    public void unregister(String userName) throws RemoteException {
        registeredUsers.remove(lookup(userName));
    }

    public RegistrationInfo lookup(String name) throws RemoteException {
        RegistrationInfo rv = null;

        for (RegistrationInfo user : registeredUsers) {
            if (name.equals(user.getUserName())) {
                rv = user;
                break;
            }
        }

        return rv;
    }

    public Vector<RegistrationInfo> listRegisteredUsers() throws RemoteException {
        return registeredUsers;
    }

    public void broadcast(String msg) throws RemoteException {
        pubSocket.send(msg.getBytes(ZMQ.CHARSET));
    }
}

