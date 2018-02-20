package com.example.demo.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * This class holds the appointment object.
 */
public class Appointment {

	/** The appointment date. */
    @NotNull
    @NotEmpty
    @Valid
	private String appointmentDate;

	/** The appointment time. */
    @NotNull
    @NotEmpty
    @Valid
	private String appointmentTime;

	/** The description. */
    @NotNull
	private String description;

	/** The message. */
	private String message;

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

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
	 * @param appointmentDate
	 *            the new appointment date
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
	 * @param appointmentTime
	 *            the new appointment time
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
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
