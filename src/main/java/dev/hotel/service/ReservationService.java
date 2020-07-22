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
import dev.hotel.exception.ReservationException;
import dev.hotel.repository.ReservationRepository;

/**
 * Classe de service des réservations
 * 
 * @author antoinelabeeuw
 *
 */
@Service
public class ReservationService {
	private ReservationRepository resRepo;
	private ClientService cliService;
	private ChambreService chaService;

	/**
	 * Constructeur
	 * 
	 * @param repo
	 */
	public ReservationService(ReservationRepository resRepo, ClientService cliService, ChambreService chaService) {
		super();
		this.resRepo = resRepo;
		this.cliService = cliService;
		this.chaService = chaService;
	}

	/**
	 * permet de chercher un client dans la BDD. Retourne true si le client est
	 * trouvé, false si non
	 * 
	 * @param uuid : l'uuid du client a rechercher
	 * @return : un boolean
	 */
	public Boolean clientPresentBdd(UUID uuid) {
		Optional<Client> cl = cliService.findByUuid(uuid);
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
			Optional<Chambre> chambre = chaService.findById(uuid);
			if (!chambre.isPresent()) {
				uuidsNonTrouvees.add(uuid);
			}
		}
		return uuidsNonTrouvees;
	}

	/**
	 * méthode qui permet de vérifier les informations données en paramètre, et crée
	 * une réservation si les infos sont bonnes
	 * 
	 * @param dateDebut     : date de début de la réservation
	 * @param dateFin       : date de fin de la réservation
	 * @param clientUuid    : UUID du client qui fait la réservation
	 * @param chambresUuids : UUID des chambres réservées
	 * @return Une réservation
	 */
	@Transactional
	public Reservation ajouterResa(LocalDate dateDebut, LocalDate dateFin, UUID clientUuid, List<UUID> chambresUuids) {
		Client client = cliService.findByUuid(clientUuid)
				.orElseThrow(() -> new ReservationException("uuid client non trouvé"));
		List<Chambre> listeChambre = new ArrayList<>();
		for (UUID uuid : chambresUuids) {
			Chambre chambre = chaService.findById(uuid)
					.orElseThrow(() -> new ReservationException("La chambre " + uuid + " n'existe pas"));
			listeChambre.add(chambre);
		}
		Reservation resa = new Reservation();
		resa.setDateDebut(dateDebut);
		resa.setDateFin(dateFin);
		resa.setClient(client);
		resa.setChambres(listeChambre);
		return resRepo.save(resa);
	}
}
