package org.pocproc.app;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Locale;

import org.pocproc.data.LoopManager;
import org.pocproc.data.PersonManager;
import org.pocproc.data.Subrics;

public class POCproc {

	public static File configpath;
	public static final String version = "NEV POCproc 1.0";

	public static File getFile(String filename) throws IOException {

		File subdir;
		if (configpath != null) {
			AppWindow.log("Konfigurationspfad: " + configpath.getCanonicalPath());
			subdir = configpath;
		} else {
			subdir = new File(System.getProperty("user.home") + File.separator + ".pocproc");
		}

		// create subdir if not exists
		if (!subdir.isDirectory()) {
			if (subdir.mkdir()) {
				AppWindow.log("Konfigurationsverzeichnis " + subdir.getCanonicalPath() + "\\ angelegt");
			} else {
				AppWindow.log("Fehler beim Anlegen des Konfigurationsverzeichnisses " + subdir.getCanonicalPath());
			}
		}

		// check existence of .pocproc folder, create it if not existing
		AppWindow.log("Dateianforderung: " + subdir.getCanonicalPath() + File.separator + filename);
		return new File(subdir + File.separator + filename);

	}


	public static void load_configurations() {

		// Crusader and POC32 settings change require restart!
		LoopManager.init();
		PersonManager.init();
		Mail.init();
		Subrics.init();
		pushover.init();
	}

//	public static Locale[] supportedLocales = { Locale.GERMAN, Locale.ENGLISH };

	
	private static String[] arguments;
	
	public static void restart() {
        StringBuilder cmd = new StringBuilder();
        cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
        for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            cmd.append(jvmArg + " ");
        }
        cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
        cmd.append(POCproc.class.getName()).append(" ");
        for (String arg : arguments) {
            cmd.append(arg).append(" ");
        }
        try {
			Runtime.getRuntime().exec(cmd.toString());
		} catch (IOException e) {
			System.err.println("Error restarting!!!!");
		}
        System.exit(0);
		
	}

	
	
	public static void main(String[] args) {

		arguments = args;
		
		if (configpath != null)
			try {
				AppWindow.log("Konfigurationspfad: " + configpath.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace(); //NOTHING
			}

			load_configurations();
			POC32.init();
			AppWindow.init();
			POC32.connect();
	}

}
