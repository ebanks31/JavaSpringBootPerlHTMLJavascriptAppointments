package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Appointment;

/**
 * The HomeController for handling REST request from the home page.
 */
@Controller
public class HomeController {

	private static final Logger HOME_LOGGER = Logger.getLogger(HomeController.class);
	private static final String HOME_PAGE = "index";

	/**
	 * Shows the appointment page.
	 *
	 * @param model
	 *            the model
	 * @return the page view
	 */
	@GetMapping(value = "/")
	public String home(final Model model) {
		HOME_LOGGER.info("Going to home page");
		// The front-end needs a new Appointment object to read from input fields for
		// appointments.
		model.addAttribute("appointment", new Appointment());
		return HOME_PAGE;
	}

	/**
	 * GET Controller for getting all appointments if search string is empty.
	 * If Search parameter is used, then gets the appointments
	 *
	 * @param model the model
	 * @param searchString the search string
	 * @return the appointments
	 */
	@GetMapping(path = "/appointments.pl")
	@ResponseBody
	public String getAppointments(Model model, @RequestParam("searchString") String searchString) {
		HOME_LOGGER.info("Retrieving all apointments");
		model.addAttribute("appointment", new Appointment());
		String perlScript = "C:\\Perl64\\bin\\perl.exe C:\\\\Users\\\\Eric\\\\eclipse-workspace\\\\SpringBootAppointments\\\\src\\\\main\\\\resources\\\\static\\\\pl\\\\appointments.pl "
				+ searchString;
		Process process;
		String appointmentJSON = StringUtils.EMPTY;

		try {
			process = Runtime.getRuntime().exec(perlScript);

		//Reading appointment JSON from PERL script and outputting to the view.
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = StringUtils.EMPTY;

		while ((line = stdInput.readLine()) != null) {
			HOME_LOGGER.info("row: " + line);
			if (line != null) {
				appointmentJSON = line;
			}
		}
		} catch (IOException e) {
			HOME_LOGGER.warn("Unable to get all appointment");
		}

		//Return emptyString is appointmentJSON is null or empty.
		if (StringUtils.isBlank(StringUtils.trim(appointmentJSON))) {
			appointmentJSON = StringUtils.EMPTY;
		}

		return appointmentJSON;
	}

	/**
	 * The POST controller for adding a new appointment.
	 *
	 * @param appointment
	 *            the appointment
	 * @return the home page
	 */
	@PostMapping("/newAppointment.pl")
	public String index2(@ModelAttribute Appointment appointment) {
		HOME_LOGGER.info("Add a new appointment");
		String perlScript = "C:\\Perl64\\bin\\perl.exe C:\\\\Users\\\\Eric\\\\eclipse-workspace\\\\SpringBootAppointments\\\\src\\\\main\\\\resources\\\\static\\\\pl\\\\newAppointments.pl "
				+ appointment.getAppointmentDate() + " " + appointment.getAppointmentTime() + " "
				+ appointment.getDescription();
		try {
			Runtime.getRuntime().exec(perlScript);
		} catch (IOException e) {
			HOME_LOGGER.warn("Unable to add appointment");
		}

		return "redirect:/";
	}
}