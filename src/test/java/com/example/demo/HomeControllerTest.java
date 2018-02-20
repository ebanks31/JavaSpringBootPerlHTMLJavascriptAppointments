package com.example.demo;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.controller.HomeController;
import com.example.demo.model.Appointment;
import com.example.demo.utils.Shell;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeControllerTest {
	@Mock
	private Shell shellMock;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

    @InjectMocks
    private HomeController homeController;

	//@Mock
	//private UserService userService;

	//@Mock
	//User mockUser;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	    this.mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
		//this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testHomePage() throws Exception {
		this.mockMvc.perform(get("/")
                .contentType(MediaType.TEXT_PLAIN))
				.andExpect(MockMvcResultMatchers.view().name("index"))
				.andExpect(status().isOk())
		        .andExpect(model().attributeExists("appointment"));
	}

	@Test
	public void testListAppointments() throws Exception {
		Appointment appointment = new Appointment();
		appointment.setAppointmentDate("Appointment Date");
		appointment.setAppointmentTime("Appointment Time");
		appointment.setDescription("Description");

		Appointment appointment2 = new Appointment();
		appointment2.setAppointmentDate("Appointment Date2");
		appointment2.setAppointmentTime("Appointment Time2");
		appointment2.setDescription("Description2");
		//Process process = new ProcessImpl;

		String appointmentJson = "[{\"date\":\"2007-01-01\",\"time\":\"10:00:00\",\"description\":\"Test\"},{\"date\":\"2007-01-01\",\"time\":\"10:00:00\",\"description\":\"Test\"}";
		Mockito.when(shellMock.exec(Mockito.anyString())).thenReturn(null);

		//assertEquals(userService.listUsers().size(), 2);
		this.mockMvc.perform(get("/appointments")
                .contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andExpect(content().string("[]"));

		verify(shellMock).exec(Mockito.anyString());

		//verify(userService, times(1)).listUsers();
		//verifyNoMoreInteractions(userService);
	}

}
