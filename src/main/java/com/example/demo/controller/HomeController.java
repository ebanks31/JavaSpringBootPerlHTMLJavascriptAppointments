package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Appointment;
import com.example.demo.utils.Shell;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The HomeController for handling REST request from the home page.
 */
@Controller
public class HomeController {

	/** The Constant HOME_LOGGER. */
	private static final Logger HOME_LOGGER = Logger.getLogger(HomeController.class);

	/** The Constant HOME_PAGE. */
	private static final String HOME_PAGE = "index";

	private final Shell shell = new Shell();

	/**
	 * Shows the appointment page.
	 *
	 * @param model
	 *            the model
	 * @return the page view
	 */
	@GetMapping(value = "/")
	@ApiOperation(value = "Returns user details", notes = "Returns a complete list of users details with a date of last modification.", response = Appointment.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval of user detail", response = Appointment.class),
			@ApiResponse(code = 404, message = "User with given username does not exist"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public String retrieveHomePage(final Model model) {
		HOME_LOGGER.info("Going to home page");
		// The front-end needs a new Appointment object to read from input fields for
		// appointments.
		model.addAttribute("appointment", new Appointment());
		return HOME_PAGE;
	}

	/**
	 * GET Controller for getting all appointments if based on the search string. If
	 * Search parameter is used, then get appointments only if description column
	 * contain search string parameter. This method returns a response body.
	 *
	 * @param model
	 *            the model
	 * @param searchString
	 *            the search string
	 * @return all appointments if search string is empty or null, Otherwise get
	 *         appointments only if description column contain search string
	 *         parameter.
	 */
	@GetMapping(path = "/appointmentsResponseBody")
	@ResponseBody
	@ApiOperation(value = "Returns list of appointments", notes = "Returns a complete list of appointments.", response = Appointment.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval of appointments", response = Appointment.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "Appointment is not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public String getAppointments(@RequestParam(value = "searchString", required = false) String searchString) {

		HOME_LOGGER.info("Retrieving all appointments");
		String perlScript = "C:\\Perl64\\bin\\perl.exe C:\\\\Users\\\\Eric\\\\eclipse-workspace\\\\SpringBootAppointments\\\\src\\\\main\\\\resources\\\\static\\\\pl\\\\appointments.pl "
				+ searchString;
		Process process;
		String appointmentJSON = StringUtils.EMPTY;

		try {
			// Executing PERL script for retrieving all appointments
			process = shell.exec(perlScript);

			// Reading appointment JSON from PERL script and outputting to the view.
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = StringUtils.EMPTY;

			while ((line = stdInput.readLine()) != null) {
				HOME_LOGGER.info("row: " + line);
				if (line != null) {
					appointmentJSON = line;
				}
			}
		} catch (IOException e) {
			HOME_LOGGER.warn("Unable to get all appointments");
		}

		// Return emptyString is appointmentJSON is null or empty.
		if (StringUtils.isBlank(StringUtils.trim(appointmentJSON))) {
			appointmentJSON = StringUtils.EMPTY;
		}

		return appointmentJSON;
	}

	/**
	 * GET Controller for getting all appointments based on the search string. If
	 * Search parameter is used, then get appointments only if description column
	 * contain search string parameter This method returns a response entity.
	 *
	 * @param model
	 *            the model
	 * @param searchString
	 *            the search string
	 * @param errors
	 *            the errors
	 * @return all appointments if search string is empty or null, Otherwise get
	 *         appointments only if description column contain search string
	 *         parameter.
	 */
	@GetMapping(path = "/appointments")
	@ApiOperation(value = "Returns list of appointments", notes = "Returns a complete list of appointments.", response = Appointment.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval of appointments", response = Appointment.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "Appointment is not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<?> getAppointmentsResponseEntity(
			@RequestParam(value = "searchString", required = false) String searchString) {
		HOME_LOGGER.info("Retrieving all appointments");

		String perlScript = "C:\\Perl64\\bin\\perl.exe C:\\\\Users\\\\Eric\\\\eclipse-workspace\\\\SpringBootAppointments\\\\src\\\\main\\\\resources\\\\static\\\\pl\\\\appointments.pl "
				+ searchString;
		Process process;
		String appointmentJSON = StringUtils.EMPTY;

		try {
			// Executing PERL script for retrieving all appointments
			process = shell.exec(perlScript);

			// Reading appointment JSON from PERL script and outputting to the view.
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = StringUtils.EMPTY;

			while ((line = stdInput.readLine()) != null) {
				HOME_LOGGER.info("row: " + line);
				appointmentJSON = line;
			}
		} catch (IOException e) {
			HOME_LOGGER.warn("Unable to get all appointments");
			return new ResponseEntity<String>(StringUtils.EMPTY, HttpStatus.NO_CONTENT);
		}

		// Return emptyString is appointmentJSON is null or empty.
		if (StringUtils.isBlank(StringUtils.trim(appointmentJSON))) {
			appointmentJSON = StringUtils.EMPTY;
		}
		// HttpHeaders headers = new HttpHeaders();
		// headers.add("Content-Type", "application/json");

		return new ResponseEntity<String>(appointmentJSON, HttpStatus.OK);
	}

	/**
	 * The POST controller for adding a new appointment.
	 *
	 * @param appointment
	 *            the appointment
	 * @return the home page
	 */
	@PostMapping("/newAppointment")
	@ApiOperation(value = "Returns home page", notes = "Adds a new appointment", response = Appointment.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful addition of new appointment", response = Appointment.class),
			@ApiResponse(code = 404, message = "User unable to add appointment"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public String addNewAppointment(@Valid @ModelAttribute Appointment appointment, BindingResult result) {
		HOME_LOGGER.info("Adds a new appointment");

		// If error, just return a 400 bad request, along with the error message
		if (result.hasErrors()) {
			return result.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(","));
		}

		String perlScript = "C:\\Perl64\\bin\\perl.exe C:\\\\Users\\\\Eric\\\\eclipse-workspace\\\\SpringBootAppointments\\\\src\\\\main\\\\resources\\\\static\\\\pl\\\\newAppointments.pl "
				+ appointment.getAppointmentDate() + " " + appointment.getAppointmentTime() + " "
				+ appointment.getDescription();
		try {
			// Executing PERL script for adding a new appointment
			shell.exec(perlScript);
		} catch (IOException e) {
			HOME_LOGGER.warn("Unable to add appointment");
		}

		return "redirect:/";
	}
}