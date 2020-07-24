package dev.hotel.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.hotel.service.ChambreService;
import dev.hotel.service.ClientService;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	ClientService cliService;
	@MockBean
	ChambreService chaService;

	@Test
	public void creerReservationInfosManquantes() {
		// on s'attend a une BAD_REQUEST
		String jsonBody = "{\"dateDebut\" : \"2019-10-01\",\"dateFin\" : \"2019-10-10\",\"clientId\" : \"dcf129f1-a2f9-47dc-8265-1d844244b192\",\"chambres\" : [\"b13e05d9-d9a9-49a9-80cb-ee03da248102\",\"666a2188-8be5-41ce-8800-9f7ba4618521\",\"0a0d4672-a273-4e1f-b399-8272ae81296f\"] }";
	}
}
