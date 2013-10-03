package org.pocproc.data;

import java.util.Vector;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root
public class Person {


	@Element(required=false)
	public String name;
	@Element(required=false)
	public String surname;
	@Element(required=false)
	public String email;
	@Element(required=false)
	public String email2;
	@Element(required=false)
	public String prowlkey;
	@Element(required=false)
	public String nmakey;
	@Element(required=false)
	public String mobile;
	@Element(required=false)
	public String pushoverkey;
	@ElementList(required=false)
	public Vector<String> loops;
	
	public String toString() {
		return this.name + this.surname;
		
	}
	
	public Person(String name, String surname, String email, String email2, String prowlkey, String nmakey, String pushoverkey, String mobile, Vector<String> loops) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.email2 = email2;
		this.prowlkey = prowlkey;
		this.loops = loops;
		this.mobile = mobile;
		this.nmakey = nmakey;
		this.pushoverkey = pushoverkey;
		
	}

	public Vector<String> getLoops() {
		return loops;
	}

	public void setLoops(Vector<String> loops) {
		this.loops = loops;
	}

	public Person() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProwlkey() {
		return prowlkey;
	}

	public void setProwlkey(String prowlkey) {
		this.prowlkey = prowlkey;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getNmakey() {
		return nmakey;
	}

	public void setNmakey(String nmakey) {
		this.nmakey = nmakey;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPushoverkey() {
		return pushoverkey;
	}

	public void setPushoverkey(String pushoverkey) {
		this.pushoverkey = pushoverkey;
	}
	
}
