package org.pocproc.app;

public class pushoverSender extends Thread {

	private String[] receiver;
	private String subject;
	private String content;
	private Integer priority;

	public pushoverSender(String[] receiver, String subject, String content,
			Integer priority) {
		super();
		this.receiver = receiver;
		this.subject = subject;
		this.content = content;
		this.priority = priority;
	}

	public void run() {
		pushover.sendpushover(receiver, subject, content, priority);
	}

}
