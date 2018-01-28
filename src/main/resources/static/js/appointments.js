//<![CDATA[
$(document).ready(function() {
	getAppointments("");

	$("#searchButton").click(function() {
		$("#appointmentTable").empty();
		getAppointments($('#searchText').val());
	});

	$("#cancelButton").click(function() {
		$("#newButton").val("New");
		$("#inputFields").hide();
	});
});

function getAppointments(searchString) {
	// Clear appointment table
	$("#appointmentTable").empty();

		 // Getting appointment page based on search parameters.
	     $.ajax({
				type : 'GET',
				url : '/appointments.pl',
				data : {
					"searchString" : searchString.trim()
				},
				success : function(appointmentsString) {

					if (!isEmpty(appointmentsString)) {

						// Initialize table bootstrap class
						var tableHeader = "<table id=\"appointmentTable\" class=\"table table-hover\"><thead><tr><th scope=\"col\">Date</th>"
								+ "<th scope=\"col\">Time</th><th scope=\"col\">Description</th></tr></thead>";

						// Converting appointments string to JSONArray.
						var appointments = JSON.parse(appointmentsString);
						console.log("appointments: " + appointments);

						var tableFullRow = "";

						// Gets all appointments based on JSONArray that
						// retrieved.
						for (var i = 0; i < appointments.length; i++) {
							var tableRow = "<tr>"
							var appointmentDateRow = "<td>"
									+ appointments[i]["date"] + "</td>";
							var appointmentTimeRow = "<td>"
									+ appointments[i]["time"] + "</td>";
							var appointmentDescriptionRow = "<td>"
									+ appointments[i]["description"]
									+ "</td></tr>";

							tableFullRow += tableRow + appointmentDateRow
									+ appointmentTimeRow
									+ appointmentDescriptionRow;
						}

						var tableEnder = "</tbody></table>";

						var appointmentTableOutput = tableHeader + tableFullRow
								+ tableEnder;
						console.log(tableHeader);
						console.log(tableFullRow);
						console.log(tableEnder);

						// Adding appointment table to HTML page.
						$("#appointmentTable").append(appointmentTableOutput);
						console.log($("#appointmentTable").html())
					}
				},
				error : function() {
					// Clear appointment table
					$("#appointmentTable").empty();
					$("#appointmentTable")
							.append(
									"<b>There was an error retrieving appointments. Please refresh page.</b>");
				}
			});

}

function newAppointment() {
	if ($("#newButton").val() === 'New') {
		event.stopPropagation();
		$("#newButton").val("Add");
		$("#inputFields").show();
		return false;

	} else {
		return true;
	}
}

function isEmpty(str) {
	return (!str || 0 === str.length);
}

//]]>