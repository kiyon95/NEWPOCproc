package org.pocproc.data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.pocproc.app.AppWindow;

public class Datagram implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2437783424017489811L;

	private String ric;

	private char subric;

	private String meldung;

	private char channel;

	private Date received;

	private boolean initial;

	// Sehr einfache, aber bei großen Datenmengen auch ineffiziente
	// Implementierung:
	// private static String nullenLinks(String value, int len) {
	// String result = value;
	// while (result.length() < len) {
	// result = "0" + result;
	// }
	// return result;
	// }

	public static Datagram parseCRUSADER(String input) {

		if (input.contains("#![]!#PING#![]!#")) { //$NON-NLS-1$

			AppWindow.log("PING"); //$NON-NLS-1$
			return null;
		} else {

			Datagram temp = new Datagram();

			String result[] = input.split("\\#\\!\\[\\]\\!\\#"); //$NON-NLS-1$

			temp.ric = result[2];

			// check if "evaluate subric" is checked and subric part of RIC
			// string...
			if (temp.ric.contains("A") || temp.ric.contains("B") || temp.ric.contains("C") || temp.ric.contains("D")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			{
				temp.ric = temp.ric.substring(0, temp.ric.length() - 1);
			} else {

			}

			// this is always the subric
			temp.subric = result[6].charAt(0);

			if (temp.subric == '0') {
				temp.subric = 'A';
			} else if (temp.subric == '1') {
				temp.subric = 'B';
			} else if (temp.subric == '2') {
				temp.subric = 'C';
			} else if (temp.subric == '3') {
				temp.subric = 'D';
			}

			if (result[9].equals("true")) //$NON-NLS-1$
				temp.channel = 'L';
			else
				temp.channel = 'R';

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd.MM.yy HH:mm:ss"); //$NON-NLS-1$

			if (result[0].contains("#!XX!#")) { //$NON-NLS-1$
				temp.initial = true;
				result[0] = result[0].substring(6);
			}

			try {
				temp.received = dateFormat.parse(result[0] + " " + result[1]); //$NON-NLS-1$
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (result.length == 12) {
				temp.meldung = result[11];
			}

			return temp;

		}

	}

	public void print() {
		AppWindow.log("------------------------------------"); //$NON-NLS-1$
		AppWindow.log(Messages.getString("Datagram.12")); //$NON-NLS-1$
		AppWindow.log("------------------------------------"); //$NON-NLS-1$
		AppWindow.log(Messages.getString("Datagram.14") + this.initial); //$NON-NLS-1$
		AppWindow.log(Messages.getString("Datagram.15") + this.ric); //$NON-NLS-1$
		AppWindow.log(Messages.getString("Datagram.16") + this.subric); //$NON-NLS-1$
		AppWindow.log(Messages.getString("Datagram.17") + this.meldung); //$NON-NLS-1$
		AppWindow.log(Messages.getString("Datagram.18") + this.channel); //$NON-NLS-1$
		AppWindow.log(Messages.getString("Datagram.19") + this.received); //$NON-NLS-1$
		AppWindow.log(""); //$NON-NLS-1$
	}

	public Datagram(String ric, char subric, String meldung, char channel,
			Date received, boolean initial) {
		this.ric = ric;
		this.subric = subric;
		this.meldung = meldung;
		this.channel = channel;
		this.received = received;
		this.initial = initial;

	}

	public Datagram() {

	}

	public void printshort() {
		AppWindow.log(received + "/" + ric + "/" + subric + "/" + meldung); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public static Datagram parsePOC32(String input) {

		if (input.contains("POC32")) { //$NON-NLS-1$

			AppWindow.log(Messages.getString("Datagram.25")); //$NON-NLS-1$
			return null;
		} else if (input.length() < 2) {
			AppWindow.log(Messages.getString("Datagram.26")); //$NON-NLS-1$
			return null;
		} else if (input.contains(Messages.getString("Datagram.27"))) { //$NON-NLS-1$
			AppWindow.log(Messages.getString("Datagram.28")); //$NON-NLS-1$
			return null;
		} else if (input.contains(Messages.getString("Datagram.29"))) { //$NON-NLS-1$
			AppWindow.log(Messages.getString("Datagram.30")); //$NON-NLS-1$
			return null;
		} else {

			Datagram temp = new Datagram();

			// AppWindow.log(input);
			String result[] = input.split("\t"); //$NON-NLS-1$

			if (result.length > 5) {
				temp.ric = result[3];
				// temp.ric = temp.ric.substring(0, temp.ric.length()-1);
				temp.subric = result[5].charAt(0);

				if (temp.subric == '0') {
					temp.subric = 'A';
				} else if (temp.subric == '1') {
					temp.subric = 'B';
				} else if (temp.subric == '2') {
					temp.subric = 'C';
				} else if (temp.subric == '3') {
					temp.subric = 'D';
				}

				if (result[1].contains("CH1")) //$NON-NLS-1$
					temp.channel = 'L';
				else
					temp.channel = 'R';

				SimpleDateFormat dateFormat = new SimpleDateFormat(
						Messages.getString("Datagram.33")); //$NON-NLS-1$

				try {
					temp.received = dateFormat.parse(result[0]);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (result.length > 6) {
					temp.meldung = result[6];
				}

				return temp;
			} else {
				AppWindow.log(Messages.getString("Datagram.0") + " " + input); //$NON-NLS-1$ //$NON-NLS-2$
				return null;
			}
		}

	}

	// override equals for comparing datagrams
	public boolean equals(Datagram d) {

		// first check ric
		if (d.ric.equals(this.ric)) {
			// second check subric
			if (d.subric == this.subric) {
				// second check message
				if (d.meldung != null && this.meldung != null) {
					if (d.meldung.equals(this.meldung)) {
						return true;
					}
				} else {
					if (d.meldung == null && this.meldung == null) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}

	public String getRic() {
		return ric;
	}

	public void setRic(String ric) {
		this.ric = ric;
	}

	public char getSubric() {
		if ((subric != 'A') && (subric != 'B') && (subric != 'C')
				&& (subric != 'D'))
			return 'A';
		else
			return subric;
	}

	public void setSubric(char subric) {
		this.subric = subric;
	}

	public String getMeldung() {
		if (meldung != null)
			return meldung;
		else
			return Messages.getString("Datagram.2"); //$NON-NLS-1$
	}

	public void setMeldung(String meldung) {
		this.meldung = meldung;
	}

	public char getChannel() {
		if ((channel != 'L') && (channel != 'R'))
			return 'L';
		else
			return channel;
	}

	public void setChannel(char channel) {
		this.channel = channel;
	}

	public Date getReceived() {
		if (received != null)
			return received;
		else
			return new Date();
	}

	public void setReceived(Date received) {
		this.received = received;
	}

	public boolean isInitial() {
		return initial;
	}

	public void setInitial(boolean initial) {
		this.initial = initial;
	}

	public long getAgeInMinutes() {

		long age = (((new Date()).getTime() / 60000) - (this.received.getTime() / 60000));

		return age;

	}

	public static Datagram parseFMS32(String input) {

		if (input.length() == 0) {
			AppWindow.log(Messages.getString("Datagram.1")); //$NON-NLS-1$
			return null;
		} else {

			Datagram temp = new Datagram();

			AppWindow.log(input);
			String result[] = input.split("\t"); //$NON-NLS-1$

			if (result.length >= 5) {
				temp.ric = result[1];
				// temp.ric = temp.ric.substring(0, temp.ric.length()-1);
				temp.subric = result[2].charAt(0);

				if (temp.subric == '0') {
					temp.subric = 'A';
				} else if (temp.subric == '1') {
					temp.subric = 'B';
				} else if (temp.subric == '2') {
					temp.subric = 'C';
				} else if (temp.subric == '3') {
					temp.subric = 'D';
				}

				temp.channel = 'L';

				temp.received = new Date();

				temp.meldung = result[3];

				return temp;
			} else {
				AppWindow.log(Messages.getString("Datagram.0") + " " + input); //$NON-NLS-1$ //$NON-NLS-2$
				return null;
			}
		}

	}

}
