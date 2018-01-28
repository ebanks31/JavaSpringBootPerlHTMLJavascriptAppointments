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
	 * Shows the home page.
	 *
	 * @param model the model
	 * @return the page view
	 */
	@GetMapping(value = "/")
	public String home(final Model model) {
		HOME_LOGGER.info("Going to home page");
		//The front-end needs a new Appointment object to read from input fields for appointments.
		model.addAttribute("appointment", new Appointment());
		return HOME_PAGE;
	}

    /**
     * The get appointments controller
     *
     * @return the string
     * @throws IOException
     */
	@GetMapping(path = "/appointments.pl")
	@ResponseBody
    public String getAppointments(Model model, @RequestParam("searchString") String searchString) throws IOException {
		HOME_LOGGER.info("Going to the appointment");
		HOME_LOGGER.info("searchString: " + searchString);
	    model.addAttribute("appointment", new Appointment());
		String perlScript = "C:\\Perl64\\bin\\perl.exe C:\\\\Users\\\\Eric\\\\eclipse-workspace\\\\SpringBootAppointments\\\\src\\\\main\\\\resources\\\\static\\\\pl\\\\appointments.pl " +
				searchString;
		Process process = Runtime.getRuntime().exec(perlScript);
		HOME_LOGGER.info("process.getInputStream():" + process.getInputStream());
		HOME_LOGGER.info("process.getOutputStream():" + process.getOutputStream());

		BufferedReader stdInput = new BufferedReader(new
			     InputStreamReader(process.getInputStream()));

			String line = "";

			// read the output from the command
			System.out.println("EXE OUTPUT");
			String appointmentJSON = "";


			while ((line = stdInput.readLine()) != null) {
				HOME_LOGGER.info("row: " + line);
				if(line != null) {
					appointmentJSON = line;
				}
			}

			HOME_LOGGER.info("line: " + appointmentJSON);
			//String appointmentJSON = stdInput.readLine();

			if(appointmentJSON == null ||
					StringUtils.trim(appointmentJSON).equals(StringUtils.EMPTY)) {
				appointmentJSON = StringUtils.EMPTY;
			}
			HOME_LOGGER.info("appointmentJSON: " + appointmentJSON);

			return appointmentJSON;
	}

	/**
	 * The new appointment POST controller
	 *
	 * @param appointment the appointment
	 * @return the home page
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping("/newAppointment.pl")
    public String index2(@ModelAttribute Appointment appointment) throws IOException {
		HOME_LOGGER.info("Going to the appointment");
		HOME_LOGGER.info("appointment.getAppointmentDate(): " + appointment.getAppointmentDate());
		HOME_LOGGER.info("appointment.getAppointmentTime(): " + appointment.getAppointmentTime());
		HOME_LOGGER.info("appointment.getDescription():" + appointment.getDescription());
		String perlScript = "C:\\Perl64\\bin\\perl.exe C:\\\\Users\\\\Eric\\\\eclipse-workspace\\\\SpringBootAppointments\\\\src\\\\main\\\\resources\\\\static\\\\pl\\\\newAppointments.pl " +
				appointment.getAppointmentDate() + " " + appointment.getAppointmentTime() + " " + appointment.getDescription();
		HOME_LOGGER.info("perlScript: " + perlScript);

		Process process = Runtime.getRuntime().exec(perlScript);
		HOME_LOGGER.info("process.getInputStream():" + process.getInputStream());
		HOME_LOGGER.info("process.getOutputStream():" + process.getOutputStream());
		BufferedReader stdInput = new BufferedReader(new
			     InputStreamReader(process.getInputStream()));

			String line = "";

			// read the output from the command
			System.out.println("EXE OUTPUT");
			while ((line = stdInput.readLine()) != null) {
				HOME_LOGGER.info("row: " + line);
			}
		appointment.setAppointmentDate("");
		appointment.setAppointmentTime("");
		appointment.setDescription("");

        return "redirect:/";
    }
}