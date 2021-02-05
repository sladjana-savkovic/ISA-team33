package rs.ac.uns.ftn.isaproject.model.utils;

import java.util.Comparator;

import rs.ac.uns.ftn.isaproject.dto.AddAppointmentDTO;

public class GradeComparator implements Comparator<AddAppointmentDTO>{
	
	private Order order;
	
	public GradeComparator(Order order) {
		// TODO Auto-generated constructor stub
		this.order = order;
	}
	@Override
	public int compare(AddAppointmentDTO o1, AddAppointmentDTO o2) {
		Double diffAsc = o1.averageGrade - o2.averageGrade;
		Double diffDesc = o2.averageGrade - o1.averageGrade;
		if (this.order == Order.ASC)
			return diffAsc.intValue();
		return diffDesc.intValue();
	}

}

