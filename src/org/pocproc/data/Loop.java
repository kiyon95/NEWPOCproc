package org.pocproc.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Loop {

	@Element
	public String name;

	@Element(required = false)
	public String comment;

	@Element(required = false)
	public Integer prio_A;

	@Element(required = false)
	public Integer prio_B;

	@Element(required = false)
	public Integer prio_C;

	@Element(required = false)
	public Integer prio_D;

	@Element(required = false)
	public String type_A;

	@Element(required = false)
	public String type_B;

	@Element(required = false)
	public String type_C;

	@Element(required = false)
	public String type_D;

	// returns Alarm type
	public String getTypeForDatagram(Datagram d) {

		String ret_str = null;

		if (d.getSubric() == 'A') {
			ret_str = type_A;
		} else if (d.getSubric() == 'B') {
			ret_str = type_B;
		} else if (d.getSubric() == 'C') {
			ret_str = type_C;
		} else if (d.getSubric() == 'D') {
			ret_str = type_D;
		};
		
		// we do not have our own Alarm Type!
		if (ret_str == null) {
			ret_str = Subrics.getDefaultType(d.getSubric());
		} else if (ret_str.equals("")) {
			ret_str = Subrics.getDefaultType(d.getSubric());
		}
		
		// initialize if failed
		
		if (ret_str == null) ret_str = new String(Messages.getString("Loop.0")); //$NON-NLS-1$
		
		return ret_str;
		
		
		
	}

	// returns possibly null
	public Integer getPrioForDatagram(Datagram d) {

		Integer ret_int = null;
		
		if (d.getSubric() == 'A') {
			ret_int = prio_A;
		} else if (d.getSubric() == 'B') {
			ret_int = prio_B;
		} else if (d.getSubric() == 'C') {
			ret_int = prio_C;
		} else if (d.getSubric() == 'D') {
			ret_int = prio_D;
		}
		
		if (ret_int == null) {
			ret_int = Subrics.getDefaultPrio(d.getSubric());
		} else if (ret_int == 0) {
			ret_int = Subrics.getDefaultPrio(d.getSubric());
		}
		
		// if still null, '3' = 'NORMAL'
		if (ret_int == null) ret_int = 3;
		
		return ret_int;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Loop() {

	}

	public Loop(String name, String comment, Integer prio_A, Integer prio_B, Integer prio_C, Integer prio_D,
			String type_A, String type_B, String type_C, String type_D) {
		this.name = name;
		this.comment = comment;
		this.prio_A = prio_A;
		this.prio_B = prio_B;
		this.prio_C = prio_C;
		this.prio_D = prio_D;
		this.type_A = type_A;
		this.type_B = type_B;
		this.type_C = type_C;
		this.type_D = type_D;

	}

	public String toString() {
		return new String(name);
	}
}
