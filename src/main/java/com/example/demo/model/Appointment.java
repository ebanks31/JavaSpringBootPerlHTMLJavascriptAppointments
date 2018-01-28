package com.example.demo.model;

import java.util.Date;

/**
 * The Class Appointment.
 */
public class Appointment {

	/** The appointment date. */
	private String appointmentDate;

	/** The appointment time. */
	private String appointmentTime;

	/** The description. */
	private String description;

	/**
	 * Gets the appointment date.
	 *
	 * @return the appointment date
	 */
	public String getAppointmentDate() {
		return appointmentDate;
	}

	/**
	 * Sets the appointment date.
	 *
	 * @param appointmentDate the new appointment date
	 */
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	/**
	 * Gets the appointment time.
	 *
	 * @return the appointment time
	 */
	public String getAppointmentTime() {
		return appointmentTime;
	}

	/**
	 * Sets the appointment time.
	 *
	 * @param appointmentTime the new appointment time
	 */
	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
