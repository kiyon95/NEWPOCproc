package org.pocproc.data;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import org.pocproc.app.AppWindow;
import org.pocproc.app.POCproc;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

@Root(name = "persons")
public class PersonManager {

	@ElementMap(entry = "person", key = "key", attribute = true, inline = true)
	private HashMap<String, Person> content;

	private static PersonManager instance;

	public static String[] getPersons() {

		return (String[]) instance.content.keySet().toArray(
				new String[instance.content.keySet().size()]);

	}

	public static void save() {

		Serializer serializer = new Persister();


		try {
			File result = POCproc.getFile("persons.xml"); //$NON-NLS-1$
			serializer.write(instance, result);
			AppWindow.log(Messages.getString("PersonManager.1") + result.getAbsolutePath()); //$NON-NLS-1$
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppWindow.log(Messages.getString("PersonManager.2")); //$NON-NLS-1$
		}
	}

	public static void init() {
		Serializer serializer = new Persister();

		try {
			File source = POCproc.getFile("persons.xml"); //$NON-NLS-1$
			instance = serializer.read(PersonManager.class, source);
		} catch (Exception e) {
			// nothing
			e.printStackTrace();
		}
		if (instance == null) {
			instance = new PersonManager();
			Person temp = new Person(Messages.getString("PersonManager.4"), Messages.getString("PersonManager.5"), Messages.getString("PersonManager.6"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					"", "", "", "", "", null); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			put(temp);
			AppWindow.log(Messages.getString("PersonManager.11")); //$NON-NLS-1$
		} else {
			AppWindow.log(instance.content.size()
					+ Messages.getString("PersonManager.12")); //$NON-NLS-1$
		}

		// AppWindow.log(instance.content.toString());

		// AppWindow.log(result.getAbsolutePath());
	}

	public HashMap<String, Person> getMap() {
		return content;
	}

	public PersonManager() {
		content = new HashMap<String, Person>();
	}

	public static void put(Person pers) {
		if (instance.content == null) {
			init();
		}
		instance.content.put(pers.getName() + " " + pers.getSurname(), pers); //$NON-NLS-1$
	}

	public void addPerson(Person pers) {
		content.put(pers.name + " " + pers.surname, pers); //$NON-NLS-1$
	}

	public static Person getPerson(String key) {
		return instance.content.get(key);
	}

	public static Person[] getPersonsForLoop(String loop) {
		Vector<Person> temp = new Vector<Person>();

		AppWindow.log(Messages.getString("PersonManager.15") + loop); //$NON-NLS-1$

		String[] keyset = instance.content.keySet().toArray(
				new String[instance.content.keySet().size()]);

		for (int i = 0; i < keyset.length; i++) {

			if (instance.content.get(keyset[i]).loops != null) {
				if (instance.content.get(keyset[i]).loops.contains(loop)) {
					Person tmp = instance.content.get(keyset[i]);
					AppWindow.log(Messages.getString("PersonManager.16") + tmp.surname + " " + tmp.name); //$NON-NLS-1$ //$NON-NLS-2$
					temp.add(tmp);
				}
			}
		}

		return (Person[]) temp.toArray(new Person[temp.size()]);
	}

	public static void delete(Object selectedValue) {
		// TODO Auto-generated method stub
		instance.content.remove(selectedValue);
	}

}
