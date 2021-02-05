package rs.ac.uns.ftn.isaproject.model.utils;

import java.util.Comparator;

import rs.ac.uns.ftn.isaproject.dto.AddAppointmentDTO;

public class PriceComparator implements Comparator<AddAppointmentDTO>{
	
	private Order order;
	
	public PriceComparator(Order order) {
		// TODO Auto-generated constructor stub
		this.order = order;
	}
	@Override
	public int compare(AddAppointmentDTO o1, AddAppointmentDTO o2) {
		Double diffAsc = o1.price - o2.price;
		Double diffDesc = o2.price - o1.price;
		if (this.order == Order.ASC)
			return diffAsc.intValue();
		return diffDesc.intValue();
	}

}

