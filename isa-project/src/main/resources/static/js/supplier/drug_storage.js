checkUserRole("ROLE_SUPPLIER");
var supplierId = getUserIdFromToken();

$(document).ready(function () {

	alert("drug storage, supplier = " + supplierId);

	
});
