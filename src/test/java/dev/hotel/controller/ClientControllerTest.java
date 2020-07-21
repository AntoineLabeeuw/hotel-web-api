package dev.hotel.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@WebMvcTest
public class ClientControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	ClientRepository repo;

	@Test
	void listTest() throws Exception {
		List<Client> l = new ArrayList<>();
		Client c1 = new Client();
		c1.setUuid(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b192"));
		c1.setNom("Odd");
		c1.setPrenoms("Ross");
		Client c2 = new Client();
		c2.setUuid(UUID.fromString("f9a18170-9605-4fe6-83c8-d03a53e08bfe"));
		c2.setNom("Don");
		c2.setPrenoms("Duck");

		l.add(c1);
		l.add(c2);

		Page<Client> p = new PageImpl<>(l);

		Mockito.when(repo.findAll(PageRequest.of(0, 2))).thenReturn(p);

		mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=0&size=2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].uuid").value("dcf129f1-a2f9-47dc-8265-1d844244b192"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Odd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].prenoms").value("Ross"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].uuid").value("f9a18170-9605-4fe6-83c8-d03a53e08bfe"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].nom").value("Don"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].prenoms").value("Duck"));
	}

	@Test
	public void sizeNegativeTest() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=0&size=-1"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
		assertThat(result.getResponse().getContentAsString()).contains("Start et size doivent être > 0.");
	}

	@Test
	public void startNullTest() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=&size=2"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
		assertThat(result.getResponse().getContentAsString()).contains("Vous devez valoriser start et size.");
	}

	@Test
	public void findByUuid200Test() throws Exception {
		Client c1 = new Client();
		c1.setNom("Odd");
		c1.setPrenoms("Ross");
		c1.setUuid(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b192"));
		Optional<Client> c2 = Optional.of(c1);
		Mockito.when(repo.findById(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b192"))).thenReturn(c2);
		mockMvc.perform(MockMvcRequestBuilders.get("/clients/dcf129f1-a2f9-47dc-8265-1d844244b192"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.uuid").value("dcf129f1-a2f9-47dc-8265-1d844244b192"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Odd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.prenoms").value("Ross"));
	}

	@Test
	public void findByUuid404Test() throws Exception {
		Mockito.when(repo.findById(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b193")))
				.thenReturn(Optional.empty());
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/clients/dcf129f1-a2f9-47dc-8265-1d844244b193"))
				.andExpect(MockMvcResultMatchers.status().is(404)).andReturn();
		assertThat(result.getResponse().getContentAsString()).contains("Client non trouvé.");
	}

}
