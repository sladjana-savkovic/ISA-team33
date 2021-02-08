package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantitySupplierDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantitySupplier;

public class DrugQuantitySupplierMapper {

	public static Collection<DrugQuantitySupplierDTO> toDrugQuantitySupplierDTOs(Collection<DrugQuantitySupplier> drugQuantitySuppliers){
		Collection<DrugQuantitySupplierDTO> drugQuantitySupplierDTOs = new ArrayList<>();
		for(DrugQuantitySupplier d : drugQuantitySuppliers) {
			DrugQuantitySupplierDTO dto = new DrugQuantitySupplierDTO();
			dto.drugQuantitySupplierId = d.getId();
			dto.drugName = d.getDrug().getName();
			dto.drugid = d.getDrug().getId();
			dto.quantity = d.getQuantity();
			dto.supplierId = d.getSupplier().getId();
			drugQuantitySupplierDTOs.add(dto);
		}
		return drugQuantitySupplierDTOs;
	}	
	
}
