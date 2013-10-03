package org.pocproc.app;

import org.pocproc.data.Datagram;
import org.pocproc.data.Loop;
import org.pocproc.data.LoopManager;
import org.pocproc.data.Person;
import org.pocproc.data.PersonManager;

public class Processor extends Thread {

	private Datagram processedDatagram;
	private String datagramSource;

	Processor(Datagram datagram, String source) {
		processedDatagram = datagram;
		datagramSource = source;
	}

	public static void process(Datagram datagram, String source) {
		if (datagram != null) {
			if (datagram.isInitial() != true) {
				Processor p = new Processor(datagram, source);
				p.start();
			} else
				AppWindow.log(Messages.getString("Processor.0")); //$NON-NLS-1$
		} else {
			AppWindow.log(source + Messages.getString("Processor.1")); //$NON-NLS-1$
		}

	}

	public void run() {

		// 1st: get a fitting loop for the datagram from LoopManager
		Loop recLoop = LoopManager.getRic(processedDatagram.getRic());

		if (recLoop == null) {
			// no ric fits: do nothing!
			AppWindow.log(datagramSource + Messages.getString("Processor.2") + processedDatagram.getRic() + Messages.getString("Processor.3"));

		} else {
			AppWindow.log(datagramSource + Messages.getString("Processor.4") + processedDatagram.getRic() //$NON-NLS-1$
							+ Messages.getString("Processor.5") + recLoop.name); //$NON-NLS-1$
			AppWindow
					.log(Messages.getString("Processor.6") + recLoop.getTypeForDatagram(processedDatagram)); //$NON-NLS-1$
			AppWindow
					.log(Messages.getString("Processor.8") + " " + recLoop.getPrioForDatagram(processedDatagram)); //$NON-NLS-1$ //$NON-NLS-2$
			processedDatagram.printshort();

			// 2nd: get subric category

			// recLoop.getTypeForDatagram(d)Subrics.getAlarm(String.valueOf(d.getSubric()));
			Person[] recipients = PersonManager
					.getPersonsForLoop(processedDatagram.getRic());

			// 4th: send Mail and Prowl and NMA to recipients.email and
			// recipients.email2

			if (recipients.length > 0) {
				Mail.process(recipients, processedDatagram);
				pushover.process(recipients, processedDatagram);
			} else
				AppWindow.log(Messages.getString("Processor.7")); //$NON-NLS-1$

		}
	}

}
