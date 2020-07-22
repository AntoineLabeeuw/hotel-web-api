package dev.hotel.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.dto.ReservationDto;
import dev.hotel.entite.Reservation;
import dev.hotel.exception.ReservationException;
import dev.hotel.service.ReservationService;

/**
 * Classe controller des réservations
 * 
 * @author antoinelabeeuw
 *
 */
@RestController
@RequestMapping("reservations")
public class ReservationController {
	private ReservationService resService;

	/**
	 * Constructeur
	 * 
	 * @param resService
	 */
	public ReservationController(ReservationService resService) {
		super();
		this.resService = resService;
	}

	/**
	 * Transforme une exception en ResponseEntity<br/>
	 * Permet d'intercepter les exceptions non catchées
	 * 
	 * @param ex : l'exception
	 * @return : une ResponseEntity contenant l'exception
	 */
	@ExceptionHandler(ReservationException.class)
	public ResponseEntity<String> onReservationException(ReservationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	/**
	 * méthode qui permet de récupérer les infos à partir d'un POST et d'un body
	 * JSON
	 * 
	 * @param resa   : les infos minimales pour créer une réservation
	 * @param result
	 * @return une réponse Http
	 */
	@PostMapping
	public ResponseEntity<?> creerReservation(@RequestBody @Valid ReservationDto resa, BindingResult result) {
		// Validation des données POST
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tous les champs doivent etre valorises.");
		}
		Reservation reservation = resService.ajouterResa(resa.getDateDebut(), resa.getDateFin(), resa.getUuidClient(),
				resa.getUuidChambres());
		String body = "Réservation bien créée sous l'id " + reservation.getUuid() + "\nDébut\t:"
				+ reservation.getDateDebut() + "\nFin\t:" + reservation.getDateFin();
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}

}
