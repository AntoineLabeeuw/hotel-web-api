package dev.hotel.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.dto.ReservationDto;
import dev.hotel.entite.Reservation;
import dev.hotel.service.ReservationService;

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

	@PostMapping
	public ResponseEntity<?> creerReservation(@RequestBody @Valid ReservationDto resa, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tous les champs doivent etre valorises.");
		}
		if(!resService.clientPresentBdd(resa.getUuidClient())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouve.");
		} else if(!resService.chambresPresentes(resa.getUuidChambres()).isEmpty()) {
			// a modifier pour avoir les id des chambres
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La (Les) chambres n'existent pas.");
		}
		Reservation reservation = resService.creerReservation(resa.getDateDebut(), resa.getDateFin(),
				resa.getUuidClient(), resa.getUuidChambres());
		return ResponseEntity.status(HttpStatus.OK).body(reservation);
	}
}
