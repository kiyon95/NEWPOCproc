package org.pocproc.app;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.pocproc.data.Datagram;
import org.pocproc.data.Loop;
import org.pocproc.data.LoopManager;
import org.pocproc.data.Person;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Mail {

	@Element
	private String server;

	@Element
	private String from;

	@Element(required = false)
	private String user;

	@Element(required = false)
	private String password;

	@Element(required = false)
	private boolean SSL;

	@Element(required = false)
	private int port;

	@Element(required = false)
	private boolean AUTH;

	@Element
	private boolean enabled;

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAUTH() {
		return AUTH;
	}

	public void setAUTH(boolean aUTH) {
		AUTH = aUTH;
	}

	public boolean isSSL() {
		return SSL;
	}

	public void setSSL(boolean sSL) {
		this.SSL = sSL;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public static Mail instance;

	public static void process(Person[] Persons, Datagram dat) {

		AppWindow.log(Messages.getString("Mail.0")); //$NON-NLS-1$

		if (instance.enabled == true) {

			Loop ric = LoopManager.getRic(dat.getRic());
			String alarm = ric.getTypeForDatagram(dat);

			AppWindow.log(Messages.getString("Mail.1") + alarm); //$NON-NLS-1$
			AppWindow.log(Messages.getString("Mail.2") + dat.getMeldung()); //$NON-NLS-1$
			AppWindow.log(Messages.getString("Mail.3") + dat.getRic()); //$NON-NLS-1$

			AppWindow.log(Messages.getString("Mail.3") + ric.getName()); //$NON-NLS-1$

			Vector<String> bcc = new Vector<String>();

			for (int i = 0; i < Persons.length; i++) {
				if (Persons[i].email != null && !(Persons[i].email.equals(""))) { //$NON-NLS-1$
					AppWindow.log("BCC: " + Persons[i].email); //$NON-NLS-1$
					bcc.add(Persons[i].email);
				}
				if (Persons[i].email2 != null && !(Persons[i].email.equals(""))) { //$NON-NLS-1$
					AppWindow.log("BCC: " + Persons[i].email2); //$NON-NLS-1$
					bcc.add(Persons[i].email2);
				}
			}

			String[] receiver = bcc.toArray(new String[bcc.size()]);

			// fill alarm
			if (alarm == null) {
				alarm = new String(""); //$NON-NLS-1$
			}

			String content = "<h1>" + HtmlEntities.encode(alarm) + "</h1>" + HtmlEntities.encode(ric.getName()) + "<br>" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ "<b>" + HtmlEntities.encode(dat.getMeldung()) + "</b><br>" + dat.getReceived(); //$NON-NLS-1$ //$NON-NLS-2$

			AppWindow.log(Messages.getString("Mail.17")); //$NON-NLS-1$
			MailSender sender = new MailSender(receiver,
					HtmlEntities.encode(alarm)
							+ " (" + HtmlEntities.encode(ric.getName()) + ")", //$NON-NLS-1$ //$NON-NLS-2$
					content);
			sender.start();

		} else {
			AppWindow.log(Messages.getString("Mail.20")); //$NON-NLS-1$
		}
	}

	public Mail() {
		instance = this;
	}

	public static void init() {
		Serializer serializer = new Persister();

		try {
			File source = POCproc.getFile("mail.xml"); //$NON-NLS-1$
			instance = serializer.read(Mail.class, source);
		} catch (Exception e) {
			// nothing
			e.printStackTrace();
		}
		if (instance == null) {
			instance = new Mail();
			instance.setServer(Messages.getString("Mail.22")); //$NON-NLS-1$
			instance.setFrom(Messages.getString("Mail.23")); //$NON-NLS-1$
			instance.setUser(Messages.getString("Mail.24")); //$NON-NLS-1$
			instance.setPassword(Messages.getString("Mail.25")); //$NON-NLS-1$
			instance.setAUTH(true);
			instance.setSSL(true);
			instance.setPort(465);
			AppWindow.log(Messages.getString("Mail.26")); //$NON-NLS-1$
		} else {
			AppWindow.log(Messages.getString("Mail.27")); //$NON-NLS-1$
		}
	}

	public static void save() {
		MailPanel.instance.setSettings();
		Serializer serializer = new Persister();

		try {
			File loops = POCproc.getFile("mail.xml"); //$NON-NLS-1$
			serializer.write(instance, loops);
			AppWindow
					.log(Messages.getString("Mail.29") + loops.getAbsolutePath()); //$NON-NLS-1$
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppWindow.log(Messages.getString("Mail.30")); //$NON-NLS-1$
		}
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Mail getInstance() {
		return instance;
	}

	public void setInstance(Mail instance) {
		Mail.instance = instance;
	}

	public static void sendMail(String[] receiver, String subject,
			String content) {

		// Recipient's email ID needs to be mentioned.
		// String to = "abcd@gmail.com";

		// // Sender's email ID needs to be mentioned
		// String from = "web@gmail.com";

		// Assuming you are sending email from localhost
		// String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", instance.server); //$NON-NLS-1$

		if (instance.AUTH = true) {
			properties.setProperty("mail.user", instance.user); //$NON-NLS-1$
			properties.setProperty("mail.password", instance.password); //$NON-NLS-1$
			properties.setProperty("mail.smtp.auth", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (instance.SSL = true) {
			properties.setProperty("mail.smtp.starttls.enable", "true"); //$NON-NLS-1$ //$NON-NLS-2$
			properties.setProperty("mail.transport.protocol", "smtps"); //$NON-NLS-1$ //$NON-NLS-2$
			properties.setProperty("mail.user", instance.user); //$NON-NLS-1$
			properties.setProperty("mail.password", instance.password); //$NON-NLS-1$
			properties.setProperty("mail.smtps.auth", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			properties.setProperty("mail.transport.protocol", "smtp"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Authenticator auth = new SMTPAuthenticator();

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties, auth);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(instance.from));

			// Set BCC: header field of the header.

			for (int i = 0; i < receiver.length; i++) {
				AppWindow.log(Messages.getString("Mail.46") + receiver[i]); //$NON-NLS-1$
				if (!receiver[i].equalsIgnoreCase("")) //$NON-NLS-1$
					message.addRecipient(Message.RecipientType.BCC,
							new InternetAddress(receiver[i]));
			}

			// Set Subject: header field
			message.setSubject(subject);

			// Send the actual HTML message, as big as you like
			message.setContent(content, "text/html"); //$NON-NLS-1$

			// Send message
			Transport.send(message);
			AppWindow.log(Messages.getString("Mail.49")); //$NON-NLS-1$
		} catch (MessagingException mex) {
			AppWindow.log(Messages.getString("Mail.50")); //$NON-NLS-1$
			AppWindow.log(mex.getMessage());
			AppWindow.log("Stack trace:");
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			mex.printStackTrace( printWriter );
			printWriter.flush();
			AppWindow.log(writer.toString());
		}
	}

	static class SMTPAuthenticator extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(instance.user, instance.password);
		}
	}

}
