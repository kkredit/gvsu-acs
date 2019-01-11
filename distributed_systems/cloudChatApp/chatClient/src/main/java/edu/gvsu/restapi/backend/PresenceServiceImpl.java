package edu.gvsu.restapi.client;

import edu.gvsu.restapi.client.PresenceService;
import edu.gvsu.restapi.client.RegistrationInfo;
import java.util.Vector;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Client;
import org.restlet.data.*;
import org.restlet.*;
import org.restlet.representation.Representation;

public class PresenceServiceImpl implements PresenceService {

    private String m_serverUri = "http://localhost:8080";

    private static final String m_usersUriExt = "/v1/users";

    public PresenceServiceImpl() {
        super();
    }

    public void setServerUri(String serverUri) {
        m_serverUri = serverUri;
    }

    public boolean register(RegistrationInfo reg) {
        boolean rv = false;

        // create and send form
	    Form form = new Form();
        form.add("name", reg.getUserName());
        form.add("host", reg.getHost());
        form.add("port", String.valueOf(reg.getPort()));
        form.add("status", Boolean.toString(reg.getStatus()));

	    String usersUrl = m_serverUri + m_usersUriExt;
	    Request request = new Request(Method.POST, usersUrl);
	    request.setEntity(form.getWebRepresentation());

//      System.out.println("Sending an HTTP POST to " + usersUrl + "." +
//                         "\n(" + form.getWebRepresentation() + ")");
	    Response resp = new Client(Protocol.HTTP).handle(request);

	    // now, let's check what we got in response
//      System.out.println(resp.getStatus());
        rv = resp.getStatus().isSuccess();

        return rv;
    }

    public boolean updateRegistrationInfo(RegistrationInfo reg) {
        boolean rv = false;

        // create and send form
	    Form form = new Form();
        form.add("name", reg.getUserName());
        form.add("host", reg.getHost());
        form.add("port", String.valueOf(reg.getPort()));
        form.add("status", Boolean.toString(reg.getStatus()));

	    String userUrl = m_serverUri + m_usersUriExt + "/" + reg.getUserName();
	    Request request = new Request(Method.PUT, userUrl);
	    request.setEntity(form.getWebRepresentation());
        
//      System.out.println("Sending an HTTP PUT to " + userUrl + "." +
//                         "\n(" + form.getWebRepresentation() + ")");
	    Response resp = new Client(Protocol.HTTP).handle(request);

        // now, let's check what we got in response
//      System.out.println(resp.getStatus());
        rv = resp.getStatus().isSuccess();

        return rv;
    }

    public void unregister(String userName) {

	    String userUrl = m_serverUri + m_usersUriExt + "/" + userName;
	    Request request = new Request(Method.DELETE, userUrl);
        request.getClientInfo().getAcceptedMediaTypes().add(new Preference(MediaType.TEXT_HTML));

//      System.out.println("Sending an HTTP DELETE to " + userUrl + ".");
	    Response resp = new Client(Protocol.HTTP).handle(request);

        // now, let's check what we got in response
//      System.out.println(resp.getStatus());

    }

    public RegistrationInfo lookup(String name) {
        RegistrationInfo rv = null;

	    String userUrl = m_serverUri + m_usersUriExt + "/" + name;
	    Request request = new Request(Method.GET, userUrl);
        request.getClientInfo().getAcceptedMediaTypes().add(new Preference(MediaType.TEXT_HTML));

//      System.out.println("Sending an HTTP GET to " + userUrl + ".");
	    Response resp = new Client(Protocol.HTTP).handle(request);

	    // now, let's check what we got in response
//      System.out.println(resp.getStatus());
        if (resp.getStatus().isSuccess()) {
            try {
                rv = new RegistrationInfo(resp.getEntity().getText());
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return rv;
    }

    public Vector<RegistrationInfo> listRegisteredUsers() {
        Vector<RegistrationInfo> users = new Vector<RegistrationInfo>();

	    String usersUrl = m_serverUri + m_usersUriExt;
	    Request request = new Request(Method.GET, usersUrl);

//      System.out.println("Sending an HTTP GET to " + usersUrl + ".");
	    Response resp = new Client(Protocol.HTTP).handle(request);

	    // now, let's check what we got in response
//      System.out.println(resp.getStatus());
        if (resp.getStatus().isSuccess()) {
            try {
                String[] usersHtml = resp.getEntity().getText().split("<title>RegistrationInfo Resource</title>");
                for (Integer i = 1; i < usersHtml.length; i++) {
                    RegistrationInfo newUser = new RegistrationInfo(usersHtml[i]);
                    if (null != newUser) {
                        users.addElement(newUser);
                    }
                }
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return users;
    }
}

