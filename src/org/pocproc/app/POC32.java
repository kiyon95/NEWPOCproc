package org.pocproc.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import org.pocproc.data.Datagram;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

@Root
public class POC32 extends Thread {

	public static POC32 instance;
	public static boolean connected;

	private static DatagramComparer comparer;

	public static void init() {

		
		Serializer serializer = new Persister();

		comparer = new DatagramComparer("POC32");

		try {
			File source = POCproc.getFile("poc32.xml"); //$NON-NLS-1$
			instance = serializer.read(POC32.class, source);
		} catch (Exception e) {
			// nothing
			e.printStackTrace();
		}
		if (instance == null) {
			instance = new POC32(
					"localhost", 8000, Messages.getString("POC32.2"), true); //$NON-NLS-1$ //$NON-NLS-2$
			AppWindow.log(Messages.getString("POC32.3")); //$NON-NLS-1$
		} else {
			AppWindow.log(Messages.getString("POC32.4")); //$NON-NLS-1$
		}

	}

	public static void save() {
		POC32Panel.instance.setSettings();
		Serializer serializer = new Persister();

		try {
			File loops = POCproc.getFile("poc32.xml"); //$NON-NLS-1$
			serializer.write(instance, loops);
			AppWindow.log(Messages.getString("POC32.6") //$NON-NLS-1$
					+ loops.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			AppWindow.log(Messages.getString("POC32.7")); //$NON-NLS-1$
		}

	}

	@Element
	private String host;
	@Element
	private int port;
	@Element(required = false)
	private String password;
	@Element
	private boolean active;

	private Date starttime;

	private Socket socket;

	static void connect() {
		instance.start();
	}

	static void disconnect() {
		if (instance.socket != null) {
			try {
				instance.socket.close();
			} catch (IOException e) {
				AppWindow.log(Messages.getString("POC32.8")); //$NON-NLS-1$
			}
		} else {
			AppWindow.log(Messages.getString("POC32.9")); //$NON-NLS-1$
		}
	}

	POC32() {
	}

	POC32(String host, int port, String password, boolean active) {
		this.port = port;
		this.password = password;
		this.host = host;
	}

	@Override
	public void run() {

		starttime = new java.util.Date();
		AppWindow.log("Determined start time: " + starttime.toString());

		if (active == true) {

			AppWindow.log(Messages.getString("POC32.10")); //$NON-NLS-1$

			PrintWriter out = null;
			BufferedReader br = null;
			if (socket == null) {
				try {
					socket = new Socket(host, port);
				} catch (IOException e) {
					AppWindow.log(Messages.getString("POC32.11")); //$NON-NLS-1$
					connected = false;
				}
			}
			if (socket != null) {
				try {
					out = new PrintWriter(socket.getOutputStream(), true);
					br = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
				} catch (IOException e) {
					System.err.println(Messages.getString("POC32.12") //$NON-NLS-1$
							+ host);
					connected = false;
				}
				if ((br != null) && (out != null)) {
					String responseLine;
					// write the password to the socket
					try {
						if (password != null) {
							out.println(password);
						} else {
							out.println();
						}
						while ((responseLine = br.readLine()) != null) {
							Datagram received = Datagram
									.parsePOC32(responseLine);
							if (received != null) {
								// message must be received AFTER POC32 has been
								// started (initial messages in POC32 ignore)
								// module started

								if (received.getReceived().getTime() > starttime
										.getTime()) {

									if (comparer.validate(received)) {

										Processor.process(received, "POC32");}} //$NON-NLS-1$
								else {
									AppWindow
											.log("received a datagram older than POCproc start from POC32, treat as INITIAL...");
									received.setInitial(true);
									if (comparer.validate(received)) {
										Processor.process(received, "POC32");}} //$NON-NLS-1$
							}

						}
						connected = false;
						AppWindow.log(Messages.getString("POC32.14")); //$NON-NLS-1$
					} catch (IOException e) {
						e.printStackTrace();
						connected = false;
					}
				}
			}

		} else {
			AppWindow.log(Messages.getString("POC32.15")); //$NON-NLS-1$
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
