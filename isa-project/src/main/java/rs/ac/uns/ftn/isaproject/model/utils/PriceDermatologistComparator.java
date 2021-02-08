package rs.ac.uns.ftn.isaproject.model.utils;

import java.util.Comparator;

import rs.ac.uns.ftn.isaproject.dto.AddAppointmentDTO;

public class PriceDermatologistComparator implements Comparator<AddAppointmentDTO>{
	
	private Order order;
	
	public PriceDermatologistComparator(Order order) {
		// TODO Auto-generated constructor stub
		this.order = order;
	}
	@Override
	public int compare(AddAppointmentDTO o1, AddAppointmentDTO o2) {
		Double diffAsc = o1.price - o2.price;
		if(diffAsc != 0) {
			diffAsc = (double) (diffAsc > 0 ? 1 : -1);
		}
		Double diffDesc = -diffAsc;
		
		if (this.order == Order.ASC)
			return diffAsc.intValue();
		return diffDesc.intValue();
	}

}

