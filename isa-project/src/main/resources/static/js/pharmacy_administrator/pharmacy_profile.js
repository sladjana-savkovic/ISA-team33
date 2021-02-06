checkUserRole("ROLE_PHARMACYADMIN");
var pharmacyAdminId = getUserIdFromToken();
var pharmacyId;
$(document).ready(function () {
	
	clearLocalStorage();
	
	$.ajax({
		type:"GET", 
		url: "/api/pharmacy-admin/" + pharmacyAdminId,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(admin){
			pharmacyId = admin.pharmacyId;
			
			$.ajax({
				type:"GET", 
				url: "/api/pharmacy/" + pharmacyId,
				headers: {
            		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        		},
				contentType: "application/json",
				success:function(pharmacy){
					addPharmacyInfo(pharmacy);
					let inputAddress = pharmacy.address + ", " + pharmacy.cityName + ", " + pharmacy.countryName;
					let lat = pharmacy.latitude;
					let lng = pharmacy.longitude;
	
	//map
	var map = L.map('map_edit').setView([45.267136, 19.833549], 10);
	
	  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: '&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
	  }).addTo(map);
		  
    var geocodeService = L.esri.Geocoding.geocodeService();
	  
	var marker;
	var address = inputAddress;
		if(lat == null && lng==null){
		lat = 45.267136;
		lng =19.833549;
		}
	var lat_select = lat;
	var lng_select = lng;
			
	var newLatLng = new L.LatLng(lat, lng);
	    marker = new L.Marker(newLatLng);
	    map.addLayer(marker);
	    map.setView([lat,lng], 12);	  

	  map.on('click', function (e) {
		
	    geocodeService.reverse().latlng(e.latlng).run(function (error, result) {
	      if (error) {
	        return;
	      }
	     
	      if(marker != undefined){
	    	  map.removeLayer(marker)
	      }
	      
	       marker = new L.Marker(result.latlng);
	       map.addLayer(marker);
	       marker.bindPopup(transliterate(result.address.Match_addr)).openPopup();
	       address = transliterate(result.address.Match_addr);
	       lat_select = result.latlng.lat;
	       lng_select = result.latlng.lng;
	      
	    });
	  });
					
					for(i = 0; i < pharmacy.doctors.length; i++){
						addDoctor(pharmacy.doctors[i]);
					}
					
					for(i = 0; i < pharmacy.appointments.length; i++){
						addAppointment(pharmacy.appointments[i]);
					}
					
					$.ajax({
						type:"GET", 
						url: "/api/drug/" + pharmacyId + "/pharmacy",
						headers: {
            				'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        				},
						contentType: "application/json",
						success:function(drugs){	
							for(i = 0; i < drugs.length; i++){
								addDrug(drugs[i]);
							}
						},
						error:function(){
							console.log('error getting drugs');
						}
					});
					
				},
				error:function(){
					console.log('error getting pharmacy');
				}
				});
			
		},
		error:function(){
			console.log('error getting pharmacy administrator');
		}
	});
	
});

function addPharmacyInfo(pharmacy){
	document.getElementById('name').innerHTML = pharmacy.name;
	document.getElementById('grade').innerHTML = pharmacy.averageGrade;
	document.getElementById('address').innerHTML = pharmacy.address + ", " + pharmacy.cityName + ", " + pharmacy.countryName;

};

function addDoctor(doctor){
	 let row = $('<tr><td>'+ doctor.name +'</td><td>' + doctor.surname + '</td><td>' + doctor.typeOfDoctor + '</td></tr>');	
	$('#doctors').append(row);
};

function addDrug(drug){
	 let row = $('<tr><td>'+ drug.name +'</td><td>' + drug.producer + '</td><td>' + drug.typeOfDrug + '</td><td>'  + drug.typeOfDrugsForm + '</td></tr>');	
	$('#drugs').append(row);
};

function addAppointment(appointment){
	 let row = $('<tr><td>'+ appointment.startTime.split('T')[0] + " " + appointment.startTime.split('T')[1] +'</td><td>' + appointment.typeOfDoctor + " " + appointment.doctorSurname + '</td><td>' + appointment.price + '</td></tr>');		
	$('#appointments').append(row);
};

function transliterate(word){
    var answer = ""
      , a = {};
    
    a["А"]="A";a["а"]="a";a["Б"]="B";a["б"]="b";a["В"]="V";a["в"]="v";a["Г"]="G";a["г"]="g";a["Д"]="D";a["д"]="d";a["Ђ"]="Đ";a["ђ"]="đ";
    a["E"]="E";a["е"]="e";a["Ж"]="Ž";a["ж"]="ž";a["З"]="Z";a["з"]="z";a["И"]="I";a["и"]="i";a["Ј"]="J";a["ј"]="j";a["К"]="K";a["к"]="k";
    a["Л"]="L";a["л"]="l";a["Љ"]="Lj";a["љ"]="lj";a["М"]="M";a["м"]="m";a["Н"]="N";a["н"]="n";a["Њ"]="Nj";a["њ"]="nj";a["О"]="O";a["о"]="o";
    a["П"]="P";a["п"]="p";a["Р"]="R";a["р"]="r";a["С"]="S";a["с"]="s";a["Т"]="T";a["т"]="t";a["Ћ"]="Ć";a["ћ"]="ć";a["У"]="U";a["у"]="u";
    a["Ф"]="F"; a["ф"]="f";a["Х"]="H";a["х"]="h";a["Ц"]="C";a["ц"]="c";a["Ч"]="Č";a["ч"]="č";a["Џ"]="dž";a["џ"]="dž";a["Ш"]="Š";a["ш"]="š";

   for (i in word){
     if (word.hasOwnProperty(i)) {
       if (a[word[i]] === undefined){
         answer += word[i];
       } else {
         answer += a[word[i]];
       }
     }
   }
   return answer;
};

function initMap() {
    // maps are now initialized.
}

function clearLocalStorage(){
	localStorage.removeItem("pharmacyId");
}