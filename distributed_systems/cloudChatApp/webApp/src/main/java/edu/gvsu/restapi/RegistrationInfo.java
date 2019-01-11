package edu.gvsu.restapi;

import java.io.*;
import org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

/**
 * This class represents the information that the chat client registers
 * with the presence server.
 */
@Entity
public class RegistrationInfo implements Serializable
{
	private static final long serialVersionUID = -1692519871343236571L;
	@Id String userName;
    private String host;
    private boolean status;
    private int port;

    /**
     * RegistrationInfo  no-arg constructor.
     * @param uname Name of the user being registered.
     */
    public RegistrationInfo()
    {
        this.userName = "";
        this.host = "";
        this.port = 0;
        this.status = false;
    }

    /**
     * RegistrationInfo  constructor.
     * @param uname Name of the user being registered.
     * @param h Name of the host their client is running on.
     * @param p The port # their client is listening for connections on.
     * @param s The status, true if the client is available, false otherwise.
     */
    public RegistrationInfo(String uname, String h, int p, boolean s)
    {
        this.userName = uname;
        this.host = h;
        this.port = p;
        this.status = s;
    }

    /**
     * Determine the name of the user.
     * @return The name of the user.
     */
    public String getUserName()
    {
        return this.userName;
    }

    /**
     * Determine the host the user is on.
     * @return The name of the host client resides on.
     */
    public String getHost()
    {
        return this.host;
    }

    /**
     * Get the port the client is listening for connections on.
     * @return port value.
     */
    public int getPort()
    {
        return this.port;
    }

    /**
     * Get the status of the client - true means availability, false means don't disturb.
     * @return status value.
     */
    public boolean getStatus()
    {
    	return this.status;
    }

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Convert this object into an HTML representation.
	 * @param fragment if true, generate an html fragment, otherwise a complete document.
	 * @return an HTML representation.
	 */
	public String toHtml(boolean fragment) {
		String retval = "";
		StringBuffer sb = new StringBuffer();

		if(fragment) {
			sb.append("<html><head><title>RegistrationInfo Resource</title></head><body>"+
					  "<h1>RegistrationInfo Representation</h1>");
		}

		sb.append("<b>serialVersionUID:</b>");
		sb.append(this.serialVersionUID);
		sb.append("<b>userName:</b>");
		sb.append(this.userName);
		sb.append("<b>host:</b>");
		sb.append(this.host);
		sb.append("<b>status:</b>");
		sb.append(this.status);
		sb.append("<b>port:</b>");
		sb.append(this.port);
		sb.append("<a href=\"/users/" + this.userName + "\">View</a>");
		sb.append("<br/>");

		if (fragment) {
			sb.append("<br/>Return to <a href=\"/RegistrationInfo\">user list<a>.</body></html>");
		}

		retval = sb.toString();
		return retval;
	}

	/**
	 * Convert this object to a string for representation
	 * @return a JSON representation.
	 */
	public JSONObject toJSON() {
		try{
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("serialVersionUID", this.serialVersionUID);
			jsonobj.put("userName", this.userName);
			jsonobj.put("host", this.host);
			jsonobj.put("status", this.status);
			jsonobj.put("port", this.port);
			return jsonobj;
		}catch(Exception e){
			return null;
		}
	}

}
