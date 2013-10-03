package org.pocproc.app;

public class MailSender extends Thread {

	private String[] receiver;
	private String subject;
	private String content;

	public MailSender(String[] receiver, String subject, String content) {
		super();
		this.receiver = receiver;
		this.subject = subject;
		this.content = content;
	}

	public void run() {
		Mail.sendMail(receiver, subject, content);
	}

}
