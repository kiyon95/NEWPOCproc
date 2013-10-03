package org.pocproc.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class AppWindow extends JFrame {

	final static long serialVersionUID = 1L;

	public JTabbedPane tabbedPane;
	public JTabbedPane tabbedOuterPane;

	public JComponent LogPanel;
	public JTextArea Log;

	AppWindow() {
		Dimension dim = new Dimension(900, 600);
		this.setMinimumSize(dim);
		LogPanel = makeLogPanel();
		this.setName("POCproc");
		this.setTitle("POCproc NEV");
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tabbedOuterPane = new JTabbedPane();

		tabbedPane = new JTabbedPane();
		ImageIcon icon = null;

		JComponent panel1 = makePOC32Panel();
		panel1.setPreferredSize(dim);
		tabbedPane.addTab("POC32", icon, panel1, "POC32 Verbindungseinstellungen");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_P);
		tabbedPane.setBackgroundAt(0, Color.green);
		
	
		JComponent panel1d = makeTestPanel();
		panel1d.setPreferredSize(dim);
		tabbedPane.addTab("Test", icon, panel1d, "Versenden von Test-Nachrichten");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_T);
		tabbedPane.setBackgroundAt(1, Color.green);
		
		tabbedOuterPane.addTab("Eingabe", icon, tabbedPane, "Eingabe Module");

		tabbedPane = new JTabbedPane();

		JComponent panel2 = makeLoopPanel();
		panel2.setPreferredSize(dim);
		tabbedPane.addTab("RICs", icon, panel2, "Configure RICs on which to react");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_L);
		tabbedPane.setBackgroundAt(0, Color.orange);

		JComponent panel6 = makeSubricPanel();
		panel6.setPreferredSize(dim);
		tabbedPane.addTab("Alarme", icon, panel6, "Standard-Alarme und Priorit\u00e4ten vergeben");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_S);
		tabbedPane.setBackgroundAt(1, Color.orange);

		JComponent panel3 = makePersonPanel();
		panel3.setPreferredSize(dim);
		tabbedPane.addTab("Empf\u00e4nger", icon, panel3, "Empf\u00e4nger konfigurieren und RICs zuweisen");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_P);
		tabbedPane.setBackgroundAt(2, Color.orange);

		tabbedOuterPane.addTab("Verarbeitung", icon, tabbedPane, "Processing Modules");

		tabbedPane = new JTabbedPane();

		JComponent panel4 = makeMailPanel();
		panel4.setPreferredSize(dim);
		tabbedPane.addTab("Mail", icon, panel4, "eMail Einstellungen");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_M);
		tabbedPane.setBackgroundAt(0, Color.blue);
		tabbedPane.setForegroundAt(0, Color.white);

		JComponent panel63 = makepushoverPanel();
		panel3.setPreferredSize(dim);
		tabbedPane.addTab("Pushover", icon, panel63, "Einstellungen für Pushover");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_P);
		tabbedPane.setBackgroundAt(1, Color.blue);
		tabbedPane.setForegroundAt(1, Color.white);
		
		tabbedOuterPane.addTab("Ausgabe", icon, tabbedPane, "Ausgabemodule");

		tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Log", icon, LogPanel, "Zeigt das Anwendungsprotokoll");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_L);
		tabbedPane.setBackgroundAt(0, Color.black);
		tabbedPane.setForegroundAt(0, Color.white);
		
		JComponent panel7 = makeAboutPanel();
		tabbedPane.addTab(Messages.getString("Info"), icon, panel7, "Informationen zu POCproc und Lizenzbedingungen");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_A);
		tabbedPane.setBackgroundAt(1, Color.black);
		tabbedPane.setForegroundAt(1, Color.white);

		tabbedOuterPane.addTab("Info", icon, tabbedPane, "Informationen und Debugmeldungen");

		tabbedOuterPane.setBackgroundAt(0, Color.green);
		tabbedOuterPane.setBackgroundAt(1, Color.orange);
		tabbedOuterPane.setBackgroundAt(2, Color.blue);
		tabbedOuterPane.setBackgroundAt(3, Color.black);
		tabbedOuterPane.setForegroundAt(2, Color.white);
		tabbedOuterPane.setForegroundAt(3, Color.white);

		this.getContentPane().add(tabbedOuterPane);
		// Display the window.
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	//
	public static String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());

	}

	private static List<String> logbuffer;

	public static void log(String log) {

		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();

		String[] classpath = stackTraceElements[2].getClassName().split("\\.");

		String prefix = classpath[classpath.length - 1] + ":" + stackTraceElements[2].getMethodName() + ":" + stackTraceElements[2].getLineNumber();

		String timestamp = now(Messages.getString("AppWindow.26"));

		prefix = timestamp + " - " + prefix;

		prefix = String.format("%1$-" + 60 + "s", prefix);

		log = prefix + log;

		if (instance != null) {
			instance.Log.append(log + "\n");
		} else {
			if (logbuffer == null) {
				logbuffer = Collections.synchronizedList(new ArrayList<String>());
				}

				logbuffer.add(log + "\n");
				}
		}

	public static void init() {
		instance = new AppWindow();
	}

	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	protected JComponent makeAboutPanel() {
		JPanel panel = new POCprocPanel(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(new JLabel(POCproc.version));
		panel.add(Box.createVerticalStrut(20));
		panel.add(new JLabel("(c) 2011 by Thomas Herzog (thomas@herzogonline.net)"));
		panel.add(new JLabel("Software is released under Apache License 2.0, see LICENSE file"));
		panel.add(Box.createVerticalStrut(20));
		panel.add(new JLabel("Software used and part of distributed JAR archive:"));
		panel.add(Box.createVerticalStrut(20));
		panel.add(new JLabel("jProwlAPI, used under BSD License, see LICENSE_PROWL"));
		panel.add(new JLabel("http://sourceforge.net/projects/jprowlapi/"));
		panel.add(Box.createVerticalStrut(20));
		panel.add(new JLabel("JavaMail, see LICENSE_JAVAMAIL file for license"));
		panel.add(new JLabel("http://www.oracle.com/technetwork/java/javamail/index.html"));
		panel.add(Box.createVerticalStrut(20));
		panel.add(new JLabel("SIMPLE-XML framework, used under Apache License 2.0, see LICENSE file"));
		panel.add(new JLabel("http://simple.sourceforge.net/"));
		panel.add(Box.createVerticalStrut(20));
		panel.add(new JLabel("NMAClientLib by Adriano Maia, used under MIT license"));
		panel.add(new JLabel("https://github.com/uskr/NMAClientLib"));
		panel.add(Box.createVerticalStrut(20));
		panel.add(new JLabel("Icon for pushover from web site http://en.clipart-fr.com/clipart_pictures.php?id=16931"));
		panel.add(new JLabel("According to web site public domain, if you are copyright owner,"));
		panel.add(new JLabel("please contact me if I should remove it."));
		panel.add(Box.createVerticalStrut(40));
		panel.add(new JLabel("The redistributable Software mentioned above has been added unaltered to the JAR archive"));
		panel.add(new JLabel("and is provided as-is for the sole purpose of running the Software."));
		return panel;

	}

	protected JComponent makeLogPanel() {
		JPanel panel = new POCprocPanel(false);
		
		Log = new JTextArea(10, 40);
		Log.setFont(new Font("Courier", Font.PLAIN, 10));
		
		Log.getDocument().addDocumentListener(new LimitLinesDocumentListener(1000));
		
		JScrollPane scrollPane = new JScrollPane(Log);
		
		DefaultCaret caret = (DefaultCaret)Log.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		panel.setLayout(new GridLayout(1, 1));
		panel.add(scrollPane);

		if (logbuffer.size() > 0) {
			for (Object obj : logbuffer) {
				Log.append((String) obj);
			}
		}

		return panel;
	}

	protected JComponent makeMailPanel() {
		JPanel panel = new MailPanel(false);
		return panel;
	}

	protected JComponent makepushoverPanel() {
		JPanel panel = new pushoverPanel(false);
		return panel;
	}

	protected JComponent makeSubricPanel() {
		JPanel panel = new SubricPanel(false);
		return panel;
	}

	protected JComponent makeLoopPanel() {
		JPanel panel = new LoopPanel(false);
		return panel;
	}

	protected JComponent makeTestPanel() {
		JPanel panel = new TestPanel(false);
		return panel;
	}

	protected JComponent makePersonPanel() {
		JPanel panel = new PersonPanel(false);
		return panel;
	}

	protected JComponent makePOC32Panel() {
		JPanel panel = new POC32Panel(false);
		return panel;
	}

	public static AppWindow instance;

}
