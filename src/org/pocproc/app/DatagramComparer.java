package org.pocproc.app;

import java.util.HashMap;

import org.pocproc.data.Datagram;

public class DatagramComparer {

	private HashMap<String, Datagram> buffer;
	private String owner;

	public boolean validate(Datagram d) {

		// na, if d is null, it is INVALID
		if (d == null) {
			return false;
		}
		
		Datagram latest = buffer.get(d.getRic());

		if (latest == null) {
			buffer.put(d.getRic(), d);
			AppWindow.log("<" + owner + Messages.getString("DatagramComparer.1") //$NON-NLS-1$ //$NON-NLS-2$
					+ d.getRic() + Messages.getString("DatagramComparer.2")); //$NON-NLS-1$
			AppWindow.log("<" + owner + Messages.getString("DatagramComparer.4") + d.getRic() //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("DatagramComparer.5")); //$NON-NLS-1$
			return true;
		} else {
			// we have got a message in buffer, time limit for deduplicator is
			// 10 minutes -- allow for regular test alarms

			if (latest.getAgeInMinutes() > 10) {
				buffer.put(d.getRic(), d);
				AppWindow.log("<" + owner + Messages.getString("DatagramComparer.7") //$NON-NLS-1$ //$NON-NLS-2$
						+ d.getRic() + Messages.getString("DatagramComparer.8")); //$NON-NLS-1$
				AppWindow.log("<" + owner + Messages.getString("DatagramComparer.10") + d.getRic() //$NON-NLS-1$ //$NON-NLS-2$
						+ Messages.getString("DatagramComparer.11")); //$NON-NLS-1$
				AppWindow.log("<" + owner + Messages.getString("DatagramComparer.13") //$NON-NLS-1$ //$NON-NLS-2$
						+ d.getRic() + Messages.getString("DatagramComparer.14")); //$NON-NLS-1$
				return true;
			} else {
				// we have a valid comparable message in buffer, compare!

				if (latest.equals(d)) {
					// replace it also, because of updating time stamp of
					// received date.. resets if new message comes in
					buffer.put(d.getRic(), d);
					AppWindow.log("<" + owner + Messages.getString("DatagramComparer.16") //$NON-NLS-1$ //$NON-NLS-2$
							+ d.getRic()
							+ Messages.getString("DatagramComparer.17")); //$NON-NLS-1$
					AppWindow.log("<" + owner + Messages.getString("DatagramComparer.19") //$NON-NLS-1$ //$NON-NLS-2$
							+ d.getRic() + Messages.getString("DatagramComparer.20")); //$NON-NLS-1$
					AppWindow.log("<" + owner + Messages.getString("DatagramComparer.22") //$NON-NLS-1$ //$NON-NLS-2$
							+ d.getRic() + Messages.getString("DatagramComparer.23")); //$NON-NLS-1$
					return false;
				} else {
					// replace it
					buffer.put(d.getRic(), d);
					AppWindow.log("<" + owner + Messages.getString("DatagramComparer.25") //$NON-NLS-1$ //$NON-NLS-2$
							+ d.getRic()
							+ Messages.getString("DatagramComparer.26")); //$NON-NLS-1$
					AppWindow.log("<" + owner + Messages.getString("DatagramComparer.28") //$NON-NLS-1$ //$NON-NLS-2$
							+ d.getRic() + Messages.getString("DatagramComparer.29")); //$NON-NLS-1$
					AppWindow.log("<" + owner + Messages.getString("DatagramComparer.31") //$NON-NLS-1$ //$NON-NLS-2$
							+ d.getRic() + Messages.getString("DatagramComparer.32")); //$NON-NLS-1$
					return true;
				}
			}
		}
	}

	public DatagramComparer(String owner) {

		// initialize
		buffer = new HashMap<String, Datagram>();

		this.owner = owner;
		AppWindow.log("<" + owner + Messages.getString("DatagramComparer.34")); //$NON-NLS-1$ //$NON-NLS-2$

	}

}
