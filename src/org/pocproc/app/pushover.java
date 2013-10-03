package org.pocproc.app;

import java.io.File;
import java.util.Vector;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.pocproc.data.Datagram;
import org.pocproc.data.Loop;
import org.pocproc.data.LoopManager;
import org.pocproc.data.Person;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

public class pushover {

	@Element
	public boolean enabled;

	@Element(required = false)
	public String AppKey;

	@Element(required = false)
	public String Event;

	@Element(required = false)
	public String Description;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public static pushover instance;

	public static void init() {
		Serializer serializer = new Persister();

		try {
			File source = POCproc.getFile("pushover.xml"); //$NON-NLS-1$
			instance = serializer.read(pushover.class, source);
		} catch (Exception e) {
			// nothing
			e.printStackTrace();
		}
		if (instance == null) {
			instance = new pushover();
			instance.setEnabled(false);
			instance.setAppKey("Rke7j2THEaMLAEmOMFQ9mTifDAvo9O");
			AppWindow.log(Messages.getString("pushover.1")); //$NON-NLS-1$
		} else {
			AppWindow.log(Messages.getString("pushover.2")); //$NON-NLS-1$
		}

		// if AppKey is null, set it to default
		if (instance.AppKey == null) {
			instance.setAppKey("Rke7j2THEaMLAEmOMFQ9mTifDAvo9O");
		}

		if (instance.Event == null) {
			instance.Event = "$ALARM$";
		}

		if (instance.Description == null) {
			instance.Description = "$RIC$ - $TEXT$";
		}

	}

	public static void save() {
		pushoverPanel.instance.setSettings();
		Serializer serializer = new Persister();

		try {
			File loops = POCproc.getFile("pushover.xml"); //$NON-NLS-1$
			serializer.write(instance, loops);
			AppWindow
					.log(Messages.getString("pushover.4") + loops.getAbsolutePath()); //$NON-NLS-1$
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppWindow.log(Messages.getString("pushover.5")); //$NON-NLS-1$
		}
	}

	public pushover() {
		instance = this;
	}

	public static void process(Person[] Persons, Datagram dat) {

		AppWindow.log(Messages.getString("pushover.6")); //$NON-NLS-1$

		if (instance.enabled == true) {
			Loop ric = LoopManager.getRic(dat.getRic());

			String alarm = ric.getTypeForDatagram(dat);
			Integer priority = ric.getPrioForDatagram(dat);

			AppWindow.log(Messages.getString("pushover.7") + alarm); //$NON-NLS-1$
			AppWindow
					.log(Messages.getString("pushover.0") + " " + priority.toString()); //$NON-NLS-1$ //$NON-NLS-2$
			AppWindow.log(Messages.getString("pushover.8") + dat.getMeldung()); //$NON-NLS-1$
			AppWindow.log(Messages.getString("pushover.9") + dat.getRic()); //$NON-NLS-1$

			AppWindow.log(Messages.getString("pushover.9") + ric.getName()); //$NON-NLS-1$

			Vector<String> bcc = new Vector<String>();

			for (int i = 0; i < Persons.length; i++) {
				if (Persons[i].pushoverkey != null) {
					AppWindow
							.log(Messages.getString("pushover.11") + Persons[i].pushoverkey); //$NON-NLS-1$
					bcc.add(Persons[i].pushoverkey);
				}
			}

			String[] receiver = bcc.toArray(new String[bcc.size()]);

			String content = dat.getMeldung() + "\n" //$NON-NLS-1$ //$NON-NLS-2$
					+ dat.getReceived();

			// here make replacements

			String lEvent;
			String lDescription;
			
			AppWindow.log("event:" + instance.Event + " description:" + instance.Description );

			lEvent = instance.Event.replace("$RIC$", ric.getName());
			lEvent = lEvent.replace("$ALARM$", alarm);
			lEvent = lEvent.replace("$TEXT$", content);
			lEvent = lEvent.replace("$NUL$", "");

			lDescription = instance.Description.replace("$RIC$", ric.getName());
			lDescription = lDescription.replace("$ALARM$", alarm);
			lDescription = lDescription.replace("$TEXT$", content);
			lDescription = lDescription.replace("$NUL$", "");

			pushoverSender send = new pushoverSender(receiver, lEvent,
					lDescription, priority);
			send.start();

		} else {
			AppWindow.log(Messages.getString("pushover.14")); //$NON-NLS-1$
		}
	}

	public static void sendpushover(String[] receiver, String Title,
			String Message, Integer priority) {
		// TODO Auto-generated method stub
		if (receiver.length > 0) {

			for (int i = 0; i < receiver.length; i++) {
				String key = receiver[i];
				String prio = "0";

				// define priorities
				if (priority == 5) {
					prio = "-1";
				} else if (priority == 4) {
					prio = "-1";
				} else if (priority == 3) {
					prio = "0";
				} else if (priority == 2) {
					prio = "1";
				} else if (priority == 1) {
					prio = "1";
				}

				AppWindow.log("event:" + Title + " description:" + Message );
				
				ClientConfig config = new DefaultClientConfig();
				Client client = Client.create(config);
				WebResource service = client.resource(UriBuilder.fromUri(
						"https://api.pushover.net/1/messages.json").build());
				Form form = new Form();
				form.add("token", instance.getAppKey());
				form.add("user", key);
				form.add("title", Title);
				form.add("message", Message);
				form.add("priority", prio);
				AppWindow.log(service.toString());

				ClientResponse response = service.type(
						MediaType.APPLICATION_FORM_URLENCODED).post(
						ClientResponse.class, form);
				AppWindow.log("Response " + response.getEntity(String.class));

			}

			// // Load parameters
			// String lApiKey = receiver[i];
			//		        String lAppName = Messages.getString("NMA.15"); //$NON-NLS-1$
			// String lEvent = subject;
			// String lDesc = content;
			// //convert priority to NMA's priority format -2, -1, 0, 1, 2
			// int lPriority = ( 6 - priority ) - 3;
			// String devKey = null;
			//
			// // lApiKey could be a list of comma separated keys, but notify
			// only accepts one key per call
			// if ( lApiKey.indexOf(',') == -1 ) {
			// if(NMAClientLib.verify(lApiKey) == 1) {
			//		                AppWindow.log(Messages.getString("NMA.16") + lApiKey + Messages.getString("NMA.17")); //$NON-NLS-1$ //$NON-NLS-2$
			// } else {
			// AppWindow.log(NMAClientLib.getLastError());
			// }
			// } else {
			//		            String apiKeysArray[] = lApiKey.split(","); //$NON-NLS-1$
			// for(int i1=0; i1<apiKeysArray.length; i1++) {
			// if(NMAClientLib.verify(apiKeysArray[i1]) == 0) {
			//		                	AppWindow.log(Messages.getString("NMA.19") + apiKeysArray[i1] + Messages.getString("NMA.20")); //$NON-NLS-1$ //$NON-NLS-2$
			// } else {
			// AppWindow.log(NMAClientLib.getLastError());
			// }
			// }
			// }
			//
			// // Sending a notification
			// if (NMAClientLib.notify(lAppName, lEvent, lDesc, lPriority,
			// lApiKey, devKey) == 1) {
			//		            AppWindow.log(Messages.getString("NMA.21")); //$NON-NLS-1$
			// } else {
			// AppWindow.log(NMAClientLib.getLastError());
			// }

			// AppWindow.log("Pushover module active, but not yet implemented");

		} else {
			AppWindow.log(Messages.getString("pushover.22")); //$NON-NLS-1$
		}

	}

	public String getAppKey() {
		return AppKey;
	}

	public void setAppKey(String appKey) {
		AppKey = appKey;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

}
