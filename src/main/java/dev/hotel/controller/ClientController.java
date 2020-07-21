package dev.hotel.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@RestController
@RequestMapping("clients")
public class ClientController {
	private ClientRepository clientRepository;

	/**
	 * Constructeur
	 * 
	 * @param clientRepository
	 */
	public ClientController(ClientRepository clientRepository) {
		super();
		this.clientRepository = clientRepository;
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
			return ResponseEntity.status(HttpStatus.OK)
					.body(clientRepository.findAll(PageRequest.of(start, size)).toList());
		}
	}

	/**
	 * méthode qui renvoie le client correspondant a son UUID. Si UUID non trouvé,
	 * renvoie un 404, client non trouvé
	 * 
	 * @param uuid : l'UUID du client
	 * @return : le client s'il il est trouvé
	 */
	@GetMapping("/{uuid}")
	public ResponseEntity<?> FindByUUID(@PathVariable UUID uuid) {
		if (uuid == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Valorisez un UUID.");
		} else {
			Optional<Client> client = clientRepository.findById(uuid);
			if (!client.isPresent()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé.");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(client);
			}
		}
	}
}
