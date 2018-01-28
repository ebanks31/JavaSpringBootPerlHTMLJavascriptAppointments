$(document).ready(function(){
	    getAppointments("");
	    windows.alert("HU");
	    $("#searchButton").click(function(){
			$("#appointmentTable").empty();
			var searchString = $('#searchText').val();
			getAppointments(searchString);
		});
		/*
	   $("#newButton").click(function(event){
		   if($("#newButton").val()==='New') {
			   	window.alert("HERE");

			//Don't submit the information
		    event.stopPropagation();
			$("#newButton").val("Add");
			$("#inputFields").show();
		   }
		   else {
			   //input fields is Add
			   var appointmentDate = $("#appointmentDate").val();
			   var appointmentTime = $("#appointmentTime").val();
			   var description = $("#description").val();

			   $.get("newAppointment.pl", {word: $(this).val()}, function(data){
					alert(data);
					$("#myWord").empty();
					$("#myWord").html(data);
				});

		   }
		});*/

	$("#cancelButton").click(function(){
		    $("#newButton").val("New");
			$("#inputFields").hide();
		});
});

function getAppointments(searchString) {
	    //Initialize table bootstrap class
		$("#appointmentTable").append("<table class=\"table\"><thead><tr><th scope=\"col\">Date</th><th scope=\"col\">Time</th><th scope=\"col\">Description</th></tr></thead>");
	                        $.ajax({
                            type: 'GET',
                            url: '/cgi-bin/ajax/stackCGI/appointments.pl',
                            data: {"searchString": searchString},
                            success: function(appointments) {
								for (i = 0; i < appointments.length; i++) {
								//Put in for loop for all appointments found
								$("#appointmentTable").append("<tbody><tr>" + res.dateTime + "</tr><tr>" +res.description + "</tr></tbody>");
								}
                                                    },
                            error: function() {alert("Error has occurred when retrieving appointments");}
                    });
		if (isEmpty(searchString)) {

		}
		else {
			//Get appointment by search string then display.
			$("#appointmentTable").append("<tbody><tr></tr></tbody>");
		}

	    $("#appointmentTable").append("</table>");

}

function isEmpty(str) {
    return (!str || 0 === str.length);
}

function newAppointment() {
		   if($("#newButton").val()==='New') {
			//Don't submit the information
		    event.stopPropagation();
			$("#newButton").val("Add");
			$("#inputFields").show();
			return false;

		   }
		   else {
			   //input fields is Add
			   var appointmentDate = $("#appointmentDate").val();
			   var appointmentTime = $("#appointmentTime").val();
			   var description = $("#description").val();

			   return true;
		   }
}