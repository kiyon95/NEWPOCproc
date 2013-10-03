package org.pocproc.data;

import java.io.File;
import java.util.HashMap;

import org.pocproc.app.AppWindow;
import org.pocproc.app.POCproc;
import org.pocproc.app.SubricPanel;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

@Root
public class Subrics {
	
	public static Subrics instance;
	
	@ElementMap
	private HashMap<String,String> content;
	
	@ElementMap(required=false)
	private HashMap<String,Integer> prios;
	
	public Subrics() {
		content = new HashMap<String,String>();
		prios   = new HashMap<String,Integer>();
		instance = this;
	}
	
	public static void put(String key, String alarm) {
		if (instance == null) {
			instance = new Subrics();
		}
		instance.content.put(key, alarm);
	}
	
	
	public static void put_prio(String key, Integer prio) {
		if (instance == null) {
			instance = new Subrics();
		}
		instance.prios.put(key, prio);
	}
	
	public static void del_prio(String key) {
		if (instance == null) {
			instance = new Subrics();
		}
		instance.prios.remove(key);
	}

	public static String getDefaultType(char subric) {
		if (instance == null) {
			return null;
		}
		return instance.content.get(String.valueOf(subric));
	}
	
	public static Integer getDefaultPrio(char subric) {
		if (instance == null) {
			return null;
		}
		return instance.prios.get(String.valueOf(subric));
	}
	
	public static Integer getDefaultPrio(String subric) {
		if (instance == null) {
			return null;
		}
		return instance.prios.get(subric);
	}
	
	
	
	public static String getDefaultType(String subric) {
		if (instance == null) {
			return null;
		}
		return instance.content.get(subric);
	}

	public static void init() {
		Serializer serializer = new Persister();

		try {
			File source = POCproc.getFile("subrics.xml"); //$NON-NLS-1$
			instance = serializer.read(Subrics.class, source);
		} catch (Exception e) {
			// nothing
			e.printStackTrace();
		}
		if (instance == null) {
			instance = new Subrics();
			put("A", Messages.getString("Subrics.2")); //$NON-NLS-1$ //$NON-NLS-2$
			put("B", Messages.getString("Subrics.4") ); //$NON-NLS-1$ //$NON-NLS-2$
			put("C", Messages.getString("Subrics.6") ); //$NON-NLS-1$ //$NON-NLS-2$
			put("D", Messages.getString("Subrics.8") ); //$NON-NLS-1$ //$NON-NLS-2$
			AppWindow.log(Messages.getString("Subrics.9")); //$NON-NLS-1$
		} else {
			AppWindow.log(instance.content.size()
					+ Messages.getString("Subrics.10")); //$NON-NLS-1$
		}

		// AppWindow.log(instance.content.toString());

		// AppWindow.log(result.getAbsolutePath());
	}
	
	public static void save() {
		SubricPanel.setSettings();
		
		Serializer serializer = new Persister();


		try {
			File result = POCproc.getFile("subrics.xml"); //$NON-NLS-1$
			serializer.write(instance, result);
			AppWindow.log(Messages.getString("Subrics.12") + result.getAbsolutePath()); //$NON-NLS-1$
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppWindow.log(Messages.getString("Subrics.13")); //$NON-NLS-1$
		}
	}
	
}
