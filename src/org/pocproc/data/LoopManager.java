package org.pocproc.data;

import java.io.File;
import java.util.TreeMap;
import java.util.Vector;

import org.pocproc.app.AppWindow;
import org.pocproc.app.POCproc;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

@Root
public class LoopManager {

	@ElementMap(entry = "property", key = "key", attribute = true, inline = true)
	private TreeMap<String, Loop> content;

	private static LoopManager instance;

	public static void save() {

		Serializer serializer = new Persister();

		try {
			File loops = POCproc.getFile("rics.xml"); //$NON-NLS-1$
			serializer.write(instance, loops);
			AppWindow.log(Messages.getString("LoopManager.1") + loops.getAbsolutePath()); //$NON-NLS-1$
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppWindow.log(Messages.getString("LoopManager.2")); //$NON-NLS-1$
		}
	}

	public static void init() {
		Serializer serializer = new Persister();

		try {
			File source = POCproc.getFile("rics.xml"); //$NON-NLS-1$
			instance = serializer.read(LoopManager.class, source);
		} catch (Exception e) {
			// nothing
			e.printStackTrace();
		}
		if (instance == null) {
			instance = new LoopManager();
			Loop temp = new Loop("Initial", Messages.getString("LoopManager.5"), null, null, null, null, null, null, null, null); //$NON-NLS-1$ //$NON-NLS-2$
			put("1234567", temp); //$NON-NLS-1$
			AppWindow.log(Messages.getString("LoopManager.7")); //$NON-NLS-1$
		} else {
			AppWindow.log(instance.content.size()
					+ Messages.getString("LoopManager.8")); //$NON-NLS-1$
		}

		// AppWindow.log(instance.content.toString());

		// AppWindow.log(result.getAbsolutePath());
	}

	public LoopManager() {
		content = new TreeMap<String, Loop>();
	}

	public Loop get(String ric) {
		return content.get(ric);
	}

	public static void put(String ric, Loop loop) {
		if (instance.content == null) {
			init();
		}
		instance.content.put(ric, loop);
	}

	public static String[] getRics() {
		// return (String[]) instance.content.keySet().

		return (String[]) instance.content.keySet().toArray(
				new String[instance.content.keySet().size()]);

	}

	public static Vector<String> getRicsLongCoded() {
		String[] data = LoopManager.getRics();
		Vector<String> tmp = new Vector<String>();

		for (int i = 0; i < data.length; i++) {
			tmp.add(((String) data[i] + "/" + LoopManager.getRic( //$NON-NLS-1$
					(String) data[i]).getName()));
		}

		return tmp;

	}

	public static Loop getRic(String key) {
		return instance.content.get(key);
	}

	public static void delete(String selloop) {
		instance.content.remove(selloop);

	}

}
