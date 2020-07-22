package dev.hotel.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import dev.hotel.entite.Chambre;
import dev.hotel.entite.Client;
import dev.hotel.entite.Reservation;
import dev.hotel.repository.ChambreRepository;
import dev.hotel.repository.ClientRepository;
import dev.hotel.repository.ReservationRepository;

@Service
public class ReservationService {
	private ReservationRepository resRepo;
	private ClientRepository cliRepo;
	private ChambreRepository chaRepo;

	/**
	 * Constructeur
	 * 
	 * @param repo
	 */
	public ReservationService(ReservationRepository resRepo, ClientRepository cliRepo, ChambreRepository chaRepo) {
		super();
		this.resRepo = resRepo;
		this.cliRepo = cliRepo;
		this.chaRepo = chaRepo;
	}

	/**
	 * permet de chercher un client dans la BDD. Retourne true si le client est
	 * trouvé, false si non
	 * 
	 * @param uuid : l'uuid du client a rechercher
	 * @return : un boolean
	 */
	public Boolean clientPresentBdd(UUID uuid) {
		Optional<Client> cl = cliRepo.findById(uuid);
		if (cl.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * permet de verifier que les chambres existent. Les UUID des chambres
	 * manquantes sont renvoyées.
	 * 
	 * @param uuids : les uuid des chambres a verifier
	 * @return : les uuid des chambres manquantes. Retourne une liste vide si elles
	 *         sont toutes présentes
	 */
	public List<UUID> chambresPresentes(List<UUID> uuids) {
		List<UUID> uuidsNonTrouvees = new ArrayList<>();
		for (UUID uuid : uuids) {
			Optional<Chambre> chambre = chaRepo.findById(uuid);
			if (!chambre.isPresent()) {
				uuidsNonTrouvees.add(uuid);
			}
		}
		return uuidsNonTrouvees;
	}

	/**
	 * permet de créer une réservation à partir d'informations données et
	 * préalablement vérifiées.
	 * 
	 * @param dateDebut     : date de début de la réservation
	 * @param dateFin       : date de fin de la réservation
	 * @param clientUuid    : uuid du client
	 * @param chambresUuids : uuid des chambres
	 * @return : la réservation créee
	 */
	@Transactional
	public Reservation creerReservation(LocalDate dateDebut, LocalDate dateFin, UUID clientUuid,
			List<UUID> chambresUuids) {
		Optional<Client> client = cliRepo.findById(clientUuid);
		List<Chambre> chambres = chaRepo.findAllById(chambresUuids);
		Reservation resa = new Reservation();
		resa.setDateDebut(dateDebut);
		resa.setDateFin(dateFin);
		resa.setClient(client.get());
		resa.setChambres(chambres);
		return resRepo.save(resa);
	}

}
