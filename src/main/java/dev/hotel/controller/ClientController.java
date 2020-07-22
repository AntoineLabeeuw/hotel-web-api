package dev.hotel.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.dto.ClientDto;
import dev.hotel.entite.Client;
import dev.hotel.service.ClientService;

@RestController
@RequestMapping("clients")
public class ClientController {
	private ClientService clientService;

	/**
	 * Constructeur
	 * 
	 * @param clientRepository
	 */
	public ClientController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}

	// /clients?start=X&size=Y
	/**
	 * methode qui liste les clients de la BDD selon une page
	 * 
	 * @param start : numero de la page
	 * @param size  : nombre de client
	 * @return : une page des clients demandés
	 */
	@GetMapping
	public ResponseEntity<?> lister(@RequestParam("start") Integer start, @RequestParam("size") Integer size) {
		if (start == null || size == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vous devez valoriser start et size.");
		} else if (start < 0 || size < 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start et size doivent être > 0.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(clientService.findAll(start, size));
		}
	}

	/**
	 * méthode qui renvoie le client correspondant a son UUID. Si UUID non trouvé,
	 * renvoie un 404, client non trouvé
	 * 
	 * @param uuid : l'UUID du client
	 * @return : le client s'il il est trouvé
	 */
	@GetMapping("/{uuidString}")
	public ResponseEntity<?> findByUUID(@PathVariable String uuidString) {
		UUID uuid = null;
		try {
			uuid = UUID.fromString(uuidString);
		} catch (IllegalArgumentException i) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Spécifiez un UUID valide.\n" +i.getMessage());
		}
		Optional<Client> client = clientService.findByUuid(uuid);
		if (!client.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(client);
		}
	}

	/**
	 * methode qui permet de creer un client dans la BDD à partir de données
	 * comprises dans un JSON
	 * 
	 * @param client : le nom et le prenom du client, au format JSON
	 * @return : le client
	 */
	@PostMapping
	public ResponseEntity<?> postClients(@RequestBody @Valid ClientDto client, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le nom et le prenom doivent etre valorises.");
		}
		Client clientBase = clientService.creerClient(client.getNom(), client.getPrenoms());
		return ResponseEntity.status(HttpStatus.OK).body(clientBase);
	}
}
