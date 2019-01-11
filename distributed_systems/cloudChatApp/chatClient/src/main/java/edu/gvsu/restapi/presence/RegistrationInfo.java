package edu.gvsu.restapi.client;

import java.io.*;

/**
 * This class represents the information that the chat client registers
 * with the presence server.
 */
public class RegistrationInfo implements Serializable
{
	private static final long serialVersionUID = -1692519871343236571L;
	private String userName;
    private String host;
    private boolean status;
    private int port;

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
     * RegistrationInfo  constructor.
     * @param html -- an html string that contains the RegistrationInfo data.
     */
    public RegistrationInfo(String html)
    {
        setFromHtml(html);
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
	 * Convert this object from an HTML representation.
	 * @return whether the conversion was successful.
	 */
	public boolean setFromHtml(String html) {
		boolean retval = false;

        try {
            String userName = html.split("<b>userName:</b>")[1].split("<b>host:</b>")[0];
            String host = html.split("<b>host:</b>")[1].split("<b>status:</b>")[0];
            String status = html.split("<b>status:</b>")[1].split("<b>port:</b>")[0];
            String port = html.split("<b>port:</b>")[1].split("<a href")[0];

            if (null != userName && null != host && null != status && null != port) {
                this.userName = userName;
                this.host = host;
                this.status = Boolean.parseBoolean(status);
                this.port = Integer.parseInt(port);
                retval = true;
            }
        }
        catch (Exception e) {
            /* exceptions possible if the first split() has len 1; bad coding to plan on exceptions,
               but I'm low on time for this assignment */
        }

		return retval;
	}

}
