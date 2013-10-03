package org.pocproc.app;

public class ConfigReloader extends Thread {

	@Override
	public void run() {

		while (true) {
			// sleep 15 Minutes, then reload config
			try {
				AppWindow.log(Messages.getString("ConfigReloader.0")); //$NON-NLS-1$
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				AppWindow.log(Messages.getString("ConfigReloader.1")); //$NON-NLS-1$
			}
			AppWindow.log(Messages.getString("ConfigReloader.2")); //$NON-NLS-1$
			POCproc.load_configurations();

		}
	}
}
